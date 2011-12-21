package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.FuzzyDomain;
import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
import fr.irisa.triskell.fuzzy.core.fuzzyset.TriangleFunction;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class ModeratlyModifier extends FuzzySetModifier 
{

	public ModeratlyModifier(FuzzySetExpression expr) {
		super(expr, new TriangleFunction(0.25, 0.5, 0.75));
	}

	public void accept(FuzzyExpressionVisitor visitor) 
	{
		visitor.visitModeratlyModifier(this);
	}

	public FuzzyDomain getDomain() 
	{
		return getExpr().getDomain(); 
	}

	public String getLabel() {
		return "moderatly " + getExpr().getLabel();
	}

}
