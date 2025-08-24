import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Playlist {
    private Node head, tail, current;
    private String name;
    Path folderPath;

    public Playlist(String name){
        this.name = name;
        this.head = null;
        this.tail = null;
        this.current = null;
        folderPath = Paths.get("Collections" , name);
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
            File destinationFile = folderPath.toFile();
            Path destinationPath = Paths.get(destinationFile.getAbsolutePath(), selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
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
