/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;

/**
 *
 * @author Matheus
 */
public class Syntactic {
    
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
    
    private void error() {
        System.exit(0);
    }
        
    int tok = getToken();
    
    void advance(){
        tok = getToken();
    }
    
    void eat(int t){
        if(tok==t)advance();
        else error();
    }
    
    //opcional
    void program (){      
        switch(tok){// init [decl-list] stmt-list stop
            case INIT: eat(INIT); /*advance(); if(tok==ID) decl_list();*/ stmt_list(); eat(STOP); break;                   
            default: error(); break;
        }   
    }
    
    void decl_list(){        
        switch(tok){//decl ";" { decl ";"}
            case ID: decl();
                    eat(PV); 
                    advance(); 
                    while(tok==ID){
                        decl();
                        eat(PV);
                        advance();
                    }break;
            default: error();
        }
    }
    
    void decl(){
        switch(tok){//ident-list is type
            case ID: ident_list(); eat(IS); type();break;
            default: error();
        }
    }
    
    void ident_list(){
        switch(tok){//identifier {"," identifier}
            case ID: identifier();
                    advance();
                    while(tok==V){
                        eat(V);
                        identifier();
                    }break;
            default: error();
        }
    }
    
    void type(){
        
    }
    
    void stmt_list(){
        switch(tok){//stmt ";" { stmt ";"}
            //letter,if,do,read,write
        }
    }
    
    void stmt(){

    }
    
    void assign_stmt(){
        
    }
    
    void if_stmt(){
        
    }
    
    void condition(){
        
    }
    
    void do_stmt(){
        
    }
    
    void do_suffix(){
        
    }
    
    void read_stmt(){
        
    }
    
    void write_stmt(){
        
    }
    
    void writeable(){
        
    }
    
    void expression(){
        
    }
    
    void simple_expr(){
        
    }
    
    void term(){
        
    }
    
    void factor_a(){
        
    }
    
    void factor(){
        
    }
    
    void relop(){
        switch(tok){//"=" |  ">" |   ">=" |   "<" |   "<=" |  "<>"
            case EQ: eat(EQ); break;//=
            case GE: eat(GE); break;//>=
            case LE: eat(LE); break;//<=
            case NE: eat(NE); break;//<>
            case GR: eat(GR); break;//>
            case LS: eat(LS); break;//< 
            default: error();
        }        
    }
    
    void addop(){
        switch(tok){//+ | - | or
            case SUM: eat(SUM); break;//+
            case MIN: eat(MIN); break;// -
            case OR: eat(OR); break;//or
            default: error();
        }
    }
    
    void mulop(){
        switch(tok){// "*" | "/" | and
            case MUL: eat(MUL); break;//*
            case DIV: eat(DIV); break;// /
            case AND: eat(AND); break;//and
            default: error();
        }        
    }
    
    void constant(){
        
    }
    
    void integer_const(){
        
    }
    
    void literal(){
        
    }
    
    void identifier(){
        
    }
    
    void if_stmt_prime(){
        switch(tok){//else begin stmt-list end |  λ
            case ELSE: eat(ELSE); eat(BEGIN); stmt_list(); eat(END);
            default: break;// λ              
        }        
    }
    
    void expression_prime(){        
        switch(tok){//relop simple-expr |  λ
            case EQ: 
            case GE: 
            case LE: 
            case NE: 
            case GR: 
            case LS: relop(); simple_expr(); break;
            default: break;// λ         
        }
    }
    
    void simple_expr_prime(){        
        switch(tok){//addop term simple-expr'
            case SUM:  
            case MIN: 
            case OR: addop(); term(); simple_expr_prime(); break;//or
            default: error();
        }
    }
    
    void term_prime(){        
        switch(tok){//mulop factor-a term' 
            case MUL://*
            case DIV:///
            case AND: mulop(); factor_a(); term_prime(); break;//and
            default: error();
        }          
    }
}
