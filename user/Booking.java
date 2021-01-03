/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.user;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;
import javax.swing.ImageIcon;
import java.sql.*;
import java.util.Collections;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.net.InetAddress;
import java.net.UnknownHostException;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

public class Booking extends javax.swing.JFrame {
    Connection conn;
     Vector<String> keys=new Vector<>();
    Vector<DayHour> dhList=new Vector<>();
    Vector<Integer> movieList=new Vector<>();// show list of movie 
    int currentIndex=0;// save current position of movie in the Vector 
    // get form database
    String[] list={
        "captainmarvel.jpg",
        "mebeforeyou.jpg",
        "aquaman.jpg"
    };
     public void SetImageSize(String anh){
        ImageIcon MyImage = new ImageIcon(getClass().getResource("/resources/"+anh));
        Image img=MyImage.getImage();
        Image newImg=img.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(newImg);
        jLabel1.setIcon(image);
    }
    public Booking() {
        initComponents();
        // fix frame to center window    
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        initCustom();
        
     
        
    }
    public void SortHour(Vector<KeyValue> v){
        for(int i=0;i<v.size()-1;i++){
            for(int j=i+1;j<v.size();j++){
                String[] vi= v.elementAt(i).getValue().split("\\:");
                String[] vj = v.elementAt(j).getValue().split("\\:");
                int check=0;
                for(int k=0;k<2;k++){
                    if(Integer.parseInt(vj[k])<Integer.parseInt(vi[k])){
                        check=1;
                        break;
                    }
                    else if(Integer.parseInt(vj[k])>Integer.parseInt(vi[k])){
                         check=-1;
                        break;
                    }
                }
                if(check==1){
                    KeyValue tmp=v.elementAt(j);
                    v.setElementAt(v.elementAt(i), j);
                    v.setElementAt(tmp, i);
                }
//                
            }
        }
    }
    public void SortDay(Vector<String> v){
        for(int i=0;i<v.size()-1;i++){
            for(int j=i+1;j<v.size();j++){
                String[] vi= v.elementAt(i).split("\\/");
                String[] vj = v.elementAt(j).split("\\/");
                int check=0;
              
                for(int k=2;k>=0;k--){
                    if(Integer.parseInt(vj[k])<Integer.parseInt(vi[k])){
                        check=1;
                        break;
                    }
                    else if(Integer.parseInt(vj[k])>Integer.parseInt(vi[k])){
                         check=-1;
                        break;
                    }
                }
                if(check==1){
                    String tmp=v.elementAt(j);
                    v.setElementAt(v.elementAt(i), j);
                    v.setElementAt(tmp, i);
                }
//                
            }
        }
    }
    String movieName="",description="",poster="",director="",trailer="";
    
    public void renderBookingInterface(int id_movie){
        
         try{
	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticketbooking","root","");
         PreparedStatement st=conn.prepareStatement("Select * from movie where id like ?;");
         st.setString(1, Integer.toString(id_movie));
        ResultSet r0=st.executeQuery();
        //get data movie
        while(r0.next()){
            movieName=r0.getString("name");
            description=r0.getString("description");
            poster=r0.getString("poster");
            director=r0.getString("director");
            trailer=r0.getString("trailer");
        }
        String descriptionHtml=description;
         jTextArea1.setText(descriptionHtml);
         // change jtextarea for display movie name 
         jTextArea2.setText(movieName);
         jTextArea2.setLineWrap(true);
         jTextArea2.setOpaque(false);
         jTextArea2.setEditable(false);
         jTextArea2.setFocusable(false);
         jTextArea2.setBackground(jTextArea1.getBackground());
         jTextArea2.setBorder(null);
         jScrollPane2.setBorder(null);
         //
          jLabel7.setText(director);
         SetImageSize(poster);
         st=conn.prepareStatement("Select * from showcalendar where id_movie like ?;");
         st.setString(1,Integer.toString(id_movie));//----------------fix that-------------------//
        ResultSet rs=st.executeQuery();
        DayHour d=new DayHour();
        int i=0;
        while(rs.next()){
              int id_timer=rs.getInt("id_timer");
              st=conn.prepareStatement("Select * from timer where id like ? ;");
              st.setString(1,Integer.toString(id_timer));
              ResultSet r1=st.executeQuery();
                    r1.next();
                    String day=r1.getString("Date");
                    if(i++==0){
                        d=new DayHour();
                        d.addDay(day);
                    }
                    //check trung
                    int check=0;
                           for(DayHour dh:dhList){
                               if(day.equals(dh.getDay())){
                                   check=1;
                                
                                   break;
                               }
                             }
                  
                    // save day to obj
                    if(check==0){
                          
                         d=new DayHour();
                         d.addDay(day);
                    }
                    st=conn.prepareStatement("Select * from timer where Date like ? and id like ?;");
                    st.setString(1,day);
                    st.setInt(2,id_timer);
                    ResultSet r2=st.executeQuery();
                        while(r2.next()){
                        String id_hour=Integer.toString(r2.getInt("id_hour"));
                    
                        st=conn.prepareStatement("Select * from hour where id like ? ;");
                        st.setString(1,id_hour);
                        ResultSet r3=st.executeQuery();
                            while(r3.next())
                            {
                            String value=r3.getString("value");
                            int key=r3.getInt("id");
                            String keyCheck=(day)+Integer.toString(key);
                            int checkhour=0;
//                            for(int i=0;i<keys.size();i++){
//                                if(keyCheck.equals(keys.elementAt(i))){
//                                    checkhour=1;
//                                    break;
//                                }
//                            }
                            if(checkhour==1){
                                continue;
                            }
                            keys.add(keyCheck);
                            
                            d.addHour(new KeyValue(key,value));
                            }
                        }
                     
                       dhList.add(d);
                    
                  
        }
	}catch(Exception ex)
	{
		System.out.println("Connect fail");
	}
         Vector<String> day=new Vector<>();
       for(int i=0;i<dhList.size();i++){
           if(i==0){
              day.add(dhList.elementAt(i).getDay());
           }
           else{
               if(!dhList.elementAt(i).getDay().equals(dhList.elementAt(i-1).getDay())){
                   day.add(dhList.elementAt(i).getDay());
               }
           }
           
       }
        SortDay(day);
       for(int i=0;i<day.size();i++){
               DayCombobox.addItem(day.elementAt(i));
         
           
       }

       // scroll bar of description back to top
       jTextArea1.setSelectionStart(0);
      jTextArea1.setSelectionEnd(0);
    }
    public void initCustom(){
        // set jTextArea for display description
        jTextArea1.setLineWrap(true);
        jTextArea1.setBorder(null);
        jTextArea1.setBackground(jLabel1.getBackground());
        jScrollPane1.setBorder(null);
        //set Icon left button
        ImageIcon MyImage =new ImageIcon(getClass().getResource("/resources/left.png"));
        Image img=MyImage.getImage();
        ImageIcon image= new ImageIcon(img);
        LeftButton.setIcon(image);
         //set Icon right button
        MyImage=new ImageIcon(getClass().getResource("/resources/right.png"));
        img=MyImage.getImage();
        image= new ImageIcon(img);
        RightButton.setIcon(image);
        // get All id_movie then push them to movieList vector
         try{
	conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticketbooking","root","");
        PreparedStatement st=conn.prepareStatement("Select * from movie;");
        ResultSet r0=st.executeQuery();
        while(r0.next()){
            movieList.add(r0.getInt("id"));
        }
        }catch(Exception ex){
            System.out.println("fail");
        }
         renderBookingInterface(movieList.elementAt(currentIndex));
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
        LeftButton = new javax.swing.JLabel();
        RightButton = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        DayCombobox = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        hourCombobox = new javax.swing.JComboBox<KeyValue>();
        BookTicketBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Movie ticket system");

        LeftButton.setBackground(new java.awt.Color(51, 0, 255));
        LeftButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LeftButtonMouseClicked(evt);
            }
        });

        RightButton.setBackground(new java.awt.Color(0, 0, 255));
        RightButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RightButtonMouseClicked(evt);
            }
        });

        jLabel8.setText("Select day");

        DayCombobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DayComboboxItemStateChanged(evt);
            }
        });
        DayCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DayComboboxActionPerformed(evt);
            }
        });

        jLabel9.setText("Select hour");

        BookTicketBtn.setText("Book ticket");
        BookTicketBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookTicketBtnActionPerformed(evt);
            }
        });

        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Director :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("James Wan");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(jTextArea2.getFont().deriveFont(jTextArea2.getFont().getStyle() | java.awt.Font.BOLD, jTextArea2.getFont().getSize()+5));
        jTextArea2.setRows(5);
        jTextArea2.setAutoscrolls(false);
        jScrollPane2.setViewportView(jTextArea2);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setFocusCycleRoot(true);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Trailer");
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
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(LeftButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RightButton)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(hourCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(DayCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(72, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(BookTicketBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DayCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(hourCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LeftButton)
                    .addComponent(RightButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BookTicketBtn)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DayComboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DayComboboxItemStateChanged
      
        // TODO add your handling code here:
    }//GEN-LAST:event_DayComboboxItemStateChanged

    private void DayComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DayComboboxActionPerformed
       //show corresponding time period when select day
        if(DayCombobox.getItemCount()!= 0)
        {
            String dayCheck=(DayCombobox.getSelectedItem().toString());
            for(DayHour dh:dhList){
             if(dayCheck==dh.getDay())
             {
                hourCombobox.removeAllItems();
                Vector<KeyValue> hour=new Vector<>();
                for(int i=0;i<dh.getHours().size();i++)
                {
                hour.add(((KeyValue)dh.getHours().elementAt(i)));
                }
                 SortHour(hour);
                 for(int i=0;i<hour.size();i++)
                {
                hourCombobox.addItem(hour.elementAt(i));
                }
              }
             }
        }
    }//GEN-LAST:event_DayComboboxActionPerformed
    
    private void RightButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RightButtonMouseClicked
  // show next movie 
        currentIndex++;
        if(currentIndex==movieList.size()){
            currentIndex=0;
        }
        // remove all before render again
        DayCombobox.removeAllItems();
        dhList.removeAllElements();
        keys.removeAllElements();
        renderBookingInterface(movieList.elementAt(currentIndex));
   //
    }//GEN-LAST:event_RightButtonMouseClicked

    private void LeftButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LeftButtonMouseClicked
     // show previous movie 
        if(currentIndex==0){
            currentIndex=movieList.size();
        }
        currentIndex--;
        // remove all before render again
        DayCombobox.removeAllItems();
        keys.removeAllElements();
        dhList.removeAllElements();
        renderBookingInterface(movieList.elementAt(currentIndex));
        // TODO add your handling code here:
    }//GEN-LAST:event_LeftButtonMouseClicked
    public String getIpAdress() throws UnknownHostException{
        InetAddress localhost = InetAddress.getLocalHost(); 
        return localhost.getHostAddress().trim(); 
       
    }
    public int getSessionIds(){
        try{
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieTicketBooking?useUnicode=true&characterEncoding=UTF-8","root","");
        PreparedStatement ps=con.prepareStatement("select * from id_session where type like 1 and privateIpAdress like ?");
        ps.setString(1, getIpAdress());
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            return rs.getInt("id_user");
        }
        }catch(Exception ex){
            System.out.println("connect fail");
        }
        return 0;
    }
    private void BookTicketBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookTicketBtnActionPerformed
        if(DayCombobox.getItemCount()!=0){
        // if not logined , process login
        int id_user=getSessionIds();
        if(id_user==0)
        {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "You must login before booking ! Do you want to login?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION)
        {
            this.setVisible(false);
            LoginDialog ld=new LoginDialog("booking","");
            ld.setVisible(true);
        }
        }
        else
            // move to book seat
        {
             //select one id in the showcalendar table
              
        int id_movie=movieList.elementAt(currentIndex);
        int id_showcalendar=0;
        Connection conn;
	try{
	conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticketbooking","root","");
	 PreparedStatement st=conn.prepareStatement("Select * from showcalendar where id_movie like ?;");
         st.setString(1, Integer.toString(id_movie));
        ResultSet rs=st.executeQuery();
        // get all show calendar related to movie
        while(rs.next()){
             int timer=rs.getInt("id_timer");
             st=conn.prepareStatement("Select * from timer where id like ?;");
             st.setString(1, Integer.toString(timer));
             ResultSet r1=st.executeQuery();
             // get timer data related to each movie
             r1.next();
             // check if day and hour from combobox same with day and hour in timer database -> get id_timer
             if(DayCombobox.getSelectedItem().toString().equals(r1.getString("Date"))){
                 if(((KeyValue)hourCombobox.getSelectedItem()).getKey()==r1.getInt("id_hour")){
                              st=conn.prepareStatement("Select * from showcalendar where id_movie like ? and id_timer like ?;");
                              st.setString(1, Integer.toString(id_movie));
                              st.setString(2, Integer.toString(timer));
                              ResultSet r2=st.executeQuery();
                              r2.next();
                              id_showcalendar=r2.getInt("id");
                              break;
                 }
             }
        }
	}catch(Exception ex)
	{
		System.out.println("Connect fail");
	}
        
        this.setVisible(false);
        BookSeat bs=new BookSeat(id_showcalendar,id_user);
        bs.setVisible(true);
        }
        }
        else{
         JOptionPane.showMessageDialog(rootPane, "This movie has no show schedule yet", "Error",  JOptionPane.ERROR_MESSAGE);

        }
       
                
        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_BookTicketBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         this.setVisible(false);
        Home hm=new Home();
        hm.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        System.out.println("moviename:"+movieName);
        video pv=new video(trailer,movieName);
       
     
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Booking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Booking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BookTicketBtn;
    private javax.swing.JComboBox<String> DayCombobox;
    private javax.swing.JLabel LeftButton;
    private javax.swing.JLabel RightButton;
    private javax.swing.JComboBox<KeyValue> hourCombobox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
