import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.api.TweetsResources;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.List;

//package com.bg.parser.twitter;


/**
 * @author KRISTIAN
 */

public class main {
	
	public static int DEFAULT_DEPTH_LEVEL = 0;
	
	/**
	 * @param args
	 */

	public static void main(String[] args) throws TwitterException{
		Twitter twitter;
		
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey("97mJoEnOQaMqamB8D7bj8w")
            .setOAuthConsumerSecret("JD3IxmcChd8eoh9IcfC3DH3bwWQAsmAD8YB7TzB0")
            .setOAuthAccessToken("1276498230-fLCjXU9fShnPHSWZvfx6EwCpOtNT0zycvuqeQtI")
            .setOAuthAccessTokenSecret("Mt7mW83Tj19fpcMimsslW0BGnsdcztQItCuTtHALc");
        twitter = new TwitterFactory(cb.build()).getInstance();
         
        //Recuperar listado de ultimos tweets escritos
        //El paging sirve para decir el número máx de tweets que quieres recuperar
        Paging pagina = new Paging(2);
        pagina.setCount(200);
         
        //Recupera como máx tweets escritos por tí
        //ResponseList<Status> listado = twitter.getUserTimeline(pagina);
        ResponseList<Status> listado = twitter.getUserTimeline("naturanet",pagina);
        System.out.println(listado.size());
        //for (int i = 0; i < listado.size(); i++)
            
        	//System.out.println(listado.get(i).getUser());
        
        //Recupera como máx los ultimos tweets de tus novedades
        /*listado = twitter.getHomeTimeline(pagina);
        for (int i = 0; i < listado.size(); i++)
            System.out.println(listado.get(i).getText());
         */
        //Actualizar tu estado
        //Status tweetEscrito = twitter.updateStatus("¡Probando!");
        
        System.out.println("-------------------------------");
        try {
	        Query query = new Query("@naturanet");
	        QueryResult result;
	        int i = 0;
	        do {
	            
	        	result = twitter.search(query);
	        	List<Status> tweets = result.getTweets();
	            //System.out.println(tweets.size());
	            for (Status tweet : tweets) {
	                //System.out.println(tweet.getUser());
	            	//System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() + " - " + tweet.getSource());
	            	//System.out.println(++i);
	            	//System.out.println(tweet.getId());
	            }
	        } while ((query = result.nextQuery()) != null);
	        System.exit(0);
	    } catch (TwitterException te) {
	        te.printStackTrace();
	        System.out.println("Failed to search tweets: " + te.getMessage());
	        System.exit(-1);
	    }
    }

}
