import java.util.ArrayList;
import java.util.HashSet;

public class TreeNode extends TNode {
	TreeNode parent;
	Edge edgeToParent;
	private ArrayList<TreeNode> children;
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
	
	

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
		for(TreeNode child:children){
			child.parent=this;
		}
	}
	
	public void addChild(TreeNode child){
		children.add(child);
	}
	
	public void removeChild(TreeNode child){
		children.remove(child);
		child.parent=null;
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
		for (TreeNode child : children) {
			child.updateEps(minval);
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
			if(parent!=null){
				if (edgeToParent.f1.getOuterFlower() == this.node) {
					edges.addAll(outerFlower.getAlternatingRouteForFlower(f,
							edgeToParent.f1));
					edges.addAll(parent.getAlternatingPathFrom(edgeToParent.f2));
				} else {
					edges.addAll(outerFlower.getAlternatingRouteForFlower(f,
							edgeToParent.f2));
					edges.addAll(parent.getAlternatingPathFrom(edgeToParent.f1));
				}
				edges.add(edgeToParent);
			}else{
				edges.addAll(outerFlower.getAlternatingRouteForFlower(f, outerFlower.stonka));
			}
		return edges;
	}

	public ArrayList<Cinka> vyrobCinky(boolean napojenaNaDruhyStrom,
			ArrayList<TreeNode> routeUp) {
		ArrayList<Cinka> c = new ArrayList<>();
		for (TreeNode child : children) {
			if (!routeUp.contains(child)) { // tie co nie su na ceste hore,
											// rovno z nich tvorim cinky
				child.edgeToParent.setState(State.FREE);
				Flower f2 = child.children.get(0).node;
				c.add(new Cinka(new CinkaNode(child.node), new CinkaNode(f2),
						child.children.get(0).edgeToParent));
			}
		}
		if (!napojenaNaDruhyStrom) {
			TreeNode up2 = this.parent.parent;
			if (up2.parent != null) {
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
				routeUp.add(this);
				this.edgeToParent.setState(State.FREE);
				c.addAll(parent.vyrobCinky(false, routeUp));
			}
		}
		return c;
	}

	public String toString() {
		String s = node.toString() + evenLevel;
		s = s + "->";
		for (TreeNode tn : children) {
			s = s + "*" + tn.toString();
		}
		s = s + "<-";
		return s;
	}

	public ArrayList<TreeNode> getRouteToRoot() {
		ArrayList<TreeNode> route = new ArrayList<>();
		route.add(this);
		if (parent != null) {
			route.addAll(parent.getRouteToRoot());
		}
		return route;
	}

	public ArrayList<TreeNode> getChildrenOnWayToParentNode(TreeNode tnspol,
			ArrayList<TreeNode> routeUp) {
		ArrayList<TreeNode> childrenNodes = new ArrayList<>();
		for (TreeNode tn : children) {
			if (!routeUp.contains(tn)) {
				childrenNodes.add(tn);
			}
		}
		routeUp.add(this);
		if (this != tnspol) {
			childrenNodes.addAll(parent.getChildrenOnWayToParentNode(tnspol,
					routeUp));
		}
		return childrenNodes;
	}

	public HashSet<Flower> setNeighborsInFlower(ArrayList<TreeNode> children2, // setne aj
																	// stonku
			TreeNode tnspol) {
		HashSet<Flower> flowers=new HashSet<Flower>();
		flowers.add(this.node);
		if (this == tnspol) {
			ArrayList<TreeNode> childrenFlowers = new ArrayList<>();
			for (TreeNode child : children) {
				if (!children2.contains(child)) {
					childrenFlowers.add(child);
				}
			}
			node.edge1 = new EdgeFlower(
					childrenFlowers.get(0).node.getOuterFlower(),
					childrenFlowers.get(0).edgeToParent);
			node.edge2 = new EdgeFlower(
					childrenFlowers.get(1).node.getOuterFlower(),
					childrenFlowers.get(1).edgeToParent);
		} else {
			node.edge1 = new EdgeFlower(parent.node.getOuterFlower(),
					edgeToParent);
			for (TreeNode child : children) {
				if (!children2.contains(child)) {
					node.edge2 = new EdgeFlower(child.node.getOuterFlower(),
							child.edgeToParent);
				}
			}
			node.stonka = tnspol.node.stonka;
			flowers.addAll(parent.setNeighborsInFlower(children2, tnspol));
		}
		return flowers;
	}

}
