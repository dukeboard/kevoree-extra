package fr.irisa.triskell.fuzzy.control;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import fr.irisa.triskell.fuzzy.control.rule.FuzzyRule;
import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression;
import fr.irisa.triskell.fuzzy.core.expression.builder.FuzzyExpressionBuilder;

 /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */

public class ConcreteFuzzyController extends AbstractFuzzyController
{
	private List<FuzzyProperty> propertyList;
	private List<FuzzyRule> ruleList;
	
	public ConcreteFuzzyController() 
	{
		propertyList = new LinkedList<FuzzyProperty>();
		ruleList = new LinkedList<FuzzyRule>();
	}
	
	public void addRule(String antecedent, String outcome) throws BuilderException, ParserException
	{
		
		Vector<FuzzyProperty> vec = new Vector<FuzzyProperty>();
		vec.addAll(propertyList);
		
		FuzzyExpressionBuilder builder = new FuzzyExpressionBuilder(vec);

		builder.build(antecedent);
		if ( builder.hasError() ){	throw new BuilderException(); }
		FuzzyExpression antecedentExpr = builder.getResult();
		
		builder.build(outcome);
		if ( builder.hasError() ){	throw new BuilderException(); }
		FuzzyExpression outcomeExpr = builder.getResult();
			
		ruleList.add(new FuzzyRule(antecedentExpr, outcomeExpr));
	}
	
	public void addProperty(FuzzyProperty p)
	{
		propertyList.add(p);
	}

	
	public void activateProperty(FuzzyProperty fp)
	{
		for(FuzzyRule r : ruleList)
		{
			if (r.involve(fp)) {
				r.activate();
			}
		}
	}
	
	public void inactivateProperty(FuzzyProperty fp)
	{
		for(FuzzyRule r : ruleList)
		{
			if (r.involve(fp)) {
				r.inactivate();
			}
		}		
	}


	public List<FuzzyProperty> getPropertyList()
	{
		return propertyList;
	}


	public List<FuzzyRule> getRuleList() {
		return ruleList;
	}
}
