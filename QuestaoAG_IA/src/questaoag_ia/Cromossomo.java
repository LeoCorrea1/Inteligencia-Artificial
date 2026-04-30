package questaoag_ia;

import java.util.List;

/**
 * Classe que representa um estado ou indivíduo de um problema para o AG
 * @author alexandrezamberlan
 */
public class Cromossomo implements Comparable<Cromossomo> {

    /**
     * atributo que armazena a rota de cidades
     */
    StringBuffer valor; 
    /**
     * variável que armazena o fitness de um estado. Aqui, quanto menor, melhor (menos restrições)
     */
    int aptidao;
    /**
     * atributo que converte a aptidao/fitness para um valor sobre 100 (porcentagem)
     */
    int porcentagemAptidao; 

    /**
     * Construtor que recebe um valor/palavra qualquer e a palavra final, calculando o fitness desse indivíduo
     * @param valor rota gerada
     * @param estadoFinal rota desejada (ex: "123456789")
     */
    public Cromossomo(StringBuffer valor, String estadoFinal) {
        this.valor = valor;
        this.aptidao = calcularAptidao(estadoFinal);
    }

    /**
     * Método que recebe um estado/indivíduo e retorna sua aptidão baseada em restrições
     * @param estadoFinal rota que se deseja gerar
     * @return o valor da aptidao do estado
     */
    int calcularAptidao(String estadoFinal) {
        int nota = 0;
        String s = this.valor.toString();

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) > s.charAt(j)) {
                    nota += 10;
                }
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    nota += 20;
                }
            }
        }
        return nota;
    }

    /**
     * Método sobreescrito para ser utilizado na ordenação (Crescente pois nota menor é melhor)
     * @param cromossomo representa o indivíduo
     * @return -1 se está ordenado
     */
    @Override
    public int compareTo(Cromossomo cromossomo) {
        if (this.aptidao < cromossomo.aptidao) {
            return -1;
        } else if (this.aptidao == cromossomo.aptidao) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cromossomo) {
            Cromossomo c = (Cromossomo)o;
            return this.valor.toString().equals(c.valor.toString());
        }
        return false;
    }
}
