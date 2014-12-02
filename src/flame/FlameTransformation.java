package flame;
import geometry2d.*;

/**
 * Classe modélisant la composition d'une transformation affine avec une variation, de manière pondéré
 *
 */
public class FlameTransformation implements Transformation {
	/**
	 * Transformation affine donnée
	 */
	private final AffineTransformation affineComposante;
	/**
	 * Tableau de pondérations des compositions
	 */
	private final double[] poids;

	/**
	 * Constructeur, avec une copie en profondeur du tableau des poids
	 * @param affineTransformation Transformation affine en question
	 * @param variationWeight Tableau de pondération des compositions ( si le tableau n'est pas de taille 6, on lance une exception ) 
	 */
	public FlameTransformation(AffineTransformation affineTransformation, double[] variationWeight){
		this.affineComposante=affineTransformation;
		if(variationWeight.length != 6)
			throw new IllegalArgumentException("Tableau de poids invalide.");
		else
			this.poids=variationWeight.clone();
	}


	/**
	 * Méthode qui tranforme un point selon la formule donnée dans l'énoncé.
	 *  Pour chaque type de variation, teste si le poids est egal a zero => si oui, ne modifie pas le point, donc autant economiser 
	 *  du temps de calcul, sinon modifie le point.
	 */
	public Point transformPoint(Point p) {
		Point newPoint = new Point (0,0);

		for(int i = 0; i < 6; i++)
		{
			if(poids[i]!=0){
				Point nextPoint = Variation.ALL_VARIATIONS.get(i).transformPoint(affineComposante.transformPoint(p));
				newPoint = new Point(newPoint.x()+nextPoint.x()*poids[i], newPoint.y()+nextPoint.y()*poids[i]);
			}
		}
		return newPoint;
	}


	/**
	 *Classe imbriqués permettant de bâtire des transformations de manière incrémental et non prédéfinie
	 *
	 */
	public static class Builder
	{
		/**
		 *Affine transformation à utiliser
		 */
		private AffineTransformation transformations;
		/**
		 *  Poids pour l'ensemble des variations 
		 */
		private double[] weightTable;

		/**
		 * Constructeur de la classe imbriquée
		 * @param flame Affine transformation correspondante
		 */
		public Builder(FlameTransformation flame)
		{
			transformations = flame.affineComposante;
			weightTable = flame.poids;
		}


		/**
		 * Methode donnant la transformation affine de l'objet courant
		 * @return L'affine transformation de l'objet courant
		 */
		public AffineTransformation affineTransformation()
		{
			return transformations;
		}


		/**
		 * Methode qui permet de remplacer une affine transformation par une autre
		 * @param transformation Nouvelle affine transformation 
		 */
		public void setAffineTransformation(AffineTransformation transformation)
		{
			transformations = transformation;
		}


		/**
		 * Methode qui donne le poids pour un certain l'index 
		 * @param index Lieu où trouver le poid
		 * @return Le poids pour un certain index 
		 */
		public double variationWeight(int index)
		{
			return weightTable[index];
		}


		/**
		 * Méthode qui permet de modifier le poids pour un certain index
		 * @param index Lieu où modifier le poids
		 * @param weight Nouveau poids
		 */
		public void setVariationWeight(int index, double weight){
			weightTable[index] = weight;
		}


		/**
		 * Méthode qui construit une transformation avec affine transformation et une table de poids
		 * @return Une transformation en fonction de l'affine transformation et du poids 
		 */
		public FlameTransformation build()
		{
			return new FlameTransformation(transformations, weightTable);
		}
	}

}
