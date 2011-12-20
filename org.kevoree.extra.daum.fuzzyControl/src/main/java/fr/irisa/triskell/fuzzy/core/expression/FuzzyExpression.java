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
public abstract class FuzzyExpression implements RuleElement
{
	public abstract void accept(FuzzyExpressionVisitor re);
	
	public abstract boolean involve(FuzzyProperty fp);
}
