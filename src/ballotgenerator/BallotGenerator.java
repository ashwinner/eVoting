package ballotgenerator;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import authorizer.Authorizer;

public class BallotGenerator {
	
	private static BallotGenerator instance=null;
	private List<String> candidateList;
	private Authorizer authorizer;
	
	public static BallotGenerator getInstance(List<String> candidateList) {
		
		if(instance==null){
			instance=new BallotGenerator(candidateList);
		}
		
		return instance;	
	}
	
	private BallotGenerator(List<String> candidateList) {
		
		this.candidateList=candidateList;
		this.authorizer=Authorizer.getInstance();
	}
	
	public List<String> requestBallot(String pvid) {
		
		if(!isValidPVID(pvid))
			throw InvalidPvidEception;
		else
			return candidateList;
		
	}

	
	
	private boolean isValidPVID(String PVID) {
		// TODO Auto-generated method stub
		BigInteger pvid= new BigInteger(PVID);
		RSAPublicKey authorizerPublicKey=authorizer.getPublicKey();
		BigInteger e= authorizerPublicKey.getPublicExponent();
		BigInteger n= authorizerPublicKey.getModulus();
		BigInteger signVerifier= pvid.modPow(e, n);
		if(signVerifier.toString().startsWith("1000"))
			return true;
		else
			return false;
	}

}
