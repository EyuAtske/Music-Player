import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;

public class MediaPlayer extends JFrame {
    MediaPlayer(){
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Media Player");
        ImageIcon logo = new ImageIcon("musicalogo.png");
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0x2f2a29));
        this.setVisible(true);
    }
}
