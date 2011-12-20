package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public abstract class FuzzySetModifier extends FuzzySetExpression 
{	
	private FuzzySetExpression expr;
	
	public FuzzySetModifier(FuzzySetExpression expr, int line, int pos)
	{
		super(line, pos);
		this.expr = expr;
	}

	public FuzzySetExpression getExpr() {
		return expr;
	}

}
