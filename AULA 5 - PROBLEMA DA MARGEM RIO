import busca.busca.BuscaProfundidade;
import busca.busca.Estado;
import busca.busca.MostraStatusConsole;
import busca.busca.Nodo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProblemaMargemRio implements Estado {

    private char homem, carneiro, alface, lobo;

    static char Direita = 'd';
    static char Esquerda = 'e';

    private String operacao;

    public ProblemaMargemRio(char homem, char carneiro, char alface, char lobo, String operacao) {
        this.homem = homem;
        this.carneiro = carneiro;
        this.alface = alface;
        this.lobo = lobo;
        this.operacao = operacao;
    }

    @Override
    public String getDescricao() {
        return "Problema da Margem do Rio";
    }

    @Override
    public boolean ehMeta() {
        return (homem == 'd' && carneiro == 'd' && alface == 'd' && lobo == 'd');
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProblemaMargemRio)) return false;
        ProblemaMargemRio p = (ProblemaMargemRio) o;
        return homem == p.homem &&
                carneiro == p.carneiro &&
                alface == p.alface &&
                lobo == p.lobo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homem, carneiro, alface, lobo);
    }

    private boolean estadoValido() {

        if (carneiro == alface && homem != carneiro) return false;

        if (lobo == carneiro && homem != lobo) return false;

        return true;
    }

    private void adicionar(List<Estado> lista, ProblemaMargemRio e) {
        if (e.estadoValido()) {
            lista.add(e);
        }
    }

    @Override
    public List<Estado> sucessores() {

        List<Estado> lista = new ArrayList<>();

        char novoLado = (homem == Esquerda) ? Direita : Esquerda;

        adicionar(lista, new ProblemaMargemRio(novoLado, carneiro, alface, lobo, "Homem atravessou sozinho"));

        if (carneiro == homem)
            adicionar(lista, new ProblemaMargemRio(novoLado, novoLado, alface, lobo, "Homem levou o carneiro"));

        if (alface == homem)
            adicionar(lista, new ProblemaMargemRio(novoLado, carneiro, novoLado, lobo, "Homem levou a alface"));

        if (lobo == homem)
            adicionar(lista, new ProblemaMargemRio(novoLado, carneiro, alface, novoLado, "Homem levou o lobo"));

        return lista;
    }

    public String toString() {
        return operacao + " -> [H:" + homem +
                " C:" + carneiro +
                " A:" + alface +
                " L:" + lobo + "]";
    }

    public static void main(String[] args) {

        ProblemaMargemRio inicial = new ProblemaMargemRio(Esquerda, Esquerda, Esquerda, Esquerda, "Estado inicial");

        System.out.println("Busca em profundidade");

        Nodo n = new BuscaProfundidade(new MostraStatusConsole()).busca(inicial);

        if (n == null) {
            System.out.println("Sem solução!");
        } else {
            System.out.println("Solução:\n" + n.montaCaminho());
        }
    }
}
