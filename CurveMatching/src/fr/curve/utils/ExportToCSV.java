package fr.curve.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;

public class ExportToCSV {
	public static void exportToCSV(String folderPath, String fileName,HashMap<String,List<int []>> stats)
	{	
		try
		{
			Path path = Paths.get(folderPath);


			File file =new File(folderPath+fileName+".tsv");
			if (!file.getParentFile().isDirectory()){
				Files.createDirectories(path);
			}

			FileWriter writer = new FileWriter(file);
			
			for (Entry <String,List<int []>>entry:stats.entrySet()){
				int[]pourcentage=new int[101];
				for (int[] percents:entry.getValue()){
					pourcentage[percents[0]]=percents[1];
				}
				for (int counter=1;counter<101; counter++){
					if (pourcentage[counter]==0){
						pourcentage[counter]=pourcentage[counter-1];
					}
				}
				writer.append(entry.getKey()+"\t");
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX changement de fichier XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				for (int percent:pourcentage){
					System.out.println(percent);
					writer.append(percent+"\t");
				}
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
	}
}
