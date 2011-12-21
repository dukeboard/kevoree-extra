package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class VeryModifier extends FuzzySetModifier 
{

	public VeryModifier(FuzzySetExpression expr, int line, int pos) 
	{
		super(expr, line, pos);
	}

	public void accept(ASTVisitor visitor) {
		visitor.visitVeryModifier(this);

	}

	public String getText() {
		return "very " + getExpr().getText();
}

}
