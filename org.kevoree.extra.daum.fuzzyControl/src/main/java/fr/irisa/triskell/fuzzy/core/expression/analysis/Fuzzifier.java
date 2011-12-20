package fr.irisa.triskell.fuzzy.core.expression.analysis;

import fr.irisa.triskell.fuzzy.control.rule.FuzzyRule;
import fr.irisa.triskell.fuzzy.core.FuzzyValue;
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
public class Fuzzifier implements FuzzyExpressionVisitor {

	private FuzzyValue fuzzyValueAccumulator;

	
	public FuzzyValue getResult()
	{
		return fuzzyValueAccumulator;
	}

	public void visitBinaryExpression(BinaryExpression target) 
	{
		// Abstract, nothing to do
	}

	public void visitConjunction(Conjunction target) {
		// Fuzzify the left member
		target.getLeft().accept(this);
		FuzzyValue leftFuzzyValue = fuzzyValueAccumulator;
		
		// Fuzzify the right member
		target.getRight().accept(this);
		FuzzyValue rightFuzzyValue = fuzzyValueAccumulator;
		
		// Keep the maximum
		fuzzyValueAccumulator = leftFuzzyValue.minimum(rightFuzzyValue);
	}

	public void visitDisjunction(Disjunction target) 
	{
		// Fuzzify the left member
		target.getLeft().accept(this);
		FuzzyValue leftFuzzyValue = fuzzyValueAccumulator;
		
		// Fuzzify the right member
		target.getRight().accept(this);
		FuzzyValue rightFuzzyValue = fuzzyValueAccumulator;
		
		// Keep the maximum
		fuzzyValueAccumulator = leftFuzzyValue.maximum(rightFuzzyValue);
	}

	public void visitExpression(FuzzyExpression target) 
	{
		// Abstract, nothing to do
	}

	public void visitFuzzyRule(FuzzyRule target) 
	{
		target.getAntecedent().accept(this);
	}

	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target) 
	{
		// Abstract, nothing to do	
	}

	public void visitFuzzySetExpression(FuzzySetExpression target) 
	{
		// Abstract, nothing to do	
	}

	public void visitFuzzySetModifier(FuzzySetModifier target) 
	{
		// Abstract, nothing to do	
	}

	public void visitFuzzySetReference(FuzzySetReference target) 
	{
		// Never reached
	}

	public void visitIntersection(Intersection target) 
	{
		// Never reached
	}

	public void visitModeratlyModifier(ModeratlyModifier target) 
	{
		// Never reached
	}

	public void visitNotModifier(NotModifier target) 
	{
		// Never reached	
	}

	public void visitSlightlyModifier(SlightlyModifier target) 
	{
		// Never reached
	}

	public void visitStatement(Statement target) 
	{
		fuzzyValueAccumulator = target.getValue().fuzzify(target.getTarget().getCrispValue());	
	}

	public void visitUnion(Union target) 
	{
		// Never reached
	}

	public void visitVeryModifier(VeryModifier target) 
	{
		// Never reached
	}

}
