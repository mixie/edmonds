

public class EdgeFlower {
	VertexFlower f;
	Edge es;
	
	public EdgeFlower(VertexFlower f, Edge es) {
		super();
		this.f = f;
		this.es=es;
	}
	
	void setEdgeStatus(State s){
		es.setState(s);
	}
	
}
