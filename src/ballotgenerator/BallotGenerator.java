package ballotgenerator;

import java.util.List;

public class BallotGenerator {
	
	private static BallotGenerator instance=null;
	private List<String> candidateList;
	
	public static BallotGenerator getInstance(List<String> candidateList) {
		
		if(instance==null){
			instance=new BallotGenerator(candidateList);
		}
		
		return instance;	
	}
	
	private BallotGenerator(List<String> candidateList) {
		
		this.candidateList=candidateList;
	}
	
	public List<String> requestBallot(String pvid) {
		
		if(!isValidPVID(pvid))
			System.out.println("Invalid PVID");

		return candidateList;
	}

	

	private boolean isValidPVID(String pvid) {
		// TODO Auto-generated method stub
		return true;
	}

}
