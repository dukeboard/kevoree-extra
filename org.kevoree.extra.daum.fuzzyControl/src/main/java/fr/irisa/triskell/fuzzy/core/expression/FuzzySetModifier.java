package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.FuzzyDomain;
import fr.irisa.triskell.fuzzy.core.fuzzyset.Function;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class FuzzySetModifier extends FuzzySetExpression 
{	
	private FuzzySetExpression expr;
	private Function membership; 
	
	public FuzzySetModifier(FuzzySetExpression expr, Function membership)
	{
		this.expr = expr;
		this.membership = membership;
	}

	public FuzzySetExpression getExpr() {
		return expr;
	}

	public Function getMembership() {
		return membership;
	}

	public FuzzyDomain getDomain() {
		return expr.getDomain();
	}
	
	public double getMembership(double crispValue) 
	{
		return membership.getMembership(getExpr().getMembership(crispValue));
	}

}
