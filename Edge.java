
public class Edge {
	int edgeVal;
	Graph g;
	final VertexFlower f1, f2;
	
	private State state;
	
	public Edge(int edgeVal, State state,Graph g,VertexFlower f1,VertexFlower f2) {
		super();
		this.edgeVal = edgeVal;
		this.state = state; 
		this.g=g;
		this.f1=f1;
		this.f2=f2;
	}
	
	public void setState(State state) {
		
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
	
	double getMinForEdge() {
		return edgeVal - f1.getOuterChargeForVertex()
				- f2.getOuterChargeForVertex();
	}
	
}

enum State {
    FREE,M,L
}
