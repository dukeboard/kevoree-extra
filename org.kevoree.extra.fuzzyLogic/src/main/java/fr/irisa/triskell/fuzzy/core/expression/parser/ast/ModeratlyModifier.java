package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class ModeratlyModifier extends FuzzySetModifier {

	public ModeratlyModifier(FuzzySetExpression expr, int line, int pos) 
	{
		super(expr, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitModeratlyModifier(this);
	}

	public String getText() 
	{
		return "moderatly " + getExpr().getText();
	}

}
