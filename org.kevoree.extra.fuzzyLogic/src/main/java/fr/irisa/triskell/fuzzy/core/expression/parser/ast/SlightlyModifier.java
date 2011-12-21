package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class SlightlyModifier extends FuzzySetModifier {

	
	public SlightlyModifier(FuzzySetExpression expr, int line, int pos) 
	{
		super(expr, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitSlightlyModifier(this);
	}

	public String getText() 
	{
		return "slightly " + getExpr().getText();
	}

}
