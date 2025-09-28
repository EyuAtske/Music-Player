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
import javax.swing.SwingUtilities;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MediaPlayer extends JFrame implements ActionListener, BasicPlayerListener {
    Collection collection;
    ImageIcon logo;
    ImageIcon note;
    ImageIcon play;
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
    FileInputStream fis;
    BufferedInputStream bis;
    private static boolean sidebarVisible = false;
    Playlist playlist;
    private BasicPlayer player;
    private boolean isPaused = false;
    private long totalBytes = 0;
    private long totalMicroseconds = 0;

    MediaPlayer(Collection collection){
        this.collection = collection;
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("Media Player");
        logo = new ImageIcon("musicalogo.png");
        note = new ImageIcon("music note.png");
        play = new ImageIcon("play.png");
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
        //progressBar = new JSlider(0, 100, 0);
        menuBar = new JMenuBar();
        addMenu = new JMenu("ADD");
        removeMenu = new JMenu("REMOVE");
        addSong = new JMenuItem("Add Song");
        addPlaylist = new JMenuItem("Add Playlist");
        removeSong = new JMenuItem("Remove Song");
        removePlaylist = new JMenuItem("Remove Playlist");
        playlist = collection.currentPlaylist();
        this.player = new BasicPlayer();
        progressBar = new JSlider();
        player.addBasicPlayerListener(this);
        

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
        progressBar.setValue(0);
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

        musicTitle.setText("Title: "+playlist.currentSongTitle());
        musicTitle.setForeground(new Color(0xe81a13));
        musicTitle.setFont(new Font("Bold", Font.BOLD, 20));
        musicArtist.setText("Artist: "+playlist.currentSongArtist());
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

        playButton.setIcon(play);
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

        playButton.addActionListener(this);
        prevButton.addActionListener(this);
        nextButton.addActionListener(this);
        
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0x2f2a29));
        this.setVisible(true);

    }


    public void play(String filepath) {
        try {
            if (isPaused) {
                player.resume();
                isPaused = false;
            } else {
                player.open(new File(filepath));
                player.play();
            }
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            player.pause();
            isPaused = true;
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            player.stop();
            isPaused = false;
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public void seek(long bytes) {
        try {
            player.seek(bytes);
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    private String formatTime(long microseconds) {
        long totalSeconds = microseconds / 1_000_000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
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
                collection.saveCollection();
            }else if(e.getSource() == removeSong){
                Playlist tempSong;
                String playlistName = getDelPlaylistName();
                if(playlistName != null){
                    tempSong = collection.secarchPlaylist(playlistName);
                    String filepath = tempSong.currentSong();
                    if(tempSong != null){
                        tempSong.display();
                        tempSong.removeSong();
                        if(tempSong.currentSong() != filepath){
                            stop();
                            musicTitle.setText("Title: ");
                            musicArtist.setText("Artist: ");
                            playButton.setIcon(play);
                        }
                        File file = new File(filepath);
                        if (file.exists() && file.delete()) {
                            System.out.println("File deleted: " + file.getAbsolutePath());
                        } else {
                            System.out.println("Could not delete file: " + file.getAbsolutePath());
                        }
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
                collection.saveCollection();
            }else if(e.getSource() == playButton){
                if(playButton.getIcon() == play){
                    play(playlist.currentSong());
                    playButton.setIcon(pause);
                }else{
                    pause();
                    playButton.setIcon(play);
                }
            }else if(e.getSource() == nextButton){
                stop();
                playlist.playNext();
                musicTitle.setText("Title: "+playlist.currentSongTitle());
                musicArtist.setText("Artist: "+playlist.currentSongArtist());
                play(playlist.currentSong());
                playButton.setIcon(pause);
            }else if(e.getSource() == prevButton){
                stop();
                playlist.playPrevious();
                musicTitle.setText("Title: "+playlist.currentSongTitle());
                musicArtist.setText("Artist: "+playlist.currentSongArtist());
                play(playlist.currentSong());
                playButton.setIcon(pause);
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
            listSong.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) listSong.getLastSelectedPathComponent();
                        if (selectedNode != null && selectedNode.isLeaf()) {
                            Object nodeInfo = selectedNode.getUserObject();
                            if (nodeInfo instanceof Song) {
                                Song song = (Song) nodeInfo;
                                System.out.println("Playing: " + song.getTitleString() + " - " + song.getArtistString());
                                play(song.getSongPathString());
                                musicTitle.setText("Title: "+ song.getTitleString());
                                musicArtist.setText("Artist: "+ song.getArtistString());
                                playButton.setIcon(pause);
                            }
                        }
                    }
                }
            });
        }

        @Override
        public void opened(Object stream, Map properties) {
            if (properties.containsKey("audio.length.bytes")) {
                Object lenObj = properties.get("audio.length.bytes");
                if (lenObj instanceof Number) {
                    totalBytes = ((Number) lenObj).longValue();
                }
            }

            if (properties.containsKey("duration")) {
                Object durObj = properties.get("duration");
                if (durObj instanceof Number) {
                    totalMicroseconds = ((Number) durObj).longValue();

                    SwingUtilities.invokeLater(() -> {
                        currentTimeLabel.setText("0:00");
                        totalTimeLabel.setText(formatTime(totalMicroseconds));
                    });
                }
            }
        }

        @Override
        public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
            if (totalBytes > 0 && !progressBar.getValueIsAdjusting()) { 
                int progress = (int) ((bytesread * 100) / totalBytes);
                progressBar.setValue(progress);
            }

            // update elapsed time
            SwingUtilities.invokeLater(() -> {
                currentTimeLabel.setText(formatTime(microseconds));
                totalTimeLabel.setText(formatTime(totalMicroseconds));
            });
        }

        @Override
        public void stateUpdated(BasicPlayerEvent event) {
            int state = event.getCode();
            switch(state) {
                case BasicPlayerEvent.PLAYING:
                    System.out.println("Playing...");
                    break;
                case BasicPlayerEvent.PAUSED:
                    System.out.println("Paused");
                    break;
                case BasicPlayerEvent.EOM:
                    System.out.println("End of media");//make it go to the next song
                    break;
                case BasicPlayerEvent.STOPPED:
                    SwingUtilities.invokeLater(() -> {
                        currentTimeLabel.setText("0:00");
                        totalTimeLabel.setText("0:00");
                        progressBar.setValue(0);
                    });
                    break;
            }
        }

        @Override
        public void setController(BasicController controller) {
            throw new UnsupportedOperationException("Unimplemented method 'setController'");
        }
    }

