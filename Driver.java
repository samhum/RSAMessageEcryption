//Tests test case for RSA in textbook
import java.util.*;
import java.math.BigInteger;
public class Driver
{
  public static void main(String[]args)
  {
    String orig = "Quoth the raven nevermore";
    String nVal = "3127";
    String aVal= "61";
    RSAencrypt enc = new RSAencrypt(orig,nVal,aVal);
    ArrayList<String> encrypted = enc.encrypt();
    Message msg = new Message(nVal,aVal,encrypted);
    Message encInLetters = new Message(msg.getN(),msg.getA(),enc.toLetters(encrypted));
    System.out.println("The original message is: "+orig);
    System.out.println("The encrypted message is: "+encInLetters);
    BigInteger b = BruteForce.primeFactors(new BigInteger(msg.getN()), new BigInteger(msg.getA()));
    msg.setB(b);
    RSAdecrypt dec = new RSAdecrypt();
    String decrypted = dec.decryptMessage(msg);
    System.out.println("The decrypted message is: "+decrypted);
  }
}
