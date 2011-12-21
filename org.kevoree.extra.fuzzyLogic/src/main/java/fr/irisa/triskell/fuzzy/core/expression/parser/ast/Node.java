package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public interface Node 
{
	public void accept(ASTVisitor visitor);
	
	public int getLine();
	
	public int getColumn();
	
	public String getText();

}
