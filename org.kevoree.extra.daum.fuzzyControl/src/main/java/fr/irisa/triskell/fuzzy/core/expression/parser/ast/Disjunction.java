package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class Disjunction extends BinaryExpression {

	public Disjunction(Expression left, Expression right, int line, int pos) 
	{
		super(left, right, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitDisjunction(this);
	}

	public String getText() 
	{
		return getLeft().getText() + " or " + getRight().getText();	
	}

}
