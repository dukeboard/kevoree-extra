package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class GaussianFunction implements Function 
{
	private double mean;
	private double deviation;

	public GaussianFunction(double mean, double deviation)
	{
		this.mean = mean;
		this.deviation = deviation;
	}
	
	public double getMembership(double v) 
	{
		return Math.exp( - Math.pow((v - mean),2) / (2 * Math.pow(deviation, 2)));
	}

	public double getDeviation() {
		return deviation;
	}

	public double getMean() {
		return mean;
	}

}
