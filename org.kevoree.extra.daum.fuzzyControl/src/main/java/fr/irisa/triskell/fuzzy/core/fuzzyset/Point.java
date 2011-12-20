package fr.irisa.triskell.fuzzy.core.fuzzyset;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Point 
{
		private double x;
		private double y;
		
		public Point(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
		
		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}
		
		public String toString()
		{
			return new String("(" + x + "," + y + ")");
		}
		
}
