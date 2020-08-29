import java.net.Socket;
import java.io.*;
/**
	Goal of this class is to open up a connection with the decryptor, and send an encoded message with some specified a and n
*/
public class Encryptor{

	Socket connToDecryptor;
		
	public static void main(String [] args)
	{
		if (args.length != 5)
		{
			System.out.println("Usage Encryptor <message> <n> <a> <ip> <port>");
		}
		
		String message = args[0];
		String n = args[1];
		String a = args[2];
		
		Encryptor enc = new Encryptor();
		
		enc.connectToDecryptor(args[3],args[4]);
		
		RSAencrypt messageEncryptor = new RSAencrypt(message,n,a);
		
		Message sendableMessage = messageEncryptor.getMessage();
		
		enc.sendToDecryptor(sendableMessage);
	}
	
	public void connectToDecryptor(String IP, String port)
	{
		try{
			Socket connToDecryptorTemp = new Socket(IP, Integer.parseInt(port));
			connToDecryptor = connToDecryptorTemp;
		}
		catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendToDecryptor(Message sendable){
	
		byte [] decodedMsg = sendable.convertToSendable();
		int len = sendable.getLen();
		OutputStream output;
		
		try {
				output = connToDecryptor.getOutputStream();// get the outputStream to send through
				
				String x = new String(decodedMsg);

				output.write(decodedMsg,0,decodedMsg.length);//send
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			connToDecryptor.close();
		}
		catch (IOException e)
		{}
	}
	
}