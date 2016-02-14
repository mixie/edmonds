
public class EdgeVertex {
	VertexFlower f;
	Edge es;
	
	public EdgeVertex(VertexFlower f, Edge es) {
		super();
		this.f = f;
		this.es=es;
	}
	
	void setEdgeStatus(State s){
		es.setState(s);
	}
	
}
