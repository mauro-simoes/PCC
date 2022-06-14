package algorithmes.types;

import java.util.ArrayList;
import exceptions.CalculImpossibleEx;
import exceptions.NoPathEx;
import graphes.IGraphe;

public class PCCDijkstra extends PCC {
	
	private static final int INFINI = Integer.MAX_VALUE;
	
	
	private static boolean estOK(IGraphe graphe) {
		for(int label1 : graphe.getSommets()) {
			for (int label2: graphe.getSuccesseurs(label1)) {
				if (graphe.getValuation(label1, label2) < 0 ) throw new IllegalArgumentException();
			}
		}
		return true;
	}
	
	public Chemin pcc(IGraphe graphe, int source,int destination){
		Chemin chemin = new Chemin(graphe,source,destination);
		if(source== destination) return chemin;
		if (!estOK(graphe)) throw new CalculImpossibleEx();
		if (!cheminPossible(graphe,source,destination)) throw new NoPathEx() ;
		int[] predecesseurs= new int[graphe.getNbSommets()];
		int sommetMin= destination;
		calculChemins(graphe,predecesseurs,source,destination);
		do{
			chemin.ajouterSommet(predecesseurs[sommetMin-1]);
			sommetMin=predecesseurs[sommetMin-1];
		}while(sommetMin!=source);
		return chemin;
	}
	
	private static void calculChemins(IGraphe graphe, int[] predecesseurs, int source,int destination) {
		int[] distances = new int[graphe.getNbSommets()];
		int[] distancesTmp = new int[graphe.getNbSommets()];
		int sommetMin=source;
		distancesTmp[sommetMin-1]=Integer.MAX_VALUE;
		while(sommetMin!=destination) {
			int idxSommetMin = sommetMin-1;
			ArrayList<Integer> arcs = graphe.getSuccesseurs(sommetMin);
			for (int sommet : arcs) {
				int val=graphe.getValuation(sommetMin, sommet)+distances[idxSommetMin];
				int indexVal=sommet-1;
				if(distancesTmp[indexVal]==0 || (distancesTmp[indexVal]>val && distancesTmp[indexVal]!=INFINI)) {
					distancesTmp[indexVal]=val;
					predecesseurs[indexVal]=sommetMin;
				}
			}
			distancesTmp[idxSommetMin]=INFINI;
			sommetMin=indexValMin(distancesTmp)+1;
			idxSommetMin = sommetMin-1;
			distances[idxSommetMin]=distancesTmp[idxSommetMin];
		}
	}
	
	private static int indexValMin(int[] tab) {
		int min=INFINI,indexMin=0;
		for (int i = 0;i<tab.length;++i) {
			if (tab[i]<min && tab[i]!=0) {
				min = tab[i];
				indexMin=i;
			}
		}
		return indexMin;
	}
	
}
