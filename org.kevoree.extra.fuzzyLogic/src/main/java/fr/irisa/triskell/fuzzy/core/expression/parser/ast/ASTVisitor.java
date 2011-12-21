package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public interface ASTVisitor 
{
	public void visitExpression(Expression target);
	public void visitBinaryExpression(BinaryExpression target);
	public void visitConjunction(Conjunction target);
	public void visitDisjunction(Disjunction target);
	public void visitStatement(Statement target);
	public void visitFuzzySetExpression(FuzzySetExpression target);
	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target);
	public void visitUnion(Union target);
	public void visitIntersection(Intersection target);
	public void visitFuzzySetModifier(FuzzySetModifier target);
	public void visitVeryModifier(VeryModifier target);
	public void visitModeratlyModifier(ModeratlyModifier target);
	public void visitSlightlyModifier(SlightlyModifier target);
	public void visitNotModifier(NotModifier target);
	public void visitFuzzySetReference(FuzzySetReference target);	
}
