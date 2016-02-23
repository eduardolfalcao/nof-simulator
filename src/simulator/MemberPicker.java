package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.peer.Collaborator;
import model.peer.Peer;
import model.peer.State;
import model.peer.Triplet;
import model.peer.history.PeerInfo;

public class MemberPicker {
	
	private Random randomGenerator;
	
	public MemberPicker(int seed){
		randomGenerator = new Random(seed);
	}	
	
	/**
	 * Randomly chooses an item of the list.
	 * @param peerList	 the list of integers (peer ids) on which we will randomly choose one
	 * @return the index of item randomly chosen
	 */
    private int anyPeer(List <Integer> peerList){    
        return this.randomGenerator.nextInt(peerList.size());
    }
	
	/**
	 * @param peerList	a list of integers
	 * @return the peer/collaborator randomly chosen, or null if something strange happens
	 */
	public Collaborator choosesRandomPeer(List <Integer> peerList){
		int id = peerList.get(anyPeer(peerList));
		return PeerComunity.peers[id] instanceof Collaborator?(Collaborator)PeerComunity.peers[id]:null;
	}
	
	public List<Triplet> getConsumersWithPositiveBalance(Collaborator provider){
		List<Triplet> peersWithPositiveBalance = new ArrayList<Triplet>();		
		
		Collections.sort(provider.getBalances());							//assure the balance order
		for(int i = provider.getBalances().size()-1; i >= 0 ; i--){
			PeerInfo balance = provider.getBalances().get(i);
			Peer peer = PeerComunity.peers[balance.getId()];
			if(balance.getBalance()>0 && peer.getState() == State.CONSUMING && peer.getDemand()>0)
				peersWithPositiveBalance.add(new Triplet(PeerComunity.peers[balance.getId()], balance.getBalance(), null));
		}	
		
		return peersWithPositiveBalance;		
	}
	
	public List<Triplet> getConsumersWithTransitiveBalance(Collaborator provider){		
		List<Triplet> peersWithTransitiveBalance = new ArrayList<Triplet>();		
		
		List <Peer> peersWithPositiveBalance = new ArrayList<Peer>();	//peers idle with balance>0 relative to the collaborator
		Collections.sort(provider.getBalances());						//assure the balance order
		for(int i = provider.getBalances().size()-1; i >= 0 ; i--){
			PeerInfo balance = provider.getBalances().get(i);
			Peer peer = PeerComunity.peers[balance.getId()];
			if(peer.getState() == State.IDLE && balance.getBalance()>0)
				peersWithPositiveBalance.add(peer);
		}
		
		for(Peer idlePeer : peersWithPositiveBalance){
			Collections.sort(idlePeer.getBalances());
			for(int i = idlePeer.getBalances().size()-1; i >= 0 ; i--){
				PeerInfo idleConsumer = idlePeer.getBalances().get(i);
				Peer consumer = PeerComunity.peers[idleConsumer.getId()];
				if(idleConsumer.getBalance()>0 && consumer.getState() == State.CONSUMING && consumer.getDemand()>0){	//the first suffices
					PeerInfo providerIdle = provider.getBalances().get(provider.getBalances().indexOf(new PeerInfo(idlePeer.getId())));
					double debtProviderWithIdle = providerIdle.getBalance();
					double debtIdleWithConsumer = idleConsumer.getBalance();
					double debt = Math.min(debtProviderWithIdle, debtIdleWithConsumer);
					peersWithTransitiveBalance.add(new Triplet(consumer, debt, idlePeer));
				}
			}			
		}
			
		return peersWithTransitiveBalance;		
	}
	
	public List<Triplet> getConsumersWithZeroBalance(Collaborator provider){		
		List<Triplet> peersWithZeroBalance = new ArrayList<Triplet>();		
		
		for(Peer peer : PeerComunity.peers){
			if(peer.getId()==provider.getId())
				continue;
			int balanceIndex = provider.getBalances().indexOf(new PeerInfo(peer.getId()));
			if((balanceIndex==-1 || (balanceIndex!=-1 && provider.getBalances().get(balanceIndex).getBalance()==0 && peer.getState()==State.CONSUMING && peer.getDemand()>0)) &&
					(peer.getState()==State.CONSUMING && peer.getDemand()>0))
				peersWithZeroBalance.add(new Triplet(peer, 0, null));			
		}
		
		return peersWithZeroBalance;	
	}
	
	public List<Triplet> getNextConsumersWithSameBalance(List<Triplet> consumers){
		List<Triplet> peersWithSameBalance = new ArrayList<Triplet>();
		
		Collections.sort(consumers);
		Triplet consumerWithHigherBalance = consumers.get(consumers.size()-1);
		peersWithSameBalance.add(consumerWithHigherBalance);
		
		if(consumers.size() > 1){		
			for(int i = consumers.size()-2; i>=0; i--){
				if(consumerWithHigherBalance.getDebt() == consumers.get(i).getDebt())
					peersWithSameBalance.add(consumers.get(i));
				else
					break;
			}
		}
		
		return peersWithSameBalance;
	}
	

}
