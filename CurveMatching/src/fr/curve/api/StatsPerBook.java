package fr.curve.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

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
					String []Parties=wholeText.split("\nPartie [0-9]");
					LinkedList<String[]>chapitresParOeuvre=new LinkedList<String[]>();
				if (Parties.length>1&&file.getName().contains("Debacle")){
					
					LinkedList<String>chapitresToutesParties=new LinkedList<String>();
					int chapitresPrecedents=0;
					for (String partie:Parties){
						partie=partie.replaceAll("[\\t\\n\\r]+", " ");
						String []chapitres=partie.split("Chapitre [0-9]+");
						for (String str:chapitres){
							if (!str.matches("[\\t\\n\\r\\s]+")&&!str.matches("[\\t\\n\\r\\s]*Partie [0-9]+[\\t\\n\\r\\s]*")){	
								chapitresToutesParties.add(str);
							}
						}
						for(int counterChapitre=0; counterChapitre<chapitresToutesParties.size();counterChapitre++){
							if (chapitresToutesParties.size()>=(counterChapitre+chapitresPrecedents+1)){
								String numChapEtTxt[]=new String[3];
								numChapEtTxt[0]="Chapitre "+(counterChapitre+chapitresPrecedents+1);//le nom du chapitre
								numChapEtTxt[1]=chapitresToutesParties.get(counterChapitre+chapitresPrecedents);//le texte du chapitre
								StringTokenizer st = new StringTokenizer(chapitresToutesParties.get(counterChapitre+chapitresPrecedents));
								numChapEtTxt[2]=String.valueOf(st.countTokens());//le nombre de mots du chapitre
								chapitresParOeuvre.add(numChapEtTxt);
							}
						}
						chapitresPrecedents=chapitresToutesParties.size();
					}
				}
				else{
					String oeuvreParChapitres[]=wholeText.split("Chapitre [0-9]+");	
					for(int counterChapitre=0; counterChapitre<oeuvreParChapitres.length;counterChapitre++){
						oeuvreParChapitres[counterChapitre]=oeuvreParChapitres[counterChapitre].replaceAll("[\\t\\n\\r]+", " ");
						String numChapEtTxt[]=new String[3];
						numChapEtTxt[0]="Chapitre "+(counterChapitre+1); //le nom du chapitre
						numChapEtTxt[1]=oeuvreParChapitres[counterChapitre]; //le texte du chapitre
						StringTokenizer st = new StringTokenizer(oeuvreParChapitres[counterChapitre]);
						numChapEtTxt[2]=String.valueOf(st.countTokens()); //le nombre de mots du chapitre
						chapitresParOeuvre.add(numChapEtTxt);
					}
				}
				mapDesOeuvres.put(file.getName(), chapitresParOeuvre);
			}
		}
		System.out.println(mapDesOeuvres.get("Emile Zola-La Debacle-Gallimard (1984).txt").get(23)[1]);	
	}
}
