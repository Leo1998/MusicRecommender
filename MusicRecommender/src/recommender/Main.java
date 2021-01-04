package recommender;

import java.io.File;
import java.io.IOException;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Music:)");

		try {
			Process p = Runtime.getRuntime().exec("./amuse.sh", null, new File("../"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
