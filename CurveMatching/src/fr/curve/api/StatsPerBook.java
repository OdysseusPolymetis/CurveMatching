package fr.curve.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.curve.utils.ExportToCSV;
import fr.curve.utils.RomanNumbers;

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
				StringTokenizer tokenizerWholeText=new StringTokenizer(wholeText);
				int nombreTotalMotsParTexte=tokenizerWholeText.countTokens();
				
				String preTraitementChiffresRomains[]=wholeText.split("\\n");
				int index=0;
				for (String nombre:preTraitementChiffresRomains){
					Pattern p2=Pattern.compile("\\b.*[A-Z]+\\b");
					Matcher m2=p2.matcher(nombre);
					while (m2.find()){
						if (nombre.length()<8){
							preTraitementChiffresRomains[index]=nombre.replace(m2.group(), "Chapitre "+String.valueOf(RomanNumbers.decode(m2.group())));
						}
					}
					index++;
				}
				StringBuilder sb= new StringBuilder();

				for (String ligne:preTraitementChiffresRomains){
					sb.append(ligne+"\n");
				}
				wholeText=sb.toString();
				int nombreTotalDeChapitres=wholeText.split("Chapitre [0-9]+").length-1;
					String []Parties=wholeText.split("\nPartie [0-9]");
					ArrayList<String[]>chapitresParOeuvre=new ArrayList<String[]>();
				if (Parties.length>1&&nombreTotalDeChapitres>1){
					ArrayList<String>chapitresToutesParties=new ArrayList<String>();
					int chapitresPrecedents=0;
					for (String partie:Parties){
						partie=partie.replaceAll("[\\t\\n\\r]+", " ");
						String []chapitres=partie.split("Chapitre [0-9]+");
						for (String str:chapitres){
							if (!str.matches("[\\t\\n\\r\\s]+")&&!str.matches("[\\t\\n\\r\\s]*Partie [0-9]+[\\t\\n\\r\\s]*")&&!str.equals("")){	
								chapitresToutesParties.add(str);
							}
						}
						for(int counterChapitre=0; counterChapitre<chapitresToutesParties.size();counterChapitre++){
							if (chapitresToutesParties.size()>=(counterChapitre+chapitresPrecedents+1)){
								String numChapEtTxt[]=new String[5];
								numChapEtTxt[0]=String.valueOf(counterChapitre+chapitresPrecedents+1);//le numéro du chapitre
								numChapEtTxt[1]=chapitresToutesParties.get(counterChapitre+chapitresPrecedents);//le texte du chapitre
								StringTokenizer st = new StringTokenizer(chapitresToutesParties.get(counterChapitre+chapitresPrecedents));
								numChapEtTxt[2]=String.valueOf(st.countTokens());//le nombre de mots du chapitre
								numChapEtTxt[3]=String.valueOf(nombreTotalDeChapitres); //le nombre total de chapitres de l'oeuvre
								numChapEtTxt[4]=String.valueOf(nombreTotalMotsParTexte); //le nombre total de mots de l'oeuvre
								chapitresParOeuvre.add(numChapEtTxt);
							}
						}
						chapitresPrecedents=chapitresToutesParties.size();
					}
				}
				else if (Parties.length>1&&nombreTotalDeChapitres<1){
					String oeuvreParChapitres[]=wholeText.split("Partie [0-9]+");	
					nombreTotalDeChapitres=oeuvreParChapitres.length-1;
					for(int counterChapitre=0; counterChapitre<oeuvreParChapitres.length;counterChapitre++){
						if (!oeuvreParChapitres[counterChapitre].equals("")){
							oeuvreParChapitres[counterChapitre]=oeuvreParChapitres[counterChapitre].replaceAll("[\\t\\n\\r]+", " ");
							String numChapEtTxt[]=new String[5];
							numChapEtTxt[0]=String.valueOf(counterChapitre); //le numéro du chapitre
							numChapEtTxt[1]=oeuvreParChapitres[counterChapitre]; //le texte du chapitre
							StringTokenizer st = new StringTokenizer(oeuvreParChapitres[counterChapitre]);
							numChapEtTxt[2]=String.valueOf(st.countTokens()); //le nombre de mots du chapitre
							numChapEtTxt[3]=String.valueOf(nombreTotalDeChapitres); //le nombre total de chapitres de l'oeuvre
							numChapEtTxt[4]=String.valueOf(nombreTotalMotsParTexte); //le nombre total de mots de l'oeuvre
							chapitresParOeuvre.add(numChapEtTxt);
							
						}
					}
				}
				else{
					String oeuvreParChapitres[]=wholeText.split("Chapitre [0-9]+");	
					nombreTotalDeChapitres=oeuvreParChapitres.length-1;
					for(int counterChapitre=0; counterChapitre<oeuvreParChapitres.length;counterChapitre++){
						if (!oeuvreParChapitres[counterChapitre].equals("")){
							oeuvreParChapitres[counterChapitre]=oeuvreParChapitres[counterChapitre].replaceAll("[\\t\\n\\r]+", " ");
							String numChapEtTxt[]=new String[5];
							numChapEtTxt[0]=String.valueOf(counterChapitre); //le numéro du chapitre
							numChapEtTxt[1]=oeuvreParChapitres[counterChapitre]; //le texte du chapitre
							StringTokenizer st = new StringTokenizer(oeuvreParChapitres[counterChapitre]);
							numChapEtTxt[2]=String.valueOf(st.countTokens()); //le nombre de mots du chapitre
							numChapEtTxt[3]=String.valueOf(nombreTotalDeChapitres); //le nombre total de chapitres de l'oeuvre
							numChapEtTxt[4]=String.valueOf(nombreTotalMotsParTexte); //le nombre total de mots de l'oeuvre
							chapitresParOeuvre.add(numChapEtTxt);
						}
					}
				}
				mapDesOeuvres.put(file.getName(), chapitresParOeuvre);
			}
		}
		
		LinkedHashMap<String, List<int[]>> mapDesPourcentages=new LinkedHashMap<String, List<int[]>>();
		
		for (String fileName:mapDesOeuvres.keySet()){
			List<int[]>listeDesPourcentagesParTexte=new ArrayList<int[]>();
			List<String[]>cloneDesValeursAbsoluesParChapitre=new ArrayList<String[]>();
			cloneDesValeursAbsoluesParChapitre.addAll(mapDesOeuvres.get(fileName));
			for (int indexMap=0; indexMap<cloneDesValeursAbsoluesParChapitre.size(); indexMap++){
				String []tableauValeursParChapitre=cloneDesValeursAbsoluesParChapitre.get(indexMap);
				int []tableauDesStats=new int[2];
				tableauDesStats[0]=(Integer.valueOf(tableauValeursParChapitre[0])*100)/Integer.valueOf(tableauValeursParChapitre[3]); //numero relatif du chapitre
				tableauDesStats[1]=(Integer.valueOf(tableauValeursParChapitre[2])*100)/Integer.valueOf(tableauValeursParChapitre[4]); //nombre relatif de tokens
				
				listeDesPourcentagesParTexte.add(tableauDesStats);
			}
//			System.out.println(listeDesPourcentagesParTexte.size());
			mapDesPourcentages.put(fileName, listeDesPourcentagesParTexte);
			
		}
		
//		for (String key:mapDesPourcentages.keySet()){
			ExportToCSV.exportToCSV("./CSVOutput/", "output",mapDesPourcentages);
//		}
		
//		System.out.println("xxxxxxxxxxxxxxxxxxx "+mapDesPourcentages.get("Emile Zola-Au Bonheur des Dames (1972).txt").get(12)[0]+" xxxxxxxxxxxxxxxxxxx");	
	}
}
