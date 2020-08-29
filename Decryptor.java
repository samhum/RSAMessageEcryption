import java.net.*;
import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class Decryptor{

	Socket encrypterConn;
	public Decryptor(int port) throws IOException  //create a server socket 
    {
      ServerSocket servSock = new ServerSocket(port);
      encrypterConn = servSock.accept();
      byte [] message = receiveMessage();
      
      
     
      Message encrypted = parseMessage(message);
      
      System.out.println(" If you want to Brute Force this encryoption, type yes, otherwise enter a b value");
      
      Scanner in = new Scanner(System.in);
      String input = in.nextLine();
      BigInteger b;
      if(input.equals("yes"))
      {
       	 b = BruteForce.primeFactors(new BigInteger(encrypted.getN()), new BigInteger(encrypted.getA()));
      }
      else
      {
        b = new BigInteger(input);
      }
      //b = BruteForce.primeFactors(new BigInteger(encrypted.getN()), new BigInteger(encrypted.getA()));
      
      encrypted.setB(b);
      System.out.println(RSAdecrypt.decryptMessage(encrypted));
    }
  
  	public byte [] receiveMessage()
    {
    
      byte [] totalMessage = new byte [0];
    
      int buffSize = 100;
      
      int bytesRead = 0;
      while(bytesRead>=0)
      {
        try {
        	
          	byte[] incomingMessage = new byte[buffSize];//byte array to receive data in
          	InputStream input = encrypterConn.getInputStream();//retrieve input stream
            bytesRead = input.read(incomingMessage, 0, incomingMessage.length);//store incoming byte into byte array
            
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );//concatenate byte arrays
			outputStream.write(totalMessage);
			outputStream.write(incomingMessage);
            totalMessage = outputStream.toByteArray();
          }catch (SocketException e)
          
          {
              break;
          } 
          catch (IOException e) {
              e.printStackTrace();
              System.exit(1);
              break;
          }    
      }
      try{
    	encrypterConn.getInputStream().close();
    	
    	}
    	catch( IOException e){
    	}
    	return totalMessage;
    } 
    
    public Message parseMessage(byte [] payload)
    {
    
    	String s = new String(payload);
    	
    	s = s.substring(3);
      	int indexOfNextObject = s.indexOf("\n");
      	ArrayList<String> payloadString = new ArrayList<String>();
      	while(indexOfNextObject != -1) 
      	{
        	payloadString.add(s.substring(0,indexOfNextObject));
			System.out.println(s.substring(0,indexOfNextObject)); // DELETE HERE
       	 	s = s.substring(indexOfNextObject+1);
       		indexOfNextObject = s.indexOf("\n");
      	}
      
      	s = s.substring(2);
      	int indexOfNEnd = s.indexOf("**");
      	String n = s.substring(0,indexOfNEnd);
      	s = s.substring(indexOfNEnd+2);
      	int indexOfAEnd = s.indexOf("*");
      	String a = s.substring(0,indexOfAEnd);
      	
      	Message m =  new Message(n,a,payloadString);
      	return m;
      	
    }
    	
    public static void main (String [] args) throws IOException
    {
    	if (args.length != 1)
    	{
    		System.out.println("Usage Decryptor.java <port>");
    	}
    	
    	Decryptor dec = new Decryptor(Integer.parseInt(args[0]));
    }
}