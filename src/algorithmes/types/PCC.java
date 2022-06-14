package algorithmes.types;

import java.util.ArrayList;

import algorithmes.IPCC;
import graphes.IGraphe;

public abstract class PCC implements IPCC{
	
	public abstract Chemin pcc(IGraphe graphe, int source,int destination);
	

	public static boolean cheminPossible(IGraphe g,int source,int destination) {
		ArrayList<Integer> visites = new ArrayList<>(g.getNbSommets());
		ArrayList<Integer> sommets = new ArrayList<>();
		visites.add(source);
		sommets.add(source);
		while (!sommets.isEmpty()) {
			int sommetP = sommets.get(0);
			sommets.remove(0);
			if(sommetP==destination) return true;
			for (int s : g.getSuccesseurs(sommetP)) {
				if (!visites.contains(s)) {
					sommets.add(s);
					visites.add(s);
				}
			}
		}
		return false;
	}
}
