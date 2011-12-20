package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class LeftGaussianFunction extends GaussianFunction {

	public LeftGaussianFunction(double mean, double deviation) 
	{
		super(mean, deviation);
	}
	
	public double getMembership(double v) 
	{
		double result;
		if ( v < getMean() )
		{
			result = super.getMembership(getMean());
		} else {
			result = super.getMembership(v);
		}
		
		return result;
	}

}
