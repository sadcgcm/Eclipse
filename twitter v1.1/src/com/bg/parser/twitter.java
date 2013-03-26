/**
 * 
 */
package com.bg.parser;
import java.util.Date;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.bg.bd.mysql;

/**
 * @author KRISTIAN
 * to define the class uses for crawling the twitter
 *
 */

public class twitter {
	private static int DEFAULT_DEPTH_LEVEL = 0;
	private int DEPTH_LEVEL;
	private String CONSUMER_KEY = "97mJoEnOQaMqamB8D7bj8w";
	private String CONSUMER_SECRET = "JD3IxmcChd8eoh9IcfC3DH3bwWQAsmAD8YB7TzB0";
    private String ACCESS_TOKEN = "1276498230-fLCjXU9fShnPHSWZvfx6EwCpOtNT0zycvuqeQtI";
    private String ACCESS_TOKEN_SECRET = "Mt7mW83Tj19fpcMimsslW0BGnsdcztQItCuTtHALc";
    private Twitter twitter_;
    
    //constructor
    public twitter(){
    	//twitter_ = null;
    }
    
    //Obtiene la profundidad por defecto
    public int getDEFAULT_DEPTH_LEVEL(){
    	return DEFAULT_DEPTH_LEVEL;
    }
    
    //Establece la profundidad
    public void setDEPTH_LEVEL(int DEPTH_LEVEL_){
    	DEPTH_LEVEL = DEPTH_LEVEL_;
    }
    
    //Obtiene la profundidad
    public int getDEPTH_LEVEL(){
    	return DEPTH_LEVEL;
    }
    
    //Establece la consumer key 
    public void setCONSUMER_KEY(String CONSUMER_KEY_){
    	CONSUMER_KEY = CONSUMER_KEY_;
    }
    
    //Establece la consumer secret
    public void setCONSUMER_SECRET(String CONSUMER_SECRET_){
    	CONSUMER_SECRET = CONSUMER_SECRET_;
    }
    
    //Establece el access token    
    public void setACCESS_TOKEN(String ACCESS_TOKEN_){
    	ACCESS_TOKEN = ACCESS_TOKEN_;
    }
    
    //Establece el access token secret
    public void setACCESS_TOKEN_SECRET(String ACCESS_TOKEN_SECRET_){
    	ACCESS_TOKEN_SECRET = ACCESS_TOKEN_SECRET_;
    }
    
    //Establece la authenticacion de la aplicacion para la extraccion de la informacion   
    public void Auth(){
    	ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(CONSUMER_KEY)
            .setOAuthConsumerSecret(CONSUMER_SECRET)
            .setOAuthAccessToken(ACCESS_TOKEN)
            .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        twitter_ = new TwitterFactory(cb.build()).getInstance();
    }
    
    //obtiene el objeto twitter
    public Twitter getTwitter(){
    	if (twitter_ == null) Auth();
    	return twitter_;
    }
    
    //almacenar en la base de datos
    public void Almacenar(Status tweet){
    	String id_tweet = Long.toString( tweet.getId() );
    	String text = tweet.getText();
    	String from = tweet.getSource();
    	String to = tweet.getUser().getScreenName();
    	String date = tweet.getCreatedAt().toString();
    	
    	//System.out.println(from.length());
    	System.out.println(tweet.getCreatedAt().toString());
    	mysql m = new mysql("twitter", "127.0.0.1", "twitter", "twitter");
    	m.InsertarTweets(id_tweet, text, from, to, date);
    	//m.Actualizar(id_tweet, "Date", date);
    }
    
    //Recuperar listado de ultimos tweets escritos 3210
    public void ObtenerTweets(String Usuario){
        for (int i = 1; i< 25; i++){ //25
	        Paging pagina = new Paging(i,200);  //200
	        ResponseList<Status> listado;
			try {
				listado = twitter_.getUserTimeline(Usuario,pagina);
				if (listado.size() > 0){
					for (int j = 0; j < listado.size(); j++){
						Almacenar( listado.get(j) );
					}
				}
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
