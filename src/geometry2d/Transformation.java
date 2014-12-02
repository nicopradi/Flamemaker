package geometry2d;

/**
 *Interface permettant d'implent� la m�thode transformPoint
 *
 */
public interface Transformation 
{
	/**
	 * M�thode qui transforme le point p en un nouveau selon une transformation
	 * @param p Point � transformer
	 * @return Le point p transform�
	 */
	Point transformPoint(Point p);
}
