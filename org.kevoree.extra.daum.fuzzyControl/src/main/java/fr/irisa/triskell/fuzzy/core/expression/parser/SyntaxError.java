package fr.irisa.triskell.fuzzy.core.expression.parser;
import java.util.Vector;


public class SyntaxError 
{
	private String found;
	private Vector<String> expected;
	private int line;
	private int column;
	
	public SyntaxError(String found, String expected, int line, int column) {
		super();
		this.found = found;
		this.expected = new Vector<String>();
		this.expected.add(expected);
		this.line = line;
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Vector<String> getExpected() {
		return expected;
	}
	
	public String getFound() {
		return found;
	}
	
	public int getLine() {
		return line;
	}
	
	public void addExpectation(String expectation)
	{
		expected.add(expectation);
	}
	
	public String toString()
	{
		String result = "Syntax error detected at line " + line + ", column " + column + ".\n";
		result += "\t -> Found \"" + found + "\"\n";
		result += "\t -> Expecting : ";
		int counter = 1;
		for(String word : expected)
		{
			if (counter != expected.size())
			{
				result += word + ", ";
			} else {
				result += word;
			}
			counter++;
		}
		return result;
	}
	
	

}
