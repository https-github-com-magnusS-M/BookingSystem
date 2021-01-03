/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieticketbooking.user;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class video {
    public video(String trailerName,String movideName){
        
        JFrame f=new JFrame();
        f.pack();
        String trailer=movideName+"(Trailer)";
        System.out.println(trailer);
      
        f.setLocation(360, 300);
        f.setSize(800,500);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
        
        Canvas c=new Canvas();
        c.setBackground(Color.BLACK);
        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());
        p.add(c);
        f.add(p);
         String currentDir = System.getProperty("user.dir");
         String x=currentDir+"\\"+"VLC";
         x=x.replace("dist","");
      NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),x);
      Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
      
      //initialize the media player
      MediaPlayerFactory mpf=new MediaPlayerFactory();
      // control
      EmbeddedMediaPlayer emp=mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(f));
      emp.setVideoSurface(mpf.newVideoSurface(c));
      f.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         emp.stop();
    }
});
//      emp.toggleFullScreen();
      emp.setEnableKeyInputHandling(false);
      emp.setEnableMouseInputHandling(false);
     
     
     currentDir = System.getProperty("user.dir");
      String file=currentDir+"\\"+trailerName;
      String fileRep=file.replace("dist","src\\resources");
      System.out.println(fileRep);
      emp.prepareMedia(fileRep);
      emp.play();
      f.setTitle(fileRep);
    }
    public static void main(String[] args){
    
    }
}
