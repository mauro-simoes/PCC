package graphes.types;

import java.util.ArrayList;
import java.util.List;

public class GrapheLA extends Graphe{
	private static class Stub {
		public int valuation, cible;
		public Stub (int valuation, int cible) {
			this.valuation = valuation;
			this.cible = cible;
		}
	}
	private	List<Stub> la[];
	
	@SuppressWarnings("unchecked")
	public GrapheLA(int nbNoeuds) {
		super();
		la = new List[nbNoeuds]; // necessite le SuppressWarnings ci-dessus
		for (int i = 0; i < nbNoeuds; ++i)
			la[i] = new ArrayList<>();
	}

	@Override
	public int getNbSommets() {
		return la.length;
	}

	@Override
	public int getValuation(int a, int b) {
		assert estArcOK(a,b);
		List<Stub> stubs = la[a-1];
		for (Stub s : stubs)
			if (s.cible == b)
				return s.valuation;
		return INFINI;
	}

	public void ajouterArc(int a, int v, int b) {
		assert ! aArc(a,b);
		la[a-1].add(new Stub(v, b));
	}
	
	public ArrayList<Integer> getSuccesseurs(int sommet){
		assert estNoeudOK(sommet);
		ArrayList<Integer> sommets = new ArrayList<>();
		for (int i=0;i<la[sommet-1].size();++i) {
			sommets.add(la[sommet-1].get(i).cible);
		}
		return sommets;
	}
	
	public int[] getSommets(){
		int[] sommets = new int[la.length];
		for (int i= 0;i<la.length;++i) sommets[i]=i+1;
		return sommets;
	}
	
	@Override
	public int dOut(int sommet) {
		assert estNoeudOK(sommet);
		return la[sommet-1].size();
	}

	
	@Override
	public int dIn(int sommet) {
		assert estNoeudOK(sommet);
		int d = 0;
		for(int i = 0; i< la.length; ++i) {
			for(int j = 0; j< la[i].size(); ++j) {
				if (la[i].get(j).cible==sommet)++d;
			}
		}
		return d;
	}

	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i< la.length; ++i) {
			str += (i+1) + " =>";
			for (Stub s : la[i]) 
				str += " "+s.cible + "("+s.valuation+")";
			str +="\n";
		}
		return str;
	}
}
