package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public abstract class FuzzySetBinaryOperation extends FuzzySetExpression 
{
	private FuzzySetExpression left;
	private FuzzySetExpression right;
	
	public FuzzySetBinaryOperation(FuzzySetExpression left, FuzzySetExpression right, int line, int pos)
	{
		super(line, pos);
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
