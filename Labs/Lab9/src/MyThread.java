import java.util.concurrent.Semaphore;

public class MyThread extends Thread {
    private static Semaphore sem = new Semaphore(1,true);

    @Override
    public void run() {
        doJob(this.getName());
    }

    static void doJob(String name) {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Start: " + name);

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End: " + name);

        sem.release();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new MyThread().start();
        }
    }
}