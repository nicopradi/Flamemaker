package color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe Tres similaire a InterpolatedPalette, a la différence que la liste
 *  de couleur n'est pas donnée, mais est calculée aléatoirement.
 *
 */
public class RandomPalette implements Palette{
	private InterpolatedPalette palette;
	List<Color> couleurs;

	/**
	 * Constructeur: 
	 * crée autant de couleurs que demandé selon le parametre nbColor. Chaque couleur possede
	 * des indices r, g et b entre 0 et 1, choisis aléatoirement.
	 * Crée une InterpolatedPalette avec la nouvelle liste.
	 * 
	 * @param nbColor, le nombre d'elements que la liste aura, et donc de couleurs de la palette.
	 */
	public RandomPalette(int nbColor){
		if(nbColor<2)
			throw new IllegalArgumentException("Liste trop courte!");

		Random rand = new Random();
		this.couleurs = new ArrayList<Color>();
		for(int i=0; i<nbColor; i++){
			this.couleurs.add(new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
		}

		palette = new InterpolatedPalette(this.couleurs);
	}


	/**
	 * La palette étant une InterpolatedPalette, comme le montre le constructeur,  on peut réutiliser 
	 * la méthode colorForIndex de cette Classe.
	 * @param index, un indice entre 0 et 1.
	 */
	public Color colorForIndex(double index) {
		return palette.colorForIndex(index);		
	}

}
