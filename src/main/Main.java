
package main;

import java.io.Serializable;
import java.security.Key;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import pvidgenerator.PVIDGenerator;
import ballotgenerator.BallotGenerator;
import utils.FileOperations;
import tallier.Tallier;
import voter.Voter;
import collector.Collector;
import collector.CollectorBullettinBoard;
import keygenerator.KeyGenerator;


public class Main {

	public static void main(String[] args) throws Exception {
	
	List<String> candidateList = FileOperations.createCandidateList(args[0]);
	KeyGenerator keyGenerator = KeyGenerator.getInstance();
	Map<String, String> emailIdToPasskeyMap = FileOperations.createEmailToPasskeyMap(args[1]);

	PVIDGenerator pvidGenerator = PVIDGenerator.getInstance(emailIdToPasskeyMap);
	BallotGenerator ballotGenerator=BallotGenerator.getInstance(candidateList);
	Collector collector=Collector.getInstance();
	
	long startTime=System.currentTimeMillis();
	
	while(System.currentTimeMillis()<startTime+TimeUnit.SECONDS.toMillis(150)){
		
		Voter voter= new Voter(pvidGenerator, ballotGenerator, collector);
		voter.run();
	}
	
	Map<String, byte[]> pvidToEncrpytedVoteMap= collector.getPVIDtoEncryptedVoteMap();
	Map<String, Key> pvidToKeyMap = keyGenerator.getPvidToKeyMap();
	
	Tallier tallier=Tallier.getInstance();
	Map<String, Integer> tallyMap=tallier.tally(pvidToEncrpytedVoteMap, pvidToKeyMap, candidateList);
	displayTallyMap(tallyMap);
	CollectorBullettinBoard collectorBullettinBoard = CollectorBullettinBoard.getInstance();
	FileOperations.saveMap("/home/reshma/Desktop/collectorBullettinBoard.txt", (Serializable) collectorBullettinBoard.getCollectorBullettinBoard());
	FileOperations.saveMap("/home/reshma/Desktop/pvidToKeyMap.txt", (Serializable) keyGenerator.getPvidToKeyMap());
	
	
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
