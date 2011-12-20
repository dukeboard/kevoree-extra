package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class HyperbolicFunction implements Function {

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double a;

	
	public HyperbolicFunction(double x1, double y1, double x2, double y2, double a) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.a = a; 
	}

	@Override
	public double getMembership(double x) 
	{
		return ( (1/( (a-(1/a)) * ((x-x1)/(x2-x1)) + (1/a) ) - (1/a) ) * (1/(a-(1/a))) ) * (y2 - y1) + y1;
	}

}
