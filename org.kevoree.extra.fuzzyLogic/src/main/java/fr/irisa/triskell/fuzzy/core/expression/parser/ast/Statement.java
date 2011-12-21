package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class Statement extends Expression 
{
	private String target;
	private FuzzySetExpression value;
	
	public Statement(String target, FuzzySetExpression value, int line, int pos)
	{
		super(line, pos);
		this.target = target;
		this.value = value;
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitStatement(this);
	}

	public String getText() 
	{
		return target + " is " + value.getText();
	}

	public String getTarget() {
		return target;
	}

	public FuzzySetExpression getValue() {
		return value;
	}

}
