package fr.irisa.triskell.fuzzy.control.rule;

import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression;
import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionPrinter;
/**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyRule
{
	private FuzzyExpression antecedent;
	private FuzzyExpression outcome;
	private boolean isActive;
	
	public FuzzyRule(FuzzyExpression antecedent, FuzzyExpression outcome)
	{
		this.antecedent = antecedent;
		this.outcome = outcome;
		this.isActive = true; 
	}
	
	public void activate()
	{
		this.isActive = true;
	}
	
	public void inactivate()
	{
		this.isActive = false;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public boolean involve(FuzzyProperty fp)
	{
		return antecedent.involve(fp) || outcome.involve(fp);
	}

	public FuzzyExpression getAntecedent() {
		return antecedent;
	}

	public FuzzyExpression getOutcome() {
		return outcome;
	}
	
	public String toString()
	{
		String result = new String();
		FuzzyExpressionPrinter printer = new FuzzyExpressionPrinter();
		
		this.antecedent.accept(printer);
		result += printer.getResult();
		result += " => ";
		this.outcome.accept(printer);
		result += printer.getResult();
		
		return result;
	}

}
