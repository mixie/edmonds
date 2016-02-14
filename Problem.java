
public class Problem {
	static enum ProblemType {
	    P1,P2,P3,P4
	}
	ProblemType p;
	EdgePair ep;
	Flower f;
	Flower fInTree;
	Flower fInCinka;
	
	public Problem(ProblemType p, EdgePair ep) {
		this.p = p;
		this.ep = ep;
	}
	public Problem(Flower f) {
		super();
		this.p = ProblemType.P1;
		this.f = f;
	}
	
	public Problem(EdgePair ep, Flower fInTree, Flower fInCinka){
		this.p=ProblemType.P2;
		this.ep=ep;
		this.fInTree=fInTree;
		this.fInCinka=fInCinka;
	}
	
	public String toString(){
		return p.toString();
	}
	
}
