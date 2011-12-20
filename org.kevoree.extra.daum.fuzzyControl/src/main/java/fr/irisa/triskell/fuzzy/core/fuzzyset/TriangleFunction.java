package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class TriangleFunction implements Function 
{ 
	private double lmin;
	private double lcenter;
	private double lmax;
	
	public TriangleFunction(double lmin, double lcenter, double lmax)
	{
		this.lmin = lmin;
		this.lcenter = lcenter;
		this.lmax = lmax;
	} 
	
	public double getMembership(double x)
	{
		double result = 0;
		
		if ( x >= lmin && x < lcenter){
			result = linearInterpolation(lmin, 0, lcenter, 1, x);
		
		} else if ( x >= lcenter && x < lmax) {
			result = linearInterpolation(lcenter, 1, lmax, 0, x);
			
		} else {
			result = 0;
		}
			
		return result;
	}
	
	private double linearInterpolation(double xa, double ya, double xb, double yb, double x)
	{
		return ((ya - yb) / (xa - xb)) * x + ((xa * yb) -(xb * ya) ) / (xa - xb); 
	}
}
