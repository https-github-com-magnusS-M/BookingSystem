/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.admin;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.sql.*;
import movieticketbooking.user.Home;

public class AddMovie extends javax.swing.JFrame {

    /**
     * Creates new form AddMovie
     */
     String imageName="";
     String videoName="";
    public AddMovie() {
        initComponents();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        UploadBtn = new javax.swing.JButton();
        BackBtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Movie name");

        jLabel2.setText("Director");

        jLabel3.setText("Description");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("          IMAGE");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextArea1InputMethodTextChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        UploadBtn.setBackground(new java.awt.Color(204, 204, 204));
        UploadBtn.setText("Choose poster for movie");
        UploadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadBtnActionPerformed(evt);
            }
        });

        BackBtn.setText("Back");
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        AddBtn.setBackground(new java.awt.Color(102, 102, 255));
        AddBtn.setText("Add");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Add Movie");

        jButton1.setText("Add trailer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(UploadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackBtn)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addComponent(UploadBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BackBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(AddBtn)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UploadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadBtnActionPerformed
    //upload image 
    JFileChooser file=new JFileChooser();
    file.setCurrentDirectory(new File(System.getProperty("user.dir")));
    FileNameExtensionFilter filter=new FileNameExtensionFilter("*.Images","jpg","gif","png");
    file.addChoosableFileFilter(filter);
    int result=file.showSaveDialog(null);
    if(result==JFileChooser.APPROVE_OPTION){
	File selectedFile=file.getSelectedFile();
	String path=selectedFile.getName();
        imageName=path;
        System.out.println(imageName);
	jLabel4.setIcon(ResizeImage(jLabel4,path));
    }
    else if(result==JFileChooser.CANCEL_OPTION){
	System.out.println("eroorr");
    }
    }//GEN-LAST:event_UploadBtnActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // return home frame
        this.setVisible(false);
         manage manage=new manage();
         manage.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_BackBtnActionPerformed
    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        String name=jTextField1.getText();
        String director=jTextField2.getText();
        String description=jTextArea1.getText();
        boolean flag=true;
        if(name.isEmpty()){
            flag=false;
            JOptionPane.showMessageDialog(rootPane, "Movie name cannot be blank", "Error",  JOptionPane.ERROR_MESSAGE);
        }
//        if(director.isEmpty()){
//            flag=false;
//            JOptionPane.showMessageDialog(rootPane, "Director cannot be blank", "Error",  JOptionPane.ERROR_MESSAGE);
//        }
//        if(description.isEmpty()){
//            flag=false;
//            JOptionPane.showMessageDialog(rootPane, "Description cannot be blank", "Error",  JOptionPane.ERROR_MESSAGE);
//        }
        if(imageName.isEmpty()){
            flag=false;
            JOptionPane.showMessageDialog(rootPane, "Image hasn't been upload", "Error",  JOptionPane.ERROR_MESSAGE);
        }
        if(videoName.isEmpty()){
            flag=false;
            JOptionPane.showMessageDialog(rootPane, "Video hasn't been upload", "Error",  JOptionPane.ERROR_MESSAGE);
        }
        if(flag){
            try
     {
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieTicketBooking?useUnicode=true&characterEncoding=UTF-8","root","");
        PreparedStatement ps=con.prepareStatement("insert into movie(name,director,poster,description,trailer) values(?,?,?,?,?)");
        // get data input from fields
        
        // set data upload database
        ps.setString(1,name);
        ps.setString(2,director);
        ps.setString(3,imageName);
        ps.setString(4,description);
        ps.setString(5, videoName);
        ps.executeUpdate();
        // return to home frame
        JOptionPane.showMessageDialog(this, "Add movie success");
        this.setVisible(false);
        Home frame=new Home();
        frame.setVisible(true);
     }
    catch(Exception ex)
    {
        ImageIcon iconic=new ImageIcon("error.png");
	JOptionPane.showMessageDialog(this, "Add movie fail.you must fill full the information!"," error!",JOptionPane.INFORMATION_MESSAGE,iconic);
    }        
        }
    
    }//GEN-LAST:event_AddBtnActionPerformed

    private void jTextArea1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextArea1InputMethodTextChanged
    }//GEN-LAST:event_jTextArea1InputMethodTextChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         JFileChooser file=new JFileChooser();
    file.setCurrentDirectory(new File(System.getProperty("user.dir")));
    FileNameExtensionFilter filter=new FileNameExtensionFilter("*.Images","jpg","gif","png");
    file.addChoosableFileFilter(filter);
    int result=file.showSaveDialog(null);
    if(result==JFileChooser.APPROVE_OPTION){
	File selectedFile=file.getSelectedFile();
	String path=selectedFile.getName();
        videoName=path;
        System.out.println(videoName);
	
    }
    else if(result==JFileChooser.CANCEL_OPTION){
	System.out.println("eroorr");
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    public ImageIcon ResizeImage(JLabel j,String ImagePath){
        // resize image fit to jlabel 
        ImageIcon MyImage = new ImageIcon(getClass().getResource("/resources/"+ImagePath));
        Image img=MyImage.getImage();
        Image newImg=img.getScaledInstance(j.getWidth(), j.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(newImg);
        return image;
    }
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
            java.util.logging.Logger.getLogger(AddMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddMovie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddMovie().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton UploadBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
