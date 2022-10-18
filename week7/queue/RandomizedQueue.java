import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int siz = 0;
    private Item[] items;
    
    // construct an empty randomized queue
    public RandomizedQueue(){
        items = (Item[]) new Object[2];
    }

    private void resizeQueue(int newSiz) {
        Item[] newItems = (Item[]) new Object[newSiz]; 
        for(int i = 0; i < siz; i++){
            newItems[i] = items[i];   
        }
        items = newItems;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return (siz == 0);
    }

    // return the number of items on the randomized queue
    public int size(){
        return siz;
    }

    // add the item
    public void enqueue(Item item){
        if( item == null ) throw new IllegalArgumentException();
        items[siz++] = item;
        if(siz == items.length) {
            resizeQueue(siz * 2);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException();   
        int chosenIndex = StdRandom.uniformInt(siz);
        Item temp = items[chosenIndex];
        items[chosenIndex] = items[siz-1];
        items[--siz] = null;
        if(siz == items.length / 4 && siz > 0 ) {
            resizeQueue(siz*2);   
        }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if(isEmpty()) throw new NoSuchElementException();
        return items[ StdRandom.uniformInt(siz) ];
    }

    private class RandomizedIterator implements Iterator<Item> {
        int[] permutationIndex = new int[siz];   
        int cur = 0;
        RandomizedIterator() {
            for(int i = 0 ;i < siz; i++) {
               permutationIndex[i] = i; 
            }
            StdRandom.shuffle(permutationIndex);   
        }

        @Override
        public boolean hasNext() {
            return ( cur < siz );
        }   

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            return items[permutationIndex[cur++]];
        }
    }   

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    // unit testing (required)

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(3);
        System.out.println(queue.isEmpty());     //==> false
        queue.enqueue(32);
        System.out.println(queue.dequeue());   //     ==> 3
        System.out.println(queue.dequeue());     //==> null
    }
}
