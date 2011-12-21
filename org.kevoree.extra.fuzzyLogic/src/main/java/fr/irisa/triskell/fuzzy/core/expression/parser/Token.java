package fr.irisa.triskell.fuzzy.core.expression.parser;

public class Token 
{
	public final static int EOF = -1;
	public final static int IDENTIFIER = 0;
	public final static int LABEL = 1;
	
	public final static int IS_KW = 100;
	public final static int AND_KW = 101;
	public final static int OR_KW = 102;
	public final static int NOT_KW = 103;
	public final static int VERY_KW = 104;
	public final static int SLIGHTLY_KW = 105;
	public final static int MODERATLY_KW = 106;
	public final static int IMPLIES_KW = 107;
	
	public final static int OPEN_BRACKET = 1001;
	public final static int CLOSE_BRACKET = 1002;
	
	private int type;
	private String value;
	private int line;
	private int column;
	
	public Token(int type, String value, int line, int column)
	{
		this.type = type;
		this.value = value;
		this.line = line;
		this.column = column;
	}
	
	public int getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}
	

}
