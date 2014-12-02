package color;


/**
 * Classe permettant d'implenter la couleur dans les fractales.
 */
public final class Color {
	private double r, g, b;

	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(1, 1, 1);
	public static final Color RED = new Color(1, 0, 0);
	public static final Color GREEN = new Color(0, 1, 0);
	public static final Color BLUE = new Color(0, 0, 1);


	/**Constructeur
	 *
	 * @param r
	 * @param g
	 * @param b
	 * 
	 * Verifie la validité des index de couleurs r, g et b avant de creer une couleur avec les indices donnés.
	 */
	public Color(double r, double g, double b){
		if(r>1 || r<0 || g>1 || g<0 || b>1 || b<0)
			throw new IllegalArgumentException("Indices couleur incorrects!");
		else{
			this.r=r;
			this.g=g;
			this.b=b;
		}		
	}

	/**
	 * getter pour r;
	 * 
	 * @return indice r de la couleur ciblée, car r est privé.
	 */
	public double red(){
		return this.r;
	}

	/**
	 * getter pour g;
	 * 
	 * @return indice g de la couleur ciblée, car g est privé.
	 */
	public double green(){
		return this.g;
	}

	/**
	 * getter pour b;
	 * 
	 * @return indice b de la couleur ciblée, car b est privé.
	 */
	public double blue(){
		return this.b;
	}	

	/**
	 * Verifie si la proportion est valide avant de procéder au mixage pour ne pas avoir de couleur ayant
	 * des indices de couleur non valide.
	 * 
	 * @param that
	 * @param proportion
	 * @return une nouvelle couleur dont chaque composante r, g, et b est mélangée a celle de l'autre
	 * couleur avec la meme proportion.
	 * 
	 */
	public Color mixWith(Color that, double proportion){
		if(proportion<0 || proportion>1)
			throw new IllegalArgumentException("Proportion non conforme!");
		else
			return new Color((proportion*red()+(1-proportion)*that.red()),
					(proportion*green()+(1-proportion)*that.green()),
					(proportion*blue()+(1-proportion)*that.blue()));
	}

	/**
	 * chaque composante est encodée selon la méthode srgbEncode, puis décalée de 0, 8 ou 16 bits
	 * selon la nature de la composante pour donner lieu a un seul entier 24 bits par couleur.
	 * 
	 * @return un entier 24 bits représentant une couleur.
	 */
	public int asPackedRGB(){
		// << x pour decaler de x bits.
		return((sRGBEncode(red(), 255) << 16)+(sRGBEncode(green(), 255) << 8)+ sRGBEncode(blue(),255));
	}

	/**
	 * on utilise la formule donnée pour transformer un indice entre 0 et 1 en une entier entre 0 
	 * et le max donné.
	 * on cast le resultat en int pour faire corespondre le return final et celui prédit.
	 * 
	 * @param v  valeur de l'indice d'une couleur.
	 * @param max valeur max de la couleur.
	 * @return un nouvel encodage de la couleur sur 8 bits.
	 */
	public static int sRGBEncode(double v, int max){
		if(v<=0.0031308)
			return (int) (12.92*v*max);
		else
			return (int) ((1.055*(Math.pow(v, (1.0/2.4)))-0.055)*max);
	}

	@Override
	public String toString(){
		return "("+r+";"+g+";"+b+")";
	}



}
