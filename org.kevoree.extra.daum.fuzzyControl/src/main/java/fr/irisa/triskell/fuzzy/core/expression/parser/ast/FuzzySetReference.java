package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class FuzzySetReference extends FuzzySetExpression {

	private String label;
	
	public FuzzySetReference(String label, int line, int pos)
	{
		super(line, pos);
		this.label = label;
	}
	
	public void accept(ASTVisitor visitor) 
	{
		visitor.visitFuzzySetReference(this);
	}

	public String getText() 
	{
		return label;
	}

	public String getLabel() {
		return label;
	}

}
