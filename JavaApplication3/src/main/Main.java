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
        String caminho = "/Users/bruno/Desktop/DeathCompiler/teste/teste1.txt";
        Lexer lex = new Lexer(caminho); 
        Token retorno = lex.scan();
        while (retorno != null){
            System.out.println(retorno.toString());
            retorno = lex.scan();
        }
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }

  }    
      
}
