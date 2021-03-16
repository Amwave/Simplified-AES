import java.io.*;
import java.net.*;
import java.math.*;
import java.util.*;
public class Client {
	
	public static String StringXOR(String a, String b)               //xor of two strings
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
	
	public static String RotW(String k)                               //function for RotWord (refer readme)
	{
		String n1 = ""; n1 = n1 + k.charAt(0) + k.charAt(1) + k.charAt(2) + k.charAt(3);
		String n2 = ""; n2 = n2 + k.charAt(4) + k.charAt(5) + k.charAt(6) + k.charAt(7);
		return n2+n1;
	}
	
	public static String SubW(String k)                              //function for SubWord (refer readme)
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
	
	public static String SubNib(String s)                           //function for substituting nibble
	{
		HashMap<String, String> sub_table = new HashMap<>();
		sub_table.put("0000", "1001");
		sub_table.put("0001", "0100");
		sub_table.put("0010", "1010");
		sub_table.put("0011", "1011");
		sub_table.put("0100", "1101");
		sub_table.put("0101", "0001");
		sub_table.put("0110", "1000");
		sub_table.put("0111", "0101");
		sub_table.put("1000", "0110");
		sub_table.put("1001", "0010");
		sub_table.put("1010", "0000");
		sub_table.put("1011", "0011");
		sub_table.put("1100", "1100");
		sub_table.put("1101", "1110");
		sub_table.put("1110", "1111");
		sub_table.put("1111", "0111");
		
		return sub_table.get(s);
	}
	
	public static String multiply4(String s)                       //function for multiplying by 4 in GF(16)
	{
		HashMap<String, String> mix_table = new HashMap<>();
		mix_table.put("0000","0000");
		mix_table.put("0001","0100");
		mix_table.put("0010","1000");
		mix_table.put("0011","1100");
		mix_table.put("0100","0011");
		mix_table.put("0101","0111");
		mix_table.put("0110","1011");
		mix_table.put("0111","1111");
		mix_table.put("1000","0110");
		mix_table.put("1001","0010");
		mix_table.put("1010","1110");
		mix_table.put("1011","1010");
		mix_table.put("1100","0101");
		mix_table.put("1101","0001");
		mix_table.put("1110","1101");
		mix_table.put("1111","1001");
		
		return mix_table.get(s);
	}
	
	 
	public static String S_AES(String pt_b, String key)    //this is where the encryption happens (16 bit)
	{
		String w0 = "", w1 = "";                               //key expansion starts here
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
		String k2 = w4 + w5;                             //key expansion ends here
		
		String r0 = StringXOR(pt_b, k0);                  //pre round transformation
		System.out.println("Pre round transformation :" + r0);
		System.out.println("Round 0 key :" + k0);
		
		String n01 = ""; n01 = n01 + r0.charAt(0) + r0.charAt(1) + r0.charAt(2) + r0.charAt(3);     //separating nibbles for further processing 
		String n02 = ""; n02 = n02 + r0.charAt(4) + r0.charAt(5) + r0.charAt(6) + r0.charAt(7);
		String n03 = ""; n03 = n03 + r0.charAt(8) + r0.charAt(9) + r0.charAt(10) + r0.charAt(11);
		String n04 = ""; n04 = n04 + r0.charAt(12) + r0.charAt(13) + r0.charAt(14) + r0.charAt(15);
		
		String r1a = SubNib(n01) + SubNib(n02) + SubNib(n03) + SubNib(n04);              //Round 1 substitute nibble operation
		System.out.println("After Round 1 Substitute nibbles :" + r1a);
		
		String r1b = SubNib(n01) + SubNib(n04) + SubNib(n03) + SubNib(n02);               //Round 1 swapping operation
		System.out.println("After Round 1 Shift rows :" + r1b);
		
		String r11 = StringXOR(SubNib(n01), multiply4(SubNib(n04)));                 //mixcolumns operation starts here
		String r12 = StringXOR(SubNib(n04), multiply4(SubNib(n01)));                 //multiply4 function is used
		                                                                             //using a lookup table
		String r13 = StringXOR(SubNib(n03), multiply4(SubNib(n02)));
		String r14 = StringXOR(SubNib(n02), multiply4(SubNib(n03)));
		
		String r1c = r11 + r12 + r13 + r14;                                         //mixcolumns operation ends here
		System.out.println("After Round 1 Mix columns :" + r1c);
		
		String r1 = StringXOR(r1c, k1);                                           //Adding round 1 key
		System.out.println("After Round 1 Add round key :" + r1);
		System.out.println("Round 1 key :" + k1);
		
		String n11 = ""; n11 = n11 + r1.charAt(0) + r1.charAt(1) + r1.charAt(2) + r1.charAt(3);       //Separating round 1 encrypted ciphertext into nibbles for further processing
		String n12 = ""; n12 = n12 + r1.charAt(4) + r1.charAt(5) + r1.charAt(6) + r1.charAt(7);
		String n13 = ""; n13 = n13 + r1.charAt(8) + r1.charAt(9) + r1.charAt(10) + r1.charAt(11);
		String n14 = ""; n14 = n14 + r1.charAt(12) + r1.charAt(13) + r1.charAt(14) + r1.charAt(15);
		
		String r2a = SubNib(n11) + SubNib(n12) + SubNib(n13) + SubNib(n14);                //Round 2 substitute nibble operation
		System.out.println("After Round 2 Substitute nibbles :" + r2a);
		
		String r2b = SubNib(n11) + SubNib(n14) + SubNib(n13) + SubNib(n12);                //Round 2 swapping operation
		System.out.println("After Round 2 Shift rows :" + r2b);
		
		String r2 = StringXOR(r2b, k2);                                                   //Adding round 2 key
		
		System.out.println("After Round 2 Add round key :" + r2);
		System.out.println("Round 2 key :" + k2);
		return r2;                                                                     //returning ciphertext chunk
		
	}
	
	public static String S2B(String S)                 //convert string into binary stream
	{
		String binary = new BigInteger(S.getBytes()).toString(2);
		return binary;
	}
	
	public static String B2S(String S)                 //convert binary stream into string
	{
		String binary = new BigInteger(S.getBytes()).toString(2);
		return binary;
	}
	
	
	public static void main(String args[]) throws Exception
	{
		while(true) {
			
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));  //initialization
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String plaintext = inFromUser.readLine();                      //input plaintext from user (text/ UTF-8)
		String key = inFromUser.readLine();                            //input key from user
		
		String plaintext_binary = S2B(plaintext);                    //converting input text into binary
		
		System.out.println("Plaintext in binary before padding :" + plaintext_binary);
		int length = plaintext_binary.length();
		int r = length%16;
		
		if(r!=0)
		for(int i=0 ; i<(16-r) ; i++)
			plaintext_binary = plaintext_binary + "0";                // padding with 0s
		
		char[] plain = plaintext_binary.toCharArray();          
		
		System.out.println("Input plaintext in binary after padding :" + plaintext_binary);
		System.out.println("Encryption of each 16-bit chunk as follows :");
		
		int limit=length/16;
		if(r!=0)
		   limit = (length + 16 - r)/16;
		
		String finalsend = "";
		for(int i=0 ; i<limit ; i++)                                         //encrypting each 16-bit chunk separately
		{
			System.out.println("");
			System.out.println("Encryption of chunk :" + i);   
			System.out.println("");
			String encrypt_chunk = "";
			for(int j=16*i ; j<16*(i+1) ; j++)
			{
				encrypt_chunk = encrypt_chunk + plain[j];                      //adding characters one by one to form 16-bit
			}
			finalsend = finalsend + S_AES(encrypt_chunk, key);             //appending each encrypted chunk
		}
		System.out.println("");
		System.out.println("Final Ciphertext :" + finalsend);                 //output final ciphertext to be sent
		int padding = 16-r; String pad = Integer.toString(padding);
		finalsend = finalsend + " " + key + " " + pad;                      //adding key and padding data to finalsend
		sendData = finalsend.getBytes(); 
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
		received.trim();
		System.out.println("FROM SERVER :" + received);
		
		}
	}
}
