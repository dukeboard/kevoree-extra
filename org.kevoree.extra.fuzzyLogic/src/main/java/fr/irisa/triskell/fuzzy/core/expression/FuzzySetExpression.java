package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.AbstractFuzzySet;
import fr.irisa.triskell.fuzzy.core.FuzzyValue;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class FuzzySetExpression implements RuleElement, AbstractFuzzySet
{
	
	public FuzzySetExpression()
	{}
	
	public FuzzyValue fuzzify(double crispValue) 
	{
		return new FuzzyValue(this, getMembership(crispValue));
	}
}
