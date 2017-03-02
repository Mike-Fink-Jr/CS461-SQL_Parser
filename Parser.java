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
class Parser {

    Lexer lexer;
    Token token;
    int tabs;
    
    public Parser(String s){
        lexer = new Lexer(s + "$");
        token = lexer.nextToken();
    }

    public void run () {
        Query();
    }

    public void Query () 
    {
        tabs=0;
        tabber("<Query>\n",true);
        tabs++;
        keys(0);
        
        
       tabs=0;
       tabber("</Query>\n",false);            
    }
    
    public void keys(int call)         
    {
      
        String  val= token.getTokenValue();
        if(token.getTokenType()==Token.TokenType.KEY)
         {
             
             tabber("<Keyword>",true);
             switch(call)
             {
                 case 0:
                        if( val.equals("SELECT") )
                        {
                            tabber("SELECT</Keyword>\n",false);
                            IdLister();        
                        }   
                       else KeyError("SELECT"); //select not the first value
                break;
                 
                 case 1: 
                        if( val.equals("FROM") )
                        {
                            tabber("FROM</Keyword>\n",false);
                            IdLister();        
                        }   
                       else KeyError("FROM"); //select not the first value
                break;
                 
                 case 2:
                        if( val.equals("WHERE") )
                        {
                    
                            tabber("WHERE</Keyword>\n",false);
                            CondLister();
                                
                        }   
                       else KeyError("WHERE"); //select not the first value
             
                        break;
                
                case 3:
                        if( val.equals("AND") )
                        {
                         //should never reach here but included in case for future projects addons
                        }else KeyError("AND"); //select not the first value 
                break;
                
                default :
                           KeyError("INVALID"); 
                                        
                   }
              //     System.out.println("</Keyword>");
             }
         }
    
    
    public void CondLister()
    {
        token=lexer.nextToken();
        tabber("<CondList>\n",true);
        tabs++;
        if(token.getTokenType() == Token.TokenType.ID)
        {//Condition
            getCond();
          
        } else error(Token.TokenType.ID);
        token = lexer.nextToken();
        
        while(token.getTokenType()==Token.TokenType.KEY &&  token.getTokenValue().equals("AND"))
        {// token == KEY->AND
            
            tabber("<Keyword>AND</Keyword>\n",true);
            token=lexer.nextToken();
            if(token.getTokenType() == Token.TokenType.ID)
            {//Condition
                getCond();
            }else error(Token.TokenType.ID);
            token=lexer.nextToken();
        }
        tabs--;
        tabber("</CondList>\n",true);
    }
     
    public boolean checkTERM()
    {
         return(token.getTokenType()==Token.TokenType.INT || token.getTokenType()==Token.TokenType.FLOAT || token.getTokenType()==Token.TokenType.ID);        
    }
    
    public void getTerm()
    {  
        tabber("<TERM>\n",true);
        tabs++;
        
        if(token.getTokenType()== Token.TokenType.ID)
           tabber("<ID>"+token.getTokenValue()+"</ID>\n",true);
        
        else if(token.getTokenType()== Token.TokenType.INT)
           tabber("<INT>"+token.getTokenValue()+"</INT>\n",true);
        
        else if(token.getTokenType()== Token.TokenType.FLOAT)
            tabber("<FLOAT>"+token.getTokenValue()+"</FLOAT>\n",true);
        
        else error(Token.TokenType.ID);
        
        tabs--;
         tabber("</TERM>\n",true);
        
    }
    
    public void getCond()
    {   //already know first value is a ID
        tabber("<Cond>\n",true);
        tabs++;
        
        tabber("<ID>"+token.getTokenValue()+"</ID>\n",true);
        token = lexer.nextToken(); // get the operator token
        
        if(token.getTokenType()==Token.TokenType.OPERATOR)
        {
         tabber("<Operator>" + token.getTokenValue()+"</Operator>\n",true);            
            token= lexer.nextToken();
        }
        else error(Token.TokenType.OPERATOR);
 //       System.out.println("\n\ndid we get this far?\n");
        if(checkTERM())
        {
            getTerm();
            
        }
        else error(Token.TokenType.ID);
        tabs--;
        tabber("</Cond>\n", true);
        
        
        
    }
    
    
    public void IdLister()
    {
        token=lexer.nextToken();
        tabber("<IdList>\n",true);
        tabs++;
        
         if(token.getTokenType()==Token.TokenType.ID)
        {
            tabber("<Id>"+ token.getTokenValue() + "</Id>\n",true);
            token = lexer.nextToken();
        }  else error(Token.TokenType.ID);
        
        while(token.getTokenType()==Token.TokenType.COMMA)
        {
            tabber("<Comma>,</Comma>\n",true);    
            token = lexer.nextToken();  //now should have an ID
            
            if(token.getTokenType()==Token.TokenType.ID)
            {
               tabber("<Id>"+ token.getTokenValue() + "</Id>\n",true);
               token = lexer.nextToken();
            }
            else error(Token.TokenType.ID); //not an ID but should be
        }
        tabs--;
        tabber("</IdList>\n",true);
        
        if(token.getTokenType() != Token.TokenType.EOI) // not end of input look for keyword
            switch (token.getTokenValue())
            {
                case "FROM":
                        keys(1);//calls keys with FROM identifier
                break;
                
                case "WHERE":
                        keys(2);
                break;
                  
                default:
                        error(Token.TokenType.KEY);
                 
            }
    
    }
    
    
    
    private void error (Token.TokenType tp) 
    {
        System.err.println("Syntax error in Parser: expecting: " + Token.typeToString(tp)
                           + "; saw: "
                           + Token.typeToString(token.getTokenType()));
        System.err.println("val"+token.getTokenValue());
        System.exit(1);
    }
    
    
    private void KeyError(String str) 
    {
        System.err.println("Syntax KeyError in Parser: expecting:" +str+ ";  saw: "+ token.getTokenValue());
        System.exit(1); 
    
    }


private void tabber(String s,boolean addTab)
{  if(addTab)
    {
        String temp="";
        for(int x=0; x<tabs;x++)
            temp= temp+"\t";
        System.out.print(temp+s);

    }else System.out.print(s);
    

}
}