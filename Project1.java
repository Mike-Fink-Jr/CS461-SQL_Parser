/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

/**
 *
 * @author Fink
 */
public class Project1 {

    public static void main (String args[]) {
         
        // testing the parser
        Parser parser = new Parser ("SELECT C1, C2 FROM T1 ");
        parser.run();
        
    }
}