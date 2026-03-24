package ia_missionarios;

import java.util.LinkedList;
import java.util.List;

import busca.BuscaProfundidade;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;

public class IA_Missionarios implements Estado {

    private int mEsq, cEsq;
    private boolean barcoEsq;
    private String op;

    public IA_Missionarios(int mEsq, int cEsq, boolean barcoEsq, String op) {
        this.mEsq = mEsq;
        this.cEsq = cEsq;
        this.barcoEsq = barcoEsq;
        this.op = op;
    }

    @Override
    public String getDescricao() {
        return "Missionários e Canibais";
    }

    @Override
    public boolean ehMeta() {
        return mEsq == 0 && cEsq == 0;
    }

    @Override
    public int custo() {
        return 1;
    }

    private boolean valido(int m, int c) {
        int mDir = 3 - m;
        int cDir = 3 - c;

        if (m < 0 || c < 0 || m > 3 || c > 3) return false;

        if (m > 0 && m < c) return false;
        if (mDir > 0 && mDir < cDir) return false;

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        IA_Missionarios outro = (IA_Missionarios) obj;

        return mEsq == outro.mEsq &&
                cEsq == outro.cEsq &&
                barcoEsq == outro.barcoEsq;
    }

    @Override
    public int hashCode() {
        return mEsq * 100 + cEsq * 10 + (barcoEsq ? 1 : 0);
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> lista = new LinkedList<>();

        int[][] movimentos = {
                {2, 0},
                {0, 2},
                {1, 1},
                {1, 0},
                {0, 1}
        };

        for (int[] mov : movimentos) {
            int m = mov[0];
            int c = mov[1];

            int novoM = mEsq;
            int novoC = cEsq;

            if (barcoEsq) {
                novoM -= m;
                novoC -= c;
            } else {
                novoM += m;
                novoC += c;
            }

            if (valido(novoM, novoC)) {
                lista.add(new IA_Missionarios(
                        novoM,
                        novoC,
                        !barcoEsq,
                        "Move " + m + "M e " + c + "C"
                ));
            }
        }

        return lista;
    }

    @Override
    public String toString() {
        return op + " | Esq: M=" + mEsq + " C=" + cEsq +
                " | Dir: M=" + (3 - mEsq) + " C=" + (3 - cEsq) + "\n";
    }

    public static void main(String[] args) {

        IA_Missionarios inicial =
                new IA_Missionarios(3, 3, true, "Estado inicial");

        Nodo n = new BuscaProfundidade(new MostraStatusConsole())
                .busca(inicial);

        if (n == null) {
            System.out.println("Sem solução");
        } else {
            System.out.println(n.montaCaminho());
        }
    }
}