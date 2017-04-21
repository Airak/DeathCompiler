/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Ana Cláudia, Bruno Marques e Matheus Martins
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
    
    String caminho = "D:\\Documentos\\Teste.txt";
    
    try {
        Lexer lex = new Lexer(caminho);
        String retorno;
        
        do{
            retorno = lex.scan().toString();
            System.out.println(retorno);
        }while(retorno!=null);
        
    } catch (Exception e) {
        System.err.println("Erro na leitura do arquivo. " + e.getMessage());
    }
  }
    
}
