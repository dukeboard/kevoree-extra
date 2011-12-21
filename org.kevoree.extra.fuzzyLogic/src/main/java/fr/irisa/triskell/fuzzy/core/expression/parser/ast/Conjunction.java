package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class Conjunction extends BinaryExpression {

	public Conjunction(Expression left, Expression right, int line, int pos) 
	{
		super(left, right, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitConjunction(this);
	}

	public String getText() {
		return getLeft().getText() + " and " + getRight().getText();
	}

}
