package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.FuzzyDomain;
import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Union extends FuzzySetBinaryOperation 
{

	public Union(FuzzySetExpression left, FuzzySetExpression right) {
		super(left, right);
	}

	public void accept(FuzzyExpressionVisitor visitor) 
	{
		visitor.visitUnion(this);
	}

	public FuzzyDomain getDomain() {
		return getLeft().getDomain();
	}

	public String getLabel() {
		return getLeft() + "and" + getRight();
	}

	public double getMembership(double crispValue) {
		return Math.max(getLeft().getMembership(crispValue) , getRight().getMembership(crispValue));
	}


}
