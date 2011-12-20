package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public abstract class BinaryExpression extends Expression 
{
	private Expression left;
	private Expression right;
	
	public BinaryExpression(Expression left, Expression right, int line, int pos)
	{
		super(line, pos);
		this.left = left;
		this.right = right;
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}

}
