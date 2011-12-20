package fr.irisa.triskell.fuzzy.core.expression;

import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class BinaryExpression extends FuzzyExpression implements RuleElement
{
	private FuzzyExpression left;
	private FuzzyExpression right;
	
	public BinaryExpression(FuzzyExpression left, FuzzyExpression right)
	{
		if( right == null) { System.out.println("Error : Taggdaadad!"); }
		this.right = right;
		this.left = left;
	}

	public FuzzyExpression getLeft() {
		return left;
	}

	public FuzzyExpression getRight() {
		return right;
	}
	

	public boolean involve(FuzzyProperty fp) {
		return this.getLeft().involve(fp) || this.getRight().involve(fp);
	}


}
