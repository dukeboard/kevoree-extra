package fr.irisa.triskell.fuzzy.core.expression;
   /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class FuzzySetBinaryOperation extends FuzzySetExpression 
{
	private FuzzySetExpression left;
	private FuzzySetExpression right;
	
	public FuzzySetBinaryOperation(FuzzySetExpression left, FuzzySetExpression right)
	{
		this.left = left;
		this.right = right;
	}

	public FuzzySetExpression getLeft() {
		return left;
	}

	public FuzzySetExpression getRight() {
		return right;
	}

}
