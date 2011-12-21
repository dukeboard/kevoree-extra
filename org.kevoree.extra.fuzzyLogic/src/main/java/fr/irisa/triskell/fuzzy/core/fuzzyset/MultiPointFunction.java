package fr.irisa.triskell.fuzzy.core.fuzzyset;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;


  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class MultiPointFunction implements Function 
{

	private Map<Interval, Function> functionList;

	public MultiPointFunction()
	{
		functionList = new Hashtable<Interval, Function>();
	}
	
	@Override
	public double getMembership(double v) 
	{
		Interval interval = null;
		
		boolean found = false;
		Iterator<Interval> it = functionList.keySet().iterator();
		while(!found && it.hasNext())
		{
			interval = it.next();
			if ( interval.contains(v) )
			{
				found = true;
			}
		}
		
		return functionList.get(interval).getMembership(v);
	}

	public void createInterval(Interval i, Function f)
	{
		functionList.put(i, f);
	}
	
	
	
	
}
