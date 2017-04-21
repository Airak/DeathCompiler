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
 * @author Ana Cláudia, Bruno Marques e Matheus Martins
 */

public class Lexer {
    
    public static int line = 1; //contador de linhas
    private char ch = ' '; //caractere lido do arquivo
    private FileReader file;
    
    private HashMap words = new HashMap();
    
    /* Método para inserir palavras reservadas no HashMap*/
    private void reserve(Word w){
        words.put(w.getLexeme(), w); // lexema é a chave para entrada no HashMap
  
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
        //Insere palavras reservadas no HashMap
            reserve(new Word ("if", Tag.IF));
            reserve(new Word ("begin", Tag.BEGIN));
            reserve(new Word ("end", Tag.END));
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

    }

        /*Lê o próximo caractere do arquivo*/
    private void readch() throws IOException{
        ch = Character.toLowerCase((char) file.read()); // A Linguagem não é case-sensitive!
    }

    /* Lê o próximo caractere do arquivo e verifica se é igual a c*/
    private boolean readch(char c) throws IOException{
        readch();
        if (ch != c) return false;
        ch = ' ';
        return true;
    }
    
    private void error() {
        System.err.println("\nErro na linha "+ line +": Token nÃ£o reconhecido! Caractere identificado como problema: "+ch);
        System.exit(-1);
    }
    
    public Token scan() throws IOException{
        
        //Desconsidera delimitadores na entrada
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++; //conta linhas
            else break;        
        }
        
        switch(ch){
            //Operadores relacionais
            case '=':
                readch();
                return Word.EQ;
            case '<':
                if (readch('=')) return Word.LE;
                else if (readch('>')) return Word.NE;
                else{
                    readch();
                    return Word.LS;
                }
            case '>':
                if (readch('=')) return Word.GE;
                else{
                    readch();
                    return Word.GR;
                }
            //Operadores aritméticos
            case '+':
                readch();
                return Word.SUM;
            case '-':
                readch();
                return Word.MIN;
            case '*':
                readch();
                return Word.MUL;
            //Operador de atribuição
            case ':':
                if (readch('=')) return Word.ATR;
            //Símbolos de pontuação
            case ',':
                readch();                
                return Word.V;
            case ';':
                readch();
                return Word.PV;
            case '(':
                readch();
                return Word.AP;
            case ')':
                readch();
                return Word.FP;               
            //Comentários:
            case '/'://Uma linha
                if (readch('/')){
                    for (;; readch()) {
                        if (ch!='\n') continue;
                        else{
                            line++;
                            readch();
                            break;
                        } 
                    } 
                }
                else return Word.DIV;
            case '{': //Várias linhas
                for (;; readch()) {
                    if (ch!='}'){
                        if(ch=='\n') line++;
                        continue;
                    }
                    else break;
                }
        }
        
        //Literais
        if(ch=='"'){
            StringBuilder sb = new StringBuilder();
            
            //Reconhece caracteres da tabela ASCII, exceto '\n' e '"'
            do{
                sb.append(ch);
                readch();
                if(ch=='"'){
                    sb.append(ch);
                    readch();
                    break;
                }
                else if (ch == '\n'){
                    error();
                    return null;
                }
            }while((int)ch>=0 && (int)ch<=255);
            
            String s = sb.toString();
            Word w = (Word)words.get(s);
            
            if (w != null) return w; //palavra já existe no HashMap
            
            w = new Word (s, Tag.LIT);
            words.put(s, w);
            return w;
        }
        
        //Números
        if (Character.isDigit(ch)){
            int value=0;
            if(Character.digit(ch,10)!=0){
                do{
                    value = 10*value + Character.digit(ch,10);
                    readch();
                }while(Character.isDigit(ch));
            }else{
                readch();
                if (!(ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b' || ch == '\n')){
                    error();
                    return null;
                }
            }         
            return new Num(value);
        }
        
        //Identificadores
        if (Character.isLetter(ch)){            
            StringBuilder sb = new StringBuilder();
            
            do{
                sb.append(ch);
                readch();
            }while(Character.isLetterOrDigit(ch) || ch == '_');
            
            String s = sb.toString();
            Word w = (Word)words.get(s);
            
            if (w != null) return w; //palavra já existe no HashMap 
            
            w = new Word (s, Tag.ID);
            words.put(s, w);
            return w;
        }
        
        //Caracteres não especificados
        error();
        return null;
    }
}
