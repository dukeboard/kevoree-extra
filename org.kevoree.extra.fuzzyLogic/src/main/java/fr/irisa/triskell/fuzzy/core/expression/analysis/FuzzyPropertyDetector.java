package fr.irisa.triskell.fuzzy.core.expression.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import fr.irisa.triskell.fuzzy.core.FuzzyProperty;
import fr.irisa.triskell.fuzzy.core.expression.BinaryExpression;
import fr.irisa.triskell.fuzzy.core.expression.Conjunction;
import fr.irisa.triskell.fuzzy.core.expression.Disjunction;
import fr.irisa.triskell.fuzzy.core.expression.FuzzyExpression;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetBinaryOperation;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetExpression;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetModifier;
import fr.irisa.triskell.fuzzy.core.expression.FuzzySetReference;
import fr.irisa.triskell.fuzzy.core.expression.Intersection;
import fr.irisa.triskell.fuzzy.core.expression.ModeratlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.NotModifier;
import fr.irisa.triskell.fuzzy.core.expression.SlightlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.Statement;
import fr.irisa.triskell.fuzzy.core.expression.Union;
import fr.irisa.triskell.fuzzy.core.expression.VeryModifier;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FuzzyPropertyDetector implements FuzzyExpressionVisitor {

	private List<FuzzyProperty> result;
	
	public FuzzyPropertyDetector()
	{
		result = new Vector<FuzzyProperty>();
	}
	
	public List<FuzzyProperty> getResult()
	{
		return result; 
	}
	
	public void reset()
	{
		result.clear();
	}
	 

	public void visitBinaryExpression(BinaryExpression target) {
		// Abstract

	}


	public void visitConjunction(Conjunction target) 
	{
		target.getLeft().accept(this);
		target.getRight().accept(this);
	}


	public void visitDisjunction(Disjunction target) 
	{
		target.getLeft().accept(this);
		target.getRight().accept(this);
	}


	public void visitExpression(FuzzyExpression target) 
	{
		// Abstract
	}


	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target) 
	{
		// Abstract
	}


	public void visitFuzzySetExpression(FuzzySetExpression target) 
	{
		// Abstract
	}


	public void visitFuzzySetModifier(FuzzySetModifier target) 
	{
		// Nothing to do
	}


	public void visitFuzzySetReference(FuzzySetReference target) 
	{
		// nothing to do
	}


	public void visitIntersection(Intersection target) 
	{
		target.getLeft().accept(this);
		target.getRight().accept(this);
	}


	public void visitModeratlyModifier(ModeratlyModifier target) 
	{
		// Nothing to do
	}

	@Override
	public void visitNotModifier(NotModifier target) 
	{
		// Nothing to do
	}

	@Override
	public void visitSlightlyModifier(SlightlyModifier target) 
	{
		// Nothing to do
	}

	@Override
	public void visitStatement(Statement target) 
	{
		boolean found = false;
		Iterator<FuzzyProperty> it = result.iterator();
		while (it.hasNext() && !found)
		{
			FuzzyProperty fp = it.next();
			if (fp.getName().equals(target.getTarget().getName())) {
				found = true;
			}
		}
		
		if (!found){ 
			result.add(target.getTarget()); 
		}
	}

	@Override
	public void visitUnion(Union target) 
	{
		target.getLeft().accept(this);
		target.getRight().accept(this);
	}

	@Override
	public void visitVeryModifier(VeryModifier target) 
	{
		// Nothing to do
	}

}
