import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Collection implements Serializable {
    private PlaylistNode head, tail, current;
    Path collectionPath = Paths.get("Collections", "collections.dat");

    public Collection(){
        this.head = null;
        this.tail = null;
        this.current = null;
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

    public void saveCollection(Collection collection){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(collectionPath.toFile()))) {
            oos.writeObject(collection);
            System.out.println("List saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection loadCollection(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(collectionPath.toFile()))) {
            return (Collection) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
