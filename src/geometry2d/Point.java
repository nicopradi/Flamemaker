package geometry2d;

import java.lang.Math;

/**
 *Cette classe mod�lise les points de 2 mani�res : cart�sienne et polaire
 */
public final class Point 
{
	/** Origine d'un rep�re */
	public final static Point ORIGIN = new Point (0.0,0.0); 
	/** Abscisse d'un point */
	private final double x;
	/** Ordonn�e d'un point */
	private final double y; 

	/**
	 * Constructeur de la classe Point
	 * @param x Abscisse
	 * @param y Ordonn�e
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
	 * Calcule la distance par rapport a l'origine du point (x,y) (Coordonn�e polaire  ( r, theta) )
	 * @return r
	 */
	public double r() 
	{
		return Math.sqrt(x*x+y*y);
	}

	/**
	 * Calcule l'angle entre l'axe des abscisse et le point (x,y) (Coordonn�e polaire ( r, theta ) )
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
