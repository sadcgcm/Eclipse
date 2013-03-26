/**
 * 
 */
import twitter4j.TwitterException;

import com.bg.parser.twitter;
import com.bg.bd.mysql;
/**
 * @author KRISTIAN
 *
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws TwitterException{
		// TODO Auto-generated method stub
		
		twitter t = new twitter();
		t.Auth();
		t.ObtenerTweets("naturanet");

	}

}
