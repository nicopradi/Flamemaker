package flame;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import geometry2d.*;

/**
 * Cette classe permet de calculer l'ensemble S des points � partir des fonctions affines caract�risant la fractale,
 *  en particulier grace a la m�thode compute 
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
		// copie profonde, c'est une nouvelle liste qui est cr�e.
		this.transformations=new ArrayList<FlameTransformation>(transformations);
	}

	/**
	 * Methode contenant l'algorithme du chaos, permettant de construire un accumulateur � partir d'une liste d'affines transformation
	 * @param frame Rectangle repr�sentant le lieu de la fractale
	 * @param width Nombre de case de l'accumulateur en largeur
	 * @param height Nombre de case de l'accumulateur en hauteur 
	 * @param density Permettant de modifier le nombre d'it�ration
	 * @return L'accumulateur correspondant qu param�tres donn�s
	 */
	public FlameAccumulator compute(Rectangle frame, int width, int height, int density){
		FlameAccumulator.Builder construction=new FlameAccumulator.Builder(frame, width, height);

		Point p=new Point(0,0);
		// Calcul du nombre d'it�rations
		int m=width*height*density;
		Random rand = new Random(2013);
		// Indice de couleur
		double index = 0.0;

		for(int i=0; i<20; i++){
			
			int j = rand.nextInt(transformations.size());// Obtention d'un entier al�atoire entre 0 et le nombre de transformations al�atoires
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
			double den = Math.pow(2, (int) Math.ceil(Math.log(index)/Math.log(2))); // Formule trouv� par t�tonnement
			return(2.0*index-den-1)/den;
		}
	}

	/**
	 * Cette classe permet de batir une fractale flame de mani�re incr�mentale et non pr�d�finie
	 *
	 */
	public static class Builder{
		/**
		 *Ensembles des transformation affines
		 */
		private List<FlameTransformation> listOfTransformations;

		/**
		 * Constructeur de la classe imbriqu�e
		 * @param flame Flame contenant des affines transformations � prendre en compte
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
		 * M�thode permettant d'ajouter des transformation � la liste 
		 * @param transformation Affine transformation � ajouter.
		 */
		public void addTransformation(FlameTransformation transformation){
			listOfTransformations.add(transformation);
		}

		
		/**
		 * M�thode permettant de savoir quelle transformation affine correspont a quel index
		 * @param index index � tester et fair correspondre
		 * @return L'affine transformation correspondant a l'index donn�
		 */
		public AffineTransformation affineTransformation(int index){
			checkIndex(index);
			FlameTransformation.Builder affineForIndex = new FlameTransformation.Builder(listOfTransformations.get(index));
			return affineForIndex.affineTransformation();
		}

		/**
		 * M�thode permttant de modifier une affine transformation � un certain index
		 * @param index Lieu o� il faut modifier la transformation
		 * @param newTransformation Nouvelle tranformation � remplacer
		 */
		public void setAffineTransformation(int index, AffineTransformation newTransformation){
			checkIndex(index);
			FlameTransformation.Builder newAffine = new FlameTransformation.Builder(listOfTransformations.get(index));
			newAffine.setAffineTransformation(newTransformation);
			// On stocke la nouvelle transformation dans la liste � la place de l'ancienne
			listOfTransformations.set(index, newAffine.build());
		}

		/**
		 * M�thode qui donne le poids de la variation pour l'indice donn�e
		 * @param index Index de l'affine transformation
		 * @param variation Calcule du point de cette variation a un certain index
		 * @return Le poids de la variation a l'indice donn�
		 */
		public double variationWeight(int index, Variation variation){
			checkIndex(index);
			FlameTransformation.Builder weightForVariation = new FlameTransformation.Builder(listOfTransformations.get(index));
			return weightForVariation.variationWeight(variation.index());
		}

		/**
		 * M�thode qui change le poids de la variation pour l'indice donn�e
		 * @param index Index de l'affine transformation� modifier
		 * @param newWeight Valeur du nouveau poids
		 */
		public void setVariationWeight(int index, Variation variation, double newWeight){
			checkIndex(index);
			FlameTransformation.Builder affineNewWeight = new FlameTransformation.Builder(listOfTransformations.get(index));
			affineNewWeight.setVariationWeight(variation.index(), newWeight);
			listOfTransformations.set(index, affineNewWeight.build());
		}

		/**
		 * Supprime la transformation pour l'index donn�
		 * @param index Lieu o� il faut supprimer la transformation
		 */
		public void removeTransformation(int index){
			checkIndex(index);
			listOfTransformations.remove(index);
		}

		/**
		 * M�thode qui construit la nouvelle Flame avec les transformations engendr�es
		 * @return La nouvelle fractale Flame
		 */
		public Flame build(){
			return new Flame(listOfTransformations);
		}

		
		/**
		 * M�thode qui v�rifie si l'index est valide ou non ( lance une exception si invalide )
		 * @param index Index � tester
		 */
		public void checkIndex(int index){
			if(index<0 || index>=listOfTransformations.size())
				throw new IndexOutOfBoundsException("index non valide");			
		}

	}
}
