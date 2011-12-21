package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Interval 
{
	private double lower;
	private double upper;
	
	
	public Interval(double p1, double p2) 
	{
		super();
		this.lower = p1;
		this.upper = p2;
	}


	public double getLower() {
		return lower;
	}


	public void setLower(double p1) {
		this.lower = p1;
	}


	public double getUpper() {
		return upper;
	}


	public void setUpper(double p2) {
		this.upper = p2;
	}
	
	public boolean contains(double v)
	{
		return v <= upper && v >= lower;
	}
	
	

}
