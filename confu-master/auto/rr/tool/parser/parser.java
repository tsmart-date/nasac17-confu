
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Thu Nov 02 23:30:56 CST 2017
//----------------------------------------------------

package rr.tool.parser;

import rr.tool.*;
import acme.util.Assert;
import acme.util.option.*;
import java_cup.runtime.*;
import java.util.*;
import java.io.*;
import rr.simple.*;
import rr.split.*;
import rr.state.agent.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Thu Nov 02 23:30:56 CST 2017
  */
public class parser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\005\000\002\002\003\000\002\002\004\000\002\002" +
    "\005\000\002\002\005\000\002\002\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\013\000\006\006\005\010\004\001\002\000\012\002" +
    "\001\004\001\005\001\007\001\001\002\000\006\006\005" +
    "\010\004\001\002\000\010\002\011\004\010\005\007\001" +
    "\002\000\006\006\005\010\004\001\002\000\006\006\005" +
    "\010\004\001\002\000\004\002\000\001\002\000\012\002" +
    "\ufffe\004\ufffe\005\ufffe\007\ufffe\001\002\000\012\002\ufffd" +
    "\004\010\005\ufffd\007\ufffd\001\002\000\010\004\010\005" +
    "\007\007\015\001\002\000\012\002\uffff\004\uffff\005\uffff" +
    "\007\uffff\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\013\000\004\002\005\001\001\000\002\001\001\000" +
    "\004\002\013\001\001\000\002\001\001\000\004\002\012" +
    "\001\001\000\004\002\011\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



    static CommandLine cl;
    static ToolLoader loader;

	private static void prepTool(String name) {
		loader.prepToolClass(name);
  	}

	public static Tool build(ToolLoader l, String chain, CommandLine c) throws Exception {
		cl = c;
		loader = l;
		parser p = new parser(new Lexer(new StringReader(chain)));        
		T e = (T)p.parse().value;
		e.prep();
		return e.gen(last());
	}

    
    static protected Tool make(String name, Tool next) throws Exception {
		Class c = loader.loadToolClass(name);
		cl.addGroup(c.getSimpleName());
		Tool tool = (Tool)c.getConstructor(String.class, Tool.class, CommandLine.class).newInstance(c.getName(), next, cl);
		return tool;
    	
    }
    
    static protected Tool last() {
        return new LastTool("Last", null);
    }

	
	public void syntax_error(Symbol cur_token) {
	       Assert.fail("Syntax Error on token '" + cur_token.value + "'");
	}
	
	public void unrecovered_syntax_error(Symbol cur_token) {
	       syntax_error(cur_token);
	}

	static abstract class T { 
		public abstract Tool gen(Tool next) throws Exception;
		public abstract void prep();
	}

	static class Id extends T {
		protected String id;
		public Id(String id) {
		  this.id = id;
		} 
		public void prep() {
			prepTool(id);
		}
		public Tool gen(Tool next) throws Exception{
			return make(id, next);
		}
	}

	static class Seq extends T {
		protected T x1, x2;
		public Seq(T x1, T x2) {
		  this.x1 = x1;
		  this.x2 = x2;
		} 
		public void prep() {
			x1.prep();
			x2.prep();
		}
		
		public Tool gen(Tool next) throws Exception {
			return x1.gen(x2.gen(next));
		}
	}

	static class Bar extends T {
		protected T x1, x2;
		public Bar(T x1, T x2) {
		  this.x1 = x1;
		  this.x2 = x2;
		} 
		public void prep() {
			x1.prep();
			x2.prep();
		}
		
		public Tool gen(Tool next) throws Exception {
		   return new SplitTool("Split", x1.gen(last()), x2.gen(last()), next, cl) ;
		}
	}
	


}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {



  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // ToolSpec ::= ToolSpec BAR ToolSpec 
            {
              parser.T RESULT =null;
		int x1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int x1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		parser.T x1 = (parser.T)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int x2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int x2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		parser.T x2 = (parser.T)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new parser.Bar(x1, x2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ToolSpec",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // ToolSpec ::= ToolSpec COLON ToolSpec 
            {
              parser.T RESULT =null;
		int x1left = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int x1right = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		parser.T x1 = (parser.T)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int x2left = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int x2right = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		parser.T x2 = (parser.T)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new parser.Seq(x1, x2); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ToolSpec",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // ToolSpec ::= OPAREN ToolSpec CPAREN 
            {
              parser.T RESULT =null;
		int clleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int clright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		parser.T cl = (parser.T)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		 RESULT = cl; 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ToolSpec",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= ToolSpec EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		parser.T start_val = (parser.T)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // ToolSpec ::= ID 
            {
              parser.T RESULT =null;
		int xleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int xright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		String x = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		 RESULT = new parser.Id(x); 
              CUP$parser$result = parser.getSymbolFactory().newSymbol("ToolSpec",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

