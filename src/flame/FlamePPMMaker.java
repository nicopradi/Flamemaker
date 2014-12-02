package flame;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

import geometry2d.*;
import color.*;

/**
 * Cette classe contient la méthode main permettant de compiler le programme 
 *
 */
public class FlamePPMMaker {

	/**
	 * Méthode main contenant la palette à utiliser, ainsi que la liste des affines transformation
	 * @param args
	 */
	public static void main(String[] args) 
	{

		List<Color> RGBList = new ArrayList<Color>(); // Liste de la palette à utiliser( rouge, vert, bleu )
		RGBList.add(Color.RED);
		RGBList.add(Color.GREEN);
		RGBList.add(Color.BLUE);
		Palette RGB = new InterpolatedPalette(RGBList);


		// Shark fin

		ArrayList<FlameTransformation> sharklist = new ArrayList<FlameTransformation>(); 
		// Liste des transformations et du tableau de poids correspond au Shark
		sharklist.add(new FlameTransformation(new AffineTransformation(-0.4113504, -0.7124804, -0.4, 0.7124795, -0.4113508, 0.8), new double[]{1.0, 0.1, 0, 0, 0, 0}));
		sharklist.add(new FlameTransformation(new AffineTransformation(-0.3957339, 0, -1.6, 0, -0.3957337, 0.2), new double[]{0, 0, 0, 0, 0.8, 1.0}));
		sharklist.add(new FlameTransformation(new AffineTransformation(0.4810169, 0, 1, 0, 0.4810169, 0.9), new double[]{1.0, 0, 0, 0, 0, 0}));
		// Rectangle, dont les dimentions sont communiquées
		Rectangle sharkframe = new Rectangle(new Point(-0.25, 0), 5, 4);		
		try {
			draw("shark-fin", sharklist, RGB, sharkframe, 500, 400, 50); // Paramètres de la méthode donnés
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		//Turbulence
		/*
		ArrayList<FlameTransformation> turblist = new ArrayList<FlameTransformation>();
		// Liste des transformations et du tableau de poids correspond au Shark
		turblist.add(new FlameTransformation(new AffineTransformation(0.7124807, -0.4113509, -0.3, 0.4113513, 0.7124808, -0.7), new double[]{0.5, 0, 0, 0.4, 0, 0}));
		turblist.add(new FlameTransformation(new AffineTransformation(0.3731079, -0.6462417, 0.4, 0.6462414, 0.3731076, 0.3), new double[]{1, 0, 0.1, 0, 0, 0}));	
		turblist.add(new FlameTransformation(new AffineTransformation(0.0842641, -0.314478, -0.1, 0.314478, 0.0842641, 0.3), new double[]{1, 0, 0, 0, 0, 0}));
		// Rectangle, dont les dimentions sont communiquées
		Rectangle turbframe = new Rectangle(new Point(0.1, 0.1), 3, 3);
		try {
			dessine("turbulences", turblist, RGB, turbframe, 500, 500, 50);// Paramètres de la méthode donnés
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		 */


	}


	public static void draw(String titre, List<FlameTransformation> liste, Palette palette, Rectangle frame, int width, int height, int density) throws FileNotFoundException {

		int lumax = 100; // Valeur maximal lors du gamma encodage
		Color background = Color.BLACK; // Couleur de fond
		// Construction de l'accumulateur correspondant à la liste de transformations
		FlameAccumulator Accumulator = new Flame(liste).compute(frame, width, height, density); 
		PrintStream stream = new PrintStream (titre + ".PPM");
		// Affichage du fichier
		stream.println("P3");
		stream.println(Accumulator.width() + " " + Accumulator.height());
		stream.println(lumax);

		for (int i=Accumulator.height()-1; i>=0; i--)
		{
			for (int j=0; j<Accumulator.width(); j++)
			{
				stream.print((Color.sRGBEncode(Accumulator.color(palette, background, j, i).red(), lumax)));
				stream.print(" ");
				stream.print((Color.sRGBEncode(Accumulator.color(palette, background, j, i).green(), lumax)));
				stream.print(" ");
				stream.print((Color.sRGBEncode(Accumulator.color(palette, background, j, i).blue(), lumax)));
				stream.print(" ");
			}
			stream.println("");
		}
		stream.close();
		System.out.println(titre + " fini!");
	}
}

