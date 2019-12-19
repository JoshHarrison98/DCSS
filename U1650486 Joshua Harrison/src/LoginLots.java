import net.jini.core.lease.Lease;
import net.jini.space.*;

import java.awt.*;
import javax.swing.*;


public class LoginLots extends JFrame {
    private static final long TWO_SECONDS = 2 * 1000;  // two thousand milliseconds
    private static final long TWO_MINUTES = 2 * 1000 * 60;

    private JavaSpace space;
    private JTextField usernameIn, passwordIn;

    public LoginLots() {

        space = SpaceUtils.getSpace();
        if (space == null) {
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }

        initComponents();
        pack();
    }

    private void initComponents() {
        setTitle("Login Or Register");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login();
            }
        });
        buttonPanel.add(loginButton, BorderLayout.CENTER);

        JButton registerButton = new JButton();
        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                register();
            }
        });
        buttonPanel.add(registerButton, BorderLayout.CENTER);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout());

        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        loginPanel.add(usernameLabel);

        usernameIn = new JTextField(12);
        usernameIn.setText("");
        loginPanel.add(usernameIn);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password: ");
        loginPanel.add(passwordLabel);

        passwordIn = new JPasswordField(12);
        passwordIn.setText("");
        loginPanel.add(passwordIn);

        cp.add(buttonPanel, BorderLayout.CENTER);
        cp.add(loginPanel, BorderLayout.PAGE_START);
    }

    private void register() {

        try {
            LotStatus qsTemplate = new LotStatus();
            LotStatus qStatus = (LotStatus) space.take(qsTemplate, null, TWO_SECONDS);

            // if there is no QueueStatus object in the space then we can't do much, so print an error and exit
            if (qStatus == null) {
                System.out.println("No LotStatus object found.  Has 'StartLots' been run?");
                System.exit(1);
            }

            String username = usernameIn.getText();
            String password = passwordIn.getText();
            listUsers newUser = new listUsers(username, password);
            space.write(newUser, null, Lease.FOREVER);
            listUsers tempUser = (listUsers)space.readIfExists(newUser, null, TWO_SECONDS);
            if (tempUser == null){
                System.out.println("Fail");
            } else {
                System.out.println(newUser.username);
                System.out.println(newUser.password);
//                space.write(qsTemplate, null, Lease.FOREVER);
//                qStatus.addUser();
                space.write(qStatus, null, Lease.FOREVER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void login () {
            try {
                LotStatus qsTemplate = new LotStatus();
                LotStatus qStatus = (LotStatus) space.take(qsTemplate, null, TWO_SECONDS);

                // if there is no QueueStatus object in the space then we can't do much, so print an error and exit
                if (qStatus == null) {
                    System.out.println("No LotStatus object found.  Has 'StartLots' been run?");
                    System.exit(1);
                }

                String username = usernameIn.getText();
                String password = passwordIn.getText();
                checkUser user = new checkUser(username,password);
                listUsers tempUser = (listUsers)space.readIfExists(user, null, TWO_SECONDS);
                if (tempUser == null){
                    System.out.println("Fail");
                } else {
//                    space.write(qsTemplate, null, TWO_SECONDS);
//                    System.out.println(user.username);
//                    System.out.println(user.password);
                    System.out.println(tempUser);
                    space.write(qStatus, null, TWO_SECONDS);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void main (String[]args){
            new LoginLots().setVisible(true);
        }
    }

