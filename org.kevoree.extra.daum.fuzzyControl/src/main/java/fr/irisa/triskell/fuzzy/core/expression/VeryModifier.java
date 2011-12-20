package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
import fr.irisa.triskell.fuzzy.core.fuzzyset.LinearFunction;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class VeryModifier extends FuzzySetModifier 
{

	public VeryModifier(FuzzySetExpression expr) 
	{
		super(expr, new LinearFunction(0.5, 0, 1, 1));
	}

	public void accept(FuzzyExpressionVisitor visitor) {
		visitor.visitVeryModifier(this);

	}
	
	public String getLabel() 
	{
		return "very " + getExpr().getLabel();
	}
	
}
