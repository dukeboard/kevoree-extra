package fr.irisa.triskell.fuzzy.core.expression.parser;



import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Vector;

import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Conjunction;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Disjunction;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Expression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetExpression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetReference;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Intersection;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.ModeratlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.NotModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.SlightlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Statement;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Union;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.VeryModifier;


  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyExpressionParser 
{
	private TokenWindow window;
	private Vector<SyntaxError> errorList;	
	
	private int level;
	
	private void debugInfo(String message)
	{
		for(int i=0 ; i<level ; i++)
		{
			System.out.print("\t");
		}
		System.out.println("-DBG >>> " + message );
		
	}
	
	private void nextLevel(){ level++; }
	private void previousLevel(){ level--; }
	
	
	public FuzzyExpressionParser(Reader source) 
	{
		errorList = new Vector<SyntaxError>();
		window = new TokenWindow(new Tokenizer(source));
	}
	
	public boolean hasErrors(){
		return (!errorList.isEmpty());
	}
	
	public Vector<SyntaxError> getErrorList()
	{
		return errorList;
	}
	
	public String accept(int tokenType)
	{
		String result;
		if (window.getCurrentToken().getType() == tokenType)
		{
			result = window.getCurrentToken().getValue();
			window.getNextToken(); 
		} else {
			result = "error";
			if (tokenType == Token.IDENTIFIER){
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "an identifier", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.LABEL) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "a label", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.IS_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "is", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.AND_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "and", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.OR_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "or", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.VERY_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "very", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.SLIGHTLY_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "slightly", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.MODERATLY_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "moderatly", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.IMPLIES_KW) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "=>", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.OPEN_BRACKET) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "(", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			
			} else if (tokenType == Token.CLOSE_BRACKET) {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), ")", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
			}
			//System.err.println(errorList.firstElement());
			//System.exit(1);
		}
		return result;
	}
	
/*	public Rule parseRule()
	{
		//debugInfo("Looking for a rule");

		Expression left = parseConjunction();
		accept(Token.IMPLIES_KW);
		Expression right = parseConjunction();
		
		//debugInfo("Rule recognized");
		return new Rule(left, right, left.getLine(), left.getColumn());
	}*/
	
	public Expression parseFuzzyExpression()
	{
		return parseConjunction();
	}
	
	public Expression parseConjunction()
	{
		nextLevel();
		Expression result = null;
		//debugInfo("Looking for a conjunction");
		
		result = parseDisjunction();
		if (window.getCurrentToken().getType() == Token.AND_KW)
		{	
			int line; int pos;
			line = window.getCurrentToken().getLine();
			pos = window.getCurrentToken().getColumn();
			accept(Token.AND_KW);
			Expression left = result;
			Expression right = parseDisjunction();
			result = new Conjunction(left, right, line, pos);
			//debugInfo("Conjunction recognized");
		
		} else {
			//debugInfo("Conjunction not recognized");
			
		}
	
		//System.out.println(" >>> --- >>> Bringing back" + result);
		previousLevel();
		return result;
	}
	
	public Expression parseDisjunction()
	{
		nextLevel();
		//debugInfo("Looking for a disjunction");
		Expression result = null;
		
		result = parseStatement();		
		
		if ( window.getCurrentToken().getType() == Token.OR_KW ) {
			int line; int pos;
			line = window.getCurrentToken().getLine();
			pos = window.getCurrentToken().getColumn();
			accept(Token.OR_KW);
			Expression left = result;
			Expression right = parseStatement();
			result = new Disjunction(left, right, line , pos);
			//debugInfo("Disjunction recognized");
			
		} else {
			//debugInfo("Disjunction not recognized");
				
		}
		
		//System.out.println(" >>> --- >>> Bringing back" + result);
		previousLevel();
		return result;
	}
	
	
	public Expression parseStatement()
	{
		nextLevel();
		Expression result = null;
		//debugInfo("Looking for a statement ");		
		
		if ( window.getCurrentToken().getType() == Token.OPEN_BRACKET )
		{
			accept(Token.OPEN_BRACKET);
			result = parseConjunction();
			accept(Token.CLOSE_BRACKET);
			//debugInfo("Conjunction with parentheses recognized");

		} else {
			if ( window.getCurrentToken().getType() == Token.IDENTIFIER ) {
				String target = window.getCurrentToken().getValue();
				accept(Token.IDENTIFIER);
				int line; int pos;
				line = window.getCurrentToken().getLine();
				pos = window.getCurrentToken().getColumn();
				accept(Token.IS_KW);
				FuzzySetExpression value = parseIntersection();
				result = new Statement(target, value, line, pos);
	
				//debugInfo("Statement recognized");
				
			} else {
				errorList.add(new SyntaxError(window.getCurrentToken().getValue(), "an identifier", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn()));
				//debugInfo("Statement not recognized");
	
			}
		}

		//System.out.println(" >>> --- >>> Bringing back" + result);
		previousLevel();
		return result;
	} 
	
	public FuzzySetExpression parseIntersection()
	{	
		nextLevel();		
		//debugInfo("Looking for an intersection");
		FuzzySetExpression result = null;
		
		result = parseUnion();

		Token token1 = window.getNextToken();
		if ( token1.getType() == Token.AND_KW )
		{
			Token token2 = window.getNextToken();
			if ( token2.getType() != Token.IDENTIFIER )
			{
				if ( token2.getType() == Token.OPEN_BRACKET )
				{
					if ( window.getCurrentToken().getType() != Token.IDENTIFIER )
					{						
						window.pushBackToken(token2);
						window.pushBackToken(token1);
						int line; int pos;
						line = window.getCurrentToken().getLine();
						pos = window.getCurrentToken().getColumn();
						accept(Token.AND_KW);
						FuzzySetExpression left = result;
						FuzzySetExpression right = parseUnion();
						result = new Intersection(left, right, line, pos);
						//debugInfo("Intersection recognized");
					} else {
						window.pushBackToken(token2);
						window.pushBackToken(token1);
						//debugInfo("Intersection not recognized");
					}
					
				} else {
					window.pushBackToken(token2);
					window.pushBackToken(token1);
					int line; int pos;
					line = window.getCurrentToken().getLine();
					pos = window.getCurrentToken().getColumn();
					accept(Token.AND_KW);
					FuzzySetExpression left = result;
					FuzzySetExpression right = parseUnion();
					result = new Intersection(left, right, line, pos);
					//debugInfo("Intersection recognized");
				}
				
			} else {
				window.pushBackToken(token2);
				window.pushBackToken(token1);
				//debugInfo("Intersection not recognized");
			}

		} else {
			window.pushBackToken(token1);
			//debugInfo("Intersection not recognized");

		}
		
		//System.out.println(" >>> --- >>> Bringing back" + result);
		previousLevel();
		return result;
	}
	
	public FuzzySetExpression parseUnion()
	{
		nextLevel();
		//debugInfo("Looking for a union");
		FuzzySetExpression result = null;
		
		result = parseModifier();
		
		Token token1 = window.getNextToken();
		if ( token1.getType() == Token.OR_KW ) 
		{
			Token token2 = window.getNextToken();
			if ( token2.getType() != Token.IDENTIFIER )
			{
				if ( token2.getType() == Token.OPEN_BRACKET )
				{
	
					if ( window.getCurrentToken().getType() != Token.IDENTIFIER )
					{			
						window.pushBackToken(token2);
						window.pushBackToken(token1);
						int line; int pos;
						line = window.getCurrentToken().getLine();
						pos = window.getCurrentToken().getColumn();
						accept(Token.OR_KW);
						FuzzySetExpression left = result;
						FuzzySetExpression right = parseModifier();
						result = new Union(left, right, line, pos);
						//debugInfo("Union recognized");
					
					} else {
						// It's the end of a conjunction or a disjunction,
						window.pushBackToken(token2);
						window.pushBackToken(token1);
						//debugInfo("Union not recognized");					
					}
					
				} else {
					window.pushBackToken(token2);
					window.pushBackToken(token1);
					int line; int pos;
					line = window.getCurrentToken().getLine();
					pos = window.getCurrentToken().getColumn();
					accept(Token.OR_KW);
					FuzzySetExpression left = result;
					FuzzySetExpression right = parseModifier();
					result = new Union(left, right, line, pos);
					//debugInfo("Union recognized");
				}
				
			}  else {
				window.pushBackToken(token2);
				window.pushBackToken(token1);
				//debugInfo("Union not recognized");			
			}
			
		} else {
			window.pushBackToken(token1);
			//debugInfo("Union not recognized");			
		}
				
		//System.out.println(" >>> --- >>> Bringing back" + result);
		previousLevel();
		return result;
	}
	
	public FuzzySetExpression parseModifier()
	{
		nextLevel();
		//debugInfo("Looking for a modifier");

		FuzzySetExpression result = null;
		
		if ( window.getCurrentToken().getType() == Token.OPEN_BRACKET )
		{
			accept(Token.OPEN_BRACKET);
			result = parseIntersection();
			accept(Token.CLOSE_BRACKET);
			//debugInfo("Intersection with parentheses recognized");

		} else {
			if ( window.getCurrentToken().getType() == Token.LABEL) {
				int line; int pos;
				line = window.getCurrentToken().getLine();
				pos = window.getCurrentToken().getColumn();
				result = new FuzzySetReference(accept(Token.LABEL).replaceAll("'", ""), line, pos);
				//debugInfo("Label recognized");
				
			} else if ( window.getCurrentToken().getType() == Token.NOT_KW ) {
				int line; int pos;
				line = window.getCurrentToken().getLine();
				pos = window.getCurrentToken().getColumn();
				accept(Token.NOT_KW);
				FuzzySetExpression target = parseModifier();
				result = new NotModifier(target, line, pos);
				//debugInfo("NOT recognized");
				
			} else if ( window.getCurrentToken().getType() == Token.VERY_KW ){
				int line; int pos;
				line = window.getCurrentToken().getLine();
				pos = window.getCurrentToken().getColumn();
				accept(Token.VERY_KW);
				FuzzySetExpression target = parseModifier();
				result = new VeryModifier(target, line, pos);
				//debugInfo("VERY recognized");
				
			} else if ( window.getCurrentToken().getType() == Token.MODERATLY_KW ) {
				int line; int pos;
				line = window.getCurrentToken().getLine();
				pos = window.getCurrentToken().getColumn();
				accept(Token.MODERATLY_KW);
				FuzzySetExpression target = parseModifier();
				result = new ModeratlyModifier(target, line, pos);
				//debugInfo("MODERATLY recognized");
				
			} else if ( window.getCurrentToken().getType() == Token.SLIGHTLY_KW ) {
				int line; int pos;
				line = window.getCurrentToken().getLine();
				pos = window.getCurrentToken().getColumn();
				accept(Token.SLIGHTLY_KW);
				FuzzySetExpression target = parseModifier();
				result = new SlightlyModifier(target, line, pos);
				//debugInfo("SLIGHTLY recognized");
			
			} else {
				//debugInfo("Modifier not recongnized");
	
				if ( window.getCurrentToken().getType() != Token.IDENTIFIER ) 
				{
					SyntaxError error = new SyntaxError(window.getCurrentToken().getValue(), "not", window.getCurrentToken().getLine(), window.getCurrentToken().getColumn());
					error.addExpectation("very");
					error.addExpectation("moderatly");
					error.addExpectation("slightly");
					error.addExpectation("a label");
					errorList.add(error);
				}
			}
		}
		//System.out.println(" >>> --- >>> Bringing back" + result);
		previousLevel();
		return result;
	}	
	
	public static void main(String args[]) throws FileNotFoundException
	{
		FuzzyExpressionParser parser = new FuzzyExpressionParser( new StringReader("temp is slightly 'hot' or pressure is 'medium'") ); 
		
		parser.parseConjunction();
		if (parser.hasErrors())
		{
			for(SyntaxError se : parser.getErrorList())
			{
				System.out.println(se);
			}
		}
	}
}



/*
 
 	rule =
		conjunction "=>" conjunction
		;
	
	expression =
		

	conjunction =
		disjunction
		| conjonction "and" disjunctionjunction
		;
		
	disjunction	=
		statement
		| statement "or" disjunction
		;

	statement =
		identifier "is" intersection
		| "(" conjunction ")"
		;

	intersection =
		union "and" intersection
		| union 
		;
	
	union =
		modifier
		| union "or" modifier
		;
		
	modifier =
		 label
		| "not" modifier
		| "very" modifier
		| "slightly" modifier
		| "moderatly" modifier
		| "(" intersection ")"
		;
	
/*
 
 */
