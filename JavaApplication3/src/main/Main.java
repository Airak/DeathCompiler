/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;

/**
 *
 * @author Ana Cláudia, Bruno Marques e Matheus Martins
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    //Scanner ler = new Scanner(System.in);

    try {
        String caminho = "C:\\Users\\Ana Cláudia\\Documents\\CEFET\\Compiladores\\DeathCompiler\\DeathCompiler\\JavaApplication3\\test\\teste7.txt";
        Lexer lex = new Lexer(caminho);
        //Token retorno = lex.scan();
        /*while (retorno != null){
            System.out.println(retorno.toString() + " " + retorno.getTag());
            retorno = lex.scan();
        }*/
        Syntactic syn = new Syntactic(lex);
        syn.program();
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }

  }

}