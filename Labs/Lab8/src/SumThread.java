import java.util.ArrayList;
import java.util.List;

public class SumThread extends Thread {
    int num[], res, iInicial = 0, iFinal = 0;

    public SumThread(int num[], int iInicial, int iFinal) {
        this.num = num;
        this.res = 0;
        this.iInicial = iInicial;
        this.iFinal = iFinal;
    }

    @Override
    public void run() {
        for (int i=iInicial; i<iFinal; i++){
            //sum
            res += num[i];

            //max
            /*if(num[i] > res)
                res = num[i];*/
        }
    }

    public static int sum(int num[], int nthreads) throws InterruptedException {
        int res = 0, counter = 0, step = num.length / nthreads;
        SumThread threads [] = new SumThread[nthreads];

        for (int i=0; i<nthreads; i++) {
            threads[i] = new SumThread(num, counter, counter+step);
            threads[i].start();
            counter +=step;
        }

        for (int i=0; i<nthreads; i++) {
            threads[i].join();

            //sum
            res += threads[i].res;

            //max
            /*if(threads.get(i).res > res)
                res = threads[i].res;*/
        }

        return res;
    }

    public static int max(int array[], int nthreads) {
        int max = 0;

        return max;
    }

    public static void main(String[] args) throws InterruptedException {
        int num[] = {10, 20, 30};
        System.out.println(sum(num, 3));
    }
}