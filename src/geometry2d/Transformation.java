package geometry2d;

/**
 *Interface permettant d'implenté la méthode transformPoint
 *
 */
public interface Transformation 
{
	/**
	 * Méthode qui transforme le point p en un nouveau selon une transformation
	 * @param p Point à transformer
	 * @return Le point p transformé
	 */
	Point transformPoint(Point p);
}
