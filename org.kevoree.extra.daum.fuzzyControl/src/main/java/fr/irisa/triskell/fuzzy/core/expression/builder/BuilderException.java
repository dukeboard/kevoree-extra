package fr.irisa.triskell.fuzzy.core.expression.builder;

import java.util.Vector;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class BuilderException extends Exception 
{
	private Vector<BuilderError> errors;
	
	public BuilderException(Vector<BuilderError> errors)
	{
		this.errors = errors;
		String message = new String("The following errors have been detected:");
		for(BuilderError e : errors)
		{
			message += e.getMessage();
		}
	}
	
}
