package fr.irisa.triskell.fuzzy.core.expression.analysis;

import java.util.Hashtable;

import fr.irisa.triskell.fuzzy.control.rule.FuzzyRule;
import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.FuzzyValue;
import fr.irisa.triskell.fuzzy.core.FuzzyVariable;
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
public class Defuzzifier implements FuzzyExpressionVisitor {

	private FuzzyValue inputFuzzyValue;
	private Hashtable<FuzzyProperty, FuzzyVariable> result; 
	
	public Defuzzifier()
	{
		inputFuzzyValue = null;;
		result = new Hashtable<FuzzyProperty, FuzzyVariable>();
	}


	
	public Hashtable<FuzzyProperty, Double> getResult()
	{
		//for(FuzzyProperty fp : result.keySet())
		//{
		//	System.out.println(result.get(fp));
		//}
		
		Hashtable<FuzzyProperty, Double> defuzzifiedResult = new Hashtable<FuzzyProperty, Double>();
		for(FuzzyProperty fp : result.keySet())
		{
			Double temp = result.get(fp).defuzzify();		
			defuzzifiedResult.put(fp, temp);
		}

		return defuzzifiedResult;
	}



	public void setInputFuzzyValue(FuzzyValue inputFuzzyValue) {
		this.inputFuzzyValue = inputFuzzyValue;
	}


	public void visitBinaryExpression(BinaryExpression target) 
	{
		// Abstract, nothing to do
	}


	public void visitConjunction(Conjunction target) 
	{
		target.getLeft().accept(this);
		target.getRight().accept(this);
	}


	public void visitDisjunction(Disjunction target) 
	{
		target.getLeft().accept(this);
		target.getRight().accept(this);		
	}


	public void visitExpression(FuzzyExpression target) 
	{
		// Abstract, nothing to do
	}


	public void visitFuzzyRule(FuzzyRule target) 
	{
		target.getOutcome().accept(this);
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
		// never reached
	}


	public void visitIntersection(Intersection target) 
	{
		// never reached
	}


	public void visitModeratlyModifier(ModeratlyModifier target) 
	{
		// never reached
	}


	public void visitNotModifier(NotModifier target) 
	{
		// never reached
	}


	public void visitSlightlyModifier(SlightlyModifier target) 
	{
		// never reached
	}


	public void visitStatement(Statement target) {
		FuzzyProperty targetProperty = target.getTarget();
		
		FuzzySetExpression fs = target.getValue();
		FuzzyValue value = new FuzzyValue(fs, inputFuzzyValue.getMembership() );
		
		if ( !result.containsKey(targetProperty) ){
			// We create a new fuzzyVariable
			FuzzyVariable fv = new FuzzyVariable(target.getTarget().getFuzzyType());
			fv.aggregateFuzzyValue(value);
						
			result.put(targetProperty, fv);
			
		} else {
			// We get the fuzzy Variable and we aggregate the fuzzyValue
			FuzzyVariable fv = result.get(targetProperty);
			
			if( fv.defineFuzzySet(fs) )
			{
				FuzzyValue oldFuzzyValue = fv.getValueForFuzzySet(fs);
				FuzzyValue newFuzzyValue = oldFuzzyValue.minimum(value);
				fv.removeFuzzyValue(oldFuzzyValue);
				fv.aggregateFuzzyValue(newFuzzyValue);
			
			} else {
				fv.aggregateFuzzyValue(value);
				
			}
			
			result.put(targetProperty, fv);	
		}

	}


	public void visitUnion(Union target) 
	{
		// never reached
	}


	public void visitVeryModifier(VeryModifier target) 
	{
		// never reached
	}

}
