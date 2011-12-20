package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class NotModifier extends FuzzySetModifier {

	
	
	public NotModifier(FuzzySetExpression expr, int line, int pos) 
	{
		super(expr, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitNotModifier(this);
	}

	public String getText() 
	{
		return "not " + getExpr().getText();
	}

}
