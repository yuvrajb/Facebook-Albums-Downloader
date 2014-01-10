/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package design;

import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class FileChooser extends javax.swing.JFrame {
    /* properties */
    public static String Path = null;
    
    /**
     * Creates new form FileChooser
     */
    public FileChooser() {
        initComponents();
        design();
    }
    
    public void design(){
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(400, 500);
        try{
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/files.png")));
        }
        catch(Exception e){ }
        getPath();
    }

    public void getPath(){
        File File = null;
        File = jFileChooser1.getCurrentDirectory();
        Path = File.getPath();
        int result = jFileChooser1.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File = jFileChooser1.getSelectedFile();
            Path = File.getPath();
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

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Destination Folder");

        jFileChooser1.setDialogTitle("Select download destination");
        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });
        jFileChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jFileChooser1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFileChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jFileChooser1PropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_jFileChooser1PropertyChange

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}