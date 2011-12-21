package fr.irisa.triskell.fuzzy.core.expression.builder;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class IncompatibleDomain extends BuilderError 
{
	private int lineNumber;
	private int columnNumber;
	
	public IncompatibleDomain(int lineNumber, int columnNumber) {
		super();
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}

	@Override
	public String getMessage() 
	{
		return "Incompatible domains at " + lineNumber + columnNumber;
	}

}
