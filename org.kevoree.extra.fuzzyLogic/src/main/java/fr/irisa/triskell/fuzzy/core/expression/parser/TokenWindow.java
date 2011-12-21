package fr.irisa.triskell.fuzzy.core.expression.parser;
import java.io.IOException;
import java.util.Stack;
  /**
 * Created by IntelliJ IDEA.
 * User: jed
 * Date: 20/12/11
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class TokenWindow 
{
	private Tokenizer tokenizer;
	private Stack<Token> window;
	
	public TokenWindow(Tokenizer tokenizer)
	{
		this.tokenizer = tokenizer;
		window = new Stack<Token>();
	}
		
	public Token getCurrentToken()
	{
		if (window.isEmpty())
		{
			try {
				window.push(tokenizer.getNextToken());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return window.peek();
	}
	
	public Token getNextToken()
	{
		Token result = null;
		if (window.isEmpty())
		{
			try {
				window.push(tokenizer.getNextToken());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 	
		result = window.pop(); 
		//System.out.println("TokenWindow >>> Consumption of " + result.getValue()  );

		return result;
	
	}
	
	public void pushBackToken(Token item)
	{
		//System.out.println("TokenWindow >>> PushBack of " + item.getValue()  );

		window.push(item);
	}

}
