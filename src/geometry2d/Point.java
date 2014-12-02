package geometry2d;

import java.lang.Math;

/**
 *Cette classe modélise les points de 2 manières : cartésienne et polaire
 */
public final class Point 
{
	/** Origine d'un repère */
	public final static Point ORIGIN = new Point (0.0,0.0); 
	/** Abscisse d'un point */
	private final double x;
	/** Ordonnée d'un point */
	private final double y; 

	/**
	 * Constructeur de la classe Point
	 * @param x Abscisse
	 * @param y Ordonnée
	 */
	public Point (double x, double y) 
	{
		this.x = x; 
		this.y = y;
	}

	/**
	 * Getter pour la variable x
	 * @return x
	 */
	public double x()
	{
		return x;
	}

	/**
	 * Getter pour la variable y
	 * @return y
	 */
	public double y() 
	{
		return y;
	}

	/**
	 * Calcule la distance par rapport a l'origine du point (x,y) (Coordonnée polaire  ( r, theta) )
	 * @return r
	 */
	public double r() 
	{
		return Math.sqrt(x*x+y*y);
	}

	/**
	 * Calcule l'angle entre l'axe des abscisse et le point (x,y) (Coordonnée polaire ( r, theta ) )
	 * @return theta
	 */
	public double theta() 
	{
		return Math.atan2(x, y);
	}

	@Override
	public String toString()
	{
		return "(" + x + "," + y +")";
	}

}
