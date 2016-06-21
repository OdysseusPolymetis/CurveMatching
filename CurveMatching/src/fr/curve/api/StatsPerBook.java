package fr.curve.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsPerBook {

	public static void main(String[] args) {
		
		HashMap<String, List<String[]>> mapDesOeuvres=new HashMap<String, List<String[]>>();
		
		File folder = new File("./FichiersThomas");
		File repertoire[]=folder.listFiles();
		for (File file:repertoire){
			if (file.getName().contains(".txt")){
				String wholeText="";
				try {
					wholeText = new String(Files.readAllBytes(file.toPath()));
				} catch (IOException e) {
					e.printStackTrace();
				}	
					String []oeuvreParPartie=wholeText.split("\nPartie [1-9]");
					List<String[]>chapitresParOeuvre=new ArrayList<String[]>();
				if (oeuvreParPartie.length>1){
					List<String>chapitreParPartie=new ArrayList<String>();
					for (String partie:oeuvreParPartie){
						partie=partie.replaceAll("[\\t\\n\\r]+", " ");
						String []chapitres=partie.split("Chapitre [1-9]+");
						for (String str:chapitres){
							chapitreParPartie.add(str);
						}
						
						for(int counterChapitre=1; counterChapitre<chapitreParPartie.size()+1;counterChapitre++){
							String numChapEtTxt[]=new String[2];
							numChapEtTxt[0]="Chapitre "+counterChapitre;
							numChapEtTxt[1]=chapitreParPartie.get(counterChapitre-1);
							chapitresParOeuvre.add(numChapEtTxt);
						}
					}
				}
				else{
					String oeuvreParChapitres[]=wholeText.split("Chapitre [0-9]+");
					
					for(int counterChapitre=0; counterChapitre<oeuvreParChapitres.length;counterChapitre++){
						oeuvreParChapitres[counterChapitre]=oeuvreParChapitres[counterChapitre].replaceAll("[\\t\\n\\r]+", " ");
						String numChapEtTxt[]=new String[2];
						numChapEtTxt[0]="Chapitre "+(counterChapitre);
						numChapEtTxt[1]=oeuvreParChapitres[counterChapitre];
						chapitresParOeuvre.add(numChapEtTxt);
					}
				}
				mapDesOeuvres.put(file.getName(), chapitresParOeuvre);
			}
		}
		System.out.println(mapDesOeuvres.get("Emile Zola-Au Bonheur des Dames (1972).txt").get(14)[1]);
	}
}
