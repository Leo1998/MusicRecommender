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
		
		AmuseHelper helper = new AmuseHelper(workingDir, taskDir);
		
		File dir = new File("/scratch/Musikinformatik/Genres-Datensatz");
		List<File> allSongs = getFiles(dir);
		//helper.extractAndProcessSongs(allSongs);
		
//		File test = new File("/home/fricke/The_FireSoul-Behind_My_Back.mp3");
//		List<Tuple<File, Integer>> similar = findSimilar(helper, test);
//
		helper.endLoop();

	}
		
	public static List<Tuple<File, Integer>> findSimilar(AmuseHelper helper, File input) {
		List<Tuple<File, Integer>> results = new ArrayList<>();
		
		File processedFile = helper.extractAndProcessSong(input);
		System.out.println(processedFile.getAbsolutePath());
		
		List<double[]> inputVectors = helper.parseProcessedFeatures(processedFile);

		List<File> allProcessedFeatures = getFiles(new File("../Processed_Features"));
		for(File file : allProcessedFeatures) {
			List<double[]> parsedVectors = helper.parseProcessedFeatures(file);
			
			double maxDist = 0;
			double[][] dists = new double[inputVectors.size()][parsedVectors.size()];
			for (int i = 0; i < inputVectors.size(); i++) {
				for (int j = 0; j < parsedVectors.size(); j++) {
					double[] vec1 = inputVectors.get(i);
					double[] vec2 = parsedVectors.get(j);
					double dist = cosine(vec1, vec2);
					
					if (dist > maxDist)
						maxDist = dist;
			
					dists[i][j] = dist;
				}
			}
			
			int countOfSimilarWindows = 0;
			for (int i = 0; i < inputVectors.size(); i++) {
				boolean found = false;
				for (int j = 0; j < parsedVectors.size(); j++) {
					//normalize
					//dists[i][j] *= 1 / maxDist;
					
					if (dists[i][j] > 0.999)
						found = true;
				}
				if (found)
					countOfSimilarWindows++;
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
	
	public static double manhattan(double[] vec1, double[] vec2) {
		assert(vec1.length == vec2.length);
		
		double sum = 0.0;
		for(int i = 0; i < vec1.length; i++) {
			sum += Math.abs(vec1[i] - vec2[i]);
		}
		return sum;
	}
	
	public static double cosine(double[] vec1, double[] vec2) {
		assert(vec1.length == vec2.length);
		
		double sumProducts = 0.0;
		double sumX = 0.0;
		double sumY = 0.0;
		for(int i = 0; i < vec1.length; i++) {
			sumProducts += vec1[i] * vec2[i];
			sumX += Math.pow(vec1[i], 2);
			sumY += Math.pow(vec2[i], 2);
		}
		return (sumProducts / (Math.sqrt(sumX) * Math.sqrt(sumY)));
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
