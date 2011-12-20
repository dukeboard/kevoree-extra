package fr.irisa.triskell.fuzzy.core;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyValue 
{
	private AbstractFuzzySet type;
	private double membership;
	
	public FuzzyValue(AbstractFuzzySet type, double membership)
	{
		this.type = type;
		if ( membership == Double.NaN ){System.out.println("Invalid Membership"); }
		this.membership = membership;
	}

	public double getMembership() {
		return membership;
	}
	
	public double getModulatedMembership(double v)
	{
		double result = this.type.getMembership(v);
		
		if ( result > this.membership ) { result = membership; }
		
		return result;		
	}

	public AbstractFuzzySet getType() {
		return type;
	}
	
	public FuzzyValue maximum(FuzzyValue toCompare)
	{
		return (this.getMembership() >= toCompare.getMembership() ) ? this : toCompare;
	}

	public FuzzyValue minimum(FuzzyValue toCompare)
	{
		return (this.getMembership() <= toCompare.getMembership() ) ? this : toCompare;
	}
	
	public String toString()
	{
		return type.getLabel() + " at " + Math.floor(membership*100)  + "%";
	}
	
}
