import java.util.ArrayList;

public class Flower {
	ArrayList<EdgeFlower> innerFlowers;
	boolean isOuterFlower;
	Flower stonka; // pamata si iba outerFlower
	private double charge; // naboj momentalnej flower
	Flower parent; // ak nie je outerflower, pamata si nadkvetinu
	// realne hrany pre vertex
	boolean isStonka; // pamata si o sebe, ci je stonka
	private TNode parentTreeNode; //
	EdgeFlower Medge; // hrana a kvetina cez parovaciu hranu
	VertexFlower Mvertex; // vrchol, cez ktory sa paruje
	EdgeFlower Ledge; // hrana a kvetina cez L hranu
	VertexFlower Lvertex; // vrchol, z ktoreho ide L hrana

	public void setParentTreeNode(TNode parentTreeNode) {
		this.parentTreeNode = parentTreeNode;
	}

	ArrayList<VertexFlower> getVerticesOfFlower() {
		ArrayList<VertexFlower> vertices = new ArrayList<>();
		for (EdgeFlower e : innerFlowers) {
			Flower f = e.f;
			vertices.addAll(f.getVerticesOfFlower());
		}
		return vertices;
	}

	Flower getOuterFlower() {
		if (isOuterFlower) {
			return this;
		} else {
			return parent.getOuterFlower();
		}
	}

	ArrayList<EdgePair> getOuterEdgePairs() { // vrati vsetky nenaplnene
												// vertexy, ktore maju pary s
												// niekym zvonku
		ArrayList<VertexFlower> vertices = getVerticesOfFlower();
		ArrayList<EdgePair> result = new ArrayList<>();
		for (VertexFlower f : vertices) {
			for (EdgeFlower e : f.edges) {
				if (f.getOuterFlower() != e.f.getOuterFlower()) {
					if (e.es.getState() == State.FREE) {
						result.add(new EdgePair(f, e.f, e.es));
					}
				}
			}
		}
		return result;
	}

	double getMinEpsForFlower(ArrayList<EdgePair> outerEdgePairs) { // z
																	// vonkajsich
																	// vrcholov
		double min = 999999999;
		for (EdgePair ep : outerEdgePairs) {
			min = Math.min(min, ep.getMinForEdgePair());
		}
		return min;
	}

	double getMinEpsForFlower() {
		return getMinEpsForFlower(getOuterEdgePairs());
	}

	double getOuterChargeForVertex() { // vrati celkovy naboj pre vsetky
										// kvetiny, v
		// ktorych je vertex obsiahnuty
		if (isOuterFlower) {
			return charge;
		} else {
			return charge + parent.getOuterChargeForVertex();
		}
	}

	public String toString() {
		String s = "";
		for (EdgeFlower e : innerFlowers) {
			Flower f = e.f;
			s = f.toString() + " ";
		}
		return s;
	}

	public double getCharge() {
		return charge;
	}

	public void increaseCharge(double charge) {
		this.charge += charge;
	}

	public ArrayList<Problem> getProblems() {
		ArrayList<Problem> probs = new ArrayList<Problem>();
		ArrayList<EdgePair> eps = getOuterEdgePairs();
		for (EdgePair ep : eps) {
			Problem p = ep.getProblem();
			if (p != null) {
				probs.add(p);
			}
		}
		return probs;
	}

	public TNode getParentTreeNode() {
		if (isOuterFlower) {
			return parentTreeNode;
		} else {
			return parent.getParentTreeNode();
		}
	}

}
