import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ex2 extends Thread {
    @Override
    public void run() {
        System.out.println(this.getName());

        Random r = new Random();
        int n = r.nextInt(2);

        try {
            sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(this.getName() + " is exiting..");
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        Ex2 threads [] = new Ex2[n];

        for (int i=0; i<n; i++) {
            threads[i] = new Ex2();
            threads[i].start();
        }

        for (int i=0; i<n; i++) {
            threads[i].join();
        }

        System.out.println("Program is ending");
    }
}
