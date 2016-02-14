import java.util.ArrayList;

public class VertexFlower extends Flower {
	int id;
	ArrayList<EdgeFlower> edges; 
	
	
	ArrayList<VertexFlower> getVerticesOfFlower() {
		ArrayList<VertexFlower> vertices = new ArrayList<>();
		vertices.add(this);
		return vertices;
	}
	
	public VertexFlower(int id) {
		isStonka = true;
		isOuterFlower = true;
		this.id = id;
		edges = new ArrayList<>();
		parentTree = null;
	}
	

	void addEdge(VertexFlower f, Edge es) {
		edges.add(new EdgeFlower(f, es));
	}

	
	public String toString(){
		return Integer.toString(id);
	}
}
