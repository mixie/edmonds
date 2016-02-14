
public class CinkaNode extends TNode{
	Cinka parentCinka;
	private Flower f;
	private CinkaNode neigh;
	public CinkaNode(Flower f) {
		super();
		this.f = f;
		f.setParentTreeNode(this);
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
