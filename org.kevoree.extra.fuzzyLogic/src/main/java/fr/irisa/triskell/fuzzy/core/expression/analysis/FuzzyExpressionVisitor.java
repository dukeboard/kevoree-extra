package fr.irisa.triskell.fuzzy.core.expression.analysis;

import fr.irisa.triskell.fuzzy.control.rule.FuzzyRule;
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
public interface FuzzyExpressionVisitor 
{

	public void visitExpression(FuzzyExpression target);
	public void visitBinaryExpression(BinaryExpression target);
	public void visitConjunction(Conjunction target);
	public void visitDisjunction(Disjunction target);
	public void visitStatement(Statement target);
	public void visitFuzzySetExpression(FuzzySetExpression target);
	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target);
	public void visitUnion(Union target);
	public void visitIntersection(Intersection target);
	public void visitFuzzySetModifier(FuzzySetModifier target);
	public void visitVeryModifier(VeryModifier target);
	public void visitModeratlyModifier(ModeratlyModifier target);
	public void visitSlightlyModifier(SlightlyModifier target);
	public void visitNotModifier(NotModifier target);
	public void visitFuzzySetReference(FuzzySetReference target);	
	
}
