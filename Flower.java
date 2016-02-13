import java.util.ArrayList;

public class Flower {
	ArrayList<EdgeFlower> innerFlowers;
	boolean isOuterFlower;
	Flower stonka; // pamata si iba outerFlower
	int charge; //naboj momentalnej flower
	Flower parent; //ak nie je outerflower, pamata si nadkvetinu
	ArrayList<EdgeFlower> edges; //realne hrany pre vertex
	int id; //ak je vertex, id vertexu
	Flower edgeToParent;
	boolean isVertex; 
	int vertexNumber; //ak je kvetina vert ex, pamata si cislo
	boolean isStonka; //pamata si o sebe, ci je stonka
	Tree parentTree; // je kvetina sucastou stromu alebo cinky, odkaz nan
	EdgeFlower Medge; //hrana a kvetina cez parovaciu hranu
	Flower Mvertex; //vrchol, cez ktory sa paruje
	EdgeFlower Ledge; //hrana a kvetina cez L hranu
	Flower Lvertex; //vrchol, z ktoreho ide L hrana
	
	public Flower(int id) {
		isStonka = true;
		isOuterFlower = true;
		isVertex = true;
		this.id = id;
		edges = new ArrayList<>();
		parentTree=null;
	}

	void addEdge(Flower f, Edge es) {
		edges.add(new EdgeFlower(f, es));
	}

	ArrayList<Flower> getVerticesOfFlower() {
		ArrayList<Flower> vertices = new ArrayList<>();
		if (isVertex) {
			vertices.add(this);
		} else {
			for (EdgeFlower e : innerFlowers) {
				Flower f = e.f;
				vertices.addAll(f.getVerticesOfFlower());
			}
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
		ArrayList<Flower> vertices = getVerticesOfFlower();
		ArrayList<EdgePair> result = new ArrayList<>();
		for (Flower f : vertices) {
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

	Edge getEdgeWithMinEps(ArrayList<EdgePair> outerEdgePairs) { //z vonkajsich vrcholov
		int min = 999999999;
		EdgePair minEdgePair = null;
		for (EdgePair ep : outerEdgePairs) {
			int chargeIn = ep.inside.getOuterChargeForVertex();
			int chargeOut = ep.outside.getOuterChargeForVertex();
			int diff = ep.es.edgeVal - chargeIn - chargeOut;
			if (diff < min) {
				minEdgePair = ep;
				min = diff;
			}
		}
		return minEdgePair.es;
	}
	
	Edge getEdgeWithMinEps(){
		return getEdgeWithMinEps(getOuterEdgePairs());
	}

	int getOuterChargeForVertex() { // vrati celkovy naboj pre vsetky kvetiny, v								
		// ktorych je vertex obsiahnuty
		if (isOuterFlower) {
			return charge;
		} else {
			return charge + parent.getOuterChargeForVertex();
		}
	}

	public String toString() {
		if (isVertex) {
			return Integer.toString(id);
		} else {
			String s = "";
			for (EdgeFlower e : innerFlowers) {
				Flower f = e.f;
				s = f.toString() + " ";
			}
			return s;
		}
	}

}
