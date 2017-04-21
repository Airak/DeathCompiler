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
public class Num extends Token{
    public final int value;
    
    public Num(int value){
        super(Tag.NUM);
        this.value = value;
    }
    
    @Override
    public String toString(){
        return "num: " + value;
    }
}

