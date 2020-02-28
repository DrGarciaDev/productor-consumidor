import java.util.logging.Level;
import java.util.logging.Logger; 
/**
 * @author Luis Alberto García Rodríguez
 * 
 * Consumidor   = si hay tartas le resto al contenedor
 * Consumidor   = si no hay tartas despierto al cocinero y me duermo
 * Cocinero     = me duermo esperando a que me llamen
 * Cocinero     = puedo producir hasta 25 tartas
 */
public class Principal implements Runnable{
    private boolean consumidor;
    Contenedor contenedor = Contenedor.getStreamInstance();
    private static int tartas = 1;
    private static Object lock = new Object();
    
    public Principal(boolean consumidor){
        this.consumidor = consumidor;
    }
    
    @Override
    public void run(){
        while(true){
            if(consumidor == true){
                consumiendo();
            }else{
                cocinando();
            }
        }
    }
    //Consumidor
    private void consumiendo(){
        synchronized(lock){            
            long ms = 0;
            ms = (long) ((Math.random() * 1 + 1) * 1000);
            try {
                if(contenedor.getTotalSize() > 0 ){
                    tartas--;
                    System.out.println(contenedor.get());
                    System.out.println("CONSUMIDOR DESPIERTO");
                    System.out.println("TOTAL en contenedor ["+contenedor.getTotalSize()+"]");
                    System.out.println("Elemento en contenedor -> "+contenedor.getPeek());
                    System.out.println("Tarta comsumida-> "+tartas);
                    System.out.println();
                    Thread.sleep(ms);
                    contenedor.pop();
                    lock.notifyAll();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //productor
    private void cocinando() {
        synchronized(lock){
            long ms = 0;
            ms = (long) ((Math.random() * 1 + 1) * 1000);
            try {
                if(contenedor.getTotalSize() < 25){
                    Thread.sleep(ms);
                    contenedor.push(tartas);
                    System.out.println(contenedor.get());
                    System.out.println("PRODUCTOR DESPIERTO"); 
                    System.out.println("Tarta producida -> "+tartas);
                    System.out.println("Elemento guardado en contenedor -> "+contenedor.getPeek());
                    System.out.println("TOTAL en contenedor ["+contenedor.getTotalSize()+"]");
                    System.out.println();
                    tartas++;
                    lock.notifyAll();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args){
        int numHilos = 2;
        
        Thread[] hilos = new Thread[numHilos];
        
        for(int i = 0; i < hilos.length; i++){
            Runnable runnable = null;
            
            if(i != 0){
                //consumidor
                runnable = new Principal(true);
            }else{
                //productor
                runnable = new Principal(false);
            }
            
            hilos[i] = new Thread(runnable);
            hilos[i].start();
        }
        /*
        for(int i = 0; i < hilos.length; i++){
            try{
                hilos[i].join();
            }catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }catch(Exception e) {
                System.out.println(e.getMessage()); 
            }
        }
        */
    }
}
