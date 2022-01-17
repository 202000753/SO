import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer extends Thread {
    static Storage storage;
    int index;

    Writer (int index) {
        this. index = index;
    }

    @Override
    public void run() {
        Random r = new Random();
        int time = r.nextInt(2000)+1;

        System.out.println("Writer " + index + "# sleep time = " + time);

        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        storage.set(index, r.nextInt(100)+1);
    }

    static class Storage {
        int [] list = new int[10];

        void set(int index, int value) {
            this.list[index] = value;
        }

        int [] get() {
            return this.list;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();
        Writer [] writers = new Worker[10];

        Writer.storage = storage;
        long lStartTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            writers [i] = new Writer (i);
            writers [i].start();
        }

        long lEndTime = System.currentTimeMillis();
        int [] localList = storage.get();

        for (int i = 0; i < 10; i++) {
            System.out.println(i+" - "+localList[i]);
        }
        System.out.println("Reading results after "+ (lEndTime-lStartTime) + " milisecs");
    }
}