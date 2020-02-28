import java.util.LinkedList; 
import java.util.Queue;
/**
 *
 * @author luis_
 */
class Contenedor {
    Queue<Integer> contenedor = new LinkedList<>();
    private static Contenedor queueInstance = null;
    
    public static Contenedor getStreamInstance() {
        if (queueInstance == null) {
                queueInstance = new Contenedor();
        }
        return queueInstance;
    }

    public Queue<Integer> get() {
        return contenedor;
    }

    // Inserts the specified element into this queue if it is possible to do so
    // immediately without violating capacity restrictions
    public void add(Integer value) {
        synchronized (contenedor) {
            contenedor.add(value);
        }
    }

    // Removes a single instance of the specified element from this collection
    public void removeValue(Integer value) {
        synchronized (contenedor) {
            contenedor.remove(value);
        }
    }
    
    // Removes a single element from this collection
    public void remove() {
        synchronized (contenedor) {
            contenedor.remove();
        }
    }

    // Retrieves and removes the head of this queue, or returns null if this
    // queue is empty.
    public Integer poll() {
        Integer data = contenedor.poll();
        return data;
    }

    // Returns true if this collection contains no elements
    public boolean isEmpty() {
        return contenedor.isEmpty();
    }

    // Returns the number of elements in this collection. If this collection
    // contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE
    public int getTotalSize() {
        return contenedor.size();
    }
    
    public int getPeek(){
        return contenedor.peek();
    }
}
