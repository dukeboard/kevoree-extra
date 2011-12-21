package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class LinearFunction implements Function {

	private double a; // a * x + b
	private double b;
	
	public LinearFunction(double a, double b)
	{
		this.a = a;
		this.b = b;
	}
	
	public LinearFunction(double xa, double ya, double xb, double yb)
	{
		this.a = (ya - yb) / (xa - xb);
		this.b =  ((xa * yb) -(xb * ya) ) / (xa - xb);
	}
	
	public double getMembership(double v) 
	{
		double result =  a * v + b;
		
		return result;
	}

	public String toString()
	{
		return new String("f(x) = " + a + " * x + " + b);
	}
	
}
