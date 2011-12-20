package fr.irisa.triskell.fuzzy.core;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public interface FuzzyProperty 
{
	public String getName();
	
	public FuzzyDomain getFuzzyType();
	
	public double getCrispValue();
	
	public void setCrispValue(double newValue);

}
