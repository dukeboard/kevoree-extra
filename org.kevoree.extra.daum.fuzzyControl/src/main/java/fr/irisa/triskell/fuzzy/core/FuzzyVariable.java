package fr.irisa.triskell.fuzzy.core;

import java.util.Iterator;
import java.util.Vector;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyVariable 
{
	public static final int SAMPLE_RATE = 100;
	
	private String name;
	private FuzzyDomain type;
	private Vector<FuzzyValue> fuzzyValueList;
	
	public FuzzyVariable(FuzzyDomain type, Vector<FuzzyValue> valueList)
	{
		this.name = "anonymous";
		this.type = type;
		this.fuzzyValueList = valueList;
	}
	
	public FuzzyVariable(FuzzyDomain type)
	{
		this.name = "anonymous";
		this.type = type;
		this.fuzzyValueList = new Vector<FuzzyValue>();
	}

	public String getName() {
		return name;
	}

	public FuzzyDomain getType() {
		return type;
	}
	
	public String toString()
	{
		String result = new String("Fuzzy Variable \'" + this.name + "' : " + this.type.getName() + "\n" ); 
		for(FuzzyValue fv : this.fuzzyValueList)
		{
			result += " - is \'" + fv.getType().getLabel() + "' at " + Math.floor(fv.getMembership() * 100) + "%\n"  ;
		}
		return result;
	}
	
	public boolean defineFuzzySet(AbstractFuzzySet fs)
	{
		boolean found = false;
		Iterator<FuzzyValue> it = fuzzyValueList.iterator();
		while ( !found && it.hasNext() )
		{
			FuzzyValue fv = it.next();
			if (fv.getType().equals(fs)){
				found = true;
			}
		}
		return found;
	}
	
	public void removeFuzzyValue(FuzzyValue fv)
	{
		fuzzyValueList.remove(fv);
	}
	
	public FuzzyValue getValueForFuzzySet(AbstractFuzzySet fs)
	{
		boolean found = false;
		Iterator<FuzzyValue> it = fuzzyValueList.iterator();
		FuzzyValue fv = null;
		while ( !found && it.hasNext() )
		{
			fv = it.next();
			if (fv.getType().equals(fs)){
				found = true;
			}
		}
		return fv;
	}
	
	public void aggregateFuzzyValue(FuzzyValue toAggregate)
	{
		fuzzyValueList.add(toAggregate);
	}
	
	public double defuzzify()
	{
		double result = 0;
		
		Vector<Double> membershipList = new Vector<Double>();
		
		for (int i=0 ; i<SAMPLE_RATE ; i++)
		{
			double currentValue = this.type.getMinimum() + ((this.type.getMaximum() - this.type.getMinimum()) / SAMPLE_RATE * i);
			double maxMembership = 0;
						
			// We look for the maximum on each value
			for (FuzzyValue fv : fuzzyValueList)
			{		
				double modulatedMembership = fv.getModulatedMembership(currentValue);
				if ( modulatedMembership > maxMembership){	
					maxMembership = modulatedMembership;
				} 
			}			
			membershipList.add(maxMembership);
		}
		
		// calculer la somme des "mu * out" sur la somme des mu
		double balancedMembershipSum = 0;
		for (int i=0; i<SAMPLE_RATE ; i++)
		{
			double currentValue = this.type.getMinimum() + ((this.type.getMaximum() - this.type.getMinimum()) / SAMPLE_RATE * i);
			balancedMembershipSum += (membershipList.elementAt(i) * currentValue) ;
		}
		
		double membershipSum = 0;
		for (int i=0 ; i<SAMPLE_RATE ; i++)
		{
			membershipSum += membershipList.elementAt(i);
		}
	
		result =  balancedMembershipSum / membershipSum;
	
		return result;
	}

}
