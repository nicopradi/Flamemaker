package flame;
import geometry2d.*;
import color.*;

/**
 *Cette classe permet de repr�senter un accumulateur d'une certaine fractale
 *
 */
public final class FlameAccumulator 
{
	/** Case de l'accumulateur qui se fait touch� le plus de fois */
	private final double maxHit;
	/** Accumulateur */
	public final int[][] hitCount;
	/**Tableau contenant la somme des index couleur de chaque case */
	private final double[][] colorIndexSum;


	/**
	 * Constructeur : Construit un accumulateur avec hitCount, qui est copi� en profondeur, de meme pour colorIndexSum.
	 * La variable coef est utilis� dans la m�thode intensity()
	 * @param hits Tableau bidimentionel utilis� pour construire accumulateur
	 * @param colorIndexSum Tableau bidimentionel contenant la somme des index de couleur de chaque case
	 */
	private FlameAccumulator(int[][] hitCount, double [][] colorIndexSum){
		this.hitCount=hitCount.clone();
		this.colorIndexSum=colorIndexSum.clone();

		int temp=0; // Sert a trouver le nombre maximum de point qu'une case contient 
		for(int i=0; i<this.hitCount.length; i++){
			for(int j=0; j<this.hitCount[0].length; j++){
				if(temp<this.hitCount[i][j])
					temp=this.hitCount[i][j];
			}
		}
		maxHit=temp;

	}

	/**
	 * Methode qui retourne la largeur de l'accumulateur en nombre de cases
	 * @return La largeur de l'accumulateur
	 */
	public int width(){
		return hitCount.length;
	}

	/**
	 * Methode qui retroune la hauteur de l'accumulateur en nombre de cases
	 * @return La hauteur de l'accumulateur
	 */
	public int height(){
		return hitCount[0].length;
	}

	/**
	 * Methode qui calcule l'intensit� de la case de l'accumulateur 
	 * On prend une valeur relative a celle max, logarithmique pour avoir une couleur plus uniforme.
	 * Lance une exception si les parametre x ou y sont invalides
	 * @param x Valeur de l'abscisse de l'accumulateur
	 * @param y Valeur de l'hauteur de l'accumulateur
	 * @return La valeur de l'intensit� de la case en question
	 */
	public double intensity(int x, int y){
		if(x<0 || x>=width() || y<0 || y>= height())
			throw new IndexOutOfBoundsException("Coordonn�es non valide");

		return Math.log(1+hitCount[x][y])/Math.log(1+maxHit);
	}

	/**
	 * Methode qui donne la couleur de la case en fonction de la case donn�e
	 * @param palette Palette utilis� pour calculer la couleur de la case
	 * @param background Couleur du font de l'image utilis� pour calcul� la couleur de la case de l'accumulateur
	 * @param x Valeur de l'abscisse de l'accumulateur
	 * @param y Valeur de l'ordonn� de l'accumulateur
	 * @return La couleur corresponsant a la case de l'accumulateur 
	 */
	public Color color(Palette palette, Color background, int x, int y){
		if( x<0 || x>=width() || y>=height() || y<0 )
			throw new IndexOutOfBoundsException ("Coordonn�es non valide");

		return palette.colorForIndex(colorIndexSum[x][y]/hitCount[x][y]).mixWith(background, intensity(x , y));	

	}


	/**
	 * Cette classe imbriqu� permet de construire, point par point un accumulateur 
	 *
	 */
	public static class Builder{
		/**Cadre utilis� pour les batisseur d'accumulateur */
		Rectangle frame;
		/** */
		private int[][] hitCount;
		private double[][] colorIndexSum;

		/** cette transfomation permetra de transformer des coordon�es du plan
		 en coordon�es du tableau, en pixels. 
		 */

		/**
		 * Constructeur : Construit un batisseur d'accumulateur selon le frame de largeur width et de hauteur height
		 * Lance une exception si largeur ou hauteur n�gative
		 * @param frame Limite du batisseur
		 * @param width Largeur du rectangle
		 * @param height Hauteur du rectangle
		 */
		public Builder(Rectangle frame, int width, int height){
			if(width<=0 || height<=0)
				throw new IllegalArgumentException();

			this.frame=frame;
			this.hitCount=new int[width][height];	
			this.colorIndexSum=new double[width][height];


		}

		/**
		 * M�thode qui incr�mente le compteur de la case pour le point p donn�, ne fait rien si le point n'est pas dans l'accumulateur
		 * @param p incr�mentation de la case correspondant a ce point 
		 * @param i index pour incr�menter
		 */
		public void hit(Point p, double i){

			if(frame.contains(p) && !(i<0 || i>1)){

				double sizeX = frame.width()/hitCount.length; // Donne l'intervalle entre chaque case pour x 
				double sizeY = frame.height()/hitCount[0].length; // Donne l'intervalle entre chaque case pour t 

				hitCount[(int) ((p.x() - frame.left())/sizeX)][(int)((p.y()-frame.bottom())/sizeY)]++;

				colorIndexSum[(int) ((p.x() - frame.left())/sizeX)][(int)((p.y()-frame.bottom())/sizeY)] += i;
			}
		}


		/**
		 * Methode qui retourne l'accumulateur avec les points collect� jusqu'� pr�sent
		 * @return Nouvelle accumulateur
		 */
		public FlameAccumulator build(){
			return new FlameAccumulator(hitCount, colorIndexSum);
		}



	}

}