package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
import fr.irisa.triskell.fuzzy.core.fuzzyset.TriangleFunction;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class SlightlyModifier extends FuzzySetModifier 
{
	public SlightlyModifier(FuzzySetExpression target) {
		super(target, new TriangleFunction(0, 0.25, 0.5));
	}
	
	public void accept(FuzzyExpressionVisitor visitor) 
	{
		visitor.visitSlightlyModifier(this); 
	}

	public String getLabel() 
	{
		return "slightly " + getExpr().getLabel();
	}
}
