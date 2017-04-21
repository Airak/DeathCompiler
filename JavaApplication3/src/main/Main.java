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
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
    //Scanner ler = new Scanner(System.in);
    
    String caminho = "D:\\Documentos\\Teste.txt";
    
    Lexer lex = new Lexer(caminho); 
    String retorno;
    do{
        retorno = lex.scan().toString();
        System.out.println(retorno);
    }while(retorno!=null);

    /*String caminho = "D:\\Downloads\\Teste.txt"; //= ler.nextLine();

    System.out.printf("\nConteúdo do arquivo texto:\n");
    try {
        FileReader arq = new FileReader(caminho);
        BufferedReader lerArq = new BufferedReader(arq);
        
        char ch;
        int r = lerArq.read(); 
        
        while (r != -1) {//Quando retornar -1 é por que chegou ao final do arquivo
            
            ch = (char) r;
            System.out.printf("%c", ch); 

            r = lerArq.read(); // lê o segundo caracter
      }

      arq.close();
      
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
        e.getMessage());
    }*/
    
  }
    
    
  
    
}
