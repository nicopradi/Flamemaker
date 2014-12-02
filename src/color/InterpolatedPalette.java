package color;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe permettant de cr�er des palettes de couleur pour en extraire une pariculiere.
 */
public class InterpolatedPalette implements Palette{
	private List<Color> liste;	

	/**
	 * Verifie que la liste mise en paramatre possede au moins deux �l�ments: un correspondant 
	 * a l'indice 0 et l'autre a l'indice 1;
	 * 
	 * @param liste de couleur possedant au moins deux elements.
	 */
	public InterpolatedPalette(List<Color> liste) {
		if(liste.size()<2)
			throw new IllegalArgumentException("Liste de couleurs trop courte!");

		this.liste = new ArrayList<Color>(liste);
	}




	/**
	 * Verifie la validit� de l'index et determine la couleur correspondant a cet index dans
	 * la palette dans laquelle on se trouve.
	 * Si la proportion est egale a 0 ou 1, on evite les calculs engendr�s par le mixWith().
	 * 
	 * @param index de couleur.
	 * @return une couleur correspondant au mix de deux couleurs consecutives de la palette. 
	 * 
	 */
	public Color colorForIndex(double index) {

		if(index<0 || index>1)
			throw new IllegalArgumentException("Index non valide");
		/**
		 * pour obtenir l'intervalle entre l'indice donn� et la couleur de la palette la plus proche (a gauche):
		 *  on multiplie  par le nombre d'intervalles: pour n couleurs dans la palette, il y a n-1 intevalles.
		 *  => un intervalle est repr�sent� par une unit�.
		 *  puis on enleve la partie entiere pour obtenir la distance, entre 0 et 1.
		 */
		double proportion=index*(this.liste.size()-1);
		int color= (int) proportion;
		proportion -= color;

		if (proportion == 0)
			return liste.get(color);
		else if (proportion == 1)
			return liste.get(color+1);
		else
			return liste.get(color).mixWith(liste.get(color + 1), 1-proportion);
	}

}

