package fr.irisa.triskell.fuzzy.core.expression.builder;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class UnknownPropertyError extends BuilderError
{	
	private String propertyName;
	private int lineNumber;
	private int columnNumber;
	
	public UnknownPropertyError(String propertyName, int line, int pos)
	{
		this.propertyName = propertyName;
		this.lineNumber = line;
		this.columnNumber  = pos;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getPropertyName() {
		return propertyName;
	}
	
	public String getMessage()
	{
		return "Unknow Property \"" + propertyName + "\" found at line " + lineNumber + ":" + columnNumber; 		
	}
}
