package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.peer.Peer;
import model.peer.Triplet;

import org.junit.Assert;
import org.junit.Test;

public class TripletTest {
	
	@Test
	public void testEquals() {
		
		Peer p1 = new Peer(1, 0, 0, null, 0, 0, 1);
		Peer p2 = new Peer(2, 0, 0, null, 0, 0, 1);
		Peer p1Aux = new Peer(1, 0, 0, null, 0, 0, 1);

		
		Triplet t1 = new Triplet(p1, 0, null);
		Triplet t1t2 = new Triplet(p1, 0, p2);
		Triplet t1Aux = new Triplet(p1Aux, 0, null);
		Triplet t1Auxt2 = new Triplet(p1Aux, 0, p2);
		
		Assert.assertTrue(t1.equals(t1Aux));
		Assert.assertFalse(t1.equals(t1t2));
		Assert.assertTrue(t1t2.equals(t1Auxt2));
		
	}
	
	@Test
	public void testCompareTo() {
		
		Triplet t1 = new Triplet(null, 10, null);
		Triplet t2 = new Triplet(null, 2, null);
		Triplet t3 = new Triplet(null, 5, null);
		Triplet t4 = new Triplet(null, 8, null);
		Triplet t5 = new Triplet(null, 1, null);
		Triplet t6 = new Triplet(null, 3, null);
		Triplet t7 = new Triplet(null, 9, null);
		Triplet t8 = new Triplet(null, 7, null);
		Triplet t9 = new Triplet(null, 10, null);
		Triplet t10 = new Triplet(null, 1, null);
		
		Triplet[] triplets = new Triplet[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10};
		
		List<Triplet> consumingPeers = new ArrayList<Triplet>();
		for(Triplet triplet : triplets)
			consumingPeers.add(triplet);		
		System.out.println(consumingPeers);
		
		Assert.assertTrue(consumingPeers.get(0).getDebt()==10);
		Assert.assertTrue(consumingPeers.get(4).getDebt()==1);
		Assert.assertTrue(consumingPeers.get(9).getDebt()==1);
		
		Collections.sort(consumingPeers);
		System.out.println(consumingPeers);
		
		Assert.assertTrue(consumingPeers.get(0).getDebt()==1);
		Assert.assertTrue(consumingPeers.get(4).getDebt()==5);
		Assert.assertTrue(consumingPeers.get(9).getDebt()==10);
		
	}

}
