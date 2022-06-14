package graphes.types;

import java.util.ArrayList;

public class GrapheMA extends Graphe{
	private int[][] ma;

	public GrapheMA(int nbNoeuds) {
		ma = new int[nbNoeuds][nbNoeuds];
		for (int a = 0; a < nbNoeuds; ++a)
			for (int b = 0; b < nbNoeuds; ++b)
				ma[a][b] = INFINI;
	}
	
	@Override
	public int getNbSommets() { return ma.length; }
	
	@Override
	public int getValuation(int a, int b) {
		assert estArcOK(a,b);
		return ma[a-1][b-1];
	}
	
	@Override
	public void ajouterArc(int a, int v, int b) {
		assert ! aArc(a,b);
		ma[a-1][b-1] = v;
	}
	
	public int dOut(int sommet) {
		assert estNoeudOK(sommet);
		int degre = 0;
		for (int i = 0; i < getNbSommets(); ++i)
			if (ma[sommet-1][i]!=INFINI) ++degre;
		return degre;
	}

	public int dIn(int sommet) {
		assert estNoeudOK(sommet);
		int degre = 0;
		for (int i = 0; i < getNbSommets(); ++i)
			if (ma[i][sommet-1]!=INFINI) ++degre;
		return degre;
	}
	
	public int[] getSommets() {
		int[] sommets = new int[ma.length];
		for (int i =0;i<ma.length;++i) sommets[i]=i+1;
		return sommets;
	}
	
	public ArrayList<Integer> getSuccesseurs(int sommet){
		assert estNoeudOK(sommet);
		ArrayList<Integer> arcs = new ArrayList<>();
		for (int i=0;i<ma.length;++i) {
			if (ma[sommet-1][i]!=INFINI) arcs.add(i+1);
		}
		return arcs;
	};

	@Override
	public String toString() {
		String str = "";
		int v;
		for(int i = 1; i<= getNbSommets(); ++i) {
			str += (i) + " =>";
			for (int j = 1; j <= getNbSommets(); ++j)
				if ((v= getValuation(i,j)) != INFINI) 
					str += " " + j + "(" + v + ")";
			str +="\n";
		}
		return str;
	}
}
