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

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class ExportToCSV {
	public static void exportToCSV(String folderPath, String fileName,HashMap<String,List<float []>> stats)
	{	
		try
		{
			Path path = Paths.get(folderPath);


			File file =new File(folderPath+fileName+".csv");
			if (!file.getParentFile().isDirectory()){
				Files.createDirectories(path);
			}

			FileWriter writer = new FileWriter(file);
			
			for (Entry <String,List<float []>>entry:stats.entrySet()){
				float[]pourcentage=new float[101];
				for (float[] percents:entry.getValue()){
					pourcentage[(int) percents[0]]=percents[1];
				}
				for (int counter=0;counter<101; counter++){
					if (pourcentage[counter]==0&&counter==0){
						int secondIndex=counter;
						float max=0;
						while (pourcentage[secondIndex]==0){
							secondIndex++;
							max=pourcentage[secondIndex];
						}
						for (int reagencement=0;reagencement<secondIndex+1; reagencement++){
							pourcentage[reagencement]=max/secondIndex;
						}
					}
					else if (pourcentage[counter]==0&&counter!=0){
						int secondIndex=counter;
						float max=0;
						while (pourcentage[secondIndex]==0){
							secondIndex++;
							max=pourcentage[secondIndex];
						}
						for (int reagencement=counter;reagencement<secondIndex+1; reagencement++){
							pourcentage[reagencement]=max/(secondIndex-counter+1);
						}
					}
				}
				writer.append(entry.getKey()+"\t");
				for (int indexAppend=0; indexAppend<pourcentage.length;indexAppend++){
					if (indexAppend==pourcentage.length-1){
						writer.append(""+pourcentage[indexAppend]);
					}
					else{
						writer.append(pourcentage[indexAppend]+"\t");
					}
					
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
