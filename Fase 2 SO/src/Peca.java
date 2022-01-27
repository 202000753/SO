public class Peca {
    private int m;
    private int maxComprimento;
    private int compPecas[];
    private int qtddPecas[];

    public Peca()
    {
        this.m = 0;
        this.maxComprimento = 0;
        this.compPecas = new int[m];
        this.qtddPecas = new int[m];
    }
    
    public Peca(int m, int maxComprimento, int[] compPecas, int[] qtddPecas) {
        this.m = m;
        this.maxComprimento = maxComprimento;
        this.compPecas = compPecas;
        this.qtddPecas = qtddPecas;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getMaxComprimento() {
        return maxComprimento;
    }

    public void setMaxComprimento(int maxComprimento) {
        this.maxComprimento = maxComprimento;
    }

    public int[] getCompPecas() {
        return compPecas;
    }

    public void setCompPecas(int[] compPecas) {
        this.compPecas = compPecas;
    }

    public int[] getQtddPecas() {
        return qtddPecas;
    }

    public void setQtddPecas(int[] qtddPecas) {
        this.qtddPecas = qtddPecas;
    }

    public void printQtddPecas()
    {
        System.out.printf("{ ");

        for(int j=0; j<m; j++)
        {
            System.out.printf("%d ", qtddPecas[j]);
        }

        System.out.println("}");
    }

    public void printCompPecas()
    {
        System.out.printf("{ ");

        for(int i=0; i<m; i++)
        {
            System.out.printf("%d ", compPecas[i]);
        }

        System.out.println("}");
    }

    public String strCompPecas() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (Integer v:compPecas) {
            sb.append(v + ", ");
        }

        sb.append("]");

        return sb.toString();
    }

    public String strQtddPecas() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (Integer v:qtddPecas) {
            sb.append(v + ", ");
        }

        sb.append("]");

        return sb.toString();
    }
}
