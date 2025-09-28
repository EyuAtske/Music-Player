package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

public class Playlist extends JFrame {
    private Node head, tail, current;
    private String name, filePath;
    // transient Path folderPath;
    transient Scanner input;

    public Playlist(String name){
        this.name = name;
        this.head = null;
        this.tail = null;
        this.current = null;
        Path folderPath = Paths.get("Collections" , name);
        try {
            if(!Files.exists(folderPath)){
                Files.createDirectories(folderPath);
                filePath = folderPath.toString();
            }else{
                JOptionPane.showMessageDialog(null,"This folder already exists", "Existing Folder", JOptionPane.WARNING_MESSAGE);
                filePath = folderPath.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Node getHeadNode(){
        return head;
    }

    public Node getTailNode(){
        return tail;
    }

    public String getFolderPath(){
        return filePath;
    }


    public void addSong(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Mp3 File");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            File destinationFile = new File(filePath);
            Path destinationPath = Paths.get(destinationFile.getAbsolutePath(), selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            input = new Scanner(System.in);
            
            String title = JOptionPane.showInputDialog("Enter the title of the song:");
            String artist = JOptionPane.showInputDialog("Enter the artist of the song:");
            String songPath = destinationPath.toString();
            Song song = new Song(title, artist, songPath);
            Node newnode = new Node(song);
            if(head == null){
                head = newnode;
                tail = newnode;
                current = head;
            }else{
                tail.next = newnode;
                newnode.prev = tail;
                tail = newnode;
            }
        } else {
            JOptionPane.showMessageDialog(null,"No File has been selected", "No File", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void playNext(){
        if(current.next != null){
            current = current.next;
        }
    }

    public void playPrevious(){
        if(current.prev != null){
            current = current.prev;
        }
    }

    public String currentSong(){
        return current.song.getSongPathString();
    }

    public String currentSongTitle(){
        return current.song.getTitleString();
    }

    public String currentSongArtist(){
        return current.song.getArtistString();
    }

    public void removeSong(){
        String title = JOptionPane.showInputDialog("Enter the title of the song:");//get title from user input
        Node temp = head;
        if(head == null){
            JOptionPane.showMessageDialog(null,"There is no playlist with this name", "No Playlist", JOptionPane.WARNING_MESSAGE);
        }else{
            do{
                if(title.equalsIgnoreCase(temp.song.getTitleString())){
                    if (temp == current) {
                        if (temp.next != null) {
                            current = temp.next;
                        } else if (temp.prev != null) {
                            current = temp.prev;
                        } else {
                            current = null;
                        }
                    }

                    if (temp.prev != null) {
                        temp.prev.next = temp.next;
                    } else {
                        head = temp.next;
                        if (head != null) head.prev = null;
                    }

                    if (temp.next != null) {
                        temp.next.prev = temp.prev;
                    } else {
                        tail = temp.prev;
                        if (tail != null) tail.next = null;
                    }

                    temp.next = null;
                    temp.prev = null;
                    JOptionPane.showMessageDialog(null,"The song deletion was succsusfull", "Song Deleted", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else{
                    temp = temp.next;
                }
            }while(temp != null);
            JOptionPane.showMessageDialog(null,"There is no song with this credentials", "No song deleted", JOptionPane.WARNING_MESSAGE);
        }
    }

    public DefaultMutableTreeNode getSongList(){
        DefaultMutableTreeNode songList = new DefaultMutableTreeNode(this.name);
        Node temp = head;
        if(head == null){
            //JOptionPane.showMessageDialog(null,"There is no song in the playlist", "No Song", JOptionPane.WARNING_MESSAGE);
        }else{
            do{
                DefaultMutableTreeNode item = new DefaultMutableTreeNode(temp.song);
                songList.add(item);
                temp = temp.next;
            }while(temp != null);
        }
        return songList;
    }

    public void display(){
        Node temp = head;
        while(temp != null){
            System.out.println( temp.song.getTitleString());
            temp =temp.next;
        }
    }

}
