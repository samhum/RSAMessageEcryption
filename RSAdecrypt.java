import java.util.*;
import java.math.BigInteger;

public class RSAdecrypt
{


	public static String decryptMessage(Message messageIn){
      	BigInteger power = messageIn.getB();
      	BigInteger modulo = new BigInteger(messageIn.getN());
		BigInteger a;
		BigInteger b;
      //message is the final decrypted message
		String message = "";
      //subIntMessage is the sub message in integer form
		String subIntMessage = "";
      //fullIntMessage is all subIntMessages combined
		String fullIntMessage = "";
      //goes through all submessages in nums
      	
      	ArrayList<String> nums = messageIn.getListOfNums();
		for(int i=0; i<nums.size(); i++){
          //gets first sub message block
			a = new BigInteger(nums.get(i));
			b = a;
          //Puts the sub message block to the power power
			for(BigInteger j= BigInteger.ONE; j.compareTo(power) < 0; j = j.add(BigInteger.ONE)){
				a = (a.multiply(b)).mod(modulo);
			}
          //Puts the BitInteger into a string
			subIntMessage = a.toString();
          //Adds back any zeroes that were left on converting from string
			for(int k=0; k<nums.get(i).length() - String.valueOf(a).length(); k++){
				subIntMessage = "0" + subIntMessage;
			}
          //Adds on the submessage to the full message of integers
			fullIntMessage = fullIntMessage + subIntMessage;
		}
      //Changes the integer message into the alphabet
		for(int l=0; l<fullIntMessage.length()/2; l++){
          //for normal characters
			if(!fullIntMessage.substring(l*2,l*2+2).equals("26")){
				message=message + Character.toString((char) (Integer.parseInt(fullIntMessage.substring(l*2,l*2+2))+65));
              //for spaces
			}else{
				message=message + " ";
			}
		}
		return message;
	}  
}