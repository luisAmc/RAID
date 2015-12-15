/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raid;

import com.sun.glass.events.KeyEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Alberto Pejuan
 */
public class Consola extends javax.swing.JFrame {

    /**
     * Creates new form Consola
     */
    public Consola() {
        initComponents();
        jp_console.setText("\nShell>");
        antesDeBorrable = jp_console.getText();
        pastCommands = new ArrayList();
        host = "192.168.56.101";
        port = 1099;
        try {
            registry = LocateRegistry.getRegistry(host, port);
            stub = (ServerNode) registry.lookup("ServerNode");

            System.out.println("Conexión hecha.");
        } catch (Exception e) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jp_console = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jp_console.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jp_consoleMouseClicked(evt);
            }
        });
        jp_console.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jp_consoleKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jp_console);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jp_consoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jp_consoleMouseClicked
        // TODO add your handling code here:
        evt.consume();
        jp_console.setCaretPosition(jp_console.getText().length());
    }//GEN-LAST:event_jp_consoleMouseClicked

    private void jp_consoleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jp_consoleKeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == 17) {    // Esta presionado ctrl
            ctrlPressed = true;
        }

        if (ctrlPressed && evt.getKeyCode() != KeyEvent.VK_D && evt.getKeyCode() != 17) {
            evt.consume();
            ctrlPressed = false;// Se hace falso porque no es ctrl-d
        }
        if (escribiendoContenido) {
            if (evt.getKeyCode() == KeyEvent.VK_D && ctrlPressed) {
                System.out.println("Nombre archivo:" + nombreArchivo);
                System.out.println("Contenido:" + contenido);
                try {
                    stub.saveFile(nombreArchivo, contenido);
                } catch (RemoteException ex) {
                    Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
                }
                contenido = "";
                escribiendoContenido = false;
                jp_console.setText(jp_console.getText() + "\nShell>");

            } else {
                contenido += evt.getKeyChar();
            }

        } else if (evt.getKeyCode() == 10) {//Enter

            evt.consume();
            String[] losSplits;
            borrable.toLowerCase();
            if (borrable.startsWith("clear")) {
                jp_console.setText("");
            } else if (borrable.startsWith("rmdir ")) {
                
                jp_console.setText(jp_console.getText() + "\nSe ejecuto rmdir");
            } else if (borrable.startsWith("rm ")) {
                jp_console.setText(jp_console.getText() + "\nSe ejecuto rm");
            } else if (borrable.equals("ls")) {
                jp_console.setText(jp_console.getText() + "\nSe ejecuto ls");
            } else if (borrable.startsWith("cat > ")) {
                borrable = borrable.replace("cat > ", "");
                contenido = "";
                nombreArchivo = borrable;
                escribiendoContenido = true;
                jp_console.setText(jp_console.getText() + "\n");
            } else if (borrable.startsWith("cat> ")) {
                borrable = borrable.replace("cat> ", "");
                contenido = "";
                nombreArchivo = borrable;
                escribiendoContenido = true;
                jp_console.setText(jp_console.getText() + "\n");

            } else if (borrable.startsWith("mkdir ")) {
                losSplits = borrable.split(" ");
                try {
                    stub.createFolder(losSplits[1]);
                } catch (RemoteException ex) {
                    Logger.getLogger(Consola.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                jp_console.setText(jp_console.getText() + "\nCOMMAND NOT FOUND");
            }

            borrable = "";
            if (escribiendoContenido) {

            } else {
                jp_console.setText(jp_console.getText() + "\nShell>");
            }

        } else {
            final int key = evt.getKeyCode();

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {

                evt.consume();
            } else if (!((evt.isActionKey() || (evt.getKeyCode() == 16) || (evt.getKeyCode() == 8) || (evt.getKeyCode() == 17))) && !(evt.isAltDown() || evt.isControlDown())) {
                if (borrable.length() >= 0) {
                    borrable += evt.getKeyChar();
                }
            }
            if (evt.getKeyCode() == 8) {
                if (borrable.length() > 0) {
                    borrable = borrable.substring(0, borrable.length() - 1);
                } else {
                    borrable = "";
                    evt.consume();
                }
            }
            System.out.println(borrable);
        }

    }//GEN-LAST:event_jp_consoleKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Consola.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Consola().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jp_console;
    // End of variables declaration//GEN-END:variables
    String borrable = "";
    String antesDeBorrable = "\nShell>";
    ArrayList<String> pastCommands;
    boolean escribiendoContenido = false;
    boolean ctrlPressed = false;
    String nombreArchivo = "";
    String contenido = "";
    String host;
    int port;
    Registry registry;
    ServerNode stub;
}
