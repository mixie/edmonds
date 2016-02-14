import java.util.ArrayList;

public class TreeNode extends TNode {
	TreeNode parent;
	Edge edgeToParent;
	ArrayList<TreeNode> children;
	Flower node;
	HungarianTree parentTree;
	boolean evenLevel;

	void setParentTree(HungarianTree parentTree) {
		this.parentTree = parentTree;
	}

	public TreeNode(TreeNode parent, Flower node, Edge edgeToParent) {
		super();
		this.parent = parent;
		if (parent == null) {
			evenLevel = true;
		} else {
			evenLevel = !parent.evenLevel;
			parent.children.add(this);
		}
		this.node = node;
		node.setParentTreeNode(this);
		children = new ArrayList<>();
		this.edgeToParent = edgeToParent;
	}

	double getMinEps() {
		double min;
		if (evenLevel) {
			double minval = node.getMinEpsForFlower();
			for (TreeNode e : children) {
				double actval = e.getMinEps();
				if (actval < minval) {
					minval = actval;
				}
			}
			min = minval;
		} else {
			if (node instanceof VertexFlower) {
				min = 999999999;
			} else {
				min = node.getCharge();
			}
		}
		for (TreeNode child : children) {
			min = Math.min(min, child.getMinEps());
		}
		return min;
	}

	public void updateEps(double minval) {
		if (evenLevel) {
			node.increaseCharge(minval);
		} else {
			node.increaseCharge(-minval);
		}
	}

	public ArrayList<Problem> getProblems() {
		ArrayList<Problem> a = new ArrayList<Problem>();
		if (evenLevel) {
			a.addAll(node.getProblems());
		} else {
			if (node.getCharge() == 0) {
				a.add(new Problem(node));
			}
		}
		return a;
	}

	public TreeNode getRoot() {
		if (parent == null) {
			return this;
		} else {
			return parent.getRoot();
		}
	}

	public ArrayList<Edge> getAlternatingPathFrom(VertexFlower f) {
		ArrayList<Edge> edges = new ArrayList<>();
		Flower outerFlower = f.getOuterFlower();
		if (this.parent == null) {
			edges.addAll(f.getAlternatingRouteForFlower(node.stonka, f));
		} else {
			if (edgeToParent.f1.getOuterFlower() == this.node) {
				edges.addAll(outerFlower.getAlternatingRouteForFlower(f,
						edgeToParent.f2));
			} else {
				edges.addAll(outerFlower.getAlternatingRouteForFlower(f,
						edgeToParent.f1));
			}
			edges.add(edgeToParent);
		}
		return edges;

	}

	public ArrayList<Cinka> vyrobCinky(boolean napojenaNaDruhyStrom,
			ArrayList<TreeNode> routeUp) {
		ArrayList<Cinka> c = new ArrayList<>();
		System.out.println("this"+this);
		for (TreeNode child : children) {
			if (!routeUp.contains(child)) { // tie co nie su na ceste hore,
											// rovno z nich tvorim cinky
				child.edgeToParent.setState(State.FREE);
				System.out.println(child.toString());
				Flower f2 = child.children.get(0).node;
				c.add(new Cinka(new CinkaNode(child.node), new CinkaNode(f2),
						child.children.get(0).edgeToParent));
			}

		}
		if (!napojenaNaDruhyStrom) {
			TreeNode up2 = this.parent.parent;
			if(up2.parent!=null){
				up2.edgeToParent.setState(State.FREE);
			}
			TreeNode up1 = this.parent;
			routeUp.add(this);
			routeUp.add(up1);
			for (TreeNode child : up1.children) {
				if (!routeUp.contains(child)) { // tie co nie su na ceste hore,
												// rovno z nich tvorim cinky
					child.edgeToParent.setState(State.FREE);
					c.add(new Cinka(new CinkaNode(child.node), new CinkaNode(
							child.children.get(0).node),
							child.children.get(0).edgeToParent));
				}

			}
			c.add(new Cinka(new CinkaNode(this.node), new CinkaNode(up1.node),
					edgeToParent));
			if (up2 != null) {
				c.addAll(up2.vyrobCinky(false, routeUp));
			}
		} else {
			if (parent != null) {
				System.out.println("ajksljsljls");
				routeUp.add(this);
				this.edgeToParent.setState(State.FREE);
				c.addAll(parent.vyrobCinky(false, routeUp));
			}
		}
		return c;
	}

	public String toString() {
		String s = node.toString()+evenLevel;
		s=s+"->";
		for (TreeNode tn : children) {
			s = s + "*" + tn.toString();
		}
		s=s+"<-";
		return s;
	}
}
