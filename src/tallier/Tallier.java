package tallier;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Tallier 
{
	
	private static Tallier instance=null;	
	public static Tallier getInstance() 
	{
		
		if(instance==null){
		instance=new Tallier();
	}
		return instance;		
}
	
	public Map<String, Integer> tally(Map<String, String> pvidToVoteMap, Map<String,Key> KeyGeneratorMap ,List<String> candidateList ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		Map<String,Integer> tallyMap;
		tallyMap=initializeTallyMap(candidateList);
		
		Iterator<Entry<String,String>> entries = pvidToVoteMap.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry<String,String> thisEntry = (Entry<String, String>) entries.next();
		  String PVID = (String) thisEntry.getKey();
		  String EncryptedVote = (String) thisEntry.getValue();
		  //Get key corresponding to the extracted PVID  from the Key Generator's map
		  Key key = KeyGeneratorMap.get(PVID);
		 
		  //Decrypt the encrypted vote using the key

		  Cipher desCipher; // Create the cipher 
		  desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");// Initialize the cipher for encryption

		  // Initialize the cipher for decryption
		  desCipher.init(Cipher.DECRYPT_MODE,key);
		  byte[] text = EncryptedVote.getBytes();

		  // Decrypt the text
		  byte[] textDecrypted = desCipher.doFinal(text);

		  System.out.println("Text Decryted : " + new String(textDecrypted));
		  ByteBuffer wrapped = ByteBuffer.wrap(textDecrypted); // big-endian by default
		  int value = wrapped.getShort(); // 1
		  String candidate=candidateList.get(value-1);
		  int count=tallyMap.get(candidate);
		  tallyMap.put(candidate, ++count);	
		}
		
		return tallyMap;
	}

	
	private Map<String, Integer> initializeTallyMap (List<String> candidateList) {
		
		Map<String,Integer> tallyMap=new TreeMap<String,Integer>();
		
		for (String candidate : candidateList)
			tallyMap.put(candidate, 0);
	
		return tallyMap;
	}

}
