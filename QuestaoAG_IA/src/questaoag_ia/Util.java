package questaoag_ia;

import java.util.Random;

/**
 * Classe que contém as letras do alfabeto no atributo letras. Além do método de gerarPalavra()
 * @author alexandrezamberlan
 */
public class Util {

    /**
     * Atributo de classe que armazena todos os números das cidades
     */
    public static String letras = "123456789";
    /**
     * Atributo de classe para guardar o tamanho do alfabeto
     */
    public static int tamanho = letras.length();
    
    /**
     * Método de classe que recebe a quantidade de caracteres que deve gerar a palavra
     * @param n quantidade de letras que a palavra gerada terá
     * @return uma palavra gerada aleatoriamente
     */
    public static StringBuffer gerarPalavra(int n) {
        StringBuffer palavra = new StringBuffer();
        Random gerador = new Random();
        for (int i = 0; i < n; i++) {
            palavra.append(letras.charAt(gerador.nextInt(tamanho)));
        }
        return palavra;
    }
}
