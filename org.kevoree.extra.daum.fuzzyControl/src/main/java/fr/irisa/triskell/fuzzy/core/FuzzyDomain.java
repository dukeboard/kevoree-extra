package fr.irisa.triskell.fuzzy.core;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import fr.irisa.triskell.fuzzy.core.fuzzyset.Function;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
/* L'environnnement du système est capturé par une ensemble de propriété reliées à  un domaine spécifique */
public class FuzzyDomain
{
	private String name;
	private double minimum;
	private double maximum;
	private Vector<FuzzySet> termList;
	
	public FuzzyDomain(String name, double min, double max)
	{
		this.name = name;
		this.minimum = min;
		this.maximum = max;
		this.termList = new Vector<FuzzySet>();
	}

	public String getName() {
		return name;
	}

	public double getMaximum() {
		return maximum;
	}

	public double getMinimum() {
		return minimum;
	}
	
	public boolean isTermDefined(String termToFind)
	{
		boolean found = false;
		Iterator<FuzzySet> it = termList.iterator();
		while (!found && it.hasNext())
		{
			FuzzySet term = it.next();
			if (term.getLabel().equals(termToFind))
			{
				found = true;
			}
		}
		return found;
	}
	
	public FuzzySet getTerm(String name)
	{
		boolean found = false;
		Iterator<FuzzySet> it = termList.iterator();
		FuzzySet term = null;
		while (!found && it.hasNext())
		{
			term = it.next();
			if (term.getLabel().equals(name))
			{
				found = true;
			}
		}
		return term;
	}
	
	public void addTerm(String label, Function membership)
	{
		termList.add(new FuzzySet(this, label, membership));
	}
	

	
	public FuzzyVariable fuzzify(double crispValue)
	{
		Vector<FuzzyValue> fuzzyValueList = new Vector<FuzzyValue>();
		
		for(FuzzySet t : this.termList)
		{
			fuzzyValueList.add(new FuzzyValue(t, t.getMembership(crispValue)));
		}
		
		FuzzyVariable result = new FuzzyVariable(this, fuzzyValueList);
		
		return result;
	}
	
	public FuzzyValue fuzzifyOnSet(FuzzySet target, double crispValue)
	{
		return new FuzzyValue(target, target.getMembership(crispValue));
	}
	
	public boolean equals(FuzzyDomain toCompare)
	{
		return name == toCompare.name;
	}
	
	public List<FuzzySet> getFuzzyTermList()
	{		
		return this.termList;
	}

}
