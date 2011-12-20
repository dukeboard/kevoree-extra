package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.expression.analysis.FuzzyExpressionVisitor;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Statement extends FuzzyExpression
{
	private FuzzyProperty target;
	private FuzzySetExpression value;
	
	public Statement(FuzzyProperty target, FuzzySetExpression value)
	{
		this.target = target;
		this.value = value;
	}

	public FuzzyProperty getTarget() {
		return target;
	}

	public FuzzySetExpression getValue() {
		return value;
	}


	public void accept(FuzzyExpressionVisitor re) {
		re.visitStatement(this);
	}


	public boolean involve(FuzzyProperty fp) {
		return this.target.equals(fp);
	}
}
