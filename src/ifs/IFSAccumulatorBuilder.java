package ifs;
import geometry2d.Rectangle;
import geometry2d.Point;

public class IFSAccumulatorBuilder {

	private Rectangle frame;
	private boolean[][] isHit;

	IFSAccumulatorBuilder(Rectangle frame, int width, int height){
		if(width<=0 || height<=0)
			throw new IllegalArgumentException();
		else{
			this.frame=frame;
			this.isHit=new boolean[width][height];
		}

	}

	public void hit(Point p){
		//Décalage du frame dans un repere centré en (frame.left, frame.bottom) puis recadrage(adaptation des coordonés a la longueur du tableau).
		if(frame.contains(p))
			isHit[(int) (((p.x()-frame.left())/frame.width())*isHit.length)][(int) (((p.y()-frame.bottom())/frame.height())*isHit[0].length)]=true;
	}

	public IFSAccumulator build(){
		return new IFSAccumulator(isHit);
	}
}
