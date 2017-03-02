/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

/**
 *
 * @author Fink
 * Token object class
 * Created for: Penn State CS 461
 * Project: 1 
 */
public class Token{
    public enum TokenType {INT, FLOAT, ID,KEY, COMMA, OPERATOR, EOI, INVALID}

    private TokenType type;
    private String val;

    Token (TokenType t, String v) {
        type = t; val = v;
    }

    TokenType getTokenType() {return type;}
    String getTokenValue() {return val;}

    void print () {
        String s = "";
        
        switch (type)
        {
        case INT:       case FLOAT:     case ID:         case KEY:
                    s = val; 
        break;
        
        case COMMA:
                s=",";
        break;
        
        case OPERATOR:
                    s = val; 
        break;
        
        case EOI:
                    s = "";
        
        case INVALID: 
                    s = "invalid";
        break;
        }
        System.out.print(s);
    }

    public static String typeToString (TokenType tp) {
        String s = "";
        switch (tp) 
        {
            case INT: s = "Int"; break;
            case FLOAT: s = "Float"; break;
            case ID: s = "ID"; break;  
            case KEY: s= "Keyword"; break;
            case COMMA: s="Comma"; break;
            case OPERATOR: s = "Operator"; break;
            case EOI: s= "End Of Input";break;
            case INVALID: s="Invalid"; break;

        }
        return s;
    }
    
}