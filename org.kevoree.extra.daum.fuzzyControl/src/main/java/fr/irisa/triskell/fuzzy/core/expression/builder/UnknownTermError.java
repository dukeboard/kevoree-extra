package fr.irisa.triskell.fuzzy.core.expression.builder;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class UnknownTermError extends BuilderError
{
	private String termName;
	private int lineNumber;
	private int columnNumber;
	
	public UnknownTermError(String termName, int line, int pos)
	{
		this.termName = termName;
		this.lineNumber = line;
		this.columnNumber = pos;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getTermName() {
		return termName;
	}
	
	public String getMessage()
	{
		return "Unknow Property \"" + termName + "\" found at line " + lineNumber + ":" + columnNumber; 		
	}

}
