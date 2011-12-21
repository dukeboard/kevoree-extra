package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class Union extends FuzzySetBinaryOperation 
{

	public Union(FuzzySetExpression left, FuzzySetExpression right, int line, int pos) 
	{
		super(left, right, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitUnion(this);
	}
	
	public String getText() 
	{
		return getLeft().getText() + " or " + getRight().getText();
	}

}
