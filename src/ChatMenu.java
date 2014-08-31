import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
/*
 * Created by JFormDesigner on Tue Aug 26 19:17:53 EDT 2014
 */



/**
 * @author Jurko Guba
 */
public class ChatMenu extends JFrame implements Runnable{
    Socket socket = LoginMenu.socket;
    BufferedReader reader = LoginMenu.reader;
    PrintWriter writer = LoginMenu.writer;

    String username = LoginMenu.username;
    int usersOnline = 0;

    ArrayList<String> users = new ArrayList<String>();


    public ChatMenu(String s) throws IOException {
        initComponents();
        label1.setText(s);

        // Sending connect message
        sendMessage("Connect/"+username);

    }

    public void sendMessage(String s) {
        writer.println(s);
        writer.flush();
    }

    public void addUser(String name) {
        users.add(name);
        textArea2.setText("");
        for (String s : users) {
            textArea2.append(s + '\n');
        }
    }

    public void removeUser(String name) {
        users.remove(name);
        textArea2.setText("");
        for (String s : users) {
            textArea2.append(s + '\n');
        }
    }

    private void sendButtonPressed(ActionEvent e) {
        // TODO add your code here
        if (!textField1.getText().equals("")) {
            sendMessage("Chat/" + username + ": " + textField1.getText());
            textField1.setText("");
        }

    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                String data[] = message.split("/");
                System.out.println(message);
                if (data[0].equals("Connect")) {
                    label2.setText("Online: " + ++usersOnline);
                    addUser(data[1]);

                } else if (data[0].equals("Disconnect")) {
                    label2.setText("Online: "+ --usersOnline);
                    removeUser(data[1]);
                } else if (data[0].equals("Chat")) {
                    textArea1.append(data[1] + "\n");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void okButtonActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jurko Guba
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();
        textField1 = new JTextField();
        okButton = new JButton();

        //======== this ========
        setResizable(false);
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
                    "292dlu, $lcgap, 57dlu, 0dlu",
                    "default, $lgap, 221dlu, $lgap, 18dlu"));

                //---- label1 ----
                label1.setText("Chat room");
                contentPanel.add(label1, CC.xy(1, 1));

                //---- label2 ----
                label2.setText("Online: 0");
                contentPanel.add(label2, CC.xy(3, 1));

                //======== scrollPane1 ========
                {

                    //---- textArea1 ----
                    textArea1.setEditable(false);
                    scrollPane1.setViewportView(textArea1);
                }
                contentPanel.add(scrollPane1, CC.xywh(1, 3, 1, 2));

                //======== scrollPane2 ========
                {

                    //---- textArea2 ----
                    textArea2.setEditable(false);
                    scrollPane2.setViewportView(textArea2);
                }
                contentPanel.add(scrollPane2, CC.xywh(3, 3, 2, 2));
                contentPanel.add(textField1, CC.xy(1, 5));

                //---- okButton ----
                okButton.setText("Send");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendButtonPressed(e);
                        okButtonActionPerformed(e);
                        sendButtonPressed(e);
                    }
                });
                contentPanel.add(okButton, CC.xywh(3, 5, 2, 1));
            }
            dialogPane.add(contentPanel, BorderLayout.SOUTH);
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
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    private JTextField textField1;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
