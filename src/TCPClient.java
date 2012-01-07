//TCPClient.java

import java.io.*;
import java.net.*;

class TCPClient 
{
 public static void main(String argv[]) throws Exception
 {
  String FromServer;
  String ToServer;
  int portnumber = 5000;
  
  Socket clientSocket = new Socket("localhost", portnumber);
  
  BufferedReader inFromUser =
                 new BufferedReader(new InputStreamReader(System.in));
     
  PrintWriter outToServer = new PrintWriter(
     clientSocket.getOutputStream(),true);
     
  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
     clientSocket.getInputStream()));
     
  while (true)
  {
    
    FromServer = inFromServer.readLine();
    
    if (FromServer.equals("~inventory~")){
    	//throw new RuntimeException("inventory!!!");
    	while (inFromServer.ready()){
    		System.out.println(inFromServer.readLine() + "\n");
    	}
    	
    }
    
    //System.out.print("received from server: " + FromServer + "\n" );
    
    if ( FromServer.equals("quit"))
        {
    		System.out.println("Server is shutting down \n Quitting... ");
        	clientSocket.close();
        	break;
        }
    
    else
    
        {
         System.out.println(" \n ***RECIEVED:*** \n" + FromServer);
         System.out.println("\n ***MESSAGE TO SERVER:***");
         
         ToServer = inFromUser.readLine();
         
         if (ToServer.equals("quit")||ToServer.equals("QUIT")||ToServer.equals("q"))
         {
        	 outToServer.println (ToServer) ;
        	 clientSocket.close();
            break;
         }
         
        else
        {
            outToServer.println(ToServer);
        }
      }
    }   
  }
}
