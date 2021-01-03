/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BookSeat extends javax.swing.JFrame {

    /**
     * Creates new form BookSeat
     */
        Connection conn;
        private Vector<Seat>id_seats=new Vector<Seat>();
        
        private JPanel p = new JPanel();
        private JPanel q = new JPanel();
        private int removedSeat[][];
        private int maxColumn;
        private int maxRow;
        private int id_user=0;
        
        
      
        private int bookedSeat[][];
        int id_showcalendar=0;
        int id_timer=0;
        int idRoom=0;
        int roomNumber=0;
    public BookSeat() {
      
        
        
    }
    public BookSeat(int id,int id2){
        initComponents();
        id_showcalendar=id;
        id_user=id2;
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        getGeneralInfor(); // include id_room,id_timer;
         // init arrays
         removedSeatInit();
         bookedSeatInit();
          cleanSeat();
         // render view
          System.out.println(idRoom);
         jLabel1.setIcon(ResizeImage(jLabel1, "screen.png"));
         jLabel2.setText("Room:"+Integer.toString(roomNumber));
         bookingButton();
         renderSeat();
    }
    private void bookedSeatInit(){
        bookedSeat=new int[maxRow][maxColumn];
          for(int i=0;i<maxRow;i++)
          {
               for(int j=0;j<maxColumn;j++)
               {
                   bookedSeat[i][j]=0;
               }
          }
    }
    private void removedSeatInit(){
         try{
         Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieTicketBooking","root","");
         PreparedStatement ps=con.prepareStatement("select * from rooom where id like ?;");
         ps.setInt(1,idRoom);
         ResultSet r0=ps.executeQuery();
         while(r0.next()){
            roomNumber=r0.getInt("number");
            maxRow=r0.getInt("rowNums");
            maxColumn=r0.getInt("colNums");
         }
        }
        catch(Exception ex){
	ex.printStackTrace();
        }
          removedSeat=new int[maxRow][maxColumn];
          for(int i=0;i< maxRow;i++)
          {
               for(int j=0;j< maxColumn;j++)
               {
                   removedSeat[i][j]=1;
               }
          }
        try{
         Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieTicketBooking","root","");
         PreparedStatement ps=con.prepareStatement("select * from removedseats where id_room like ?;");
         ps.setInt(1,idRoom);
         ResultSet r0=ps.executeQuery();
         while(r0.next()){
             int i=r0.getInt("rowIndex");
             int j=r0.getInt("colIndex");
             removedSeat[i][j]=0;
         }
    }
     catch(Exception ex){
	ex.printStackTrace();
        }
    }
    
     private void bookingButton(){
        JButton btnBooking=new JButton("Booking");
        int btnWidth=100;
        int btnHeight=30;
        int xLocation=(getContentPane().getWidth()/2)-(btnWidth/2);
        int yLocation=(getContentPane().getHeight()-btnHeight-10);
        btnBooking.setBounds(xLocation,yLocation, btnWidth, btnHeight);
        btnBooking.addActionListener(new ActionListener() {
            @Override
        public void actionPerformed(ActionEvent e) {
        int id_bill=0;
        try{
            // init bill data
	conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticketbooking","root","");
	PreparedStatement q=conn.prepareStatement("insert into bill(id_showcalendar,quantity,total,id_user) value(?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
        q.setInt(1,(id_showcalendar));
        q.setInt(2,id_seats.size());
        q.setDouble(3,id_seats.size()*70000);
        q.setInt(4, id_user);
        q.executeUpdate();
        ResultSet lastid = q.getGeneratedKeys();
        lastid.next();
        id_bill=lastid.getInt(1);
              for(Seat number:id_seats)
              {
               System.out.println(number.getNumber());
              q=conn.prepareStatement("insert into seat(number,id_room,id_timer,id_bill,rowIndex,colIndex) value(?,?,?,?,?,?) ;");
              q.setString(1,number.getNumber());
              q.setInt(2,idRoom);
              q.setInt(3,id_timer);
              q.setInt(4,id_bill);
              q.setInt(5,number.getRow());
              q.setInt(6,number.getCol());
              q.executeUpdate();
            }
	}catch(Exception ex)
	{
		System.out.println("Connect fail");
	}
        setVisible(false);
        BookSuccess bs=new BookSuccess(id_bill);
        bs.setVisible(true);
         }

          
           
         });
   
        this.add(btnBooking);
    }
     public void cleanSeat(){
         try{
             Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieTicketBooking","root","");
             PreparedStatement ps;
             ps=con.prepareStatement("select * from seat where id_room like ? and id_timer like ?;");
             ps.setInt(1,idRoom);
             ps.setInt(2,id_timer);
             ResultSet r1=ps.executeQuery();
              while(r1.next()){
                     int i=r1.getInt("rowIndex");
                     int j=r1.getInt("colIndex");
                     bookedSeat[i][j]=1;
              }
         }
         catch(Exception e){
             
         }
           
     }
    public void getGeneralInfor(){
       
          try{
         Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieTicketBooking","root","");
         PreparedStatement ps=con.prepareStatement("select * from showcalendar where id like ?;");
         ps.setInt(1,id_showcalendar); // fix that
         ResultSet r0=ps.executeQuery();
            while(r0.next()){
             int id_room=r0.getInt("id_room");
             idRoom=id_room; // fix that
             id_timer=r0.getInt("id_timer");
           
         }
        }
    catch(Exception ex){
	ex.printStackTrace();
        }
    }
    public void removeIdSeat(Seat s){
       for(int i=0; i<id_seats.size(); i++){
          Seat target = id_seats.get(i);
           if(target.getRow()==s.getRow())
           {
               if(target.getCol()==s.getCol())
               {
               id_seats.remove(i); // will throw CME
           }
       }
     }
    }
    public void renderSeat(){
        //get room and get removedSeat
       
        //handle view
        final JButton btn[][]=new JButton[maxRow][maxColumn];
        p.setBounds(40, 160, 900, 450);
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        this.add(p);
        GridLayout g=new GridLayout(maxRow,maxColumn,4,2);
        p.setLayout(g);
        int asciiCode=65;
        for(int i = 0; i < maxRow; i++) {
            char character=(char)asciiCode;
            for(int j = 0; j < maxColumn; j++) {
            int jplus=j+1;    
            String txt=Character.toString(character)+jplus;    
            btn[i][j]=new JButton(txt);
            btn[i][j].setFont((new Font("Arial", Font.PLAIN, 10)));
             btn[i][j].setMargin(new Insets(0,0,0,0));
            final JButton b=btn[i][j];
            Seat tmp=new Seat(txt,i,j);
            final int I=i;
            final int J=j;
            btn[i][j].addActionListener(new ActionListener() {
            private int clickCount=1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clickCount%2!=0){
                      b.setBackground(Color.green);
                      id_seats.add(tmp);
                }
                else{
                    //hanlde view
                    b.setBackground(jButton1.getBackground());
                     removeIdSeat(tmp);
                    //controller
                }
                clickCount++;
             }
             });
             if(removedSeat[i][j]==0){
                     btn[i][j].setVisible(false);
             }
             else if(bookedSeat[i][j]==1){
                    btn[i][j].setEnabled(false);
             }
            p.add(btn[i][j]);
                  }
            asciiCode++;
          }
         revalidate();
         p.validate();
    }
 public ImageIcon ResizeImage(JLabel j,String ImagePath){
        // resize image fit to jlabel 
        ImageIcon MyImage = new ImageIcon(getClass().getResource("/resources/"+ImagePath));
        Image img=MyImage.getImage();
        Image newImg=img.getScaledInstance(j.getWidth(), j.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(newImg);
        return image;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MovieTicketBooking");
        setPreferredSize(new java.awt.Dimension(1000, 700));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(507, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        Booking bk=new Booking();
        bk.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

       
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
            java.util.logging.Logger.getLogger(BookSeat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookSeat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookSeat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookSeat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookSeat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
