package algorithmes;

import algorithmes.types.Chemin;
import graphes.IGraphe;

public interface IPCC {
	Chemin pcc(IGraphe g, int debut, int fin);
}
