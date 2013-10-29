package tallier;

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
	
	public static Tallier getInstance() {
		
		if(instance==null){
		instance=new Tallier();
	}
		return instance;		
}
	
	public Map<String, Integer> tally(Map<String, byte[]> pvidToEncrpytedVoteMap, Map<String,Key> keyGeneratorMap ,List<String> candidateList ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		Map<String,Integer> tallyMap;
		tallyMap=initializeTallyMap(candidateList);
		
		for(Entry<String, byte[]> entry : pvidToEncrpytedVoteMap.entrySet()) {
			
		  String PVID = entry.getKey();
		  byte[] EncryptedVote = entry.getValue();

		  //Get key corresponding to the extracted PVID  from the Key Generator's map
		  Key key = keyGeneratorMap.get(PVID);
		 
		  
		  // Create and initialize the cipher for encryption
		  Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		  // Initialize the cipher for decryption
		  aesCipher.init(Cipher.DECRYPT_MODE, key);
		  
		  byte[] text = EncryptedVote;

		  // Decrypt the text
		  byte[] textDecrypted = aesCipher.doFinal(text);

		  int value = Integer.parseInt(new String(textDecrypted));
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
