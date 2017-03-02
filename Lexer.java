/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.util.Arrays;

/**
 *
 * @author Fink
 */
class Lexer{
    private final String letters = "abcdefghijklmnopqrstuvmxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String digits = "0123456789";
    private final String[] keywords= {"SELECT","FROM","WHERE","AND"};
    private final String[] operators = {"'=",">", "<"};
    String stmt;
    int index = 0;
    char ch;
    
    public Lexer(String s){
        stmt = s; index=0; ch = nextChar();
    }

    public Token nextToken(){
        String s;
        do {
            if (Character.isLetter(ch)) 
            {
                String id = concat (letters + digits);
                //Handles ID
                if(Arrays.asList(keywords).contains(id))
                {
                    return new Token(Token.TokenType.KEY, id);
                }
                
                return new Token(Token.TokenType.ID, id);
            }
            else if (Character.isDigit(ch))
            {
                String num = concat(digits);
                
                if (ch != '.')
                    return new Token(Token.TokenType.INT, num);
                
                num += ch; 
                ch = nextChar();
                
                if (Character.isDigit(ch)) 
                {
                    num += concat(digits);
                    return new Token(Token.TokenType.FLOAT, num);
                } 
                else    //invalid 
                    return new Token(Token.TokenType.INVALID, num);
            } 
            else switch (ch) 
            {
                case ' ':   //Whitespace handler
                    ch = nextChar(); 
                break;
                
                case ',':
                    ch = nextChar();
                    return new Token(Token.TokenType.COMMA, ",");
                
                
                case '=':   
                case '>':
                case '<':
                    s= Character.toString(ch);
                    ch = nextChar();
                    return new Token(Token.TokenType.OPERATOR, s);
                
                case '$':
                    return new Token(Token.TokenType.EOI, "");
                
                default:
                    ch = nextChar();
                    return new Token(Token.TokenType.INVALID, 
                                     Character.toString(ch));
                }

        } while (true);
    }

    private char nextChar() {
        char Char = stmt.charAt(index); 
        index = index+1;
        return Char;
    }

    private String concat (String set) {
        StringBuilder r = new StringBuilder("");
        do 
        {
            r.append(ch); 
            ch = nextChar();
        }while (set.indexOf(ch) >= 0);
        
        return r.toString();
    }

    private boolean check(char c) {
        ch = nextChar();
        if (ch == c) {ch = nextChar(); return true;}
        else return false;
    }

    public void error (String msg) {
        System.err.println("\nError: location " + index + " " + msg);
        System.exit(1);
    }

}