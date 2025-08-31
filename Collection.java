import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Collection implements Serializable {
    private PlaylistNode head, tail, current;
    //transient Path collectionPath = Paths.get("Collections", "collections.dat");

    public Collection(){
        this.head = null;
        this.tail = null;
        this.current = null;
    }

    public PlaylistNode getHeadNode(){
        return head;
    }

    public PlaylistNode getTailNode(){
        return tail;
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
