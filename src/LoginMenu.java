import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/*
 * Created by JFormDesigner on Tue Aug 26 18:20:00 EDT 2014
 */

/**
 * @author Jurko Guba
 */
public class LoginMenu extends JFrame {
    static Socket socket;
    static BufferedReader reader;
    static PrintWriter writer;

    static String username, address;
    int port;

    JFrame main = this;

    public LoginMenu() {
        initComponents();
    }

    private void loginButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
        username = textField1.getText();
        address = textField3.getText();
        port = Integer.parseInt(textField4.getText());

        // Handle incorrect username
        if (textField1.getText().equals("") || textField3.getText().equals("") || textField4.getText().equals("") ) {
            label7.setText("Fill in all fields!");
        } else {
            try {
                System.out.println("Address: " + address + " Port: " + port);
                socket = new Socket(address, port);
                writer = new PrintWriter(socket.getOutputStream());

                // Sending Login message with delimiters
                writer.println(username);
                writer.flush();

                // Recieving a message back
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message, motd = "Unnamed Chat room!";


                while ((message = reader.readLine()) != null) {
                    String data[] = message.split("/");
                    if (data[0].equals("Success")) {
                        main.dispose();
                        motd = data[1];

                        break;
                    }
                }

                ChatMenu chatMenu = new ChatMenu(motd);
                chatMenu.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        try {
                            writer.println("Disconnect/"+username);
                            writer.flush();
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        System.exit(0);
                    }
                });
                new Thread(chatMenu).start();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void usernameActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void addressActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void portActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jurko Guba
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        label5 = new JLabel();
        textField3 = new JTextField();
        label6 = new JLabel();
        textField4 = new JTextField();
        buttonBar = new JPanel();
        label7 = new JLabel();
        okButton = new JButton();

        //======== this ========
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(Borders.createEmptyBorder("7dlu, 7dlu, 7dlu, 7dlu"));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new FormLayout(
                    "default, $lcgap, 137dlu",
                    "6*(default, $lgap), default"));

                //---- label1 ----
                label1.setText("Simple TCP/IP Chat v2.0");
                label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 8f));
                contentPanel.add(label1, CC.xywh(1, 1, 3, 1));

                //---- label2 ----
                label2.setText("Source code: http://github.com/jurkoguba");
                contentPanel.add(label2, CC.xywh(1, 3, 3, 1));

                //---- label3 ----
                label3.setText("Username: ");
                contentPanel.add(label3, CC.xy(1, 7));

                //---- textField1 ----
                textField1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        usernameActionPerformed(e);
                    }
                });
                contentPanel.add(textField1, CC.xy(3, 7));

                //---- label5 ----
                label5.setText("IP Address:");
                contentPanel.add(label5, CC.xy(1, 11));

                //---- textField3 ----
                textField3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addressActionPerformed(e);
                    }
                });
                contentPanel.add(textField3, CC.xy(3, 11));

                //---- label6 ----
                label6.setText("Port:");
                contentPanel.add(label6, CC.xy(1, 13));

                //---- textField4 ----
                textField4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        portActionPerformed(e);
                    }
                });
                contentPanel.add(textField4, CC.xy(3, 13));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(Borders.createEmptyBorder("5dlu, 0dlu, 0dlu, 0dlu"));
                buttonBar.setLayout(new FormLayout(
                    "4*($lcgap, default), $glue, $button, $rgap, $button, 3*($lcgap, default)",
                    "pref"));

                //---- label7 ----
                label7.setText("Status code: 0");
                buttonBar.add(label7, CC.xy(2, 1));

                //---- okButton ----
                okButton.setText("Login");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, CC.xywh(11, 1, 8, 1));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Jurko Guba
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JLabel label5;
    private JTextField textField3;
    private JLabel label6;
    private JTextField textField4;
    private JPanel buttonBar;
    private JLabel label7;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
