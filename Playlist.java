import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

public class Playlist {
    private Node head, tail, current;
    private String name;
    public Playlist(String name){
        this.name = name;
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
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected.");
        }

    }
}
