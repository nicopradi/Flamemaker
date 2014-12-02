package geometry2d;

import java.lang.Math;

/**
 * Classe représentant l'ensemble des transformations affines
 *
 */
public final class AffineTransformation
implements Transformation
{
	/** Matrice identitée*/
	public final static AffineTransformation IDENTITY = new AffineTransformation(1.0,0.0,0.0,0.0,1.0,0.0);
	/** Variables correspondant au valeurs de la matrice correspondant a une transformation ( coordonnées homogènes ) */
	private final double a;
	private final double b;
	private final double c;
	private final double d;
	private final double e;
	private final double f;

	/**
	 * Constructeur de la classe AffineTransformation
	 * @param a Valeur M(1,1) de la matrice
	 * @param b Valeur M(1,2) de la matrice
	 * @param c Valeur M(1,3) de la matrice
	 * @param d Valeur M(2,1) de la matrice
	 * @param e Valeur M(2,2) de la matrice
	 * @param f Valeur M(2,3) de la matrice
	 */
	public AffineTransformation (double a, double b, double c, double d, double e, double f)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	/**
	 * Methode qui créé une transformation affine représentant une translation de dx unités par rapport a l'axe des abscisses
	 * et dy unités par rapport a l'axe des ordonnées
	 * @param dx Valeur de translation pour les abscisses
	 * @param dy Valeur de translation pour les ordonnées
	 * @return La nouvelle AffineTransformation correspondant a une translation 
	 */
	public static AffineTransformation newTranslation(double dx, double dy)
	{
		return new AffineTransformation(1.0,0.0,dx,0.0,1.0,dy);
	}

	/**
	 * Methode qui créé une transformation affine représentant une rotation d'angle theta en radiant autour de l'origine
	 * @param theta Angle en radiant corresponsant a la rotation
	 * @return La nouvelle AffineTransformation correspondant a une rotation
	 */
	public static AffineTransformation newRotation(double theta)
	{
		double cost = Math.cos(theta);
		double sint = Math.sin(theta);
		return  new AffineTransformation(cost, -sint,0.0,sint,cost,0.0);
	}

	/**
	 *  Methode qui créé une transformation affine représentant une dilatation de facteur sx par rapport a l'axe des abscisse 
	 *  et sy par rapport a l'axe des ordonnées
	 * @param sx Facteur de dilatation pour abscisse
	 * @param sy Facteur de dilatation pour ordonnée
	 * @return La nouvelle AffineTransformation correspondant a une dilatation 
	 */
	public static AffineTransformation newScaling(double sx, double sy)
	{
		return new AffineTransformation(sx,0.0,0.0,0.0,sy,0.0);
	}

	/**
	 *  Methode qui créé une transformation affine représentant une transvection du facteur sx par rapport a l'axe des abscisse
	 * @param sx Facteur de transvection pour abscisse
	 * @return La nouvelle AffineTransformation correspondant a une transvection ( abscisse ) 
	 */
	public static AffineTransformation newShearX(double sx)
	{
		return new AffineTransformation(1.0,sx,0.0,0.0,1.0,0.0);
	}

	/**
	 * Methode qui créé une transformation affine représentant une transvection du facteur sy par rapport a l'axe des ordonnées
	 * @param sy Facteur de transvection pour ordonnée
	 * @return La nouvelle AffineTransformation corresponsant a une transvection ( ordonnée )
	 */
	public static AffineTransformation newShearY(double sy)
	{
		return new AffineTransformation(1.0,0.0,0.0,sy,1.0,0.0);
	}


	/**
	 * Methode qui calcule le point p transformé par la AffineTransformation
	 */
	public Point transformPoint(Point p)
	{
		return new Point(a*p.x()+b*p.y()+c,d*p.x()+e*p.y()+f);
	}

	/**
	 * Méthode qui retourne la composante horizontale de la translation, soit l'élément c de la matrice
	 * @return c
	 */

	private double translationX()
	{
		return c;
	}

	/**
	 * Méthode qui retourne la composante vertical de la translation, soit l'élément f de la matrice
	 * @return f
	 */
	private double translationY()
	{
		return f;
	}

	/**
	 * Methode qui compose 2 AffineTransformations
	 * @param that AffineTransformation a composé
	 * @return La nouvelle AffineTransformation qui est la composition de cette transformation affine avec la transformation affine that.
	 */
	public AffineTransformation composeWith (AffineTransformation that)
	{
		return new AffineTransformation (a*that.a + b*that.d,a*that.b+b*that.e,a*that.c+b*that.f+c,
				d*that.a+e*that.d, d*that.b+e*that.e,d*that.c+e*that.f+f  );
	}



}
