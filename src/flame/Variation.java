package flame;
import geometry2d.*;


import java.util.Arrays;
import java.util.List;

/**
 * Cette classe représente l'ensemble des variations
 *
 */
public abstract class Variation implements Transformation
{
	/** Contient indice de la variation */
	private final int indice; 
	/** Contient le nom de la variation */
	private final String nom;

	/**
	 * Constructeur
	 * @param indice Indice de la variation
	 * @param nom Nom de la variation 
	 */
	public Variation (int indice, String nom)
	{
		this.indice = indice;
		this.nom = nom;
	}

	/**
	 * Getter de la variable name
	 * @return name
	 */
	public String name() {
		return nom;
	}

	/**
	 * Getter de la variable index
	 * @return index
	 */
	public int index() { 
		return indice;
	}

	/**
	 * Transforme le point p avec la transformation donné
	 */
	abstract public Point transformPoint(Point p);

	/**
	 * Arraylist de variations
	 */
	public final static List<Variation> ALL_VARIATIONS = Arrays.asList(new Variation(0, "Linear")
	{
		public Point transformPoint(Point p) {
			return p;
		}
	},
	/**
	 *Ensembles de Variations dans des classes imbriquées 
	 *Les formules sont variations sont données dans l'énoncé 
	 */

	/**
	 * Variation 1 : Sinusoidal, transforme les coordonées du point avec sinus 
	 */
	new Variation(1 , "Sinusoidal") {
		public Point transformPoint( Point p ) {
			return new Point(Math.sin(p.x()), Math.sin(p.y()));
		}
	},
	/**
	 * Variation 2 : Spherical, divise les coordonées du point par r² ( coordonnées polaires ) 
	 */
	new Variation(2, "Spherical") {
		public Point transformPoint( Point p ) {
			return new Point(p.x()/(p.r()*p.r())  ,  p.y()/(p.r()*p.r() ) );
		}
	},
	/**
	 * Variation 3 : Swirl, tranforme les coordonées du point avec sin et r²
	 */
	new Variation(3, "Swirl") {
		public Point transformPoint ( Point p) {
			double sinr = Math.sin(p.r()*p.r());
			double cosr = Math.cos(p.r()*p.r());
			return new Point(  p.x()*sinr- p.y()*cosr ,  p.x()*cosr + p.y()*sinr );
		}
	}, 
	/**
	 * Variation 4 Horseshoe, transforme les coordonées du point en forme de " fer de cheval "
	 */
	new Variation(4, "Horseshoe") {
		public Point transformPoint ( Point p) {
			return new Point ((p.x()-p.y())*(p.x()+p.y())/p.r(), 2.0*p.x()*p.y()/p.r());
		}
	},
	/**
	 * Variation 5 Bubble, transforme les coordonées du point en forme de bulle
	 */
	new Variation(5, "Bubble") {
		public Point transformPoint ( Point p){ 
			double r2 = Math.pow( p.r(), 2);
			return new Point ( 4.0*p.x()/(r2+4.0), 4.0*p.y()/(r2+4.0));
		}
	}
			);


}