/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Ana Claúdia, Bruno Marques e Matheus Martins
 */
public class Word  extends Token{
    private String lexeme = "";
    
    //Tokens de operadores relacionais
    public static final Word EQ = new Word ("=", Tag.EQ);
    public static final Word NE = new Word ("<>", Tag.NE);
    public static final Word LE = new Word ("<=", Tag.LE);
    public static final Word GE = new Word (">=", Tag.GE);
    public static final Word GR = new Word (">", Tag.GR);
    public static final Word LS = new Word ("<", Tag.LS);
    
    //Tokens de operadores matemáticos
    public static final Word SUM = new Word ("+", Tag.SUM);
    public static final Word MIN = new Word ("-", Tag.MIN);
    public static final Word MUL = new Word ("*", Tag.MUL);
    public static final Word DIV = new Word ("/", Tag.DIV);
    
    //Token do operador de atribuição
    public static final Word ATR = new Word(":=", Tag.ATR);
    
    //Token dos Símbolos de Pontuação
    public static final Word V = new Word ("+", Tag.V);
    public static final Word PV = new Word ("-", Tag.PV);
    public static final Word AP = new Word ("*", Tag.AP);
    public static final Word FP = new Word ("/", Tag.FP);
    
    public Word (String s, int tag){
        super (tag);
        lexeme = s;
    }
    
    @Override
    public String toString(){
        return "" + lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }
    
}

