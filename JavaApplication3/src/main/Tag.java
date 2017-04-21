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
public class Tag {
    public final static int
            
            //Palavras reservadas
            INIT = 257,
            STOP = 258,
            IS = 259,
            INTEGER = 260,
            STRING = 261,
            IF = 262,
            BEGIN = 263,
            END = 264,
            ELSE = 265,
            DO = 266,
            WHILE = 267,
            READ = 268,
            WRITE = 269,
            NOT = 270,
            OR = 271,
            AND = 272,
            
            //Operadores relacionais
            EQ=290, //=
            GE=291, //>=
            LE=292, //<=
            NE=293, //<>
            GR=294, //>
            LS=295, //<
            
            //Operadores aritméticos
            SUM=296, //+
            MIN=297, //-
            MUL=298, //*
            DIV=299, // /
            
            //Operador de atribuição
            ATR=300, //:=
            
            //Símbolos de pontuação
            PV = 301, //;
            V = 302, //,
            AP = 303, //(
            FP = 304, //)
            
            //Outros tokens
            NUM = 305,
            ID = 306,
            LIT = 307;   
}

