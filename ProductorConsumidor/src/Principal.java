import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Alberto García Rodríguez
 * 
 * Consumidor   = si hay tarta le resto a la variable
 * Consumidor   = si no hay tarta despierto al cocinero y me duermo
 * Cocinero     = me duermo esperando a que me llamen
 * Cocinero     = si me llaman produzco 10 trozos de tarta y me duermo
 */
public class Principal implements Runnable{
    private boolean consumidor;
    
    private static int tarta = 0;
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
    
    private void consumiendo(){
        synchronized(lock){
            if(tarta > 0){
                tarta--;
                System.out.println("Quedan "+tarta+" porciones");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                lock.notifyAll();
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void cocinando() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        synchronized(lock){
            if(tarta == 0){
                tarta = 10;
                System.out.println("Soy el cocinero y quedan "+tarta+".");
                lock.notifyAll();
            }
            try{
                lock.wait();
            }catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }catch(Exception e) {
                System.out.println(e.getMessage()); 
            }
        }
    }
    
    public static void main(String[] args){
        int numHilos = 11;
        
        Thread[] hilos = new Thread[numHilos];
        
        for(int i = 0; i < hilos.length; i++){
            Runnable runnable = null;
            
            if(i != 0){
                runnable = new Principal(true);
            }else{
                runnable = new Principal(false);
            }
            
            hilos[i] = new Thread(runnable);
            hilos[i].start();
        }
        
        for(int i = 0; i < hilos.length; i++){
            try{
                hilos[i].join();
            }catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }catch(Exception e) {
                System.out.println(e.getMessage()); 
            }
            
        }
    }
}
