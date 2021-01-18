package recommender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Music:)");
		
		File workingDir = new File("../");
		File taskDir = new File(workingDir, "tasks");
						
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
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		File file = new File(taskDir, "end_loop");
		try {			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("-end_loop");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
