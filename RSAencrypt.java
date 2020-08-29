import java.math.BigInteger;
import java.util.*;

public class RSAencrypt{
  private String message;
  private BigInteger n;
  private BigInteger a;

  public RSAencrypt(String message, String n, String a)
  {
    this.n = new BigInteger(n);
    this.a = new BigInteger(a);
    this.message = message;
  }
  /**
   * Changes list of strings into list of numbers then encrypts and returns list of encrypted numbers
   * @return list of encrypted numbers in the form of strings to preserve 0 in front
   */
  public ArrayList<String> encrypt()
  {
    //Breaks up message based on length of the modulus
    int len = n.toString().length()-1;
    if(len%2==1)
      len--;
    try{
      while(message.length()%len!=0)
      {
        message+=" ";
      }
    }
    catch(Exception e)
    {
      System.out.println("The modulus must be larger than the message");
      System.exit(0);
    }
    ArrayList<String> messageText = new ArrayList();
    int start = 0;
    int end = message.length();
    while(start < end)
    {
      if((start+len)<=message.length())
        messageText.add(message.substring(start,start+len));
      else
        messageText.add(message.substring(start));
      start+=len;
    }
    //Changes original message into numbers 0-26 (a-' ')
    ArrayList<int[]> numbers = new ArrayList<int[]>();
  	int ctr = 0;
  	for(int k = 0; k < messageText.size();k++)
  	{
    		int[] nums = new int[2*messageText.get(k).length()];
    		for(int j = 0; j< messageText.get(k).length();j++)
    		{
      		char c = messageText.get(k).charAt(j);

      		if(c>='A' && c<='Z')
      		{
        			int n = c-'A';
        			if(n < 10)
        			{
          			nums[ctr] = 0;
          			ctr++;
        			}
              else
              {
                nums[ctr] = n/10;
                ctr++;
              }
        			nums[ctr] = n%10;
          		ctr++;
      		}
      		else if(c>='a' && c<='z')
      		{
       			  int n = c-'a';
        			if(n < 10)
        			{
          			nums[ctr] = 0;
          			ctr++;
        			}
              else
              {
                nums[ctr] = n/10;
                ctr++;
              }
              nums[ctr] = n%10;
          		ctr++;
      		}
      	else if(c == ' ')
        {
          nums[ctr] = 26/10;
          ctr++;
          nums[ctr] = 26%10;
          ctr++;
        }
      }
    		ctr = 0;
    		numbers.add(nums);
  	}

    //Changes from having one int per index in arraylist to storing whole number in arraylist
    BigInteger bigNum = BigInteger.ZERO;
    ctr = 0;
    ArrayList<BigInteger> listNum = new ArrayList();
    for(int k = 0; k<numbers.size();k++)
    {
      for(int j = numbers.get(k).length-1; j>=0;j--)
      {
        BigInteger mult = BigInteger.valueOf(numbers.get(k)[ctr]);
        bigNum = bigNum.add((BigInteger.TEN.pow(j).multiply(mult)));
        ctr++;
      }
      listNum.add(bigNum);
      ctr = 0;
      bigNum = BigInteger.ZERO;
    }

    //apply encryption
    ArrayList<BigInteger> encoded = new ArrayList();
    for(int k = 0; k < listNum.size();k++)
    {
      BigInteger coded = (listNum.get(k).pow(a.intValue())).mod(n);
      encoded.add(coded);
    }
    ArrayList<String> finalencrypt = new ArrayList();
    for(int x = 0; x<encoded.size();x++)
    {
      String send = encoded.get(x).toString();
      while(2*len>send.length())
      {
        send="0"+send;
      }
      finalencrypt.add(send);
    }
    return finalencrypt;
  }

  /**
   * Takes list of encrypted Integers and changes them back into words (for aesthetics when testing)
   * @param msg the list of encrypted Integers
   * @return the list of strings to form an encrypted message
   */
  public ArrayList<String> toLetters(ArrayList<String> msgs)
  {
    ArrayList<BigInteger> msg = new ArrayList();
    for(int l = 0; l < msgs.size();l++)
    {
      msg.add(new BigInteger(msgs.get(l)));
    }
    ArrayList<String> words = new ArrayList();
    String word = "";
    char letter=0;
    for(int k = 0; k<msg.size();k++)
    {
      BigInteger toCode = msg.get(k);
      while(toCode.compareTo(BigInteger.valueOf(0))>0)
      {
        int mod = toCode.mod(BigInteger.valueOf(100)).intValue();
        int toLetter = (int)(mod%26);
        if(toLetter<26)
        	letter = (char)('a'+toLetter);
        else if(toLetter == 26)
          letter = ' ';
        word+= letter;
        toCode = toCode.divide(BigInteger.valueOf(100));
      }
      String reverse ="";
      for(int j = word.length()-1;j>=0;j--)
      {
        reverse+= word.charAt(j);
      }
      words.add(reverse);
      word = "";
    }
    return words;
  }
  /**
   * Call this method to encrypt message sent
   * @return message to send to decryptor
   */
  public Message getMessage()
  {
    Message m = new Message(n.toString(),a.toString(),this.encrypt());
    return m;
  }
}
