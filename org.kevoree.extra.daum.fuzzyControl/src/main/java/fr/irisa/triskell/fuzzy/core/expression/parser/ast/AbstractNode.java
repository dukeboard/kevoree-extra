package fr.irisa.triskell.fuzzy.core.expression.parser.ast;

public abstract class AbstractNode implements Node 
{
	
	private int line;
	private int pos;
	
	public AbstractNode(int line, int pos)
	{
		this.line = line;
		this.pos = pos;
	}
	
	public int getColumn() 
	{
		return pos;
	}

	public int getLine() 
	{
		return line;
	}

}
