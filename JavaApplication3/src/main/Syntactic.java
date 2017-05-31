/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;

/**
 *
 * @author Ana Cláudia, Bruno Marques e Matheus Martins
 */
public class Syntactic {
    
    Lexer lexer;            
    Token tok = null;
    boolean correct = true;
    
    public Syntactic (Lexer lexer) throws IOException{
        this.lexer = lexer;
        this.tok = lexer.scan(); //faz a leitura dos tokens
    }
    
    private void error() throws IOException {
        this.correct = false;
        if (this.tok.getTag() == 0){
            System.out.println("\nErro na linha " + lexer.line + ": Final de arquivo inesperado");
            System.exit(0);
        }
        else {
            System.err.println("\nErro na linha "+ Lexer.line +": Token " + tok.toString() + " não esperado.");
        }
        this.advance();
        
    }

    void advance() throws IOException{
            tok = lexer.scan();
    }
    
    void eat(int t) throws IOException{
        if(tok.getTag() ==t) advance();
        else error();
    }
    
    void program() throws IOException{
        //eat(Tag.INIT);
        switch(tok.getTag()){// init [decl-list] stmt-list stop
            case Tag.INIT: eat(Tag.INIT); /*advance(); if(tok==Tag.ID)*/ decl_list(); stmt_list(); eat(Tag.STOP); break;                   
            default: error(); break;
            //default: decl_list(); stmt_list(); eat(Tag.STOP); break;
        }   
    }
    
    void decl_list() throws IOException{
        switch(tok.getTag()){//decl ";" { decl ";"}
            case Tag.ID: 
                    decl();
                    eat(Tag.PV); 
                    advance(); 
                    while(tok.getTag()==Tag.ID){
                        decl();
                        eat(Tag.PV);
                        advance();
                    }
             break;
            default: error();
            
            //default: decl(); eat(Tag.PV); decl_list();
    
        }
    }
    
    void decl() throws IOException{
        switch(tok.getTag()){//ident-list is type
            case Tag.ID: ident_list(); eat(Tag.IS); type();break;
            default: error();
        }
    }
    
    void ident_list() throws IOException{
        switch(tok.getTag()){//identifier {"," identifier}
            case Tag.ID: identifier();
                    System.out.println("CHEGUEI AQUI \n"); 
                    advance(); 
                    while(tok.getTag()==Tag.V){
                        eat(Tag.V);
                        identifier();
                        advance();
                    }
                    break;
            default: error();
        }
    }
    
    void type() throws IOException{
        switch(tok.getTag()){//type ::= integer | string
            case Tag.INTEGER: eat(Tag.INTEGER); break;
            case Tag.STRING: eat(Tag.STRING); break;
            default: error();
        }        
    }
    
    void stmt_list() throws IOException{
        switch(tok.getTag()){//stmt ";" { stmt ";"}
            case Tag.ID:
            case Tag.IF:
            case Tag.DO:
            case Tag.READ:
            case Tag.WRITE:stmt();
                eat(Tag.PV);
                advance();
                while(tok.getTag()==Tag.ID || this.tok.getTag()==Tag.IF || this.tok.getTag()==Tag.DO || this.tok.getTag()==Tag.READ || this.tok.getTag()==Tag.WRITE){
                    stmt();
                    eat(Tag.PV);
                    advance();
                }break;
        }
    }
    
    void stmt() throws IOException{//letter,if,do,read,write
        switch(tok.getTag()){//assign-stmt |  if-stmt |  do-stmt |  read-stmt | write-stmt
            case Tag.ID: assign_stmt(); break;
            case Tag.IF: if_stmt(); break;
            case Tag.DO: do_stmt(); break;
            case Tag.READ: read_stmt(); break;
            case Tag.WRITE: write_stmt(); break;
            default: error();
        }
    }
    
    void assign_stmt() throws IOException{
        switch(tok.getTag()){// identifier ":=" simple_expr
            case Tag.ID: identifier(); eat(Tag.ATR); simple_expr();
            default: error();
        }
    }
    
    void if_stmt() throws IOException{
        switch(tok.getTag()){//if "(" condition  ")" begin stmt-list end if-stmt’
            case Tag.IF: eat(Tag.IF); eat(Tag.AP); condition(); eat(Tag.FP); 
                eat(Tag.BEGIN); stmt_list(); eat(Tag.END); if_stmt_prime(); break;
            default: error();
        }
    }
    
    void condition() throws IOException{
        switch(tok.getTag()){//expression
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: expression(); break;
            default: error();            
        }
    }
    
    void do_stmt() throws IOException{
       switch(tok.getTag()){//do stmt-list do-suffix
           case Tag.DO: eat(Tag.DO); stmt_list(); do_suffix(); break;
           default: error();
       } 
    }
    
    void do_suffix() throws IOException{
        switch(tok.getTag()){//while "(" condition ")"
            case Tag.WHILE: eat(Tag.WHILE); eat(Tag.AP); condition(); eat(Tag.FP); break;
            default: error();
        }        
    }
    
    void read_stmt() throws IOException{
        switch(tok.getTag()){//read "(" identifier ")"
            case Tag.READ: eat(Tag.READ); eat(Tag.AP); identifier(); eat(Tag.FP); break;
            default: error();
        }
    }
    
    void write_stmt() throws IOException{
        switch(tok.getTag()){//write "(" writable ")"
            case Tag.WRITE: eat(Tag.WRITE); eat(Tag.AP); writeable(); eat(Tag.FP); break;
            default: error();
        }
    }
    
    void writeable() throws IOException{
        switch(tok.getTag()){//simple-expr 
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: simple_expr(); break;
            default: error();            
        }
    }
    
    void expression() throws IOException{
        switch(tok.getTag()){//simple-expr expression'
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: simple_expr(); expression_prime(); break;
            default: error();            
        }
    }
    
    void simple_expr() throws IOException{
        switch(tok.getTag()){//= term simple-expr'
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: term(); simple_expr_prime(); break;
            default: error(); 
        }
    }
    
    void term() throws IOException{
        switch(tok.getTag()){// factor-a term'
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: factor_a(); term_prime(); break;
            default: error();            
        }
    }
    
    void factor_a() throws IOException{
        switch(tok.getTag()){//factor | not factor | "-" factor
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LIT: 
            case Tag.AP: 
            case Tag.NOT:
            case Tag.MIN: factor();break;
            default: error();
        }
    }
    
    void factor() throws IOException{
        switch(tok.getTag()){//identifier | constant |  "(" expression ")"
            case Tag.ID: identifier(); break;
            case Tag.NUM:
            case Tag.LIT: constant(); break;
            case Tag.AP: eat(Tag.AP);expression();eat(Tag.FP);break;
            default: error();
        }        
    }
    
    void relop() throws IOException{
        switch(tok.getTag()){//"=" |  ">" |   ">=" |   "<" |   "<=" |  "<>"
            case Tag.EQ: eat(Tag.EQ); break;//=
            case Tag.GE: eat(Tag.GE); break;//>=
            case Tag.LE: eat(Tag.LE); break;//<=
            case Tag.NE: eat(Tag.NE); break;//<>
            case Tag.GR: eat(Tag.GR); break;//>
            case Tag.LS: eat(Tag.LS); break;//< 
            default: error();
        }        
    }
    
    void addop() throws IOException{
        switch(tok.getTag()){//+ | - | or
            case Tag.SUM: eat(Tag.SUM); break;//+
            case Tag.MIN: eat(Tag.MIN); break;// -
            case Tag.OR: eat(Tag.OR); break;//or
            default: error();
        }
    }
    
    void mulop() throws IOException{
        switch(tok.getTag()){// "*" | "/" | and
            case Tag.MUL: eat(Tag.MUL); break;//*
            case Tag.DIV: eat(Tag.DIV); break;// /
            case Tag.AND: eat(Tag.AND); break;//and
            default: error();
        }        
    }
    
    void constant() throws IOException{
        switch(tok.getTag()){//integer_const |  literal
            case Tag.NUM: integer_const(); break;
            case Tag.LIT: literal(); break;
            default: error();
        }
    }
    
    void integer_const() throws IOException{
        switch(tok.getTag()){//nozero {digit} | "0"
            case Tag.NUM: eat(Tag.NUM);break;
            default: error();
        }
    }
    
    void literal() throws IOException{
        switch(tok.getTag()){//" " " {caractere} " " "
            case Tag.LIT: eat(Tag.LIT);break;
            default: error();
        }
    }
    
    void identifier() throws IOException{
        switch(tok.getTag()){// (letter) {letter | digit | " _ " }
            case Tag.ID: eat(Tag.ID);break;
            default: error();
        }
    }
    
    void if_stmt_prime() throws IOException{
        switch(tok.getTag()){//else begin stmt-list end |  λ
            case Tag.ELSE: eat(Tag.ELSE); eat(Tag.BEGIN); stmt_list(); eat(Tag.END);
            default: break;// λ              
        }        
    }
    
    void expression_prime() throws IOException{  
        switch(tok.getTag()){//relop simple-expr |  λ
            case Tag.EQ: 
            case Tag.GE: 
            case Tag.LE: 
            case Tag.NE: 
            case Tag.GR: 
            case Tag.LS: relop(); simple_expr(); break;
            default: break;// λ         
        }
    }
    
    void simple_expr_prime() throws IOException{ 
        switch(tok.getTag()){//addop term simple-expr'
            case Tag.SUM:  
            case Tag.MIN: 
            case Tag.OR: addop(); term(); simple_expr_prime(); break;//or
            default: error();
        }
    }
    
    void term_prime() throws IOException{        
        switch(tok.getTag()){//mulop factor-a term' 
            case Tag.MUL://*
            case Tag.DIV:///
            case Tag.AND: mulop(); factor_a(); term_prime(); break;//and
            default: error();
        }          
    }
}
