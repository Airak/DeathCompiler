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
            reserve(new Word ("if", Tag.IF, Token.RES));
            reserve(new Word ("begin", Tag.BEGIN, Token.RES));
            reserve(new Word ("end", Tag.END, Token.RES));
            reserve(new Word ("init", Tag.INIT, Token.RES));
            reserve(new Word ("stop", Tag.STOP, Token.RES));
            reserve(new Word ("is", Tag.IS, Token.RES));
            reserve(new Word ("integer", Tag.INTEGER, Token.RES));
            reserve(new Word ("string", Tag.STRING, Token.RES));
            reserve(new Word ("else", Tag.ELSE, Token.RES));
            reserve(new Word ("do", Tag.DO, Token.RES));
            reserve(new Word ("while", Tag.WHILE, Token.RES));
            reserve(new Word ("read", Tag.READ, Token.RES));
            reserve(new Word ("write", Tag.WRITE, Token.RES));
            reserve(new Word ("not", Tag.NOT, Token.RES));
            reserve(new Word ("or", Tag.OR, Token.RES));
            reserve(new Word ("and", Tag.AND, Token.RES));

    }

        /*Lê o próximo caractere do arquivo*/
    private void readch() throws IOException{
        ch = Character.toLowerCase((char) file.read()); // A Linguagem não é case-sensitive!
    }

    /* Lê o próximo caractere do arquivo e verifica se é igual a c*/
    private boolean readch(char c) throws IOException{
        readch();
        if (ch != c) {
            return false;
        }
        ch = ' ';
        return true;
    }
    
    private void error() {
        System.err.println("\nErro na linha "+ line +": Token não reconhecido - caractere com provável erro: "+ch);
        System.exit(0);
    }
    
    private void error(String msg) {
        System.err.println(msg);
        System.exit(0);
    }
    
    public Token scan() throws IOException{
        
        //Desconsidera delimitadores e comentários na entrada
        for (;; readch()) {
            //Comentários
            if(ch == '/'){ //Uma linha
                if (readch('/')){
                    for (;; readch()) {
                        if (Integer.valueOf(ch) == 65535) { //Fim de arquivo
                            System.exit(0);
                        }
                        if (ch=='\n') {
                            line++;
                            break;
                        }
                    } 
                }
                else return Word.DIV;//Divisão
            }
            else if(ch=='{'){//Várias linhas
                int linhaInicioComentario = line;
                for (;; readch()) {
                    if (ch!='}'){
                        if (Integer.valueOf(ch) == 65535) { //Fim de arquivo
                            error("Comentário de bloco iniciado na linha "+linhaInicioComentario+" sem fim!");
                        }
                        if(ch=='\n') line++;
                    }
                    else{
                        ch = ' ';
                        break;
                    }                        
                }
            }
            //Delimitadores
            else if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++; //conta linhas
            else break;        
        }
        
        switch(ch){
            //Operadores relacionais
            case '=':
                ch = ' ';
                return Word.EQ;
            case '<':
                readch();
                char chAux = ch;
                if (chAux == '=') { ch = ' '; return Word.LE; }
                else if (chAux == '>') { ch = ' '; return Word.NE; }
                else return Word.LS;
            case '>':
                if (readch('=')) return Word.GE;
                else return Word.GR;
            //Operadores aritméticos
            case '+':
                ch = ' ';
                return Word.SUM;
            case '-':
                ch = ' ';
                return Word.MIN;
            case '*':
                ch = ' ';
                return Word.MUL;
            //Operador de atribuição
            case ':':
                if (readch('=')) return Word.ATRIB;
                break;
            //Símbolos de pontuação
            case ',':
                ch = ' ';
                return Word.V;
            case ';':
                ch = ' ';
                return Word.PV;
            case '(':
                ch = ' ';
                return Word.AP;
            case ')':
                ch = ' ';
                return Word.FP; 
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
            
            w = new Word (s, Tag.LIT,Token.LIT);
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
                if (Character.isLetterOrDigit(ch)){
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
            }while(Character.isLetterOrDigit(ch) || Character.isDigit(ch) || ch == '_');
            
            String s = sb.toString();
            Word w = (Word)words.get(s);
            
            if (w != null) return w; //palavra já existe no HashMap 
            
            w = new Word (s, Tag.ID, Token.ID);
            words.put(s, w);
            return w;
        }
                
        //Caracteres não especificados
        if (!(file.read()==-1)) { 
            error();
        }
        return null;
    }
}
