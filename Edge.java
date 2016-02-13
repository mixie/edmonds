
public class Edge {
	int edgeVal;
	Graph g;
	final Flower f1, f2;
	
	private State state;
	
	public Edge(int edgeVal, State state,Graph g,Flower f1,Flower f2) {
		super();
		this.edgeVal = edgeVal;
		this.state = state; 
		this.g=g;
		this.f1=f1;
		this.f2=f2;
	}
	
	public void setState(State state) {
		
		if (this.state==State.L){
			g.lEdges.remove(this);
		}
		if(this.state==State.M){
			g.mEdges.remove(this);
		}
		if(state==State.M){
			g.mEdges.add(this);
		}
		if(state==State.L){
			g.lEdges.add(this);
		}
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
	
	int getMinForEdge() {
		return edgeVal - f1.getOuterChargeForVertex()
				- f2.getOuterChargeForVertex();
	}
	
}

enum State {
    FREE,M,L
}
