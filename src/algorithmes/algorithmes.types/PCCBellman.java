package algorithmes.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import exceptions.NoPathEx;
import graphes.IGraphe;

public class PCCBellman extends PCC{
	
	private final static int INFINI = Integer.MAX_VALUE;
	
	public  Chemin pcc(IGraphe graphe, int source,int destination){
		Chemin chemin = new Chemin(graphe,source,destination);
		if(source== destination) return chemin;
		if (!cheminPossible(graphe,source,destination)) throw new NoPathEx();
		return calcul(graphe,source,destination);
	}
	
	private static void decomposition(IGraphe g, ArrayList<Integer> tri,int source) {
		if(!tri.contains(source)) {
			if(g.dOut(source)==0 || g.dIn(source)==0) {
				tri.add(source);
			}
			for (int sommet : g.getSuccesseurs(source)) {
				decomposition(g,tri,sommet);
				tri.add(sommet);
			}
		}
	}
	
	private static Chemin calcul(IGraphe g,int source, int destination) {
		ArrayList<Integer> decomp = new ArrayList<Integer>();
		decomposition(g,decomp,source);
		Collections.reverse(decomp);
		int[] distances = new int[g.getNbSommets()], predecesseurs =  new int[g.getNbSommets()];
		Chemin chemin = new Chemin(g,source,destination);
		Arrays.fill(distances, INFINI);
		for (int i = 0; i< g.getNbSommets();++i) {if(g.dIn(i+1)==0) distances[i]=0; }
		for (int s : decomp)  {
			ArrayList<Integer> successeurs =  g.getSuccesseurs(s);
			for (int sommet : successeurs) {
				if (distances[sommet-1] > (distances[s-1] + g.getValuation(s,sommet))) {
					distances[sommet-1] = distances[s-1] + g.getValuation(s,sommet);
					predecesseurs[sommet-1] = s;
				} 
			}
		}
		int sommet = predecesseurs[destination-1];
		while(sommet!=source && sommet>0){
			chemin.ajouterSommet(sommet);
			sommet=predecesseurs[sommet-1];
		};
		return chemin;
	}

}
