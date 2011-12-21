package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class SquareFunction implements Function 
{
	private double x1, x2, y1, y2;
	
	public SquareFunction(double x1, double y1, double x2, double y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public double getMembership(double x) 
	{
		return Math.pow( ( (x-x1)/(x2-x1) ) ,2 ) * (y2 - y1) + y1; 
	}

}
