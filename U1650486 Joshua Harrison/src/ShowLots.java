import net.jini.space.JavaSpace;
import javax.swing.*;
import java.awt.*;


public class ShowLots extends JFrame {

    private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds

    private JavaSpace space;
    private JTextArea lotList;

    public ShowLots() {
        space = SpaceUtils.getSpace();
        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }

        initComponents ();
        pack ();
        setVisible(true);
        processLots();
    }

    private void initComponents () {
        setTitle ("Lots");
        addWindowListener (new java.awt.event.WindowAdapter () {
            public void windowClosing (java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        }   );

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout());

        lotList = new JTextArea(30,30);
        jPanel1.add(lotList);
        lotList.setEditable(false);

        cp.add(jPanel1,"Center");
    }

    private void processLots(){
        while(true){
            try {
                listUser qiTemplate = new listUser();
                listUser nextLot = (listUser)space.take(qiTemplate,null, TWO_SECONDS);
                if (nextLot == null) {
                    // no lot was found, so sleep for a couple of seconds and try again
                    Thread.sleep(TWO_SECONDS);
                } else {
                    // we have a job to process
                    int nextLotNumber = nextLot.lotNumber;
                    String nextLotName = nextLot.lotName;
                    String nextLotDesc = nextLot.lotDesc;
                    String nextLotBuyNow = nextLot.lotBuyNow;
                    lotList.append("Lot Number: " + nextLotNumber  + "\n" + "Lot Name: " + nextLotName + "\n" + "Lot Description: " + nextLotDesc + "\n" + "Buy Now Price: " + nextLotBuyNow + "\n" + "\n");
                }
            }  catch ( Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ShowLots();
    }
}
