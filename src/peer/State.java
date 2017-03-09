package peer;

public enum State {

	CONSUMING("Consuming"), PROVIDING("Providing"), IDLE("Idle");

	private String state;

	private State(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

	public String toString() {
		return this.state;
	}

}