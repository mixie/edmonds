public class EdgePair {
	VertexFlower f1;
	VertexFlower f2;
	Edge es;

	public EdgePair(VertexFlower inside, VertexFlower outside, Edge es) {
		super();
		this.f1 = inside;
		this.f2 = outside;
		this.es = es;
	}

	double getMinForEdgePair() {
		double val = 999999999;
		TNode tn1 = f1.getParentTreeNode();
		TNode tn2 = f2.getParentTreeNode();
		if ((tn1 instanceof CinkaNode) && (tn2 instanceof TreeNode)) {
			TreeNode ttn2 = (TreeNode) tn2;
			if (ttn2.evenLevel) {
				val = es.edgeVal - f2.getOuterChargeForVertex()-f1.getOuterChargeForVertex();
			}
		} else if ((tn2 instanceof CinkaNode) && (tn1 instanceof TreeNode)) {
			TreeNode ttn1 = (TreeNode) tn1;
			if (ttn1.evenLevel) {
				val = es.edgeVal - f1.getOuterChargeForVertex() - f2.getOuterChargeForVertex();
			}
		} else if ((tn1 instanceof TreeNode) && (tn2 instanceof TreeNode)) {
			TreeNode ttn1 = (TreeNode) tn1;
			TreeNode ttn2 = (TreeNode) tn2;
			if ((ttn1.evenLevel) && (ttn2.evenLevel)) {
				double chargeIn = f1.getOuterChargeForVertex();
				double chargeOut = f2.getOuterChargeForVertex();
				double diff = es.edgeVal - chargeIn - chargeOut;
				val = diff / 2;
			}
		}
		return val;
	}

	Problem getProblem() {
		if ((getMinForEdgePair() == 0) && (es.getState() == State.FREE)) {
			TNode tn1 = f1.getParentTreeNode();
			TNode tn2 = f2.getParentTreeNode();
			if ((tn1 instanceof TreeNode) && (tn2 instanceof TreeNode)) {
				TreeNode ttn2 = (TreeNode) tn2;
				TreeNode ttn1 = (TreeNode) tn1;
				if ((ttn1.evenLevel) && (ttn2.evenLevel)) {
					if (ttn1.getRoot() == ttn2.getRoot()) {
						return new Problem(Problem.ProblemType.P3, this);
					} else {
						return new Problem(Problem.ProblemType.P4, this);
					}
				}
				return null;
			} else if ((tn1 instanceof TreeNode) && ((TreeNode) tn1).evenLevel) {
				return new Problem(this, f1, f2);
			} else {
				return new Problem(this, f2, f1);
			}
		} else {
			return null;
		}
	}
}