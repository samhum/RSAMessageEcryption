import java.util.*;
import java.math.BigInteger;


public class Message{

  String n;
  String a;
  ArrayList<String> messageText;
  int lenOfMessage;
  BigInteger b;

  public Message(String nVal, String aVal, ArrayList<String> message)
  {
    n = nVal;
    a = aVal;
    messageText = message;
    lenOfMessage = getLen();
  }

  public void setB(BigInteger bS)
  {
  	b = bS;
  }

  public ArrayList<String> getListOfNums()
  {
  	return messageText;
  }

  public BigInteger getB()
  {
  	return b;
  }

  public String getN()
  {
  	return n;
  }
  public String getA()
  {
  	return a;
  }

  public int getLen()
  {
  	int len = 0;
  	for (int i = 0; i<messageText.size(); i++)
  	{
  		len += messageText.get(i).length();
  	}
  	return len;
  }

  public String toString()
  {
  	String messageAsText = "";

  	for (int i = 0; i<messageText.size(); i++)
    {
    	messageAsText+= messageText.get(i)+" ";
    }

    String totalString = "MSG: "+messageAsText; //+"**"+n+"**"+a+"*";
    return totalString;
  }

  public byte [] convertToSendable()
  {
    String totalString =toString();

    byte [] totalPayload = totalString.getBytes();
    return totalPayload;
  }
}
