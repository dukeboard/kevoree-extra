package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Disjunction extends BinaryExpression implements RuleElement
{

	public Disjunction(FuzzyExpression left, FuzzyExpression right) {
		super(left, right);
	}


	public void accept(FuzzyExpressionVisitor re) {
		re.visitDisjunction(this);
	}

}
