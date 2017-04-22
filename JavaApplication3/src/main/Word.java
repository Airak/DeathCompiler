/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Ana Cláudia, Bruno Marques e Matheus Martins
 */
public class Word  extends Token{
    private String lexeme = "";
    
    //Tokens de operadores relacionais
    public static final Word EQ = new Word ("=", Tag.EQ, Token.RELOP);
    public static final Word NE = new Word ("<>", Tag.NE, Token.RELOP);
    public static final Word LE = new Word ("<=", Tag.LE, Token.RELOP);
    public static final Word GE = new Word (">=", Tag.GE, Token.RELOP);
    public static final Word GR = new Word (">", Tag.GR, Token.RELOP);
    public static final Word LS = new Word ("<", Tag.LS, Token.RELOP);
    
    //Tokens de operadores matemáticos
    public static final Word SUM = new Word ("+", Tag.SUM, Token.ARITOP);
    public static final Word MIN = new Word ("-", Tag.MIN, Token.ARITOP);
    public static final Word MUL = new Word ("*", Tag.MUL, Token.ARITOP);
    public static final Word DIV = new Word ("/", Tag.DIV, Token.ARITOP);
    
    //Token do operador de atribuição
    public static final Word ATRIB = new Word(":=", Tag.ATR, Token.ATROP);
    
    //Token dos Símbolos de Pontuação
    public static final Word V = new Word (",", Tag.V, Token.SIMB);
    public static final Word PV = new Word (";", Tag.PV, Token.SIMB);
    public static final Word AP = new Word ("(", Tag.AP, Token.SIMB);
    public static final Word FP = new Word (")", Tag.FP, Token.SIMB);
    
    public Word (String s, int tag, String tipo){
        super (tag,tipo);
        lexeme = s;
    }
      
    @Override
    public String toString(){
        if (super.getTipo().equals(Token.RES)) return "<" + lexeme + ">";
        return "<" + super.getTipo() + ",'" + lexeme + "'>";
    }

    public String getLexeme() {
        return lexeme;
    }
    
}

