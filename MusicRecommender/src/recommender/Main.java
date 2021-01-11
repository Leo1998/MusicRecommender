package recommender;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Music:)");

		Process process = null;
		try {
			ProcessBuilder builder = new ProcessBuilder("./amuse.sh");
			builder.redirectErrorStream(true);
			builder.redirectOutput(Redirect.INHERIT);
			builder.directory(new File("../"));
			process = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
