package questaoag_ia;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Collections;

/**
 * Classe que executa um AG sobre o problema do roteamento
 * @author alexandrezamberlan
 */
public class AG {
    
    /**
     * Método de classe que gera a 1a população totalmente aleatória
     * @param populacao lista para os indivíduos gerados
     * @param tamanhoPopulacao quantos indivíduos/estados se quer colocar na lista
     * @param estadoFinal palavra/valor desejado
     */
    static void gerarPopulacao(List<Cromossomo> populacao, int tamanhoPopulacao, String estadoFinal){
        for (int i = 0; i < tamanhoPopulacao; i++) {
            populacao.add(new Cromossomo(Util.gerarPalavra(estadoFinal.length()), estadoFinal));
        }
    }

    /**
     * Método de classe que ordena uma lista contendo Cromossomos/Estados/Indivíduos
     * @param populacao 
     */
    static void ordenar(List<Cromossomo> populacao) {
        Collections.sort(populacao); 
    }

    /**
     * Método de classe que exibe os Cromossomos de uma lista
     * @param populacao lista contendo todos os cromossomos/indivíduos/estados 
     */
    static void exibir(List<Cromossomo> populacao){
        for (int i = 0; i < populacao.size(); i++) {
            System.out.println("Rota: " + populacao.get(i).valor + 
                               " - Penalidade: " + populacao.get(i).aptidao);
        }
    }

    /**
     * Método de classe que seleciona elementos para a novaPopulacao a partir do algoritmo de torneio
     * @param populacao lista com os Cromossomos da população atual
     * @param novaPopulacao lista para os Cromossomos selecionados
     * @param taxaSelecao porcentagem de quantos serão selecionados
     */
    static void selecionarPorTorneio(List<Cromossomo> populacao, List<Cromossomo> novaPopulacao, int taxaSelecao) {
        Cromossomo c1, c2, c3; 
        List<Cromossomo> torneio = new ArrayList<Cromossomo>();
        Cromossomo selecionado;

        int qtdSelecionados = taxaSelecao * populacao.size() / 100;
        novaPopulacao.add(populacao.get(0)); 
        
        Random gerador = new Random();
        int i = 1;
        do {
            c1 = populacao.get( gerador.nextInt(populacao.size()) );
            c2 = populacao.get( gerador.nextInt(populacao.size()) );
            c3 = populacao.get( gerador.nextInt(populacao.size()) );

            torneio.add(c1);
            torneio.add(c2);
            torneio.add(c3);
            ordenar(torneio);//o primeiro é o mais apto
            
            selecionado = torneio.get(0);
            if (!novaPopulacao.contains(selecionado)) { 
                novaPopulacao.add(selecionado);
                i++;
            }
            torneio.clear(); 
        } while (i < qtdSelecionados);
    }

    /**
     * Método de classe que gera novos Cromossomos a partir de um cromossomo Pai e outro Mae
     * @param populacao lista com os cromossomos vigentes
     * @param novaPopulacao lista para os cromossomos gerados no cruzamento
     * @param taxaReproducao porcentagem de quantos serão reproduzidos
     * @param estadoFinal palavra desejada
     */
    public static void reproduzir(List<Cromossomo> populacao, List<Cromossomo> novaPopulacao, int taxaReproducao, String estadoFinal) {
        String sPai, sMae, sFilho1, sFilho2;
        Random gerador = new Random();
        Cromossomo pai, mae;
        
        int qtdReproduzidos = taxaReproducao * populacao.size() / 100;

        int i = 0;
        do {
            pai = populacao.get( gerador.nextInt(populacao.size()) );
            mae = populacao.get( gerador.nextInt(populacao.size()) );

            sPai = pai.valor.toString();
            sMae = mae.valor.toString();
            
            sFilho1 = sPai.substring(0, sPai.length() / 2) + sMae.substring(sMae.length() / 2);
            sFilho2 = sMae.substring(0, sMae.length() / 2) + sPai.substring(sPai.length() / 2);

            novaPopulacao.add(new Cromossomo(new StringBuffer(sFilho1), estadoFinal));
            novaPopulacao.add(new Cromossomo(new StringBuffer(sFilho2), estadoFinal));
            i = i + 2;
        } while (i < qtdReproduzidos);
        
        while(novaPopulacao.size() > populacao.size()) {
            novaPopulacao.remove(novaPopulacao.size() - 1);
        }
    }

    /**
     * Método de classe com a função de esporadicamente mutar elementos da populacao
     * @param populacao lista de cromossomos vigentes
     * @param estadoFinal palavra desejada
     */
    public static void mutar(List<Cromossomo> populacao, String estadoFinal) {
        Random gerador = new Random();
        int qtdMutantes = gerador.nextInt(populacao.size() / 5 + 1); 
        int posicaoMutante;

        for (; qtdMutantes > 0; qtdMutantes--) {
            posicaoMutante = gerador.nextInt(populacao.size());
            Cromossomo mutante = populacao.get(posicaoMutante);
            
            char[] genes = mutante.valor.toString().toCharArray();
            genes[gerador.nextInt(genes.length)] = Util.letras.charAt(gerador.nextInt(Util.tamanho));
            
            populacao.set(posicaoMutante, new Cromossomo(new StringBuffer(new String(genes)), estadoFinal));
        }
    }

    public static void main(String[] args) {
        int tamanhoPopulacao = 20;
        String estadoFinal = "123456789";
        int taxaSelecao = 30;
        int taxaReproducao = 100 - taxaSelecao;
        int taxaMutacao = 10;
        int qtdGeracoes = 100;

        List<Cromossomo> populacao = new ArrayList<>();
        List<Cromossomo> novaPopulacao = new ArrayList<>();

        gerarPopulacao(populacao, tamanhoPopulacao, estadoFinal);
        ordenar(populacao);

        for (int i = 0; i < qtdGeracoes; i++) {
            selecionarPorTorneio(populacao, novaPopulacao, taxaSelecao);
            reproduzir(populacao, novaPopulacao, taxaReproducao, estadoFinal);
            
            if (i % 5 == 0) {
                mutar(novaPopulacao, estadoFinal);
            }
            
            populacao.clear();
            populacao.addAll(novaPopulacao);
            novaPopulacao.clear();
            ordenar(populacao);

            System.out.println("Geracao " + (i + 1) + " Melhor: " + populacao.get(0).valor + " (" + populacao.get(0).aptidao + ")");
            if(populacao.get(0).aptidao == 0) break;
        }
    }
}
