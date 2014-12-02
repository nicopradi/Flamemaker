package flame;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import geometry2d.*;

/**
 * Cette classe permet de calculer l'ensemble S des points à partir des fonctions affines caractérisant la fractale,
 *  en particulier grace a la méthode compute 
 *
 */
public class Flame {
	/**
	 * Liste de d'affine transformations
	 */
	private final List<FlameTransformation> transformations;

	/**
	 * Constructeur de la classe Flame
	 * @param transformations Liste de transformations affines
	 */
	public Flame(List<FlameTransformation> transformations){
		// copie profonde, c'est une nouvelle liste qui est crée.
		this.transformations=new ArrayList<FlameTransformation>(transformations);
	}

	/**
	 * Methode contenant l'algorithme du chaos, permettant de construire un accumulateur à partir d'une liste d'affines transformation
	 * @param frame Rectangle représentant le lieu de la fractale
	 * @param width Nombre de case de l'accumulateur en largeur
	 * @param height Nombre de case de l'accumulateur en hauteur 
	 * @param density Permettant de modifier le nombre d'itération
	 * @return L'accumulateur correspondant qu paramètres donnés
	 */
	public FlameAccumulator compute(Rectangle frame, int width, int height, int density){
		FlameAccumulator.Builder construction=new FlameAccumulator.Builder(frame, width, height);

		Point p=new Point(0,0);
		// Calcul du nombre d'itérations
		int m=width*height*density;
		Random rand = new Random(2013);
		// Indice de couleur
		double index = 0.0;

		for(int i=0; i<20; i++){
			
			int j = rand.nextInt(transformations.size());// Obtention d'un entier aléatoire entre 0 et le nombre de transformations aléatoires
			p=transformations.get(j).transformPoint(p);//Obtention de la transformation corespondante dans la liste avec les variations
			index = (index+ indexColor(j))/2.0; // Obtention de la couleur du points
		}

		for(int i=0; i<m; i++){ // Boucle qui calcule les points appartenant a l'accumulateur
			
			int j = rand.nextInt(transformations.size());
			p=transformations.get(j).transformPoint(p);
			index = (index + indexColor(j))/2.0;

			construction.hit(p, index); // Methode hit de FlameAccumulateur.Builder, permettant d'ajouter des points a l'accumulateur 
		}

		// On retourne l'accumulateur fini.
		return construction.build();

	}

	/**
	 * Cette methode permet de calculer l'index de couleur correspondant a la transformation F d'index : index
	 * @param index Index de la transformation
	 * @return Index de couleur correspondant a la transformation
	 */
	public double indexColor(int index){
		if(index == 1 || index == 0)
			return index;
		else{
			double den = Math.pow(2, (int) Math.ceil(Math.log(index)/Math.log(2))); // Formule trouvé par tâtonnement
			return(2.0*index-den-1)/den;
		}
	}

	/**
	 * Cette classe permet de batir une fractale flame de manière incrémentale et non prédéfinie
	 *
	 */
	public static class Builder{
		/**
		 *Ensembles des transformation affines
		 */
		private List<FlameTransformation> listOfTransformations;

		/**
		 * Constructeur de la classe imbriquée
		 * @param flame Flame contenant des affines transformations à prendre en compte
		 */
		public Builder (Flame flame){
			listOfTransformations = new ArrayList<FlameTransformation>(flame.transformations);
		}

		
		/**
		 * @return La taille de la liste d'affines transformations
		 */
		public int transformationCount(){
			return listOfTransformations.size();
		}

		/** 
		 * Méthode permettant d'ajouter des transformation à la liste 
		 * @param transformation Affine transformation à ajouter.
		 */
		public void addTransformation(FlameTransformation transformation){
			listOfTransformations.add(transformation);
		}

		
		/**
		 * Méthode permettant de savoir quelle transformation affine correspont a quel index
		 * @param index index à tester et fair correspondre
		 * @return L'affine transformation correspondant a l'index donné
		 */
		public AffineTransformation affineTransformation(int index){
			checkIndex(index);
			FlameTransformation.Builder affineForIndex = new FlameTransformation.Builder(listOfTransformations.get(index));
			return affineForIndex.affineTransformation();
		}

		/**
		 * Méthode permttant de modifier une affine transformation à un certain index
		 * @param index Lieu où il faut modifier la transformation
		 * @param newTransformation Nouvelle tranformation à remplacer
		 */
		public void setAffineTransformation(int index, AffineTransformation newTransformation){
			checkIndex(index);
			FlameTransformation.Builder newAffine = new FlameTransformation.Builder(listOfTransformations.get(index));
			newAffine.setAffineTransformation(newTransformation);
			// On stocke la nouvelle transformation dans la liste à la place de l'ancienne
			listOfTransformations.set(index, newAffine.build());
		}

		/**
		 * Méthode qui donne le poids de la variation pour l'indice donnée
		 * @param index Index de l'affine transformation
		 * @param variation Calcule du point de cette variation a un certain index
		 * @return Le poids de la variation a l'indice donné
		 */
		public double variationWeight(int index, Variation variation){
			checkIndex(index);
			FlameTransformation.Builder weightForVariation = new FlameTransformation.Builder(listOfTransformations.get(index));
			return weightForVariation.variationWeight(variation.index());
		}

		/**
		 * Méthode qui change le poids de la variation pour l'indice donnée
		 * @param index Index de l'affine transformationà modifier
		 * @param newWeight Valeur du nouveau poids
		 */
		public void setVariationWeight(int index, Variation variation, double newWeight){
			checkIndex(index);
			FlameTransformation.Builder affineNewWeight = new FlameTransformation.Builder(listOfTransformations.get(index));
			affineNewWeight.setVariationWeight(variation.index(), newWeight);
			listOfTransformations.set(index, affineNewWeight.build());
		}

		/**
		 * Supprime la transformation pour l'index donné
		 * @param index Lieu où il faut supprimer la transformation
		 */
		public void removeTransformation(int index){
			checkIndex(index);
			listOfTransformations.remove(index);
		}

		/**
		 * Méthode qui construit la nouvelle Flame avec les transformations engendrées
		 * @return La nouvelle fractale Flame
		 */
		public Flame build(){
			return new Flame(listOfTransformations);
		}

		
		/**
		 * Méthode qui vérifie si l'index est valide ou non ( lance une exception si invalide )
		 * @param index Index à tester
		 */
		public void checkIndex(int index){
			if(index<0 || index>=listOfTransformations.size())
				throw new IndexOutOfBoundsException("index non valide");			
		}

	}
}
