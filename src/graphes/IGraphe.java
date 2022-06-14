package graphes;

import java.util.ArrayList;

public interface IGraphe {
	int INFINI = Integer.MAX_VALUE;
	int getNbSommets();
	void ajouterArc(int source, int valuation, int destination);
	int getValuation(int i, int j);
	boolean aArc(int i, int j);
	int[] getSommets();
	ArrayList<Integer> getSuccesseurs(int i);
	int dOut(int sommet); 
	int dIn(int sommet);
}
