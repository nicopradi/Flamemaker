package ifs;
import java.util.ArrayList;
import geometry2d.Rectangle;
import geometry2d.AffineTransformation;
import geometry2d.Point;
import java.util.List;
import java.util.Random;

public final class IFS {

	List<AffineTransformation> transformations;

	public IFS(List<AffineTransformation> transformations){
		this.transformations=new ArrayList<AffineTransformation>();
		for(int i=0; i<transformations.size(); i++){
			this.transformations.add(transformations.get(i));
		}
	}

	public IFSAccumulator compute(Rectangle frame, int width, int height, int density){
		IFSAccumulatorBuilder construction=new IFSAccumulatorBuilder(frame, width, height);
		Point p=new Point(0,0);
		int m=width * height * density;
		Random gene= new Random();
		for(int i=0; i<20; i++){
			p=transformations.get(gene.nextInt(transformations.size())).transformPoint(p);

		}

		for(int i=0; i<m; i++){
			p=transformations.get(gene.nextInt(transformations.size())).transformPoint(p);
			construction.hit(p);
		}
		return construction.build();	
	}
}
