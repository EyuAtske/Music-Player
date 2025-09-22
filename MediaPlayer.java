import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTree;

import javazoom.jl.player.Player;
//import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MediaPlayer extends JFrame implements ActionListener {
    Collection collection;
    ImageIcon logo;
    ImageIcon note;
    //ImageIcon play;
    ImageIcon pause;
    ImageIcon next;
    ImageIcon prev;
    ImageIcon playnote;
    ImageIcon menu;

    JPanel controlPanel;
    JPanel titlePanel;
    JPanel playPanel;
    JPanel gifPanel;
    JPanel progressPanel;
    JPanel playPanal;
    JPanel sideBar;
    JPanel centerPanel;
    JPanel topPanel;
    JLabel musicNote;
    JLabel musicTitle;
    JLabel musicArtist;
    JLabel playNote;
    JLabel currentTimeLabel;
    JLabel totalTimeLabel;
    JButton playButton;
    JButton nextButton;
    JButton prevButton;
    JButton listButton;
    JSlider progressBar;
    JMenuBar menuBar;
    JMenu addMenu;
    JMenu removeMenu;
    JMenuItem addSong;
    JMenuItem addPlaylist;
    JMenuItem removeSong;
    JMenuItem removePlaylist;
    DefaultMutableTreeNode songList;
    JTree listSong;
    Timer timer;
    Clip clip;
    Player player;
    FileInputStream fis;
    BufferedInputStream bis;
    private static boolean sidebarVisible = false;

    MediaPlayer(Collection collection){
        this.collection = collection;
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Media Player");
        logo = new ImageIcon("musicalogo.png");
        note = new ImageIcon("music note.png");
        //play = new ImageIcon("play.png");
        pause = new ImageIcon("pause.png");
        next = new ImageIcon("next.png");
        prev = new ImageIcon("prev.png");
        playnote = new ImageIcon("playnote.gif");
        menu = new ImageIcon("menu.png");

        controlPanel = new JPanel();
        titlePanel = new JPanel();
        playPanel = new JPanel();
        gifPanel = new JPanel();
        progressPanel = new JPanel();
        playPanal = new JPanel();
        sideBar = new JPanel();
        centerPanel = new JPanel();
        topPanel = new JPanel();
        musicNote = new JLabel();
        musicTitle = new JLabel();
        musicArtist = new JLabel();
        playNote = new JLabel();
        currentTimeLabel = new JLabel("0:00");
        totalTimeLabel = new JLabel("0:00");
        playButton = new JButton();
        nextButton = new JButton();
        prevButton = new JButton();
        listButton = new JButton();
        progressBar = new JSlider(0, 100, 0);
        menuBar = new JMenuBar();
        addMenu = new JMenu("ADD");
        removeMenu = new JMenu("REMOVE");
        addSong = new JMenuItem("Add Song");
        addPlaylist = new JMenuItem("Add Playlist");
        removeSong = new JMenuItem("Remove Song");
        removePlaylist = new JMenuItem("Remove Playlist");

        this.setJMenuBar(menuBar);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(playPanal, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);

        listButton.setPreferredSize(new Dimension(80, 30));
        listButton.setBorderPainted(false);
        listButton.setContentAreaFilled(false);
        listButton.setFocusPainted(false);
        listButton.setIcon(menu);

        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(listButton);
        topPanel.setOpaque(false);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(musicNote, BorderLayout.CENTER);
        centerPanel.add(sideBar, BorderLayout.EAST);

        playPanal.setLayout(new BorderLayout());
        playPanal.setOpaque(false);
        playPanal.add(progressPanel, BorderLayout.NORTH);
        playPanal.add(controlPanel, BorderLayout.CENTER);

        
        progressPanel.setLayout(new BorderLayout(10,0));
        progressPanel.setOpaque(false);
        progressPanel.add(currentTimeLabel, BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.add(totalTimeLabel, BorderLayout.EAST);


        controlPanel.setPreferredSize(new Dimension(800, 100));
        controlPanel.setLayout(new GridLayout(1,3));
        controlPanel.setOpaque(false);
        controlPanel.add(titlePanel);
        controlPanel.add(playPanel);
        controlPanel.add(gifPanel);


        
        progressBar.setOpaque(false);
        progressBar.setPaintTicks(false);
        progressBar.setPaintTrack(true);
        progressBar.setPaintLabels(false);
        progressBar.setUI(new javax.swing.plaf.metal.MetalSliderUI() {
            @Override
            public void paintThumb(Graphics g) {
                g.setColor(new Color(0xe81a13));
                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }
        });

        currentTimeLabel.setForeground(new Color(0xe81a13));
        totalTimeLabel.setForeground(new Color(0xe81a13));


        sideBar.setBackground(Color.white);
        sideBar.setPreferredSize(new Dimension(0, musicNote.getHeight()));
        sideBar.setLayout(new BorderLayout());


        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Playlists");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        listSong = new JTree(treeModel);

        JScrollPane scrollPane = new JScrollPane(listSong);
        sideBar.add(scrollPane);
        getSidebarSongs();


        //showProgressBar("C:\\Users\\eyuel\\Downloads\\Telegram Desktop\\ElevenLabs_2024-04-25T08_57_17_Brian_pre_s50_sb75_se0_b_m2.mp3");
        
        
        musicNote.setIcon(note);
        musicNote.setSize(new Dimension(50,50));
        musicNote.setHorizontalAlignment(JLabel.CENTER);
        musicNote.setVerticalAlignment(JLabel.CENTER);

        


        Timer expandTimer = new Timer(5, null); // 5ms delay per step
        expandTimer.addActionListener(new ActionListener() {  
            int width = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!sidebarVisible) { 
                    // expanding
                    width += 10;
                    sideBar.setPreferredSize(new Dimension(width, centerPanel.getHeight()));
                    centerPanel.revalidate();
                    if (width >= 300) { // max width
                        expandTimer.stop();
                        sidebarVisible = true;
                    }
                } else { 
                    // collapsing
                    width -= 10;
                    sideBar.setPreferredSize(new Dimension(width, centerPanel.getHeight()));
                    centerPanel.revalidate();
                    if (width <= 0) {
                        expandTimer.stop();
                        sidebarVisible = false;
                    }
                }
            }
        });


        listButton.addActionListener(e -> expandTimer.start());

        menuBar.add(addMenu);
        menuBar.add(removeMenu);

        addMenu.add(addSong);
        addMenu.add(addPlaylist);
        removeMenu.add(removeSong);
        removeMenu.add(removePlaylist);

        addSong.addActionListener(this);
        addPlaylist.addActionListener(this);
        removeSong.addActionListener(this);
        removePlaylist.addActionListener(this);
        

        titlePanel.setOpaque(false);
        titlePanel.setLayout(new GridLayout(2,1));
        titlePanel.add(musicTitle);
        titlePanel.add(musicArtist);

        musicTitle.setText("Unknown");
        musicTitle.setForeground(new Color(0xe81a13));
        musicTitle.setFont(new Font("Bold", Font.BOLD, 20));
        musicArtist.setText("Unknown");
        musicArtist.setForeground(new Color(0xe81a13));
        musicArtist.setFont(new Font("Bold", Font.BOLD, 20));

        gifPanel.setOpaque(false);
        gifPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        gifPanel.add(playNote);
        playNote.setIcon(playnote);

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
        
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0x2f2a29));
        this.setVisible(true);

    }


    public void showProgressBar(String fileString) {
    try {
        File musicFile = new File(fileString);
        fis = new FileInputStream(musicFile);
        bis = new BufferedInputStream(fis);
        player = new Player(bis);

        long totalBytes = musicFile.length(); // total file size in bytes

        // Estimate total duration (approximate, depends on bitrate)
        int totalSeconds = (int) (totalBytes / 16000);
        totalTimeLabel.setText(formatTime(totalSeconds));

        // Thread to play the song
        Thread playThread = new Thread(() -> {
            try {
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Swing Timer to update progress bar
        timer = new Timer(1000, e -> {
            try {
                long remaining = fis.available();
                long played = totalBytes - remaining;
                int progress = (int) ((played * 100) / totalBytes);
                progressBar.setValue(progress);

                int currentSeconds = (int) (played / 16000);
                currentTimeLabel.setText(formatTime(currentSeconds));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        playThread.start();
        timer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        private String formatTime(int seconds) {
            int min = seconds / 60;
            int sec = seconds % 60;
            return String.format("%02d:%02d", min, sec);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == addSong){
                Playlist tempSong;
                String playlistName = getAddPlaylistName();
                if(playlistName != null){
                    tempSong = collection.secarchPlaylist(playlistName);
                    if(tempSong != null){
                        tempSong.addSong();
                        collection.saveCollection();
                    }else{
                        JOptionPane.showMessageDialog(null,"no Playlist", "There is no playlist with this name", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"no Playlist", "There is no playlist with this name", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                getSidebarSongs();
            }else if(e.getSource() == addPlaylist){
                String playlistName = JOptionPane.showInputDialog("Enter the name of the playlist");
                Playlist playlist = new Playlist(playlistName);
                collection.addPlayList(playlist);
                getSidebarSongs();
            }else if(e.getSource() == removeSong){
                Playlist tempSong;
                String playlistName = getDelPlaylistName();
                if(playlistName != null){
                    tempSong = collection.secarchPlaylist(playlistName);
                    if(tempSong != null){
                        tempSong.display();
                        tempSong.removeSong();
                        collection.saveCollection();
                    }else{
                        JOptionPane.showMessageDialog(null,"There is no playlist with this name", "No Playlist", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"There is no playlist with this name", "No Playlist", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                getSidebarSongs();
            }else if (e.getSource() == removePlaylist){
                String playlistName = JOptionPane.showInputDialog("Enter the name of the playlist");
                collection.removePlaylist(playlistName);
                getSidebarSongs();
            }
        }

        public String getAddPlaylistName(){
            return JOptionPane.showInputDialog("What is the name of the playlist you want it in?");  
        }
        public String getDelPlaylistName(){
            return JOptionPane.showInputDialog("What is the name of the playlist you want to delete in?");  
        }

        public void getSidebarSongs(){
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Playlists");
            for(Playlist p : collection.getCurrentPlaylists()){
                System.out.println(p.getName());
                DefaultMutableTreeNode playlistNode = p.getSongList();
                root.add(playlistNode);  
            }
            DefaultTreeModel model = (DefaultTreeModel) listSong.getModel();
            model.setRoot(root);
            model.reload();    
        }
    }

