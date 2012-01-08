//TCPServer.java

import java.io.*;
import java.net.*;

class TCPServer 
{
	public static String fromclient;
    static String toclient;
    static int portnumber = 5000;
    static ServerSocket Server;
	
	public static String updateQty(String Bcode){
		//Charset charset = Charset.forName("US-ASCII");
		String itemCode = Bcode.replace('B', ' ');
		itemCode = itemCode.trim();
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader("inventory.txt"));
		    String str;
		    while(in.ready()){
		    	
		    	str = in.readLine();
		    	if (str.startsWith(itemCode)){
		    		in.close();
		    		return str;
		    	}
		    }
		    
		    
		    
		} catch (IOException e) {
			System.err.print(" oh oh ");
		}
		return "item not found!";
		
				
		
		
	}
	public static void sendInventory(Socket s){
		
		
		File myFile = new File("inventory.txt");
         byte[] mybytearray = new byte[(int) myFile.length()];

         FileInputStream fis = null;

         try {
             fis = new FileInputStream(myFile);
         } catch (FileNotFoundException ex) {
             // Do exception handling
         }
         BufferedInputStream bis = new BufferedInputStream(fis);

         try {
        	 BufferedOutputStream outToClient = new BufferedOutputStream(s.getOutputStream());
             bis.read(mybytearray, 0, mybytearray.length);
             outToClient.write(mybytearray, 0, mybytearray.length);
             outToClient.flush();
             //outToClient.close();
             //s.close();  DONT THINK I NEED TO CLOSE THE SOCKET JUST YET!

             // File sent, exit
             return;
         } catch (IOException ex) {
             // Do exception handling
         }
     }
	
	public static boolean isItemCode(String str){
		if ((str.startsWith("B#"))&& (str.length()==6)){
			//TODO : pre check if the remaining 4 characters are numbers
			return true;
			
		}
		return false;
	}
   public static void main(String argv[]) throws Exception
      {
         
	     Server = new ServerSocket (portnumber);
         
         System.out.println ("TCPServer Waiting for client on port " + portnumber);

         while(true) 
         {
         	Socket connected = Server.accept();
            System.out.println( " THE CLIENT"+" "+
            connected.getInetAddress() +":"+connected.getPort()+" IS CONNECTED ");
            
            BufferedReader inFromUser = 
            new BufferedReader(new InputStreamReader(System.in));    
     
            BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader (connected.getInputStream()));
                  
            PrintWriter outToClient =
               new PrintWriter(
                  connected.getOutputStream(),true);
            //outToClient.print("************************\n");
            //outToClient.print("**********YOU ARE CONNECTED TO THE SERVER**********");
            //outToClient.print("************************\n");
            
            while ( true )
            {
            	
            	System.out.println("\n ***MESSAGE TO CLIENT:***");
            	toclient = inFromUser.readLine();
            	
            	if ( toclient.equals ("quit"))
            	{
            		outToClient.println(toclient);
            		connected.close();
            		break;
            	}
            	else
            	{
            	outToClient.println(toclient);
                }
            	
            	fromclient = inFromClient.readLine();
            	
            	//if ( isItemCode(fromclient)){
            	if (fromclient.startsWith("B#")){
            		if (fromclient.length() == 2){
            			outToClient.println("incorrect purchase code. Try again! ");
            			continue;
            		}
            		System.out.println("scanning inventory for item...\n");
            		Thread.sleep(990);
            		String itemCode = fromclient.replace('B',' ');
            		itemCode = itemCode.trim();
            		outToClient.println(updateQty(itemCode));
            		System.out.println("Item " + fromclient + " has been updated in the inventory.");
            		outToClient.println("Item " + fromclient + " has been updated in the inventory.");
            		
            	}
            	
            	if ( fromclient.equals("i") || fromclient.equals("I") ){
            		
            		sendInventory(connected);
            		//outToClient.println("Here is the inventory!");
            		//while(readInventory().ready()){
            			//outToClient.println(readInventory().readLine());
            			
            		//}
            		
            		System.out.println("\n ***just sent inventory to client*** \n");
            	}
            	
                if ( fromclient.equals("quit")|| fromclient.equals("QUIT") || fromclient.equals("q") )
                {
                	System.out.println("Client has disconnected from the socket\n CLOSING CONNECTION.... \n" );
                	connected.close();
                	break;
                }
                	
		        else
		        {
		         System.out.println( "\n ***RECEIVED:*** \n" + fromclient );
		        } 
			    
			}  
			
          }
         
      }
}
