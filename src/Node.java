package src;

import java.io.Serializable;

public class Node implements Serializable{
    Song song;
    Node next;
    Node prev;
    public Node(Song song){
        this.song = song;
        this.next = null;
        this.prev = null;
    }
}
