import java.util.ArrayList;
import java.util.List;

import busca.BuscaLargura;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;

public class Jarras implements Estado {

    private final int CAP1 = 6;
    private final int CAP2 = 5;

    private int j1;
    private int j2;
    private String operacao;

    public Jarras(int j1, int j2, String operacao) {
        this.j1 = j1;
        this.j2 = j2;
        this.operacao = operacao;
    }

    @Override
    public String getDescricao() {
        return "Problema das Jarras";
    }

    @Override
    public boolean ehMeta() {
        return (j1 == 2 && j2 == 0) || (j1 == 0 && j2 == 2);
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {

        List<Estado> lista = new ArrayList<>();

        adicionar(lista, new Jarras(CAP1, j2, "Encher jarra1"));
        adicionar(lista, new Jarras(j1, CAP2, "Encher jarra2"));
        adicionar(lista, new Jarras(0, j2, "Esvaziar jarra1"));
        adicionar(lista, new Jarras(j1, 0, "Esvaziar jarra2"));

        int a = j1;
        int b = j2;

        if (a > CAP2 - b) {
            a = a - (CAP2 - b);
            b = CAP2;
        } else {
            b = b + a;
            a = 0;
        }
        adicionar(lista, new Jarras(a, b, "Jarra1 -> Jarra2"));

        a = j1;
        b = j2;

        if (b > CAP1 - a) {
            b = b - (CAP1 - a);
            a = CAP1;
        } else {
            a = a + b;
            b = 0;
        }
        adicionar(lista, new Jarras(a, b, "Jarra2 -> Jarra1"));

        return lista;
    }

    private void adicionar(List<Estado> l, Jarras e) {
        if (!l.contains(e)) {
            l.add(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Jarras) {
            Jarras x = (Jarras) o;
            return j1 == x.j1 && j2 == x.j2;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (j1 * 31) + j2;
    }

    @Override
    public String toString() {
        return "(" + j1 + "," + j2 + ") " + operacao + "\n";
    }

    public static void main(String[] args) {

        Jarras inicial = new Jarras(0, 0, "estado inicial");

        System.out.println("busca em largura");

        Nodo n = new BuscaLargura(new MostraStatusConsole()).busca(inicial);

        if (n == null) {
            System.out.println("sem solucao!");
        } else {
            System.out.println("solucao:\n" + n.montaCaminho());
        }
    }
}
