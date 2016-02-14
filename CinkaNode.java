
public class CinkaNode extends TNode{

	private Flower f;
	private CinkaNode neigh;
	public CinkaNode(Flower f) {
		super();
		this.f = f;
	}
	
	public CinkaNode getNeigh() {
		return neigh;
	}
	
	public void setNeigh(CinkaNode neigh) {
		this.neigh = neigh;
	}
	
	public Flower getF() {
		return f;
	}
	
}
