package fr.irisa.triskell.fuzzy.control.rule;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.expression.analysis.Defuzzifier;
import fr.irisa.triskell.fuzzy.core.expression.analysis.Fuzzifier;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class BasicRuleSetExecutor {
	
	private	Fuzzifier fuzzifier;
	private Defuzzifier defuzzifier;

	public BasicRuleSetExecutor()
	{
		fuzzifier = new Fuzzifier();
		defuzzifier = new Defuzzifier();
	}	
	
	public void apply(List<FuzzyRule> list) 
	{
		int i = 1;
		for(FuzzyRule rule : list)
		{
			System.out.println(" >>> DBG >>> Execution de la règle n°" + i);
			if ( rule.isActive() ){
				rule.getAntecedent().accept(fuzzifier);
				if ( fuzzifier.getResult().getMembership() != 0){
					System.out.println("\t - Fuzzification => " + fuzzifier.getResult());
					defuzzifier.setInputFuzzyValue(fuzzifier.getResult());
					rule.getOutcome().accept(defuzzifier);
					System.out.println("\t - Defuzzification => ");
					defuzzifier.getResult();
					i++;
					if (defuzzifier.getResult().contains(Double.NaN)) 
					{
						System.err.println(rule);
						System.exit(1);
					}
				}
			}
		}
	}	
	
	public Hashtable<FuzzyProperty, Double> getResult()
	{
		return defuzzifier.getResult();
	}

}
