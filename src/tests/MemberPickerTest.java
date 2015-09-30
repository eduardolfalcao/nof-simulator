package tests;

import java.util.ArrayList;
import java.util.List;

import nof.Interaction;
import nof.NetworkOfFavors;


import org.junit.Assert;
import org.junit.Test;

import peer.Collaborator;
import peer.Peer;
import peer.State;
import peer.balance.PeerInfo;
import simulator.MemberPicker;

public class MemberPickerTest {
	
	//chooses a peer with balance > 0 to consume resources.
	@Test
	public void choosesConsumerWithTransitiveCreditTest(){
		
//		MemberPicker mp = new MemberPicker(1);
		
//		public List<Peer> choosesConsumerWithTransitiveCredit(Collaborator provider, List <Integer> consumersList, List <Integer> alreadyConsumed, List<Peer> peersInvolvedInTheIndirectCredit){
		
//		//teste1 quando nenhum peer tem credito direto
//		Collaborator A, B = null;
//		A = new Collaborator(0, 1, 0, State.PROVIDING, 0, 0, 1);
//		B = new Collaborator(1, 1, 1, State.CONSUMING, 1, 0, 1);
//		Interaction interactionAB = new Interaction(B, 1, 1);
//		interactionAB.donate(1);
//		PeerInfo balanceOfB = new PeerInfo(B.getId());
//		balanceOfB.setBalance(NetworkOfFavors.calculateBalance(interactionAB.getConsumed(), interactionAB.getDonated()));
//		A.getBalances().add(balanceOfB);
//		A.getInteractions().add(interactionAB);
//		
//		List <Integer> consumersList = new ArrayList<Integer>();
//		consumersList.add(B.getId());
//		List <Integer> alreadyConsumed = new ArrayList<Integer>();
//		List<Peer> peersInvolvedInTheIndirectCredit = null;
//		peersInvolvedInTheIndirectCredit = mp.choosesConsumerWithTransitiveCredit(A, consumersList, alreadyConsumed, peersInvolvedInTheIndirectCredit);
//		Assert.assertNull(peersInvolvedInTheIndirectCredit);
		
		
		//teste2 quando um peer tem credito direto
//		interactionAB.consume(2);
//		balanceOfB.setBalance(NetworkOfFavors.calculateBalance(interactionAB.getConsumed(), interactionAB.getDonated()));
//		peersInvolvedInTheIndirectCredit = mp.choosesConsumerWithTransitiveCredit(A, consumersList, alreadyConsumed, peersInvolvedInTheIndirectCredit);
//		Assert.assertNotNull(peersInvolvedInTheIndirectCredit);
//		Assert.assertTrue(peersInvolvedInTheIndirectCredit.get(0).getId()==1);
//		
		//teste3 quando um peer tem credito indireto, com apenas um peer de intermediário
		
		//teste4 quando um peer tem credito indireto, com dois peers de intermediário
		
		//teste5 quando um peer tem credito indireto, com cinco peers de intermediário
		
		//teste6 quando o credito indireto volta para o próprio provider
		
			
	}

}
