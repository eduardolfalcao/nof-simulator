package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import peer.balance.PeerInfo;

@RunWith(Suite.class)
@SuiteClasses({ StateGeneratorTest.class, PeerTest.class, PeerInfoTest.class, NetworkOfFavorsTest.class, InteractionTest.class})
public class JUnitTestSuite {

	
	
	
}
