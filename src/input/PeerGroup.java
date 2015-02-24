package input;

import java.util.Arrays;

public class PeerGroup {
	
	private char [] productsProvided;
	private char [] productsDemanded;
	private int numberOfPeers;
	
	public PeerGroup(char[] productsProvided, char[] productsDemanded,
			int numberOfPeers) {
		super();
		this.productsProvided = productsProvided;
		this.productsDemanded = productsDemanded;
		this.numberOfPeers = numberOfPeers;
	}
	



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeerGroup other = (PeerGroup) obj;
		if (Arrays.equals(productsDemanded, other.productsDemanded) &&
				Arrays.equals(productsProvided, other.productsProvided))
			return true;
		return false;
	}



	/**
	 * @return the productsProvided
	 */
	public char[] getProductsProvided() {
		return productsProvided;
	}

	/**
	 * @return the productsDemanded
	 */
	public char[] getProductsDemanded() {
		return productsDemanded;
	}

	/**
	 * @return the numberOfPeers
	 */
	public int getNumberOfPeers() {
		return numberOfPeers;
	}

	/**
	 * @param productsProvided the productsProvided to set
	 */
	public void setProductsProvided(char[] productsProvided) {
		this.productsProvided = productsProvided;
	}

	/**
	 * @param productsDemanded the productsDemanded to set
	 */
	public void setProductsDemanded(char[] productsDemanded) {
		this.productsDemanded = productsDemanded;
	}

	/**
	 * @param numberOfPeers the numberOfPeers to set
	 */
	public void setNumberOfPeers(int numberOfPeers) {
		this.numberOfPeers = numberOfPeers;
	}
	
	

}
