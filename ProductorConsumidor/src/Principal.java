import java.util.logging.Level;
import java.util.logging.Logger; 
/**
 * @author Luis Alberto García Rodríguez
 * 
 * Consumidor   = si hay tartas le resto a la variable
 * Consumidor   = si no hay tartas despierto al cocinero y me duermo
 * Cocinero     = me duermo esperando a que me llamen
 * Cocinero     = si me llaman produzco 10 trozos de tartas y me duermo
 */
public class Principal implements Runnable{
    private boolean consumidor;
    Contenedor contenedor = Contenedor.getStreamInstance();
    
    private static int tartas = 0;
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
                    System.out.println("En consumidor el contenedor tiene "+contenedor.getTotalSize()+" elementos. Eliminado-> "+contenedor.poll());
                    Thread.sleep(ms);
                    
                    tartas--;
                    lock.notifyAll();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    //productor
    private void cocinando() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        synchronized(lock){
            
            long ms = 0;
            if(contenedor.getTotalSize() < 25){
                try {
                    ms = (long) ((Math.random() * 1 + 1) * 1000);
                    Thread.sleep(ms);
                    contenedor.add(tartas);
                    System.out.println("En productor el contenedor tiene "+contenedor.getTotalSize()+" Produciendo tarta -> "+tartas);
                    tartas++;
                    lock.notifyAll();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
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
