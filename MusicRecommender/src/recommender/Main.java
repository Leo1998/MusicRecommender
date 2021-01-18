package recommender;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Music:)");
		
		File workingDir = new File("../");
		File taskDir = new File(workingDir, "tasks");
		taskDir.mkdir();
						
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
		
		AmuseHelper helper = new AmuseHelper(taskDir);
		
		List<File> allSongs = new ArrayList<>();
		File dir = new File("/scratch/Musikinformatik/Genres-Datensatz");
		try {
			Files.walk(dir.toPath(), FileVisitOption.FOLLOW_LINKS).filter(Files::isRegularFile).forEach(path -> allSongs.add(path.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		helper.extractAndProcessSongs(allSongs);

		helper.endLoop();

	}

}
