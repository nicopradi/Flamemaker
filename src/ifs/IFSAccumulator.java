package ifs;

public final class IFSAccumulator {

	final boolean[][] isHit;

	IFSAccumulator(boolean[][] isHit){
		this.isHit=isHit.clone();
	}

	public int width(){
		return this.isHit.length;
	}

	public int height(){
		return this.isHit[0].length;
	}



}
