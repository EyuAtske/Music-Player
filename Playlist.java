import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Playlist extends JFrame {
    private Node head, tail, current;
    private String name, filePath;
    JComboBox<Song> songList = new JComboBox<>();
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
                System.out.println("Folder already exist");
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
            
            System.out.println("Enter the title of the song:");
            String title = input.nextLine();
            System.out.println("Enter the artist of the song:");
            String artist = input.nextLine();
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
            System.out.println("No file selected.");
        }

    }

    public void playNext(){
        //Cancle the if if not needed
        if(current.next != null){
            current = current.next;
        }
    }

    public void playPrevious(){
        //Cancle the if if not needed
        if(current.prev != null){
            current = current.prev;
        }
    }

    public String currentSong(){
        return current.song.getSongPathString();
    }

    public void removeSong(){
        String title = "you"; //get title from user input
        Node temp = head;
        if(head == null){
            System.out.println("There is no playlist");
            //display deletion was not succesfull
        }else{
            do{
                if(title.equals(temp.song.getTitleString())){
                    if (temp.prev != null) {
                        temp.prev.next = temp.next;
                    } else {
                        head = temp.next;
                        if (head != null) {
                            head.prev = null;
                        }
                    }

                    if (temp.next != null) {
                        temp.next.prev = temp.prev;
                    } else {
                        tail = temp.prev;
                        if (tail != null) {
                            tail.next = null;
                        }
                    }
                    temp.next = null;
                    temp.prev = null;
                    //display deletion was succesfull
                    return;
                }else{
                    temp = temp.next;
                }
            }while(temp.next != null);
            //display deletion was not succesfull
        }
    }

    public JComboBox<Song> getSongList(){
        Node temp = head;
        if(head == null){
            System.out.println("There is no song in the playlist");
        }else{
            do{
                Song temp1 = (Song) temp.song;
                songList.addItem(temp1);
                System.out.println("toString() -> " +temp1);
                temp = temp.next;
            }while(temp != null);
        }
        return songList;
    }

    // @Override
    // public String toString() {
    //     return name ; // what gets displayed in JComboBox
    // }

}
