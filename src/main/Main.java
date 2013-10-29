
package main;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import pvidgenerator.PVIDGenerator;
import authorizer.Authorizer;
import ballotgenerator.BallotGenerator;
import utils.FileOperations;
import tallier.Tallier;
import voter.Voter;
import collector.Collector;


public class Main {

	public static void main(String[] args) throws Exception {
	
	List<String> candidateList = FileOperations.createCandidateList(args[0]);
	//Map<String, String> emailIdToPasskeyMap = FileOperations.createEmailToPasskeyMap(args[1]);
	
	/*Authorizer authorizer = Authorizer.getInstance();*/
	//PVIDGenerator pvidGenerator = PVIDGenerator.getInstance(authorizer, emailIdToPasskeyMap);
	BallotGenerator ballotGenerator=BallotGenerator.getInstance(candidateList);
	Collector collector=Collector.getInstance();
	
	long startTime=System.currentTimeMillis();
	
	while(System.currentTimeMillis()<startTime+TimeUnit.SECONDS.toMillis(30)){
		
		Voter voter= new Voter(ballotGenerator,collector);
		voter.run();
	}
	
	Map<String,Integer> pvidToVoteMap= collector.getPVIDtoVoteMap();
	Tallier tallier=Tallier.getInstance();
	displayTallyMap(pvidToVoteMap);
	Map<String, Integer> tallyMap=tallier.tally(pvidToVoteMap, candidateList);
	displayTallyMap(tallyMap);
	
	
}
private static void displayTallyMap(Map<String, Integer> tallyMap) {
	// TODO Auto-generated method stub
	System.out.println("Results");
	Iterator<Entry<String, Integer>> entries = tallyMap.entrySet().iterator();
	while (entries.hasNext()) {
	  Entry<String, Integer> thisEntry = (Entry<String, Integer>) entries.next();
	  String key =  thisEntry.getKey();
	  int value = thisEntry.getValue();
	  System.out.printf("%s\t%d\n", key,value);
}}
}
