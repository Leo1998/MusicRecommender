package recommender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmuseHelper {

	private File taskDir;
	
	public AmuseHelper(File taskDir) {
		this.taskDir = taskDir;
	}
	
	public File createMusicFileList(List<File> mp3List) {
		try {
			File file = Files.createTempFile(null, null).toFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("@RELATION 'Data'\n");
			
			writer.write("@ATTRIBUTE Id NUMERIC\n");			
			writer.write("@ATTRIBUTE Path STRING\n");
			writer.write("@ATTRIBUTE Unit {milliseconds,samples}\n");
			writer.write("@ATTRIBUTE Start NUMERIC\n");
			writer.write("@ATTRIBUTE End NUMERIC\n");
			
			writer.write("@DATA\n");
			
			int i = 0;
			for(File f : mp3List) {
				writer.write(i + ", " + f.getAbsolutePath() + ", milliseconds, 0, -1\n");
				i++;
			}

			writer.close();
			
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public File createExtractFeaturesTask(File musicFileList) {
		try {
			File file = Files.createTempFile(null, null).toFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("@RELATION amuse_task\n");
			
			writer.write("@ATTRIBUTE MusicFileList String\n");
			writer.write("@ATTRIBUTE FeatureTable String\n");
			
			writer.write("@DATA\n");
			writer.write("'" + musicFileList.getAbsolutePath() + "', '" + new File(taskDir, "../featureTable.arff").getAbsolutePath() + "'\n");

			writer.close();
			
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public File createProcessFeaturesTask(File musicFileList) {
		try {
			File file = Files.createTempFile(null, null).toFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("@RELATION amuse_task\n");
			
			writer.write("@ATTRIBUTE FileList String\n");
			writer.write("@ATTRIBUTE InputSourceType {RAW_FEATURE_LIST, PROCESSING_CONFIG}\n");
			writer.write("@ATTRIBUTE Input string\n");
			writer.write("@ATTRIBUTE ReductionSteps string\n");
			writer.write("@ATTRIBUTE Unit {samples,milliseconds}\n");
			writer.write("@ATTRIBUTE AggregationWindowSize numeric\n");
			writer.write("@ATTRIBUTE AggregationWindowStepSize numeric\n");
			writer.write("@ATTRIBUTE MatrixToVectorMethod string\n");
			writer.write("@ATTRIBUTE FeatureDescription string\n");

			
			writer.write("@DATA\n");
			writer.write("'" + musicFileList.getAbsolutePath() + "', RAW_FEATURE_LIST, '" + new File(taskDir, "../featureTable.arff").getAbsolutePath() + "', '1', milliseconds, 5000, 2500, '0', ''\n");

			writer.close();
			
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void extractAndProcessSongs(List<File> files) {
		File musicListFile = createMusicFileList(files);
		File extractFile = createExtractFeaturesTask(musicListFile);
		File processFile = createProcessFeaturesTask(musicListFile);
		
		runTask("extract", "-fe " + extractFile.getAbsolutePath());
		runTask("process", "-fp " + processFile.getAbsolutePath());
	}
	
	public File extractAndProcessSong(File file) {
		extractAndProcessSongs(Arrays.asList(file));
		
		String name = file.getName().substring(0, file.getName().length() - 4);
		return new File("../Processed_Features" + file.getParent() + "/" + name + "/" + name + "_1__0__5000ms_2500ms.arff");
	}
		
	public void endLoop() {
		runTask("end_loop", "-end_loop");
	}
	
	public void runTask(String name, String command) {
		File file = new File(taskDir, name);
		try {			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(command);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(file.exists()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<double[]> parseProcessedFeatures(File file) {
		List<double[]> result = new ArrayList<double[]>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = "";
			while((line = reader.readLine()) != null) {
				if (line.startsWith("@"))
					continue;
				if (line.startsWith("%"))
					continue;
				if (line.trim().isEmpty())
					continue;
				
				String[] tokens = line.split(",");
				double[] vector = new double[26];
				for (int i = 0; i < 26; i++) {
					vector[i] = Double.parseDouble(tokens[i]);
				}
				
				result.add(vector);
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
}
