package geometry2d;

/**
 *Cette classe mod�lise les rectangles parrall�les aux axes x et y 
 */
public final class Rectangle {
	/** Centre de rectangle */
	private final Point center;
	/** Largeur du rectangle */
	private final double width;
	/** Hauteur du rectangle */
	private final double height;

	/**
	 * Constructeur de la classe Rectangle ( Lance une exception si les
	 * parametres donn�s sont incorrects )
	 * 
	 * @param center
	 *            Centre du rectangle
	 * @param width
	 *            Largeur du rectangle
	 * @param height
	 *            Hauteur du rectangle
	 * 
	 */
	public Rectangle(Point center, double width, double height) {
		this.center = center;
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException(" Coordonn�es x ou y incorrect ");
		} else {
			this.width = width;
			this.height = height;
		}
	}

	/**
	 * Calcule la valeur de l'abscisse du point le plus a gauche du rectangle,
	 * nomm� left
	 * 
	 * @return left
	 */
	public double left() {
		return center.x() - (width / 2.0);
	}

	/**
	 * Calcule la valeur de l'abscisse du point le plus a droite du retcangle,
	 * nomm� right
	 * 
	 * @return right
	 */
	public double right() {
		return center.x() + (width / 2.0);
	}

	/**
	 * Calcule la valeur de l'ordonn�e du point le plus bas du rectangle, nomm�
	 * bottom
	 * 
	 * @return bottom
	 */
	public double bottom() {
		return center.y() - (height / 2.0);
	}

	/**
	 * Calcule la valeur de l'ordonn�e du point le plus haut du rectangle, nomm�
	 * top
	 * 
	 * @return top
	 */
	public double top() {
		return center.y() + (height / 2.0);
	}

	/**
	 * Getter de la variable width
	 * 
	 * @return width
	 */
	public double width() {
		return width;
	}

	/**
	 * Getter de la variable height
	 * 
	 * @return height
	 */
	public double height() {
		return height;
	}

	/**
	 * Getter de la variable center
	 * 
	 * @return center
	 */
	public Point center() {
		return center;
	}

	/**
	 * Methode qui test si un point p appartient ou non au rectangle, selon les
	 * conditions indiqu�es
	 * 
	 * @param p
	 *            Point � tester
	 * @return true si le point p appartient au rectangle, sinon false
	 */
	public boolean contains(Point p) {
		return (p.x() >= left() && p.x() < right() && p.y() >= bottom() && p.y() < top());
		/**
		 * Condition
		 * qui v�rifie si un point appartient au rectangle
		 */
	}

	/**
	 * Calcule la rapport largeur/hauteur du rectangle, nomm� aspectRatio
	 * 
	 * @return aspectRatio
	 */
	public double aspectRatio() {
		return (width() / height());
	}

	/**
	 * Methode qui retourne le plus petit rectangle ayant le meme centre que le
	 * recepteur, la valeur aspectRatio pass� en param�tre et contenant
	 * totalement le recepteur. Lance une exception si l'aspect ratio est
	 * n�gatif
	 * 
	 * @param aspectRatio Ratio � respecter dans la construction du nouveau rectangle
	 * @return Le nouveau rectangle selon les indication ci-contre
	 */
	public Rectangle expandToAspectRatio(double aspectRatio) {
		if (aspectRatio <= 0) {
			throw new IllegalArgumentException();
		} else {
			return new Rectangle(center, Math.max(width, height * aspectRatio),
					Math.max(height, width / aspectRatio));
		}
	}

	@Override
	public String toString() {
		return "((" + center.x() + "," + center.y() + "),"
				+ width() + "," + height() + ").";
	}

}
