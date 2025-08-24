import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;

public class MediaPlayer extends JFrame {
    Collection collection;
    MediaPlayer(Collection collection){
        this.collection = collection;
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Media Player");
        ImageIcon logo = new ImageIcon("musicalogo.png");
        ImageIcon note = new ImageIcon("music note.png");
        //ImageIcon play = new ImageIcon("play.png");
        ImageIcon pause = new ImageIcon("pause.png");
        ImageIcon next = new ImageIcon("next.png");
        ImageIcon prev = new ImageIcon("prev.png");


        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0x2f2a29));
        this.setVisible(true);

        JPanel controlPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        JPanel playPanel = new JPanel();
        JLabel musicNote = new JLabel();
        JLabel musicTitle = new JLabel();
        JLabel musicArtist = new JLabel();
        JButton playButton = new JButton();
        JButton nextButton = new JButton();
        JButton prevButton = new JButton();
        JButton temp = new JButton();

        this.add(musicNote, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        
        controlPanel.setPreferredSize(new Dimension(800, 100));
        controlPanel.setLayout(new GridLayout(1,3));
        controlPanel.setOpaque(false);
        controlPanel.add(titlePanel);
        controlPanel.add(playPanel);
        controlPanel.add(temp);

        temp.setText("temp");
        musicNote.setIcon(note);
        musicNote.setSize(new Dimension(50,50));
        musicNote.setHorizontalAlignment(JLabel.CENTER);
        musicNote.setVerticalAlignment(JLabel.CENTER);

        //titlePanel.setOpaque(false);
        titlePanel.setLayout(new GridLayout(2,1));
        titlePanel.add(musicTitle);
        titlePanel.add(musicArtist);

        musicTitle.setText("Unknown");
        musicArtist.setText("Unknown");

        playPanel.setOpaque(false);
        playPanel.setLayout(new GridLayout(1,3));
        playPanel.add(prevButton);
        playPanel.add(playButton);
        playPanel.add(nextButton);

        playButton.setIcon(pause);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);

        nextButton.setIcon(next);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setFocusPainted(false);

        prevButton.setIcon(prev);
        prevButton.setBorderPainted(false);
        prevButton.setContentAreaFilled(false);
        prevButton.setFocusPainted(false);
        
        
    }
}
