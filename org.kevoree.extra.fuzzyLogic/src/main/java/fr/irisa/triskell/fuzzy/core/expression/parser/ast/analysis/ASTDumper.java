package fr.irisa.triskell.fuzzy.core.expression.parser.ast.analysis;

import java.io.PrintStream; 
import java.io.StringReader;

import fr.irisa.triskell.fuzzy.core.expression.parser.FuzzyExpressionParser;
import fr.irisa.triskell.fuzzy.core.expression.parser.SyntaxError;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.ASTVisitor;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.BinaryExpression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Conjunction;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Disjunction;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Expression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetBinaryOperation;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetExpression;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.FuzzySetReference;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Intersection;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.ModeratlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.NotModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.SlightlyModifier;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Statement;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.Union;
import fr.irisa.triskell.fuzzy.core.expression.parser.ast.VeryModifier;


public class ASTDumper implements ASTVisitor {

	private PrintStream stream;
	private int level;
	
	public ASTDumper(PrintStream target)
	{
		this.stream = target;
		level = 0;
	}
	
	private void indentation()
	{
		for(int i=0 ; i<level ; i++)
		{
			stream.print("\t");
		}
	}
	
	private void nextLevel(){ level++; }
	private void previousLevel(){ level--; }
	
	public void visitBinaryExpression(BinaryExpression target) 
	{
		// Abstract
	}

	public void visitConjunction(Conjunction target) 
	{
		indentation();
		stream.println("AND");
		nextLevel();
		target.getLeft().accept(this);
		target.getRight().accept(this);
		previousLevel();
	}

	public void visitDisjunction(Disjunction target) 
	{
		indentation();
		stream.println("OR");
		nextLevel();
		target.getLeft().accept(this);
		target.getRight().accept(this);
		previousLevel();
	}

	public void visitExpression(Expression target) 
	{
		// Abstract : nothing to do
	}

	public void visitFuzzySetBinaryOperation(FuzzySetBinaryOperation target) 
	{
		// Abstract : nothing to do
	}

	public void visitFuzzySetExpression(FuzzySetExpression target) 
	{
		// Abstract : nothing to do
	}

	public void visitFuzzySetModifier(FuzzySetModifier target) 
	{
		// Abstract : nothing to do
	}

	public void visitFuzzySetReference(FuzzySetReference target) 
	{
		indentation();
		stream.println(target.getLabel());
	}

	public void visitIntersection(Intersection target) 
	{
		indentation();
		stream.println("Intersection");
		nextLevel();
		target.getLeft().accept(this);
		target.getRight().accept(this);
		previousLevel();
	
	}

	public void visitModeratlyModifier(ModeratlyModifier target) 
	{
		indentation();
		stream.println("Moderatly");	
		nextLevel();
		target.getExpr().accept(this);
		previousLevel();
	}

	public void visitNotModifier(NotModifier target) 
	{
		indentation();
		stream.println("Not");		
		nextLevel();
		target.getExpr().accept(this);
		previousLevel();
	}

	public void visitSlightlyModifier(SlightlyModifier target) 
	{
		indentation();
		stream.println("Slightly");
		nextLevel();
		target.getExpr().accept(this);
		previousLevel();
	}

	public void visitStatement(Statement target) 
	{

		indentation();
		stream.println(target.getTarget() + " is");
		nextLevel();		
		target.getValue().accept(this);
		previousLevel();	
	}

	public void visitUnion(Union target) 
	{
		indentation();
		stream.println("Union");
		nextLevel();
		target.getLeft().accept(this);
		target.getRight().accept(this);
		previousLevel();	
	}

	public void visitVeryModifier(VeryModifier target) 
	{
		indentation();
		stream.println("Very");		
		nextLevel();
		target.getExpr().accept(this);
		previousLevel();
	}
	

}
