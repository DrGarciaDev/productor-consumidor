import java.util.Stack;
/**
 *
 * @author Luis Alberto García Rodríguez
 */
class Contenedor {
    Stack contenedor;
    private static Contenedor stackInstance = null;
    
    public static Contenedor getStreamInstance() {
        if (stackInstance == null) {
                stackInstance = new Contenedor();
        }
        return stackInstance;
    }
    // Constructor
    Contenedor() {
        this.contenedor = new Stack();
    }
    // Returns all elements in stack
    public Stack get() {
        return contenedor;
    }
    // Inserts the specified element into this stack if it is possible to do so
    // immediately without violating capacity restrictions
    public void push(Integer value){
        synchronized(contenedor){
            contenedor.push(value);
        }
    }
    // Returns the number of elements in this collection. If this collection
    // contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE
    public int getTotalSize() {
        return contenedor.size();
    }
    // Removes a single element from this collection
    public void pop(){
        contenedor.pop();
    }
    // Returns the head of this stack
    public int getPeek(){
        return (int) contenedor.peek();
    }
    // Returns true if this collection contains no elements
    public boolean isEmpty() {
        return contenedor.isEmpty();
    }
}