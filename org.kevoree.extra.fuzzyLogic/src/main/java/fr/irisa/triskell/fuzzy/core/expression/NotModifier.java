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
public class NotModifier extends FuzzySetModifier {


	public NotModifier(FuzzySetExpression expr) 
	{
		super(expr, new LinearFunction(0, 1, 1, 0));
	}

	public void accept(FuzzyExpressionVisitor visitor) 
	{
		visitor.visitNotModifier(this);
	}

	public String getLabel() 
	{
		return "not " + getExpr().getLabel();
	}

}
