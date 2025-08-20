import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Playlist {
    private Node head, tail, current;
    private String name;
    public Playlist(String name){
        this.name = name;
        this.head = null;
        this.tail = null;
        this.current = null;
        Path folderPath = Paths.get(name);
        try {
            if(!Files.exists(folderPath)){
                Files.createDirectories(folderPath);
            }else{
                System.out.println("Folder already exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            
            //set up input window
            Song song = new Song();
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
        String title = "you";
        Node temp = head;
        if(head == null){
            System.out.println("There is no playlist");
            //display deletion was not succesfull
        }else{
            do{
                if(title == temp.song.getTitleString()){
                    if (temp.prev != null) {
                        temp.prev.next = temp.next;
                    } else {
                        head = temp.next; 
                        head.prev = null;
                    }

                    if (temp.next != null) {
                        temp.next.prev = temp.prev;
                    } else {
                        tail = temp.prev; 
                        tail.next = null;
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
}
