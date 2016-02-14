public class EdgePair {
	Flower f1;
	Flower f2;
	Edge es;

	public EdgePair(Flower inside, Flower outside, Edge es) {
		super();
		this.f1 = inside;
		this.f2 = outside;
		this.es = es;
	}
	
	double getMinForEdgePair() {
		double val=0;
		TNode tn1 = f1.parentTreeNode;
		TNode tn2 = f2.parentTreeNode;
		if ((tn1 instanceof CinkaNode) && (tn2 instanceof TreeNode)) {
			TreeNode ttn2 = (TreeNode) tn2;
			if (ttn2.evenLevel) {
				val=es.edgeVal - f2.getOuterChargeForVertex();
			}
		} else if ((tn2 instanceof CinkaNode) && (tn1 instanceof TreeNode)) {
			TreeNode ttn1 = (TreeNode) tn1;
			if (ttn1.evenLevel) {
				val = es.edgeVal - f1.getOuterChargeForVertex();
			}
		} else if ((tn1 instanceof TreeNode) && (tn2 instanceof TreeNode)) {
			TreeNode ttn1 = (TreeNode) tn1;
			TreeNode ttn2 = (TreeNode) tn2;
			if ((ttn1.evenLevel) && (ttn2.evenLevel)) {
				double chargeIn = f1.getOuterChargeForVertex();
				double chargeOut = f2.getOuterChargeForVertex();
				double diff = es.edgeVal - chargeIn - chargeOut;
				val= diff / 2;
			}
		}
		return val;
	}
	
	boolean naplnilaSaHrana(){
		if ((getMinForEdgePair()==0)&&(es.getState()==State.FREE)){
			return true;
		}else{
			return false;
		}
	}

}