package tests;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import peer.balance.PeerInfo;

public class PeerInfoTest {

	@Test
	public void testEquals() {
		PeerInfo p1 = new PeerInfo(1);
		PeerInfo p2 = new PeerInfo(2);
		PeerInfo p1aux = new PeerInfo(1);
		
		Assert.assertTrue(p1.equals(p1aux));
		Assert.assertFalse(p1.equals(p2));
	}
	
	@Test
	public void testSort() {
		ArrayList<PeerInfo> peerInfoList = new ArrayList<PeerInfo>();
		
		PeerInfo p1 = new PeerInfo(1);
		p1.setBalance(10);
		PeerInfo p2 = new PeerInfo(2);
		p2.setBalance(5);
		PeerInfo p3 = new PeerInfo(3);
		p3.setBalance(15);
		PeerInfo p4 = new PeerInfo(4);
		p4.setBalance(20);
		PeerInfo p5 = new PeerInfo(5);
		p5.setBalance(0);
		
		peerInfoList.add(p1);
		peerInfoList.add(p2);
		peerInfoList.add(p3);
		peerInfoList.add(p4);
		peerInfoList.add(p5);
		
		Assert.assertTrue(peerInfoList.get(0).getId()==1 && peerInfoList.get(4).getId()==5);
		Collections.sort(peerInfoList);
		Assert.assertTrue(peerInfoList.get(0).getId()==5 && peerInfoList.get(4).getId()==4);		
	}
}