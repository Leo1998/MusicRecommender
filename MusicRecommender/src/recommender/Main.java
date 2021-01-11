package recommender;

import java.io.File;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Music:)");

		Process process = null;
		try {
			ProcessBuilder builder = new ProcessBuilder(".././amuse.sh");
			builder.redirectErrorStream(true);
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
