

public class EdgeFlower {
	Flower f;
	Edge es;
	
	public EdgeFlower(Flower f, Edge es) {
		super();
		this.f = f;
		this.es=es;
	}
	
	void setEdgeStatus(State s){
		es.setState(s);
	}
	
}
