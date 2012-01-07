/**
 * 
 */

/**
 * @author osamie
 *
 */

import java.lang.*;
import java.io.*;
import java.net.*;

public class Server {
	
	
	public static void main(){
		run();
	}
	
	
	public static void run() {
	    
		
		
		try{
	    	//connect to DB using jdbc or something
	    }
	    catch(Exception e){
	    	System.out.print("problem connecting to database!");
	    	
	    }
		
		String data = ""; //value from databass
	      
	      
	    //while (true){
	    	try {
		    	  
		    	  
		         ServerSocket srvr = new ServerSocket(1234); 
		         //your socket number has to be the same with your client...remember! :)
		         
		         
		         //accept() waits until a client attempts to connect to the server, and it returns an instance of the Socket class
		         Socket skt = srvr.accept();
		         
		         
		         
		         System.out.print("Server has connected!\n");
		         
		         
		         //skt.getOutputStream() returns the output stream through which the server can talk to the client
		         PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
		         
		         System.out.print("Sending string: '" + data + "'\n");
		         out.print(data);
		         out.close();
		         skt.close();
		         srvr.close();
		      }
		      catch(Exception e) {
		         System.out.print("Whoops! It didn't work!\n" + e);
		      }
	    	
	    //}
	      
	      
	      
	      
	   }

}
