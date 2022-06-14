package algorithmes.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graphes.IGraphe;

public class Chemin {
	private IGraphe graphe;
	private int distance,source,destination;
	private List<Integer> chemin;
	
	public Chemin(IGraphe g,int source, int destination) {
		graphe = g;
		distance = 0;
		this.source = source;
		this.destination =  destination;
		chemin = new ArrayList<>();
	}
	
	public boolean ajoutPossible(int sommet) {
		return (sommet!=destination && sommet!=source && !chemin.contains(sommet));
	}
	
	public void ajouterSommet(int sommet) {
		if (ajoutPossible(sommet))chemin.add(sommet);
	}
	
	private void calculDistance() {
		if(!chemin.isEmpty()) {
			if(!graphe.aArc(chemin.get(chemin.size()-1), destination))Collections.reverse(chemin);
			distance=graphe.getValuation(source, chemin.get(0));
			for (int i=1;i<chemin.size();++i) {
				distance+=graphe.getValuation(chemin.get(i-1),chemin.get(i));
			}
			distance+=graphe.getValuation(chemin.get(chemin.size()-1),destination);
		}
		else distance = graphe.getValuation(source, destination);
	}
	
	public int getDernierSommet() {
		if (chemin.isEmpty()) return source;
		return chemin.get(0);
	}
	
	public int getDistance() {
		if (distance==0 && source!=destination)calculDistance();
		return distance;
	}
	
	public String toString() {
		String s ="";
		if(source==destination) return ""+source;
		if(!chemin.isEmpty() && !graphe.aArc(chemin.get(chemin.size()-1), destination))Collections.reverse(chemin);
		s+= source;
		for (int sommet : chemin) s+= " " + sommet;
		s+= " " + destination;
		return s;
	}
	
}
