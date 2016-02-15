import java.util.ArrayList;
import java.util.HashSet;

public class Problem {
	static enum ProblemType {
		P1, P2, P3, P4
	}

	ProblemType p;
	EdgePair ep;
	Flower f;
	Flower fInTree;
	Flower fInCinka;

	public Problem(ProblemType p, EdgePair ep) {
		this.p = p;
		this.ep = ep;
	}

	public Problem(Flower f) {
		super();
		this.p = ProblemType.P1;
		this.f = f;
	}

	public Problem(EdgePair ep, Flower fInTree, Flower fInCinka) {
		this.p = ProblemType.P2;
		this.ep = ep;
		this.fInTree = fInTree;
		this.fInCinka = fInCinka;
	}

	public String toString() {
		String s="";
		if (p == ProblemType.P1){
			s=p.toString()+" "+f.toString();
		}else if (p ==ProblemType.P2){
			s=p.toString()+" "+fInCinka+" "+fInTree;
		}else{
			s=p.toString()+" "+ep.f1+" "+ep.f2;
		}
		
		return s;
	}

	public void fix(ArrayList<HungarianTree> trees, ArrayList<Cinka> cinky)
			throws Exception {
		if (ProblemType.P4 == p) {
			TreeNode tn1 = (TreeNode) ep.f1.getParentTreeNode();
			trees.remove(tn1.parentTree);
			TreeNode tn2 = (TreeNode) ep.f2.getParentTreeNode();
			trees.remove(tn2.parentTree);
			ArrayList<Edge> altpath = new ArrayList<>();
			altpath.addAll(tn1.getAlternatingPathFrom(ep.f1));
			altpath.addAll(tn2.getAlternatingPathFrom(ep.f2));
			for (Edge e : altpath) {
				e.reverseState();
			}
			ep.es.setState(State.M);
			Flower spodnaCinka1 = ep.f1.getOuterFlower();
			Flower spodnaCinka2 = ep.f2.getOuterFlower();
			TreeNode cinkaNode1 = (TreeNode) spodnaCinka1.getParentTreeNode();
			cinky.addAll(cinkaNode1.vyrobCinky(true, new ArrayList<TreeNode>()));
			TreeNode cinkaNode2 = (TreeNode) spodnaCinka2.getParentTreeNode();
			cinky.addAll(cinkaNode2.vyrobCinky(true, new ArrayList<TreeNode>()));
	
			cinky.add(new Cinka(new CinkaNode(spodnaCinka1), new CinkaNode(
					spodnaCinka2), ep.es));
		}
		if (ProblemType.P2 == p) {
			ep.es.setState(State.L);
			TreeNode tn = (TreeNode) fInTree.getParentTreeNode();
			CinkaNode cn1 = (CinkaNode) fInCinka.getParentTreeNode();
			Cinka c = cn1.parentCinka;
			CinkaNode cn2;
			if (c.c1 != cn1) {
				cn2 = c.c1;
			} else {
				cn2 = c.c2;
			}
			TreeNode fromCinka1 = new TreeNode(tn, fInCinka.getOuterFlower(),
					ep.es);
			TreeNode fromCinka2 = new TreeNode(fromCinka1, cn2.getF()
					.getOuterFlower(), c.es);
			cinky.remove(c);
		}
		if (ProblemType.P3 == p) {
			ep.es.setState(State.L);
			TreeNode tn1 = (TreeNode) ep.f1.getOuterFlower()
					.getParentTreeNode();
			TreeNode tn2 = (TreeNode) ep.f2.getOuterFlower()
					.getParentTreeNode();
			ArrayList<TreeNode> r1 = tn1.getRouteToRoot();
			ArrayList<TreeNode> r2 = tn2.getRouteToRoot();
			ArrayList<TreeNode> spolRoute = new ArrayList<TreeNode>();
			spolRoute.addAll(r1);
			spolRoute.addAll(r2);
			TreeNode tnspol = null;
			for (TreeNode t : r1) {
				if (r2.contains(t)) {
					tnspol = t;
					break;
				}
			}
			if (tn1 == tnspol) {
				tn1.addChild(tn2);
			}
			if (tn2 == tnspol) {
				tn2.addChild(tn1);
			}

			ArrayList<TreeNode> children = tn1.getChildrenOnWayToParentNode(
					tnspol, spolRoute);
			children.addAll(tn2.getChildrenOnWayToParentNode(tnspol, spolRoute));
			HashSet<Flower> flowers = tn1
					.setNeighborsInFlower(children, tnspol);
			flowers.addAll(tn2.setNeighborsInFlower(children, tnspol));
			Flower outer = new Flower();
			outer.edge1 = null;
			outer.edge2 = null;
			outer.innerFlowers = new ArrayList<>(flowers);
			for (Flower f : flowers) {
				f.parent = outer;
			}
			outer.parent = null;
			outer.stonka = tnspol.node.stonka;
			TreeNode tnNew = new TreeNode(tnspol.parent, outer,
					tnspol.edgeToParent);
			tnNew.setParentTree(tnspol.parentTree);
			tnNew.setChildren(children);
			tn1.node.edge2 = new EdgeFlower(tn2.node, ep.es);
			tn2.node.edge2 = new EdgeFlower(tn1.node, ep.es);
			if (tnspol.parentTree.root == tnspol) {
				tnspol.parentTree.root = tnNew;
			}

		}
		if (ProblemType.P1 == p) {
			TreeNode tn = (TreeNode) f.getParentTreeNode();
			Edge parentEdge = tn.edgeToParent;
			Edge childEdge = tn.getChildren().get(0).edgeToParent;
			VertexFlower vfUp = f.getInnerVertexOfFlowerFromEdge(parentEdge);
			VertexFlower vfDown = f.getInnerVertexOfFlowerFromEdge(childEdge);
			Flower bigUp = vfUp.getFlowerOnLevelForVertex(1);
			Flower bigDown = vfDown.getFlowerOnLevelForVertex(1);
			ArrayList<Flower> path1 = new ArrayList<Flower>();
			ArrayList<Flower> path2 = new ArrayList<Flower>();
			path1.add(bigUp);
			Flower actFlower = bigUp;
			while (actFlower != bigDown) {
				Flower oldActFlower = actFlower;
				actFlower = oldActFlower.edge1.f;
				if (path1.contains(actFlower)) {
					actFlower = oldActFlower.edge2.f;
				}
				path1.add(actFlower);
			}
			path2.add(bigUp);
			while (actFlower != bigDown) {
				Flower oldActFlower = actFlower;
				actFlower = oldActFlower.edge2.f;
				if (path2.contains(actFlower)) {
					actFlower = oldActFlower.edge1.f;
				}
				path2.add(actFlower);
			}
			ArrayList<Flower> odd;
			ArrayList<Flower> even;
			if(path1.size()%2==0){
				even=path1;
				odd=path2;
			}else{
				even=path2;
				odd=path1;
			}
			even.remove(0); even.remove(even.size()-1);
			for(int i=0;i<even.size();i+=2){
				Flower f1=even.get(i);
				Flower f2=even.get(i+1);
				f1.parent=null;f2.parent=null;
				Edge eM1; Edge eFreed1;
				if(f1.edge1.f==f2){
					eM1=f1.edge1.es;
					eFreed1=f1.edge2.es;
				}else{
					eM1=f1.edge2.es;
					eFreed1=f1.edge1.es;
				}
				Edge eFreed2;
				if(f2.edge1.f==f1){
					eFreed2=f2.edge2.es;
				}else{
					eFreed2=f2.edge1.es;
				} 
				eFreed1.setState(State.FREE);eFreed2.setState(State.FREE);
				f1.edge1=null;f2.edge2=null;
				Cinka c=new Cinka(new CinkaNode(f1), new CinkaNode(f2), eM1);
				cinky.add(c);
			}
			ArrayList<TreeNode> newNodes=new ArrayList<TreeNode>();
			for(Flower f:odd){
				if(f==bigUp){
					newNodes.add(new TreeNode(((TreeNode)(this.f.getParentTreeNode())).parent, f, ((TreeNode)(this.f.getParentTreeNode())).edgeToParent));
				}else{
					newNodes.add(new TreeNode(newNodes.get(newNodes.size()-1), f, f.getEdgeToFlower(newNodes.get(newNodes.size()-1).node).es));
				}
			}
			newNodes.get(newNodes.size()-1).addChild(tn.getChildren().get(0));
			tn.getChildren().get(0).parent=newNodes.get(newNodes.size()-1);
			tn.parent.removeChild(tn);
			
		}
	}

	public Cinka createCinka(Flower f1, Flower f2, Edge e) {
		Cinka c = new Cinka(new CinkaNode(f1), new CinkaNode(f2), e);
		return c;
	}

}
