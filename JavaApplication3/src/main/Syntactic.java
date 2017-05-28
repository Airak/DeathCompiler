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
            case Tag.INIT: eat(Tag.INIT); /*advance(); if(tok==Tag.ID) decl_list();*/ stmt_list(); eat(Tag.STOP); break;                   
            default: error(); break;
        }   
    }
    
    void decl_list(){        
        switch(tok){//decl ";" { decl ";"}
            case Tag.ID: decl();
                    eat(Tag.PV); 
                    advance(); 
                    while(tok==Tag.ID){
                        decl();
                        eat(Tag.PV);
                        advance();
                    }break;
            default: error();
        }
    }
    
    void decl(){
        switch(tok){//ident-list is type
            case Tag.ID: ident_list(); eat(Tag.IS); type();break;
            default: error();
        }
    }
    
    void ident_list(){
        switch(tok){//identifier {"," identifier}
            case Tag.ID: identifier();
                    advance();
                    while(tok==Tag.V){
                        eat(Tag.V);
                        identifier();
                        advance();
                    }break;
            default: error();
        }
    }
    
    void type(){
        switch(tok){//type ::= integer | string
            case Tag.NUM: eat(Tag.NUM); break;
            case Tag.LIT: eat(Tag.LIT); break;
            default: error();
        }        
    }
    
    void stmt_list(){
        switch(tok){//stmt ";" { stmt ";"}
            case Tag.ID:
            case Tag.IF:
            case Tag.DO:
            case Tag.READ:
            case Tag.WRITE:stmt();
                eat(Tag.PV);
                advance();
                while(tok==Tag.ID || tok==Tag.IF || tok==Tag.DO || tok==Tag.READ || tok==Tag.WRITE){
                    stmt();
                    eat(Tag.PV);
                    advance();
                }break;
        }
    }
    
    void stmt(){//letter,if,do,read,write       
        switch(tok){//assign-stmt |  if-stmt |  do-stmt |  read-stmt | write-stmt
            case Tag.ID: assign_stmt(); break;
            case Tag.IF: if_stmt(); break;
            case Tag.DO: eat(Tag.DO); do_stmt(); break;
            case Tag.READ: eat(Tag.READ); read_stmt(); break;
            case Tag.WRITE: eat(Tag.WRITE); write_stmt(); break;
            default: error();
        }
    }
    
    void assign_stmt(){
        switch(tok){// identifier ":=" simple_expr
            case Tag.ID: identifier(); eat(Tag.ATR); simple_expr();
            default: error();
        }
    }
    
    void if_stmt(){
        switch(tok){//if "(" condition  ")" begin stmt-list end if-stmt’
            case Tag.IF: eat(Tag.IF); eat(Tag.AP); condition(); eat(Tag.FP); 
                eat(Tag.BEGIN); stmt_list(); eat(Tag.END); if_stmt_prime(); break;
            default: error();
        }
    }
    
    void condition(){
        switch(tok){//expression
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: expression(); break;
            default: error();            
        }
    }
    
    void do_stmt(){
       switch(tok){//do stmt-list do-suffix
           case Tag.DO: eat(Tag.DO); stmt_list(); do_suffix(); break;
           default: error();
       } 
    }
    
    void do_suffix(){
        switch(tok){//while "(" condition ")"
            case Tag.WHILE: eat(Tag.WHILE); eat(Tag.AP); condition(); eat(Tag.FP); break;
            default: error();
        }        
    }
    
    void read_stmt(){
        switch(tok){//read "(" identifier ")"
            case Tag.READ: eat(Tag.READ); eat(Tag.AP); identifier(); eat(Tag.FP); break;
            default: error();
        }
    }
    
    void write_stmt(){
        switch(tok){//write "(" writable ")"
            case Tag.WRITE: eat(Tag.WRITE); eat(Tag.AP); writeable(); eat(Tag.FP); break;
            default: error();
        }
    }
    
    void writeable(){
        switch(tok){//simple-expr 
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: simple_expr(); break;
            default: error();            
        }
    }
    
    void expression(){
        switch(tok){//simple-expr expression'
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: simple_expr(); expression_prime(); break;
            default: error();            
        }
    }
    
    void simple_expr(){
        switch(tok){//= term simple-expr'
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: term(); simple_expr_prime(); break;
            default: error(); 
        }
    }
    
    void term(){
        switch(tok){// factor-a term'
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: factor_a(); term_prime(); break;
            default: error();            
        }
    }
    
    void factor_a(){
        switch(tok){//factor | not factor | "-" factor
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: factor();break;
            default: error();
        }
    }
    
    void factor(){
        switch(tok){//identifier | constant |  "(" expression ")"
            case Tag.ID: identifier(); break;
            case Tag.NUM:
            case Tag.LIT: constant(); break;
            case Tag.AP: eat(Tag.AP);expression();eat(Tag.FP);break;
            default: error();
        }        
    }
    
    void relop(){
        switch(tok){//"=" |  ">" |   ">=" |   "<" |   "<=" |  "<>"
            case Tag.EQ: eat(Tag.EQ); break;//=
            case Tag.GE: eat(Tag.GE); break;//>=
            case Tag.LE: eat(Tag.LE); break;//<=
            case Tag.NE: eat(Tag.NE); break;//<>
            case Tag.GR: eat(Tag.GR); break;//>
            case Tag.LS: eat(Tag.LS); break;//< 
            default: error();
        }        
    }
    
    void addop(){
        switch(tok){//+ | - | or
            case Tag.SUM: eat(Tag.SUM); break;//+
            case Tag.MIN: eat(Tag.MIN); break;// -
            case Tag.OR: eat(Tag.OR); break;//or
            default: error();
        }
    }
    
    void mulop(){
        switch(tok){// "*" | "/" | and
            case Tag.MUL: eat(Tag.MUL); break;//*
            case Tag.DIV: eat(Tag.DIV); break;// /
            case Tag.AND: eat(Tag.AND); break;//and
            default: error();
        }        
    }
    
    void constant(){
        switch(tok){//integer_const |  literal
            case Tag.NUM: integer_const(); break;
            case Tag.LIT: literal(); break;
            default: error();
        }
    }
    
    void integer_const(){
        switch(tok){//nozero {digit} | "0"
            case Tag.NUM: eat(Tag.NUM);break;
            default: error();
        }
    }
    
    void literal(){
        switch(tok){//" " " {caractere} " " "
            case Tag.LIT: eat(Tag.LIT);break;
            default: error();
        }
    }
    
    void identifier(){
        switch(tok){// (letter) {letter | digit | " _ " }
            case Tag.ID: eat(Tag.IF);break;
            default: error();
        }
    }
    
    void if_stmt_prime(){
        switch(tok){//else begin stmt-list end |  λ
            case Tag.ELSE: eat(Tag.ELSE); eat(Tag.BEGIN); stmt_list(); eat(Tag.END);
            default: break;// λ              
        }        
    }
    
    void expression_prime(){        
        switch(tok){//relop simple-expr |  λ
            case Tag.EQ: 
            case Tag.GE: 
            case Tag.LE: 
            case Tag.NE: 
            case Tag.GR: 
            case Tag.LS: relop(); simple_expr(); break;
            default: break;// λ         
        }
    }
    
    void simple_expr_prime(){        
        switch(tok){//addop term simple-expr'
            case Tag.SUM:  
            case Tag.MIN: 
            case Tag.OR: addop(); term(); simple_expr_prime(); break;//or
            default: error();
        }
    }
    
    void term_prime(){        
        switch(tok){//mulop factor-a term' 
            case Tag.MUL://*
            case Tag.DIV:///
            case Tag.AND: mulop(); factor_a(); term_prime(); break;//and
            default: error();
        }          
    }
}
