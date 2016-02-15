import java.util.ArrayList;

public class Flower {
	ArrayList<Flower> innerFlowers;
	VertexFlower stonka; // pamata si iba outerFlower
	private double charge; // naboj momentalnej flower
	Flower parent; // ak nie je outerflower, pamata si nadkvetinu
	// realne hrany pre vertex
	private TNode parentTreeNode; //
	EdgeFlower edge1; // hrana a kvetina cez 1 z vystupnych hran z kvetiny
	EdgeFlower edge2; // hrana a kvetina cez 1 z vystupnych hran z kvetiny

	Flower(){
		charge=0;
	}
	
	public EdgeFlower getEdgeToFlower(Flower f){
		if(edge1.f==f){
			return edge1;
		}else{
			return edge2;
		}
	}
	
	public EdgeFlower getEdgeNotToFlower(Flower f){
		if(edge1.f==f){
			return edge2;
		}else{
			return edge1;
		}
	}
	
	public void setParentTreeNode(TNode parentTreeNode) {
		this.parentTreeNode = parentTreeNode;
	}

	ArrayList<VertexFlower> getVerticesOfFlower() {
		ArrayList<VertexFlower> vertices = new ArrayList<>();
		for (Flower f : innerFlowers) {
			vertices.addAll(f.getVerticesOfFlower());
		}
		return vertices;
	}

	Flower getOuterFlower() {
		if (parent == null) {
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
			for (EdgeVertex e : f.edges) {
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
		if (parent == null) {
			return charge;
		} else {
			return charge + parent.getOuterChargeForVertex();
		}
	}

	public String toString() {
		String s = "";
		for (Flower f : innerFlowers) {
			s = f.toString() + " "+"C"+charge+"C";
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
		if (parent == null) {
			return parentTreeNode;
		} else {
			return parent.getParentTreeNode();
		}
	}

	public ArrayList<Flower> getAllParentFlowersForVertex() {
		ArrayList<Flower> f = new ArrayList<>();
		f.add(this);
		if (parent != null) {
			f.addAll(parent.getAllParentFlowersForVertex());
		}
		return f;
	}

	public int getLevelOfFlower() {
		int i=0;
		if (parent == null) {
			return i;
		} else {
			 i=1+parent.getLevelOfFlower();
		}
		return i;
	}

	public ArrayList<Edge> reconstructPath(ArrayList<Flower> path) {
		ArrayList<Edge> edges = new ArrayList<>();
		for (int i = 0; i < path.size() - 1; i++) {
			if (path.get(i).edge1.f == path.get(i + 1)) {
				edges.add(path.get(i).edge1.es);
			} else {
				edges.add(path.get(i).edge2.es);
			}
		}
		return edges;
	}

	public VertexFlower getInnerVertexOfFlowerFromEdge(Edge e) {
		int level = this.getLevelOfFlower();
		Flower f1 = e.f1.getFlowerOnLevelForVertex(level);
		if (this == f1) {
			return e.f1;
		}
		Flower f2 = e.f2.getFlowerOnLevelForVertex(level);
		if (this == f2) {
			return e.f2;
		}
		return null;
	}

	public ArrayList<Edge> getAlternatingRouteForFlower(VertexFlower from,
			VertexFlower to) {
		if (from == to) {
			return new ArrayList<Edge>();
		}
		if(this instanceof VertexFlower){
			return new ArrayList<Edge>();
		}
		int vertexLevelFrom = from.getLevelOfFlower();
		int actLevel = this.getLevelOfFlower();
		int vertexLevelTo = to.getLevelOfFlower();
		Flower lowerFromFlower = from.getFlowerOnLevelForVertex(actLevel + 1);
		Flower lowerToFlower = to.getFlowerOnLevelForVertex(actLevel + 1);
		int pathlen = 1;
		Flower actFlower = lowerFromFlower;
		ArrayList<Flower> path = new ArrayList<>();
		path.add(lowerFromFlower);
		while (actFlower != lowerToFlower) {
			Flower oldActFlower=actFlower;
			actFlower = oldActFlower.edge1.f;
			if (path.contains(actFlower)) {
				actFlower = oldActFlower.edge2.f;
			}
			path.add(actFlower);
			pathlen++;
		}
		if (pathlen % 2 == 0) {
			path.clear();
			path.add(lowerFromFlower);
			while (actFlower != lowerToFlower){
				Flower oldActFlower=actFlower;
				actFlower = oldActFlower.edge2.f;
				if (path.contains(actFlower)) {
					actFlower = oldActFlower.edge1.f;
				}
				path.add(actFlower);
				pathlen++;
			}
		}
		ArrayList<Edge> edgepath = new ArrayList<>();
		ArrayList<Edge> edgePathBetweenFlowers = reconstructPath(path);
		int i = 0;
		for (Flower f : path) {
			if (f == lowerFromFlower) {
				edgepath.addAll(getAlternatingRouteForFlower(from,
						f.getInnerVertexOfFlowerFromEdge(edgePathBetweenFlowers.get(0))));
			} else if (f == lowerToFlower) {
				edgepath.addAll(getAlternatingRouteForFlower(f
						.getInnerVertexOfFlowerFromEdge(edgePathBetweenFlowers.get(edgePathBetweenFlowers
								.size() - 1)), to));
			} else {
				edgepath.addAll(getAlternatingRouteForFlower(
						f.getInnerVertexOfFlowerFromEdge(edgePathBetweenFlowers.get(i-1)),
						f.getInnerVertexOfFlowerFromEdge(edgePathBetweenFlowers.get(i))));
			}
			if (i != edgePathBetweenFlowers.size()) {
				edgepath.add(edgePathBetweenFlowers.get(i));
			}
			i++;
		}
		return edgepath;
	}
}
