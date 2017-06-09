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

        String caminho;

        if (args.length >= 1) {
            caminho = args[0];
        } else {
            caminho = "entrada.txt";
        }
        try {
            Lexer lex = new Lexer(caminho);
            Syntactic syn = new Syntactic(lex);
            syn.program();
            System.out.println("Compilação concluída com sucesso!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}