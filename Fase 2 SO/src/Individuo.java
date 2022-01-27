import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individuo implements Comparable<Individuo> {
    private List<Integer> array;
    private List<List<Integer>> pecas;
    private double cost;
    private List<Integer> wastes;
    private int maxComprimento;
    private int n;
    private int wins;

    public Individuo(int[] compPecas,int[] qtddPecas, int maxComprimento) {
        array = new ArrayList<>();
        pecas = new ArrayList<>();
        wastes = new ArrayList<>();

        this.wins = 0;

        this.maxComprimento = maxComprimento;
        createArray(compPecas, qtddPecas);

        int sumPecas = 0, i = 0;
        n = 1;
        List<Integer> list = new ArrayList<>();
        for (Integer peca:array) {
            sumPecas += peca;
            i++;

            if (sumPecas > maxComprimento) {
                n++;
                pecas.add(new ArrayList<>(list));
                list.clear();
                list.add(peca);
                sumPecas = peca;
            }
            else
                list.add(peca);

            if (sumPecas == maxComprimento) {
                n++;
                pecas.add(new ArrayList<>(list));
                list.clear();

                sumPecas = 0;
            }
        }
        if (!list.isEmpty())
            pecas.add(new ArrayList<>(list));

        //2 e 4
        calculateWaste(compPecas);
        this.cost = calculateCost(maxComprimento, qtddPecas);
    }

    public Individuo(int maxComprimento) {
        array = new ArrayList<>();
        wastes = new ArrayList<>();

        wastes.add(Integer.MAX_VALUE);

        this.maxComprimento = maxComprimento;

        cost = Double.MAX_VALUE;

        n = 0;
    }

    public Individuo(List<Integer> array, int[] compPecas,int[] qtddPecas, int maxComprimento) {
        this.array = array;
        this.maxComprimento = maxComprimento;
        pecas = new ArrayList<>();
        wastes = new ArrayList<>();

        int sumPecas = 0, i = 0;
        n = 1;
        List<Integer> list = new ArrayList<>();
        for (Integer peca:array) {
            sumPecas += peca;
            i++;

            if (sumPecas > maxComprimento) {
                n++;
                pecas.add(new ArrayList<>(list));
                list.clear();
                list.add(peca);
                sumPecas = peca;
            }
            else
                list.add(peca);

            if (sumPecas == maxComprimento) {
                n++;
                pecas.add(new ArrayList<>(list));
                list.clear();

                sumPecas = 0;
            }
        }
        if (!list.isEmpty())
            pecas.add(new ArrayList<>(list));

        //2 e 4
        calculateWaste(compPecas);
        this.cost = calculateCost(maxComprimento, qtddPecas);
    }

    private void createArray(int[] compPecas, int[] qtddPecas) {
        for (int i=0;i<compPecas.length;i++) {
            for (int j=0;j<qtddPecas[i];j++) {
                array.add(compPecas[i]);
            }
        }

        Collections.shuffle(array);
    }

    private double calculateCost(int maxComprimento, int[] qtddPecas) {
        if (array.isEmpty())
            return Double.MAX_VALUE;

        double custo = 0, v = 0;

        for (Integer waste:wastes) {
            custo += (double) Math.sqrt((double)waste/maxComprimento);

            if(waste > 0)
            {
                custo += (double) (1 / n);
            }
        }

        custo *= (double) 1/(n+1);

        return custo;
    }

    private void calculateWaste(int[] compPecas) {
        if (array.isEmpty())
            return;

        int sumPecas = 0, i = 0;

        for (Integer peca:array) {
            sumPecas += peca;


            if (sumPecas > maxComprimento) {
                wastes.add(maxComprimento - (sumPecas-peca));
                sumPecas = peca;
            }

            if (sumPecas == maxComprimento) {
                wastes.add(maxComprimento - sumPecas);
                sumPecas = 0;
            }

            i++;

            if(i == array.size()) {
                if (sumPecas > 0)
                    wastes.add(maxComprimento - sumPecas);
            }
        }
    }

    public void individuoToString() {
        if (array.isEmpty())
            return;

        for (List<Integer> list:pecas) {
            for (Integer peca:list) {
                System.out.printf("%d ", peca);
            }

            if(pecas.indexOf(list) < pecas.size()-1)
                System.out.printf("| ");
        }

        System.out.println("\n" + wastes);
        System.out.println("cost: " + cost);
    }

    public List<Integer> getArray() {
        return array;
    }

    public int getN() {
        return n;
    }

    public List<Integer> getWastes() {
        return wastes;
    }

    public List<List<Integer>> getPecas() {
        return pecas;
    }

    public int getMaxComprimento() {
        return maxComprimento;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(Individuo individuo) {
        if (this.wins >= individuo.wins)
            return -1;
        else
            return 0;
    }

    public void win() {
        this.wins++;
    }

    public void showList() {
        System.out.printf("[");

        for (int i=0;i<array.size();i++) {
            System.out.printf("%d", array.get(i));

            if (i < array.size())
                System.out.printf(", ");
        }

        System.out.printf("]\n");
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWins() {
        return wins;
    }

    public int getWaste() {
        int waste = 0;

        for (int val:wastes)
            waste += val;

        return waste;
    }
}