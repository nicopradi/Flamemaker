package ifs;
import java.util.List;
import java.util.ArrayList;
import geometry2d.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class IFSMaker {
	public static void main(String[] args){

		// Triangle de Sierpinski
		/*
				List<AffineTransformation> triangle= new ArrayList<AffineTransformation>();
				triangle.add(new AffineTransformation(0.5, 0, 0, 0, 0.5, 0));
				triangle.add(new AffineTransformation(0.5, 0, 0.5, 0, 0.5, 0));
				triangle.add(new AffineTransformation(0.5, 0, 0.25, 0, 0.5, 0.5));
				Rectangle frame=new Rectangle(new Point(0.5,0.5), 1, 1);
				dessine("Triangle", triangle, frame, 500, 500, 1);
		 */

		// Fougere de Barnsley
		
				List<AffineTransformation> fougere= new ArrayList<AffineTransformation>();
				fougere.add(new AffineTransformation(0, 0, 0, 0, 0.16, 0));
				fougere.add(new AffineTransformation(0.2, -0.26, 0, 0.23, 0.22, 1.6));
				fougere.add(new AffineTransformation(-0.15, 0.28, 0, 0.26, 0.24, 0.44));
				fougere.add(new AffineTransformation(0.85, 0.04, 0, -0.04, 0.85, 1.6));

		Rectangle frame=new Rectangle(new Point (0,4.5), 6, 10);
		dessine("Fougere", fougere, frame, 120, 200, 150);
		 


//		//test
//		List<AffineTransformation> test= new ArrayList<AffineTransformation>();
//		test.add(new AffineTransformation(0, 0, 0, 0, 0.1, 0));
//		test.add(new AffineTransformation(0.2, -0.2, 1.7, -0.2, 0.2, -1.6));
//		test.add(new AffineTransformation(-0.1, 0.2, 0.7, 0, 0.2, 0.4));
//		test.add(new AffineTransformation(0.8, 0.04, 0, -0.04, -0.8, -1.6));
//		Rectangle frame=new Rectangle(new Point(0,0), 10, 10);
//		dessine("Test", test, frame, 500, 500, 100);

	}

	public static void dessine(String titre, List<AffineTransformation> liste, Rectangle frame, int width, int height, int density){
		try {
			PrintStream stream=new PrintStream(titre);
			stream.println("P1");
			stream.println(width+" "+height);
			IFS ifs=new IFS(liste);
			IFSAccumulator ifsAcc =ifs.compute(frame, width, height, density);
			for(int j=0; j<ifsAcc.height(); j++){

				for(int i=0; i<ifsAcc.width(); i++){
					if(ifsAcc.isHit[i][ifsAcc.height()-1-j])
						stream.print("1");
					else
						stream.print("0");
				}
				stream.println("");
			}
			stream.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(titre+" fini!");
	}

}
