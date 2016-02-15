
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
		if(this.state==State.M){
			g.numberOfMs--;
			g.sumOfMs-=edgeVal;
		}
		if(state==State.M){
			g.numberOfMs++;
			g.sumOfMs+=edgeVal;
		}
		this.state = state;
	}
	
	public State getState(){
		return state;
	}
	
	double getMinForEdge() {
		return edgeVal - f1.getOuterChargeForVertex()
				- f2.getOuterChargeForVertex();
	}
	
	public void reverseState() throws Exception{
		if (state==State.M){
			state=State.L;
		}else if (state==State.L){
			state=State.M;
		}else{
			throw new Exception("Free edge v alternujucej ceste");
		}
		
	}
	
}

enum State {
    FREE,M,L
}
