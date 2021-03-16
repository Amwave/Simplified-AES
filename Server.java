import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;

public class Server {
	
	public static String StringXOR(String a, String b)
	{
		int len = a.length();
		String xored = "";
		for(int i=0 ; i<len ; i++)
		{
		    if(a.charAt(i) == b.charAt(i))
		    	xored = xored + "0";
		    else
		    	xored = xored + "1";
		}
		return xored;
	}
	
	public static String RotW(String k)
	{
		String n1 = ""; n1 = n1 + k.charAt(0) + k.charAt(1) + k.charAt(2) + k.charAt(3);
		String n2 = ""; n2 = n2 + k.charAt(4) + k.charAt(5) + k.charAt(6) + k.charAt(7);
		return n2+n1;
	}
	
	public static String SubW(String k)
	{
		HashMap<String, String> sub_table = new HashMap<>();
		sub_table.put("0000", "1001");
		sub_table.put("0001", "0100");
		sub_table.put("0010", "1010");
		sub_table.put("0011", "1011");
		sub_table.put("0100", "1101");
		sub_table.put("0101", "0001");
		sub_table.put("0110", "1001");
		sub_table.put("0111", "0101");
		sub_table.put("1000", "0110");
		sub_table.put("1001", "0010");
		sub_table.put("1010", "0000");
		sub_table.put("1011", "0011");
		sub_table.put("1100", "1100");
		sub_table.put("1101", "1110");
		sub_table.put("1110", "1111");
		sub_table.put("1111", "0111");
		
		String n1 = ""; n1 = n1 + k.charAt(0) + k.charAt(1) + k.charAt(2) + k.charAt(3);
		String n2 = ""; n2 = n2 + k.charAt(4) + k.charAt(5) + k.charAt(6) + k.charAt(7);
		
		String n3 = sub_table.get(n1), n4 = sub_table.get(n2);
		return n3 + n4;
	}
	
	public static String SubNib(String s)
	{
		HashMap<String, String> sub_table = new HashMap<>();
		sub_table.put("1001", "0000");
		sub_table.put("0100", "0001");
		sub_table.put("1010", "0010");
		sub_table.put("1011", "0011");
		sub_table.put("1101", "0100");
		sub_table.put("0001", "0101");
		sub_table.put("1000", "0110");
		sub_table.put("0101", "0111");
		sub_table.put("0110", "1000");
		sub_table.put("0010", "1001");
		sub_table.put("0000", "1010");
		sub_table.put("0011", "1011");
		sub_table.put("1100", "1100");
		sub_table.put("1110", "1101");
		sub_table.put("1111", "1110");
		sub_table.put("0111", "1111");
		
		return sub_table.get(s);
	}
	
	public static String multiply9(String s)                     //function for multiplying by 9 in GF(16)
	{
		HashMap<String, String> mix_table = new HashMap<>();
		mix_table.put("0000","0000");
		mix_table.put("0001","1001");
		mix_table.put("0010","0001");
		mix_table.put("0011","1000");
		mix_table.put("0100","0010");
		mix_table.put("0101","1011");
		mix_table.put("0110","0011");
		mix_table.put("0111","1010");
		mix_table.put("1000","0100");
		mix_table.put("1001","1101");
		mix_table.put("1010","0101");
		mix_table.put("1011","1100");
		mix_table.put("1100","0110");
		mix_table.put("1101","1111");
		mix_table.put("1110","0111");
		mix_table.put("1111","1110");
		
		return mix_table.get(s);
	}
	
	public static String multiply2(String s)                 //function for multiplying by 2 in GF(16)
	{
		HashMap<String, String> mix_table = new HashMap<>();
		mix_table.put("0000","0000");
		mix_table.put("0001","0010");
		mix_table.put("0010","0100");
		mix_table.put("0011","0110");
		mix_table.put("0100","1000");
		mix_table.put("0101","1010");
		mix_table.put("0110","1100");
		mix_table.put("0111","1110");
		mix_table.put("1000","0011");
		mix_table.put("1001","0001");
		mix_table.put("1010","0111");
		mix_table.put("1011","0101");
		mix_table.put("1100","1011");
		mix_table.put("1101","1001");
		mix_table.put("1110","1111");
		mix_table.put("1111","1101");
		
		return mix_table.get(s);
	}
	
	public static String AES_Decryption(String ciphertext, String key)        //this is main decryption funtion
	{
		String w0 = "", w1 = "";                                        //key expansion starts here
		for(int i=0 ; i<8 ; i++)
		{
			w0 = w0 + key.charAt(i);
		}
		
		for(int i=8 ; i<16 ; i++)
		{
			w1 = w1 + key.charAt(i);
		}
		
		String w1_r = RotW(w1);
		String w1_s = SubW(w1_r);
		
		String w2 = StringXOR(StringXOR("10000000", w0), w1_s);
		String w3 = StringXOR(w1, w2);
		
		String w3_r = RotW(w3);
		String w3_s = SubW(w3_r);
		
		String w4 = StringXOR(StringXOR("00110000", w2), w3_s);
		String w5 = StringXOR(w4, w3);
		
		String k0 = key;
		String k1 = w2 + w3;
		String k2 = w4 + w5;                                                   //key expansion ends here
		
		
		String r2 = StringXOR(ciphertext, k2);                                 //pre round transformation (using round 2 key k2)
		System.out.println("After pre round transformation :" + r2);
		System.out.println("Round 2 key k2:" + k2);
		
		String n21 = ""; n21 = n21 + r2.charAt(0) + r2.charAt(1) + r2.charAt(2) + r2.charAt(3);                //separating nibbles for further processing
		String n22 = ""; n22 = n22 + r2.charAt(4) + r2.charAt(5) + r2.charAt(6) + r2.charAt(7);
		String n23 = ""; n23 = n23 + r2.charAt(8) + r2.charAt(9) + r2.charAt(10) + r2.charAt(11);
		String n24 = ""; n24 = n24 + r2.charAt(12) + r2.charAt(13) + r2.charAt(14) + r2.charAt(15);
		
		String r2a = n21 + n24 + n23 + n22;                                    //Round 1 inverse shift row operation
		System.out.println("After Round 1 InvShift rows :" + r2a);
		
		String r2b = SubNib(n21) + SubNib(n24) + SubNib(n23) + SubNib(n22);      //Round 1 inverse substitute nibbles operation
		System.out.println("After Round 1 InvSubstitute nibbles :" + r2b);
		
		String r1 = StringXOR(r2b, k1);                                           //Adding round 1 key k1
		System.out.println("After Round 1 InvAdd round key :" + r1);
		System.out.println("Round 1 key k1:" + k1);
		
		String n11 = ""; n11 = n11 + r1.charAt(0) + r1.charAt(1) + r1.charAt(2) + r1.charAt(3);         //separating nibbles for further processing
		String n12 = ""; n12 = n12 + r1.charAt(4) + r1.charAt(5) + r1.charAt(6) + r1.charAt(7);
		String n13 = ""; n13 = n13 + r1.charAt(8) + r1.charAt(9) + r1.charAt(10) + r1.charAt(11);
		String n14 = ""; n14 = n14 + r1.charAt(12) + r1.charAt(13) + r1.charAt(14) + r1.charAt(15);
		
		String r11 = StringXOR(multiply9(n11), multiply2(n12));                      //inverse mix columns operation starts here
		String r12 = StringXOR(multiply9(n12), multiply2(n11));                      //multiplication with 2 and 9 is done by function
		String r13 = StringXOR(multiply9(n13), multiply2(n14));                      //with the help of lookup tables
		String r14 = StringXOR(multiply9(n14), multiply2(n13));
		
		
		String r1a = r11 + r12 + r13 + r14;                                         //inverse mix columns operation ends here
		System.out.println("After Round 1 InvMix columns :" + r1a);
		
		String r1b = r11 + r14 + r13 + r12;                                         //round 2 inverse shift rows operation
		System.out.println("After Round 2 InvShift rows :" + r1b);                   
		
		String r1c = SubNib(r11) + SubNib(r14) + SubNib(r13) + SubNib(r12);          //round 2 inverse substitute nibbles operation
		System.out.println("After Round 2 InvSubstitute nibbles :" + r1c);   
		
		String r0 = StringXOR(r1c, k0); 
		System.out.println("After Round 2 Add round key :" + r0);                    //Add round key k0
		
		System.out.println("Round 0 key k0:" + k0);                                  //round key k0
		
		System.out.println("Plaintext chunk :" + r0);                             //output plaintext (decrypted) 16-bit chunk
		
		return r0;
		
		
	}
	
	
	public static void main(String args[]) throws Exception
	{
	DatagramSocket serverSocket = new DatagramSocket(9876);
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	while(true)
	{   
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
		receiveData = receivePacket.getData();
		String receivedData = new String(receiveData, 0, receivePacket.getLength());  //receiving data from client
		InetAddress IPAddress = receivePacket.getAddress();
		int port = receivePacket.getPort();
		
		
		String[] received = receivedData.split(" ");                          //separating ciphertext, key and padding data
		String ciphertext = received[0];                                      //ciphertext
		String key = received[1];                                             //key
		String padding = received[2]; int pad = Integer.parseInt(padding);    //padding
		int length = ciphertext.length();                 
		
		char[] cipher = ciphertext.toCharArray();
		String plaintext_padded_binary = "";
		int limit = length/16;
		for(int i=0 ; i<limit ; i++)                         //decrypting each 16-bit chunk separately
		{
			String decrypt_chunk = "";
			System.out.println("");
			System.out.println("Decryption of chunk :" + i);
			for(int j=16*i ; j<16 * (i+1) ; j++)
			{
				decrypt_chunk = decrypt_chunk + cipher[j];
			}
			System.out.println("");
			plaintext_padded_binary = plaintext_padded_binary + AES_Decryption(decrypt_chunk, key);       //appending decrypted chunks to form original plaintext
		}
		int original_length = length - pad;
		String plaintext_binary = "";
		
		for(int i=0 ; i<original_length ; i++)                                     //removing padding
		{
			plaintext_binary = plaintext_binary + plaintext_padded_binary.charAt(i);
		}
		
		
		String plaintext = new String(new BigInteger(plaintext_binary, 2).toByteArray());        //converting binary plaintext back to text (original input)
		System.out.println("");
		System.out.print("Original plaintext :" + plaintext);
		sendData = plaintext.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		serverSocket.send(sendPacket);
	}
	
	}
}
