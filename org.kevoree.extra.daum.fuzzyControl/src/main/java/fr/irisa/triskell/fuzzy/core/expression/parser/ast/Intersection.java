package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public class Intersection extends FuzzySetBinaryOperation {

	public Intersection(FuzzySetExpression left, FuzzySetExpression right, int line, int pos) {
		super(left, right, line, pos);
	}

	public void accept(ASTVisitor visitor) 
	{
		visitor.visitIntersection(this);
	}

	public String getText() {
		return getLeft().getText() + " and " + getRight().getText();
	}

}
