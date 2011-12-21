package fr.irisa.triskell.fuzzy.core;

import fr.irisa.triskell.fuzzy.core.fuzzyset.Function;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzySet implements AbstractFuzzySet
{
	private String label;
	private Function membership;
	private FuzzyDomain domain;
	
	public FuzzySet(FuzzyDomain domain, String label, Function membership)
	{
		this.label = label;
		this.membership = membership;
		this.domain = domain;
	}
	
	public double getMembership(double x)
	{
		return this.membership.getMembership(x);
	}
	
	public boolean equals(FuzzySet toCompare)
	{
		return ( label == toCompare.getLabel() ); 
	}

	public FuzzyDomain getDomain() {
		return domain;
	}

	public String getLabel() {
		return label;
	}

	public FuzzyValue fuzzify(double crispValue) 
	{
		return new FuzzyValue(this, getMembership(crispValue));
	}

}
