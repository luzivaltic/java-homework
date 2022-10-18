import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // linked-list implement
    private Node first = null;
    private Node last = null;
    private int siz = 0;

    private class Node {
        Item item;
        Node nexNode = null;
        Node preNode = null;

        public Node(Item item){
            this.item = item;
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node firsNode = first;
        @Override
        public boolean hasNext() {
            return firsNode != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item curItem = firsNode.item;
            firsNode = firsNode.nexNode;
            return curItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // construct an empty deque
    public Deque(){
        siz = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return (siz == 0);
    }

    // return the number of items on the deque
    public int size(){
        return siz;
    }   

    // add the item to the front
    public void addFirst(Item item){
        if( item == null ) throw new IllegalArgumentException();
        Node oldFirstNode = first;
        first = new Node(item);
        first.nexNode = oldFirstNode;
        if( siz == 0 ) {
            last = first;   
        }
        else {
            oldFirstNode.preNode = first;
        }
        siz++;
    }

    // add the item to the back
    public void addLast(Item item){
        if( item == null ) throw new IllegalArgumentException();
        Node oldLastNode = last;
        last = new Node(item);
        last.preNode = oldLastNode;
        if( siz == 0 ) {
            first = last;   
        }
        else {
            oldLastNode.nexNode = last;
        }
        siz++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if( isEmpty() ) throw new NoSuchElementException();
        Node need = first;   
        first = first.nexNode;
        if( siz == 1 ) {
            last = null;   
        }
        else {
            first.preNode = null;
        }
        siz--;
        return need.item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if( isEmpty() ) throw new NoSuchElementException();
        Node need = last;   
        last = last.preNode;
        if( siz == 1 ) {
            first = null;   
        }
        else {
            last.nexNode = null;
        }
        siz--;
        return need.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> new_deque = new Deque<>();
        new_deque.addFirst(12);
        new_deque.addFirst(13);
        new_deque.addLast(245);

        Iterator<Integer> something = new_deque.iterator();
        while( something.hasNext() ) {
            System.out.println(something.next());   
        }
    }
}
