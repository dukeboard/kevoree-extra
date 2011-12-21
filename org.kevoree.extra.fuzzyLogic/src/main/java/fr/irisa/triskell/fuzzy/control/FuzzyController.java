package fr.irisa.triskell.fuzzy.control;

import java.util.List;

import fr.irisa.triskell.fuzzy.control.rule.FuzzyRule;
import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
 /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public interface FuzzyController  
{
	public List<FuzzyRule> getRuleList();
	
	public List<FuzzyProperty> getPropertyList();
	
	public void control();	
	
}
