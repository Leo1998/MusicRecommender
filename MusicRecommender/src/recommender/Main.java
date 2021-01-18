package recommender;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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

		//File dir = new File("/scratch/Musikinformatik/Genres-Datensatz");
		//List<File> allSongs = getFiles(dir);
		//helper.extractAndProcessSongs(allSongs);
		

		

		/*List<File> allProcessedFeatures = getFiles(new File("../Processed_Features"));
		for(File file : allProcessedFeatures) {
			List<double[]> parsedVectors = helper.parseProcessedFeatures(file);
		}*/
		
		//File processedFile = helper.extractAndProcessSong(new File("/home/fricke/The_FireSoul-Behind_My_Back.mp3"));
		//System.out.println(processedFile.getAbsolutePath());
		

		helper.endLoop();

	}
	
	public static List<File> getFiles(File dir) {
		List<File> files = new ArrayList<>();
		try {
			Files.walk(dir.toPath(), FileVisitOption.FOLLOW_LINKS).filter(Files::isRegularFile).filter(f -> !f.endsWith("history.arff")).forEach(path -> files.add(path.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}

}
