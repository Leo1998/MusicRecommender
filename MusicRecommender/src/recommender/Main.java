package recommender;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
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
		
		File dir = new File("/scratch/Musikinformatik/Genres-Datensatz");
		List<File> allSongs = getFiles(dir);
		//helper.extractAndProcessSongs(allSongs);
		
//		File test = new File("/home/fricke/The_FireSoul-Behind_My_Back.mp3");
//		List<Tuple<File, Integer>> similar = findSimilar(helper, test);
//
		helper.endLoop();

	}
	
	public static double MIN_DIST = 10;
	
	public static List<Tuple<File, Integer>> findSimilar(AmuseHelper helper, File input) {
		List<Tuple<File, Integer>> results = new ArrayList<>();
		
		File processedFile = helper.extractAndProcessSong(input);
		System.out.println(processedFile.getAbsolutePath());
		
		List<double[]> inputVectors = helper.parseProcessedFeatures(processedFile);

		List<File> allProcessedFeatures = getFiles(new File("../Processed_Features"));
		for(File file : allProcessedFeatures) {
			List<double[]> parsedVectors = helper.parseProcessedFeatures(file);
			
			int countOfSimilarWindows = 0;

			for (double[] vec1 : inputVectors) {
				for (double[] vec2 : parsedVectors) {
					double dist = euclid(vec1,  vec2);
					if (dist < MIN_DIST) {
						countOfSimilarWindows++;
					}
				}
			}
			
			results.add(new Tuple<File, Integer>(file, countOfSimilarWindows));
			System.out.println("Found : " + file.getName() + " similarWindows: " + countOfSimilarWindows);
		}
		
		results.sort(new Comparator<Tuple<File, Integer>>() {
			@Override
			public int compare(Tuple<File, Integer> o1, Tuple<File, Integer> o2) {
				return o2.getSecond() - o1.getSecond();
			}
		});
				
		return results;
	}
	
	public static double euclid(double[] vec1, double[] vec2) {
		assert(vec1.length == vec2.length);
		
		double sum = 0.0;
		for(int i = 0; i < vec1.length; i++) {
			sum += Math.pow(vec1[i] - vec2[i], 2);
		}
		return Math.sqrt(sum);
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
	
	public static File findMp3(String songname) {
		File dir = new File("/scratch/Musikinformatik/Genres-Datensatz");
		List<File> allSongs = getFiles(dir);
		
		for(File file : allSongs) {
			if (file.getName().startsWith(songname)) {
				return file;
			}
		}
		
		return null;
	}

}
