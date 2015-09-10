package tests;

import static org.junit.Assert.assertTrue;
import nof.NetworkOfFavors;

import org.junit.Test;

/**
 * @author eduardolfalcao
 *
 */
public class NetworkOfFavorsTest {

	@Test
	public void testCalculateLocalReputation() {		
		assertTrue(NetworkOfFavors.calculateBalance(50, 50)==0);		
		assertTrue(NetworkOfFavors.calculateBalance(0, 50)==0);
		assertTrue(NetworkOfFavors.calculateBalance(50, 0)==50);
	}

}
