import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Collection implements Serializable {
    private PlaylistNode head, tail, current;
    private List<Playlist> currentPlaylists = new ArrayList<>();

    public Collection(){
        this.head = null;
        this.tail = null;
        this.current = null;
    }

    public List<Playlist> getCurrentPlaylists(){
        return currentPlaylists;
    }

    public void addPlayList(Playlist pl){
        PlaylistNode pln = new PlaylistNode(pl);
        if(head == null){
            head = pln;
            tail = pln;
            current = head;
        }else{
            tail.next = pln;
            pln.prev = tail;
            tail = pln;
        }
        currentPlaylists.add(pl);
    }

    public void removePlaylist(String name){
        String title = name;
        PlaylistNode temp = head;
        if(head == null){
            System.out.println("There is no playlist");
        }else{
            do{
                if(title.equals(temp.playlist.getName())){
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
                    currentPlaylists.remove(temp.playlist);
                    return;
                }else{
                    temp = temp.next;
                }
            }while(temp.next != null);
        }
    }

    public void display(){
        PlaylistNode temp = head;
        while(temp != null){
            System.out.println(temp.playlist.getName());
            temp = temp.next;
        }
    }

    public Playlist currenPlaylist(){
        return current.playlist;
    }

    public Playlist secarchPlaylist(String name){
        PlaylistNode temp = head;
        while(temp != null){
            if(temp.playlist.getName().equals(name)){
                return temp.playlist;
            }else{
                temp = temp.next;
            }
        }
        return null;
    }

    public void saveCollection(){
        Path collectionPath = Paths.get("Collections", "collections.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(collectionPath.toFile()))) {
            oos.writeObject(this);
            System.out.println("List saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collection loadCollection() {
        Path collectionPath = Paths.get("Collections", "collections.dat");
        File file = collectionPath.toFile();

        if (!file.exists() || file.length() == 0) {
            return new Collection();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Collection loaded = (Collection) ois.readObject();
            System.out.println("List loaded successfully!");
            return loaded;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Collection();
    }
}
