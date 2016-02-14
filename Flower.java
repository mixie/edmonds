import java.util.ArrayList;

public class Flower {
	ArrayList<EdgeFlower> innerFlowers;
	VertexFlower stonka; // pamata si iba outerFlower
	private double charge; // naboj momentalnej flower
	Flower parent; // ak nie je outerflower, pamata si nadkvetinu
	// realne hrany pre vertex
	private TNode parentTreeNode; //
	EdgeFlower edge1; // hrana a kvetina cez 1 z vystupnych hran z kvetiny
	EdgeFlower edge2; // hrana a kvetina cez 1 z vystupnych hran z kvetiny

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
		for (EdgeFlower e : innerFlowers) {
			Flower f = e.f;
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

	public ArrayList<Flower> getFlowerOnLevelForVertex() {
		ArrayList<Flower> f = new ArrayList<>();
		f.add(this);
		if (parent != null) {
			f.addAll(parent.getFlowerOnLevelForVertex());
		}
		return f;
	}

	public int getLevelOfFlower(int i) {
		if (parent == null) {
			return i;
		} else {
			i++;
			return parent.getLevelOfFlower(i);
		}
	}

	public int getLevelOfFlower() {
		return getLevelOfFlower(0);
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
			actFlower = actFlower.edge1.f;
			if (path.contains(actFlower)) {
				actFlower = actFlower.edge2.f;
			}
			path.add(actFlower);
			pathlen++;
		}
		path.clear();
		if (pathlen % 2 != 0) {
			path.add(lowerFromFlower);
			while (actFlower != lowerToFlower) {
				actFlower = actFlower.edge2.f;
				if (path.contains(actFlower)) {
					actFlower = actFlower.edge1.f;
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
						f.getInnerVertexOfFlowerFromEdge(edgepath.get(0))));
			} else if (f == lowerToFlower) {
				edgepath.addAll(getAlternatingRouteForFlower(f
						.getInnerVertexOfFlowerFromEdge(edgepath.get(edgepath
								.size() - 1)), to));
			} else {
				edgepath.addAll(getAlternatingRouteForFlower(
						f.getInnerVertexOfFlowerFromEdge(edgepath.get(i)),
						f.getInnerVertexOfFlowerFromEdge(edgepath.get(i + 1))));
			}
			if (i != edgePathBetweenFlowers.size()) {
				edgepath.add(edgePathBetweenFlowers.get(i));
			}
			i++;
		}
		return edgepath;
	}
}
