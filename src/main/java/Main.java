
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++){
            Luchador t = new Luchador();
            t.setName("Luchador " + i);
            t.start();
        }
    }
}

class Luchador extends Thread {


    @Override
    public void run() {
        try {
            Cuadrilatero.addParticipante(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Cuadrilatero{

    private static final int NUM_ACCESO_SIMULTANEOS = 2;
    static Luchador luchador1=null;
    static Luchador luchador2=null;
    static Semaphore semaphore = new Semaphore(NUM_ACCESO_SIMULTANEOS,true);

    public static void addParticipante(Luchador luchador) throws InterruptedException {

        try {
            semaphore.acquire();
            if (luchador1 == null){
                luchador1 = luchador;
                Thread.sleep(2000);
            }
            else { luchador2 = luchador; }
            if (luchador1!=null && luchador2!=null) {
                System.out.println("Comienza la pelea"+ luchador1.getName() + " y "+ luchador2.getName());
                luchar();
                semaphore.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void luchar(){
        boolean ganar=new Random().nextBoolean();
        if (ganar){
            System.out.println(luchador1.getName()+" gana"+"\n");
            luchador2=null;
        } else {
            System.out.println(luchador2.getName()+" gana"+"\n");
            luchador1=null;
        }
    }
}