import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Projeto extends Thread {
    private static Individuo result;
    private static Peca peca;
    private static int tempo, nIndividuos, nTeste;

    public Projeto(Peca peca, int nIndividuos, int tempo) {
        this.peca = peca;
        this.nIndividuos = nIndividuos;
        this.tempo = tempo;
    }

    @Override
    public void run() {
        algoritmo(peca, nIndividuos);
    }

    public static void main(String[] args) {
        result = null;
        Individuo individuo = null;
        nIndividuos = 75;
        String problema = "";
        int nthreads = 0;
        tempo = 0;
        Scanner scanner = new Scanner(System.in);
        nTeste = 0;

        System.out.printf("pcu ");
        problema = scanner.next();
        nthreads = scanner.nextInt();
        tempo = scanner.nextInt();
        scanner.close();

        Peca peca = new Peca();
        peca = readFile(problema);

        System.out.println("Problem = " + problema + "  Number Threads = " + nthreads + "  Time = " + tempo);
        System.out.println("Params{m=" + peca.getM() + ", maxLenght=" + peca.getMaxComprimento() + ", lenghts=" + peca.strCompPecas() + ", limits=" + peca.strQtddPecas() + "]}");

        Projeto threads [] = new Projeto[nthreads];

        for (int i=0;i<nthreads;i++) {
            threads[i] = new Projeto(peca, nIndividuos, tempo);
            threads[i].start();
        }

        try {
            sleep(tempo * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i=0; i<nthreads; i++) {
            threads[i].interrupt();

            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n\nMelhor solução");
        result.individuoToString();
    }

    public static Peca readFile (String filename)
    {
        Peca peca = new Peca();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(Projeto.class.getResourceAsStream(filename)));

            peca.setM(Integer.parseInt(in.readLine()));
            peca.setMaxComprimento(Integer.parseInt(in.readLine()));
            String str = in.readLine();

            String[] compData = str.split("\\s+");
            int[] compPecas = new int[peca.getM()];
            int i = 0;
            for(String part: compData)
            {
                compPecas[i] = Integer.parseInt(part);
                i++;
            }
            peca.setCompPecas(compPecas);

            String str2 = in.readLine();

            String[] qttdData = str2.split("\\s+");
            int[] qttdPecas = new int[peca.getM()];

            int j = 0;
            for(String part: qttdData)
            {
                qttdPecas[j] = Integer.parseInt(part);
                j++;
            }

            peca.setQtddPecas(qttdPecas);

        } catch (Exception e) {
            System.out.println("readFile: Erro");
        }

        return peca;
    }

    public void algoritmo(Peca peca, int n) {
        Individuo melhorIndividuo = new Individuo(peca.getMaxComprimento());
        List<Individuo> progenitores = new ArrayList<>();
        List<Individuo> descendentes = new ArrayList<>();
        List<Individuo> populacao = new ArrayList();
        int r = 0;
        Random rand = new Random();

        //1
        for (int i=0;i<n;i++) {
            progenitores.add(new Individuo(peca.getCompPecas(), peca.getQtddPecas(), peca.getMaxComprimento()));
            populacao.add(progenitores.get(i));

            if(progenitores.get(i).getCost() < melhorIndividuo.getCost())
                melhorIndividuo = progenitores.get(i);
        }

        do {
            if(interrupted()) {
                substituirResult(melhorIndividuo);

                break;
            }

            //3
            for (int i=0;i<n;i++) {
                descendentes.add(mutacao3PS(progenitores.get(i), peca));
                populacao.add(descendentes.get(i));

                if(descendentes.get(i).getCost() < melhorIndividuo.getCost())
                    melhorIndividuo = descendentes.get(i);
            }

            //5
            for(Individuo individuo:populacao)
            {
                individuo.setWins(0);
            }

            for(Individuo individuo:populacao)
            {
                for (int i=0;i<10;i++) {
                    do {
                        r = rand.nextInt(populacao.size());
                    } while (r == populacao.indexOf(individuo));

                    if(individuo.getCost() <= populacao.get(r).getCost())
                        individuo.win();
                }
            }

            //6
            Collections.sort(populacao);
            int max = populacao.size();
            for (int i=max/2;i<max;i++) {
                populacao.remove(populacao.get(max/2));
            }

            System.out.println("-------------- Test # " + (nTeste +1) + " ---------------------");
            melhorIndividuo.showList();
            System.out.println(melhorIndividuo.getWastes() + " Eval = " + (melhorIndividuo.getN() +melhorIndividuo.getWaste()) + " Best Iteration = " + 0 + " Best Time = " + 0);

            nTeste++;
        }while (true);
    }

    private  void substituirResult(Individuo melhorIndividuo) {if(result == null)
            result = melhorIndividuo;
        else if((result.getN() +result.getWaste()) < (melhorIndividuo.getN() +melhorIndividuo.getWaste()))
            result = melhorIndividuo;
    }

    private static Individuo mutacao3PS(Individuo progenitor, Peca peca) {
        int p1 = 0, p2 = 0, p3 = 0, aux = 0, n = 0;
        List<Integer> array = new ArrayList<>(progenitor.getArray());
        Random random = new Random();

        n = progenitor.getN();

        p1 = random.nextInt(array.size());
        p2 = calcularPosicao(progenitor, p1, array.size());
        p3 = calcularPosicao(progenitor, p1, p2);

        aux = array.get(p1);
        array.set(p1, array.get(p2));
        array.set(p2, array.get(p3));
        array.set(p3, aux);

        return new Individuo(array, peca.getCompPecas(), peca.getQtddPecas(), progenitor.getMaxComprimento());
    }

    private static int calcularPosicao(Individuo progenitor, int p1, int p2) {
        int posicao = 0, peca = 0, somaPosicoesPossiveis = 0;
        double divisor = 0, randNum = 0;
        List<Double> probabilidades = new ArrayList<>();
        Random random = new Random();

        //Probabilidades
        for(Integer waste:progenitor.getWastes()) {
            if (waste == 0) {
                probabilidades.add(0.0);
            }
            else {
                divisor = 0;

                for (Integer wastes : progenitor.getWastes()) {
                    if(wastes != 0)
                        divisor += Math.sqrt((double) 1 / wastes);
                }

                probabilidades.add(Math.sqrt((double) 1 / waste) / divisor);
            }
        }

        //Probabilidades Acumuladas
        for (int i=0;i<probabilidades.size();i++) {
            if(i > 0) {
                probabilidades.set(i, probabilidades.get(i-1)+probabilidades.get(i));
            }
        }

        randNum = random.nextDouble();

        for (int i=probabilidades.size()-1;i>=0;i--) {
            if(probabilidades.get(i) > randNum)
                peca = i;
        }

        for (int i=0;i<peca;i++) {
            if(progenitor.getPecas().get(i) != progenitor.getPecas().get(peca))
                somaPosicoesPossiveis += progenitor.getPecas().get(i).size();
        }


        posicao = random.nextInt((somaPosicoesPossiveis + progenitor.getPecas().get(peca).size()) - (somaPosicoesPossiveis)) + (somaPosicoesPossiveis);

        return posicao;
    }
}
