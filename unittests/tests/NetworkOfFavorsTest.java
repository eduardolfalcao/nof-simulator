/**
 * 
 */
package tests;

import static org.junit.Assert.*;
import nof.Interaction;
import nof.NetworkOfFavors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

/**
 * @author eduardolfalcao
 *
 */
public class NetworkOfFavorsTest {


	/**
	 * Test method for {@link NetworkOfFavors#calculateLocalReputation(double, double, boolean)}.
	 */
	@Test
	public void testCalculateLocalReputation() {
		
		/**
		 * The case that donation is equal to consumption (with or without log).
		 * The case that donations is greater than consumption (with or without log).
		 * The case that consumption is greater than donations (with or without log).
		 */
		
		assertTrue(NetworkOfFavors.calculateLocalReputation(50, 50, true)>0);
		assertTrue(NetworkOfFavors.calculateLocalReputation(50, 50, false)>0);
		
		assertTrue(NetworkOfFavors.calculateLocalReputation(0, 50, true)==0);
		assertTrue(NetworkOfFavors.calculateLocalReputation(50, 0, true)>0);
		assertTrue(NetworkOfFavors.calculateLocalReputation(0, 50, false)==0);
		assertTrue(NetworkOfFavors.calculateLocalReputation(50, 0, false)>0);
	}

}
