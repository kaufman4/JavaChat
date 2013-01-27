
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


public class Client extends javax.swing.JFrame {

    BufferedReader sockIn;
    BufferedWriter sockOut;
    Socket so;
    int port;
    String host;

    //Konstruktor 
    public Client() {
        //Erstellt die Komponenten, deren Eigenschaften, Eventlistener, Layout,..
        initComponents();
    }

    public class Empfangen implements Runnable {

        public void run() {
            String nachricht;
            try {
                while ((nachricht = sockIn.readLine()) != null) {
                    empfangenArea.append(nachricht + "\n");
                }
            } catch (IOException ex) {
            }
        }
    }

    //Erstellt einen neuen Client
    public static void main(String args[]) {
        Client client = new Client();
        client.setVisible(true);
        client.los();
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

                    //  javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    public void los() {
        hostField.setText("localhost");
        portField.setText("8080");
        //Aktiviert/Deaktiviert gewisse Buttons und Felder
        ButtonsNichtVerbunden();
    }

    //Stellt die Verbindung zum Server her
    private void Connect() {
        try {
            //Nickname muss aus Buchstaben und/oder Zahlen bestehen
            if (nicknameField.getText().matches("[a-zA-Z0-9]+")) {

                host = hostField.getText();
                port = Integer.parseInt(portField.getText());

                //Verbindung mit Server aufnehmen
                so = new Socket("localhost", port);

                //char-streams als Reader/Writer oeffnen
                InputStreamReader streamReader = new InputStreamReader(so.getInputStream());
                sockIn = new BufferedReader(streamReader);
                sockOut = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
                //Aktiviert/Deaktiviert gewisse Buttons und Felder
                ButtonsVerbunden();
                //Erstellt einen neuen Thread (jeder Client hat einen eigenen) der auf Nachrichten vom Server wartet
                Thread reader = new Thread(new Empfangen());
                //startet run()
                reader.start();
            } else {
                JOptionPane.showMessageDialog(null, "Bitte geben sie einen gültigen Nicknamen ein!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Verbindung konnte nicht hergestellt werden!\n" + e);
            //Aktiviert/Deaktiviert gewisse Buttons und Felder
            ButtonsNichtVerbunden();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Bitte geben sie einen gültigen Port ein!\n" + e);
            //Aktiviert/Deaktiviert gewisse Buttons und Felder
            ButtonsNichtVerbunden();
        }
    }

    //sendet eine Nachricht an den Server
    private void sendeNachricht() {
        try {
            if (gesendetField.getText().length() != 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss ");
                Date currentTime = new Date();
                // Nachricht formatieren (Nickname - Uhrzeit: Nachricht) und in den Socket
                sockOut.write(nicknameField.getText() + " - " + formatter.format(currentTime) + ": " + gesendetField.getText() + "\n");
                //puffer leeren (sofort ausgeben)
                sockOut.flush();
            } else {
                JOptionPane.showMessageDialog(null, "Bitte geben sie eine Nachricht ein!\n");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mitteilung konnte nicht gesendet werden \n" + e);
            //Aktiviert/Deaktiviert gewisse Buttons und Felder
            ButtonsNichtVerbunden();
        }
        //Eingabefeld leeren
        gesendetField.setText("");
        //Focus wieder auf Eingabefeld
        gesendetField.requestFocus();
    }

    private void ButtonsVerbunden() {
        trennenButton.setEnabled(true);
        statusLabel.setForeground(Color.green);
        statusLabel.setText("ONLINE");
        nicknameField.setEnabled(false);
        verbindenButton.setEnabled(false);
        gesendetField.setEnabled(true);
        empfangenArea.setEnabled(true);
        sendenButton.setEnabled(true);
        hostField.setEnabled(false);
        portField.setEnabled(false);
    }

    private void ButtonsNichtVerbunden() {
        trennenButton.setEnabled(false);
        statusLabel.setForeground(Color.red);
        statusLabel.setText("OFFLINE");
        nicknameField.setEnabled(true);
        verbindenButton.setEnabled(true);
        gesendetField.setEnabled(false);
        empfangenArea.setEnabled(false);
        sendenButton.setEnabled(false);
        hostField.setEnabled(true);
        portField.setEnabled(true);
    }

    //Erstellt die Komponenten, deren Eigenschaften, Eventlistener, Layout,..
    //Automatisch erstellt mit Swing und JFrame
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        empfangenArea = new javax.swing.JTextArea();
        gesendetField = new javax.swing.JTextField();
        sendenButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nicknameField = new javax.swing.JTextField();
        verbindenButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        hostField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        trennenButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaChat");

        empfangenArea.setColumns(20);
        empfangenArea.setRows(5);
        jScrollPane1.setViewportView(empfangenArea);

        gesendetField.addKeyListener(new java.awt.event.KeyAdapter() {

            public void keyPressed(java.awt.event.KeyEvent evt) {
                gesendetFieldKeyPressed(evt);
            }
        });

        sendenButton.setText("Senden");
        sendenButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendenButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Nickname");

        verbindenButton.setText("Verbinden");
        verbindenButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verbindenButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Host");

        jLabel3.setText("Port");

        jLabel4.setText("Status");

        statusLabel.setText("Offline");

        trennenButton.setText("Trennen");
        trennenButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trennenButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(nicknameField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(verbindenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(trennenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(statusLabel)).addComponent(jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(gesendetField).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(sendenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(nicknameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(verbindenButton).addComponent(jLabel2).addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel3).addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel4).addComponent(statusLabel).addComponent(trennenButton)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(gesendetField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(sendenButton)).addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Event bei Klick auf SendenButton
    private void sendenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendenButtonActionPerformed
        sendeNachricht();
    }//GEN-LAST:event_sendenButtonActionPerformed

    //Event bei Klick auf Verbindenbutton
    private void verbindenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verbindenButtonActionPerformed
        Connect();
    }//GEN-LAST:event_verbindenButtonActionPerformed

    //Nachricht wird auch bei drücken der Enter Taste abgeschickt (bzw. versucht)
    private void gesendetFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gesendetFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendeNachricht();
        }
    }//GEN-LAST:event_gesendetFieldKeyPressed

    //Verbindung wird getrennt bei Klick auf Trennen Button
    //Sinnvoll falls mehrere Server laufen und man die Verbindung wechseln will
    private void trennenButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trennenButtonActionPerformed
        try {
            //Socket schließen
            so.close();
            ButtonsNichtVerbunden();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Trennen der Verbindung!\n" + e);
        }
    }//GEN-LAST:event_trennenButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea empfangenArea;
    private javax.swing.JTextField gesendetField;
    private javax.swing.JTextField hostField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nicknameField;
    private javax.swing.JTextField portField;
    private javax.swing.JButton sendenButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton trennenButton;
    private javax.swing.JButton verbindenButton;
    // End of variables declaration//GEN-END:variables
}
