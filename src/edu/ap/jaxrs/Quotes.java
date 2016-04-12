package edu.ap.jaxrs;

import javax.ws.rs.*;

@Path("/quotes")
public class Quotes {

	@GET
	@Produces({"text/html"})
	public String getAllQuotes(){
		return "";
	}
	
	@POST
	@Produces({"text/html"})
	public String getQuotesByAuthor(String author){
		return "";
	}
}
