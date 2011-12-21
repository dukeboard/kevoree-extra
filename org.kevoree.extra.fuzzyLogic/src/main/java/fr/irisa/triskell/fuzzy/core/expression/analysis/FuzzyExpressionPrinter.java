package fr.irisa.triskell.fuzzy.core.expression.analysis;

import fr.irisa.triskell.fuzzy.control.rule.FuzzyRule;
import fr.irisa.triskell.fuzzy.core.expression.BinaryExpression;
import fr.irisa.triskell.fuzzy.core.expression.Conjunction;
import fr.irisa.triskell.fuzzy.core.expression.Disjunction;
import fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetBinaryOperation;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetModifier;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetReference;
import fr.irisa.triskell.fuzzy.core.expression.Intersection;
import fr.irisa.triskell.fuzzy.core.expression.ModeratlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.NotModifier;
import fr.irisa.triskell.fuzzy.core.expression.SlightlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.Statement;
import fr.irisa.triskell.fuzzy.core.expression.Union;
import fr.irisa.triskell.fuzzy.core.expression.VeryModifier;
 /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyExpressionPrinter implements FuzzyExpressionVisitor {

	private String result;
	
	public String getResult()
	{
		return result;
	}

	public void visitBinaryExpression(BinaryExpression target) 
	{
		// Abstract, nothing to do!
	}

	public void visitConjunction(Conjunction target) 
	{
		target.getLeft().accept(this);
		String left = result;
		
		target.getRight().accept(this);
		String right = result;
		
		result = "(" + left + " and " + right + ")";
	}

	public void visitDisjunction(Disjunction target) 
	{
		target.getLeft().accept(this);
		String left = result;
		
		target.getRight().accept(this);
		String right = result;
		
		result = "(" + left + " or " + right + ")";
	}

	public void visitExpression(FuzzyExpression target) 
	{
		// Abstract, nothing to do!
	}

	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target) 
	{
		// Abstract, nothing to do!
}

	public void visitFuzzySetExpression(FuzzySetExpression target) 
	{
		// Abstract, nothing to do!
	}

	public void visitFuzzySetModifier(FuzzySetModifier target) 
	{
		// Abstract, nothing to do!
	}

	public void visitFuzzySetReference(FuzzySetReference target) 
	{
		result = target.getLabel(); 	
	}

	public void visitIntersection(Intersection target) 
	{
		target.getLeft().accept(this);
		String left = result;
		
		target.getRight().accept(this);
		String right = result;
		
		result = "(" + left + " and " + right + ")";		
	}

	public void visitModeratlyModifier(ModeratlyModifier target) 
	{
		result = target.getLabel();
	}

	public void visitNotModifier(NotModifier target) 
	{
		result = target.getLabel();
	}

	public void visitSlightlyModifier(SlightlyModifier target) 
	{
		result = target.getLabel();
	}

	public void visitStatement(Statement target) 
	{
		target.getValue().accept(this);
		String expr = result;
		
		result = target.getTarget().getName() + " is " + expr;
		}

	public void visitUnion(Union target) 
	{
		target.getLeft().accept(this);
		String left = result;
		
		target.getRight().accept(this);
		String right = result;
		
		result = "(" + left + " or " + right + ")";
	}

	public void visitVeryModifier(VeryModifier target) 
	{
		result = target.getLabel();
	}

}
