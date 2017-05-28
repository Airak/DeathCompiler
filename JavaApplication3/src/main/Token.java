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
public class Token {
    
    private final int tag; //constante que representa o token
    private final String tipo;
    public final static String ID = "id";
    public final static String NUM = "num";
    public final static String LIT = "lit";
    public final static String RELOP = "rel_op";
    public final static String ATROP = "atr_op";
    public final static String ARITOP = "arit_op";
    public final static String SIMB = "simb_pont";
    public final static String RES = "pal_chave";
    
    public Token (int t, String tipo){
        tag = t;
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        return "<" + tipo + ">";
    }
    
    public int getTag(){
        return tag;
    }    
   
    public String getTipo() {
        return tipo;
    }
}