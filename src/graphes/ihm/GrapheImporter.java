package graphes.ihm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import graphes.IGraphe;
import algorithmes.IPCC;
import algorithmes.types.Chemin;
import algorithmes.types.PCCBellman;
import algorithmes.types.PCCDijkstra;
import graphes.types.GrapheLA;

public class GraphImporter {
	public static void main(String[] args) throws NumberFormatException, FileNotFoundException, IOException {
		String fileName;
		System.out.println();
		if (args.length != 1) 
			fileName = "graphes/sc/g-10-6.txt";
		else fileName = args[0];
		System.out.println("filename = "+ fileName);
		Arc df = new Arc();
		IGraphe g = importer(fileName, df);
		System.out.print(g);
		System.out.println("debut fin : "+ df.getSource() + " ==> "+ df.getDestination());
		IPCC algo = new PCCBellman();
		Chemin chemin = algo.pcc(g,df.getSource(),df.getDestination());
		System.out.println("Bellman");
		System.out.println(chemin.getDistance());
		System.out.println(chemin);
		//afficherExo31();
		//verifierGraphes();
	}
	
	public static String cheminToString(List<Integer> chemin) {
		StringBuilder sb = new StringBuilder();
		for (int n : chemin)
			sb.append(n + " ");
		return sb.toString();
	}
	
	static void afficherExo31() {
		// ci dessous voici comme afficher la sortie attendue
		// pour l'exo 3.1 du poly de maths
		// en supposant que A = 1, B = 2, ... I = 9
		// il aurait été plus pratique d'ailleurs de la mettre dans un fichier texte
		// comme pour les autres graphes
		
		int nbNoeuds = 9;
		IGraphe g = new GrapheLA(nbNoeuds);
		g.ajouterArc(1,2,3);
		g.ajouterArc(1,1,4);
		g.ajouterArc(2,3,7);
		g.ajouterArc(3,2,8);
		g.ajouterArc(4,3,2);
		g.ajouterArc(4,5,3);
		g.ajouterArc(4,3,5);
		g.ajouterArc(5,1,3);
		g.ajouterArc(5,3,7);
		g.ajouterArc(5,7,8);
		g.ajouterArc(7,2,2);
		g.ajouterArc(7,1,6);
		g.ajouterArc(8,4,6);
		g.ajouterArc(8,2,7);
		g.ajouterArc(9,10,8);
		
		IPCC algo = new PCCDijkstra();
		Chemin chemin = algo.pcc(g,1,6);
		
		System.out.println("Dijkstra");
		System.out.println(chemin);
	}
	
	public static boolean comparer(String fichierGraphe, String fichierReponse, IPCC algo)
					throws NumberFormatException, FileNotFoundException, IOException {
		ArrayList<Integer> cheminPossible = new ArrayList<>();
		Arc df = new Arc();
		IGraphe g;
		g = GraphImporter.importer(fichierGraphe, df);
		Chemin chemin = algo.pcc(g, df.getSource(), df.getDestination());
		int distanceCalculee = chemin.getDistance();
		int distanceAttendue = GraphImporter.importerReponse(fichierReponse, cheminPossible);
		System.out.println(fichierGraphe + " vs " +  fichierReponse);
		System.out.println("Chemin possible : "+ cheminToString(cheminPossible));
		System.out.println("Chemin calcule : "+ chemin);
		System.out.println("Distance attendue : " + distanceAttendue);
		System.out.println("Distance calculee : " + distanceCalculee);
		if (distanceCalculee != distanceAttendue)
			return false;
		int distanceVerifiee = chemin.getDistance();
		if (distanceVerifiee == IGraphe.INFINI)
			throw new RuntimeException("Le chemin retourne est invalide");
		System.out.println("Distance verifiee "+ distanceVerifiee);
		return true;
	}
	
public static void verifierGraphes() throws FileNotFoundException {
	IGraphe g;
	Arc df = new Arc(); 
	String dirStr = System.getProperty("user.dir")+ "\\graphes\\sc";
	System.out.println("Working Directory = " + dirStr);
	File dir = new File(dirStr);
	  File[] directoryListing = dir.listFiles();
	  if (directoryListing != null) {
	    for (File child : directoryListing) {
	      System.out.println(child);
	      g = importer(child, df);
	      System.out.println(g.getNbSommets() + " sommets");
	      System.out.println("debut et fin du chemin à trouver : "+ df.getSource()+ " ==> "+ df.getDestination()+"\n");
	    }
	  } else {
	    System.out.println("Erreur : "+ dirStr + " n'est pas un réperoire");
	  }
}

	public static Arc parse(String string) {
		String[] parts = string.split(" ");
		int source, valuation, destination;
		try {  
			source = Integer.parseInt(parts[0]);
			valuation = Integer.parseInt(parts[1]);
			destination = Integer.parseInt(parts[2]);
		} catch (Exception e) {
			throw new IllegalArgumentException(string + " n'est pas un arc");
		}
		Arc a = new Arc(source, valuation, destination);
		return a;
	}
	private static IGraphe importer(File file, Arc df) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		String line;
		IGraphe g;
		if (! sc.hasNextLine()) {
			sc.close();
    		throw new IllegalArgumentException("Pas de graphe dans "+ file);
		}
		line = sc.nextLine();
		int nbNodes = Integer.parseInt(line.trim());
		g = new GrapheLA(nbNodes);
		Arc a;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			a = parse(line);
			if (sc.hasNextLine())
				g.ajouterArc(a.getSource(),  a.getValuation(), a.getDestination());
			else {// la derniere ligne n'est pas un arc mais le debut et la fin du chemin à trouver
				df.set(a);
			}
		}
		sc.close();
		return g;		
	}
	public static IGraphe importer(String filepath, Arc df) 
								throws  NumberFormatException, IOException, FileNotFoundException {
		File file = new File(filepath);
		return importer(file, df);
      }
	
	public static int importerReponse(String filePath, List<Integer> chemin) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner sc = new Scanner(file);
		String line;
		if (! sc.hasNextLine()) {
			sc.close();
    		throw new IllegalArgumentException("Pas de reponse dans "+ file);
		}
		line = sc.nextLine(); // nom de l'algo recommandé
		line = sc.nextLine(); // distance attendue
		int distance = Integer.parseInt(line.trim());
		line = sc.nextLine(); // chemin
		String[] noeuds = line.split(" ");
		for(String s : noeuds)
			chemin.add(Integer.parseInt(s));
		sc.close();
		return distance;
		}
}
