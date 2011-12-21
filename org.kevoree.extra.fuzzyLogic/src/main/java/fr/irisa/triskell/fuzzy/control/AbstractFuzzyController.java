package fr.irisa.triskell.fuzzy.control;

import java.util.Hashtable;
import java.util.Iterator;

import fr.irisa.triskell.fuzzy.control.rule.BasicRuleSetExecutor;
import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
 /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFuzzyController implements FuzzyController {


	public void control() 
	{
		BasicRuleSetExecutor executor = new BasicRuleSetExecutor();
		executor.apply(getRuleList());
		Hashtable<FuzzyProperty, Double> result = executor.getResult();
		Iterator<FuzzyProperty> it = result.keySet().iterator();
		while( it.hasNext() )
		{
			FuzzyProperty property = it.next();
			Double temp = result.get(property);
			property.setCrispValue(temp);
		}
	}


}
