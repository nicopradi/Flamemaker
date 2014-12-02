package color;
/**
 * Inteface reliant RandomPalette et InterpolatedPalette.
 * Possede la méthode colorForIndex a définir dans les classes implémentant 
 * cette inteface.
 *
 */
public interface Palette {
	/**
	 * cf class InterpolatedPalette.
	 * 
	 */
	Color colorForIndex(double index);

}
