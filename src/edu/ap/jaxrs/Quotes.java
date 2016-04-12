package edu.ap.jaxrs;

import java.util.ArrayList;
import java.util.Set;

import javax.ws.rs.*;

import redis.clients.jedis.Jedis;

@Path("/quotes")
public class Quotes {

	private boolean filled = false;
	
	private void fillDB(){
		if (!filled){
			Jedis jedis = new Jedis("localhost");
			jedis.set("author:1", "Winston Churchill");
			jedis.set("author:2", "Albert Einstein");
			jedis.set("author:3", "W. C. Fields");
			jedis.set("quote:1:1", "I may be drunk, Miss, but in the morning I will be sober and you will still be ugly.");
			jedis.set("quote:1:2", "You have enemies? Good. That means you've stood up for something, sometime in your life.");
			jedis.set("quote:3:1", "I never drink water because of the disgusting things that fish do in it.");
			jedis.set("quote:3:2", "No doubt exists that all women are crazy; it's only a question of degree.");
			jedis.set("quote:2:1", "Try not to become a man of success, but rather try to become a man of value.");
			jedis.set("quote:2:2", "The true sign of intelligence is not knowledge but imagination.");
			filled = true;
		}
	}
	
	@GET
	@Produces({"text/html"})
	public String getAllQuotes(){
		fillDB();
		Jedis jedis = new Jedis("localhost");
		String returnString = "<h1>All quotes:</h1><br><br><ul>";
		Set<String> keys = jedis.keys("quote:*");
		for(String key : keys) {
			returnString += "<li>" + jedis.get(key) + "</li>";
		}
		return returnString + "</ul>";
	}
	
	@POST
	@Produces({"text/html"})
	public String getQuotesByAuthor(String author){
		fillDB();
		Jedis jedis = new Jedis("localhost");
		String returnString = "<h1>All quotes by " + author + ":</h1><br><br><ul>";
		Set<String> authorKeys = jedis.keys("author:?");
		String authorKey = "";
		for(String key : authorKeys){
			if(jedis.get(key).equals(author)){
				authorKey = key.substring(key.length() - 1);
			}
		}
		Set<String> quoteKeys = jedis.keys("quote:" + authorKey + ":?");
		for(String key : quoteKeys){
			returnString += "<li>" + jedis.get(key) + "<li>";
		}
		return returnString + "</ul>";
	}
}
