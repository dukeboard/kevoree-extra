package fr.irisa.triskell.fuzzy.core.fuzzyset;

public class RightGaussianFunction extends GaussianFunction 
{

	public RightGaussianFunction(double mean, double deviation) 
	{
		super(mean, deviation);
	}
	
	public double getMembership(double v) 
	{
		double result;
		if ( v > getMean() )
		{
			result = super.getMembership(getMean());
		} else {
			result = super.getMembership(v);
		}
		
		return result;
	}

}
