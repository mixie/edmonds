public class EdgePair {
	Flower inside;
	Flower outside;
	Edge es;

	public EdgePair(Flower inside, Flower outside, Edge es) {
		super();
		this.inside = inside;
		this.outside = outside;
		this.es = es;
	}
	
	int getMinForEdgePair() {
		return es.edgeVal - inside.getOuterChargeForVertex()
				- outside.getOuterChargeForVertex();
	}
}