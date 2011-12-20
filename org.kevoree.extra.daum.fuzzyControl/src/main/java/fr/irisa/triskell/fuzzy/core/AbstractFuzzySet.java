package fr.irisa.triskell.fuzzy.core;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public interface AbstractFuzzySet 
{	
	public String getLabel();
	
	public double getMembership(double crispValue);
	
	public FuzzyDomain getDomain();
	
	public FuzzyValue fuzzify(double crispValue);

}
