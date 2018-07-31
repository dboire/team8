package mastermind;

import java.util.Random;

public class LocalConnector {

	private String secret;
	
	public void start() {
		final Random rand = new Random();
		final int size = rand.nextInt(10);
		
		secret = "";
		
		for (int i = 1; i < 1 + size; i++) {
			secret += rand.nextInt(10);
		}
	}

	public void start(int size) {
		final Random rand = new Random();
		
		secret = "";
		
		for (int i = 1; i < 1 + size; i++) {
			secret += rand.nextInt(10);
		}
	}

	public void start(String value) {
		secret = value;
	}
	
	public String getSecret() {
		return secret;
	}

	public int[] test(String testValue) {
		return null;
	}
	
}
