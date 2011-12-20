package fr.irisa.triskell.fuzzy.core.expression.builder;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

import fr.irisa.triskell.fuzzy.control.ParserException;
import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.expression.Conjunction;
import fr.irisa.triskell.fuzzy.core.expression.Disjunction;
import fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression;
import fr.irisa.triskell.fuzzy.core.expression.RuleElement;
import fr.irisa.triskell.fuzzy.core.expression.parser.FuzzyExpressionParser;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.ASTVisitor;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Expression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetBinaryOperation;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetExpression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetReference;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Intersection;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.ModeratlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.NotModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Union;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.VeryModifier;

  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyExpressionBuilder implements ASTVisitor 
{
	
	private final static boolean DEBUGGING = true;
	private Vector<FuzzyProperty> context;
	private Vector<BuilderError> errorList;
	
	private RuleElement accumulator;
	private FuzzyProperty currentProperty;
	
	private int level;
	
	private void debugInfo(String message)
	{
		if (DEBUGGING)
		{
			for(int i=0 ; i<level ; i++)
			{
				System.out.print("\t");
			}
			System.out.println("-DBG >>> " + message );
		}
	}

	
	public FuzzyExpressionBuilder(Vector<FuzzyProperty> context) 
	{
		this.context = context;
		this.errorList = new Vector<BuilderError>();
	}
	
	public void build(String target) throws ParserException
	{
		errorList.clear();
		FuzzyExpressionParser parser =  new FuzzyExpressionParser(new StringReader(target));
		
		Expression parsingResult = parser.parseFuzzyExpression();
		if ( parser.hasErrors() ){ throw new ParserException(); } 
		
		parsingResult.accept(this);
	}
	
	public boolean hasError()
	{
		return !errorList.isEmpty();
	}
	
	
	public FuzzyExpression getResult()
	{
		return (FuzzyExpression) accumulator;
	}
	
	public void visitBinaryExpression(fr.irisa.triskell.fuzzy.core.expression.parser.ast.BinaryExpression target)
	{
		// Abstract: Nothing to do	
	}

	public void visitConjunction(fr.irisa.triskell.fuzzy.core.expression.parser.ast.Conjunction target) 
	{
		debugInfo("Visiting Conjunction!");
		level++;
		target.getLeft().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression left = (FuzzyExpression) accumulator;
		target.getRight().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression right = (FuzzyExpression) accumulator;
		accumulator = new Conjunction(left, right);		
	}

	public void visitDisjunction(fr.irisa.triskell.fuzzy.core.expression.parser.ast.Disjunction target) 
	{
		debugInfo("Visiting Disjunction!");
		level++;
		target.getLeft().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression left =  (FuzzyExpression) accumulator;
		target.getRight().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression right =  (FuzzyExpression) accumulator;
		accumulator = new Disjunction(left, right);
	}

	public void visitExpression(Expression target) 
	{
		// Abstract: Nothing to do			
	}

	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target) 
	{
		// Abstract: Nothing to do			
	}

	public void visitFuzzySetExpression(FuzzySetExpression target) 
	{
		// Abstract: Nothing to do	
	}

	public void visitFuzzySetModifier(FuzzySetModifier target) 
	{
		// Abstract: Nothing to do	
	}

	public void visitFuzzySetReference(FuzzySetReference target) 
	{
		
		String termName = target.getLabel();
		if ( currentProperty.getFuzzyType().isTermDefined(termName) )
		{
			accumulator = new fr.irisa.triskell.fuzzy.core.expression.FuzzySetReference(currentProperty.getFuzzyType().getTerm(termName));

		} else {
			errorList.add(new UnknownTermError(termName, target.getLine(), target.getColumn()));
		}			
		
	}

	public void visitIntersection(Intersection target) 
	{
		target.getLeft().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression left = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator;
		target.getRight().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression right = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator;
		if (!left.getDomain().equals(right.getDomain()))
		{
			errorList.add(new IncompatibleDomain(target.getLine(), target.getColumn()));
			
		} else {
			accumulator = new fr.irisa.triskell.fuzzy.core.expression.Intersection(left, right); 
		}
		
	}

	public void visitModeratlyModifier(ModeratlyModifier target) 
	{
	
		target.getExpr().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression expr = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator; 
		accumulator = new fr.irisa.triskell.fuzzy.core.expression.ModeratlyModifier(expr);
	}

	public void visitNotModifier(NotModifier target) 
	{
	
		target.getExpr().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression expr = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator; 
		accumulator = new fr.irisa.triskell.fuzzy.core.expression.NotModifier(expr);		
	}

	// TODO Remove this operation, deprecated
	/*public void visitRule(Rule target) 
	{
	
		target.getAntecedent().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression antecedent = (fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression) accumulator;
		target.getOutcome().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression outcome = (fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression) accumulator;
		accumulator = new FuzzyRule(antecedent, outcome);
	
	}*/

	public void visitSlightlyModifier(fr.irisa.triskell.fuzzy.core.expression.parser.ast.SlightlyModifier target) 
	{
		target.getExpr().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression expr = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator; 
		accumulator = new fr.irisa.triskell.fuzzy.core.expression.SlightlyModifier(expr);		
	}

	public void visitStatement(fr.irisa.triskell.fuzzy.core.expression.parser.ast.Statement target) 
	{
		
		debugInfo("Visiting Statement " + target.getTarget());
		// We check if the property exists
		boolean propertyFound = false;
		FuzzyProperty property = null;
		Iterator<FuzzyProperty> it = context.iterator();
		while( !propertyFound && it.hasNext())
		{
			property = it.next();
			if (property.getName().equals(target.getTarget())) {
				propertyFound = true;
			}
		}
		
		if ( !propertyFound )
		{
			errorList.add(new UnknownPropertyError(target.getTarget(), target.getLine(), target.getColumn()));
			
		} else {
			currentProperty = property;
			System.out.println(target.getValue());
			target.getValue().accept(this);
			debugInfo("Type of the Accumulator " + accumulator.getClass().getName());
			fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression expr = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator;
			accumulator = new fr.irisa.triskell.fuzzy.core.expression.Statement(property, expr);
		}		
	}

	public void visitUnion(Union target) 
	{
		target.getLeft().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression left = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator;
		target.getRight().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression right = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator;
		
		if (!left.getDomain().equals(right.getDomain()))
		{
			errorList.add(new IncompatibleDomain(target.getLine(), target.getColumn()));
			
		} else {
			accumulator = new fr.irisa.triskell.fuzzy.core.expression.Union(left, right); 
		}
		
	}

	public void visitVeryModifier(VeryModifier target) 
	{
		target.getExpr().accept(this);
		fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression expr = (fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression) accumulator; 
		accumulator = new fr.irisa.triskell.fuzzy.core.expression.VeryModifier(expr);			
	}

	



}
