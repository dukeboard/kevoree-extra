package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.FuzzyDomain;
import fr.irisa.triskell.fuzzy.core.FuzzySet;
import fr.irisa.triskell.fuzzy.core.FuzzyValue;
import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzySetReference extends FuzzySetExpression 
{
	private FuzzySet target;
	
	public FuzzySetReference(FuzzySet target)
	{
		this.target = target;
	}
	
	public void accept(FuzzyExpressionVisitor visitor) 
	{
		visitor.visitFuzzySetReference(this); 
	}

	public FuzzySet getTarget() 
	{
		return target;
	}

	public FuzzyDomain getDomain() 
	{
		return target.getDomain();
	}

	public String getLabel() 
	{
		return target.getLabel();
	}

	public double getMembership(double crispValue) 
	{
		return target.getMembership(crispValue);
	}

	public FuzzyValue fuzzify(double crispValue) 
	{
		return target.fuzzify(crispValue);
	}

}
