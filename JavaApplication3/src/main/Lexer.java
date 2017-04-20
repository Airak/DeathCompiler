/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.*;
import java.util.*;

/**
 *
 * @author Matheus
 */

public class Lexer {
    
    public static int line = 1; //contador de linhas
    private char ch = ' '; //caractere lido do arquivo
    private FileReader file;
    
    private Hashtable words = new Hashtable();
    
    /* Método para inserir palavras reservadas na HashTable*/
    private void reserve(Word w){
        words.put(w.getLexeme(), w); // lexema é a chave para entrada na HashTable
  
    }
    
    //método construtor
    public Lexer(String fileName) throws FileNotFoundException{
        try{
            file = new FileReader (fileName);
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        //Insere palavras reservadas na HashTable
            reserve(new Word ("if", Tag.IF));
            reserve(new Word ("begin", Tag.BEGIN));
            reserve(new Word ("end", Tag.END));
            reserve(new Word ("int", Tag.INT));
            reserve(new Word ("init", Tag.INIT));
            reserve(new Word ("stop", Tag.STOP));
            reserve(new Word ("is", Tag.IS));
            reserve(new Word ("integer", Tag.INTEGER));
            reserve(new Word ("string", Tag.STRING));
            reserve(new Word ("else", Tag.ELSE));
            reserve(new Word ("do", Tag.DO));
            reserve(new Word ("while", Tag.WHILE));
            reserve(new Word ("read", Tag.READ));
            reserve(new Word ("write", Tag.WRITE));
            reserve(new Word ("not", Tag.NOT));
            reserve(new Word ("or", Tag.OR));
            reserve(new Word ("and", Tag.AND));
            reserve(new Word ("true", Tag.TRUE));
            reserve(new Word ("false", Tag.FALSE));

    }

        /*Lê o próximo caractere do arquivo*/
    private void readch() throws IOException{
        ch = (char) file.read();
    }

    /* Lê o próximo caractere do arquivo e verifica se é igual a c*/
    private boolean readch(char c) throws IOException{
        readch();
        if (ch != c) return false;
        ch = ' ';
        return true;
    }
    
    public Token scan() throws IOException{
        
        //Desconsidera delimitadores na entrada
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++; //conta linhas
            else break;        
        }
        
        switch(ch){
            //Operadores
            case '&':
                if (readch('&')) return Word.and;
                else return new Token('&');
            case '|':
                if (readch('|')) return Word.or;
                else return new Token('|');
            case '=':
                if (readch('=')) return Word.eq;
                else return new Token('=');
            case '<':
                if (readch('=')) return Word.le;
                else return new Token('<');
            case '>':
                if (readch('=')) return Word.ge;
                else return new Token('>');
        }
        
        //Numeros
        if (Character.isDigit(ch)){
            int value=0;
            do{
                value = 10*value + Character.digit(ch,10);
                readch();
            }while(Character.isDigit(ch));
            
            return new Num(value);
        }
        
        //Identificadores
        if (Character.isLetter(ch)){            
            StringBuffer sb = new StringBuffer();
            
            do{
                sb.append(ch);
                readch();
            }while(Character.isLetterOrDigit(ch) || Character.isDigit(ch) || ch == '_');
            
            String s = sb.toString();
            Word w = (Word)words.get(s);
            
            if (w != null) return w; //palavra já existe na HashTable HashTable
            
            w = new Word (s, Tag.ID);
            words.put(s, w);
            return w;
        }
        
        
        
        //Caracteres não especificados
        Token t = new Token(ch);
        ch = ' ';
        return t;
    }
}
