import net.jini.space.*;
import net.jini.core.lease.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class AddLots extends JFrame {

	private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds
	private static final long TWO_MINUTES = 2 * 1000 * 60;

	private JavaSpace space;

	private JTextField lotNameIn, lotNumberOut, lotDescIn, lotBuyNowIn;


	public AddLots() {
		space = SpaceUtils.getSpace();
		if (space == null){
			System.err.println("Failed to find the javaspace");
			System.exit(1);
		}

		initComponents ();
		pack ();
	}

	private void initComponents () {
		setTitle ("Add Lots");
		addWindowListener (new WindowAdapter () {
			public void windowClosing (WindowEvent evt) {
				System.exit (0);
			}
		}   );

		Container cp = getContentPane();
		cp.setLayout (new BorderLayout ());

		JPanel jPanel1 = new JPanel();
		jPanel1.setLayout (new FlowLayout ());

		JLabel lotLabel = new JLabel();
		lotLabel.setText ("Name of Lot: ");
		jPanel1.add (lotLabel);

		lotNameIn = new JTextField (12);
		lotNameIn.setText ("");
		jPanel1.add (lotNameIn);

		JLabel lotDescLabel = new JLabel();
		lotDescLabel.setText("Description of Item: ");
		jPanel1.add(lotDescLabel);

		lotDescIn = new JTextField(12);
		lotDescIn.setText("");
		jPanel1.add(lotDescIn);

		JLabel lotNumberLabel = new JLabel();
		lotNumberLabel.setText ("Lot Number: ");
		jPanel1.add (lotNumberLabel);

		lotNumberOut = new JTextField (6);
		lotNumberOut.setText ("");
		lotNumberOut.setEditable(false);
		jPanel1.add (lotNumberOut);

		JPanel jPanelCentre = new JPanel();

        JLabel buyNowPrice = new JLabel();
        buyNowPrice.setText("Sell Now Price: ");
        jPanelCentre.add(buyNowPrice);

        lotBuyNowIn = new JTextField(12);
        lotBuyNowIn.setText("");
        jPanelCentre.add(lotBuyNowIn);

		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout (new FlowLayout ());

		JButton addLotButton = new JButton();
		addLotButton.setText("Add Lot");
		addLotButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent evt) {
				addlot();
			}
		}  );

		jPanel2.add(addLotButton);

        cp.add (jPanel1, "North");
        cp.add(jPanelCentre, "Center");
        cp.add (jPanel2, "South");
	}


	private void addlot(){
		try {
			LotStatus qsTemplate = new LotStatus();
			LotStatus qStatus = (LotStatus)space.take(qsTemplate,null, TWO_SECONDS);

			// if there is no QueueStatus object in the space then we can't do much, so print an error and exit
			if (qStatus == null){
				System.out.println("No LotStatus object found.  Has 'StartLots' been run?");
				System.exit(1);
			}

			// create the new LotItem, write it to the space, and update the GUI
			int lotNumber = qStatus.nextLot;
			String lotName = lotNameIn.getText();
			String lotDesc = lotDescIn.getText();
			String lotBuyNow = lotBuyNowIn.getText();
			listUser newLot = new listUser(lotNumber, lotName, lotDesc, lotBuyNow);
			space.write( newLot, null, TWO_MINUTES);
			lotNumberOut.setText(""+lotNumber);

			// update the QueueStatus object by incrementing the counter and write it back to the space
			qStatus.addlot();
			space.write( qStatus, null, Lease.FOREVER);
		}  catch ( Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new AddLots().setVisible(true);
	}
}

