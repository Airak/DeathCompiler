/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Matheus
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
            TRUE = 273,
            FALSE = 274,
            INT = 275,
            //Operadores
            EQ=290,
            GE=291,
            LE=292,
            NE=293,            
            //Outros tokens
            NUM = 300,
            ID = 301;   
}
