package color;
/**
 * Inteface reliant RandomPalette et InterpolatedPalette.
 * Possede la m�thode colorForIndex a d�finir dans les classes impl�mentant 
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
