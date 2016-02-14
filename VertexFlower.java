import java.util.ArrayList;

public class VertexFlower extends Flower {
	int id;
	ArrayList<EdgeVertex> edges; 
	
	
	ArrayList<VertexFlower> getVerticesOfFlower() {
		ArrayList<VertexFlower> vertices = new ArrayList<>();
		vertices.add(this);
		return vertices;
	}
	
	public VertexFlower(int id) {
		stonka = this;
		parent = null;
		this.id = id;
		edges = new ArrayList<>();
	}
	

	void addEdge(VertexFlower f, Edge es) {
		edges.add(new EdgeVertex(f, es));
	}

	
	public String toString(){
		return Integer.toString(id)+"C"+this.getCharge()+"C";
	}
	
	public Flower getFlowerOnLevelForVertex(int level){ //vrati kvetinu na leveli, kde 0 je vonkajsia, 1 kvety vo vonk...
		ArrayList<Flower> parentFlowers=new ArrayList<Flower>();
		parentFlowers.add(this);
		if(this.parent!=null){
			parentFlowers.addAll(this.parent.getFlowerOnLevelForVertex());
		}
		return parentFlowers.get(parentFlowers.size()-level-1);
	}
	
	public ArrayList<Edge> getAlternatingRouteForFlower(Edge from, Edge to){
		return new ArrayList<>();
	}
	
	public ArrayList<Edge> getAlternatingRouteForFlower(VertexFlower from,
			VertexFlower to) {
		return new ArrayList<>();
	}
}
