import java.util.ArrayList;
import java.util.HashSet;


public class Problem {
	static enum ProblemType {
	    P1,P2,P3,P4
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
	
	public Problem(EdgePair ep, Flower fInTree, Flower fInCinka){
		this.p=ProblemType.P2;
		this.ep=ep;
		this.fInTree=fInTree;
		this.fInCinka=fInCinka;
	}
	
	public String toString(){
		return p.toString();
	}
	public void fix(ArrayList<HungarianTree> trees,ArrayList<Cinka> cinky) throws Exception {
		if(ProblemType.P4==p){
			TreeNode tn1= (TreeNode)ep.f1.getParentTreeNode();
			trees.remove(tn1.parentTree);
			TreeNode tn2= (TreeNode)ep.f2.getParentTreeNode();
			trees.remove(tn2.parentTree);
			System.out.println("p4 Spajame:"+tn1+tn2);
			ArrayList<Edge> altpath=new ArrayList<>();
			altpath.addAll(tn1.getAlternatingPathFrom(ep.f1));
			altpath.addAll(tn2.getAlternatingPathFrom(ep.f2));
			for(Edge e:altpath){
				e.reverseState();
			}
			ep.es.setState(State.M);
			Flower spodnaCinka1 = ep.f1.getOuterFlower();
			Flower spodnaCinka2 = ep.f2.getOuterFlower();
			TreeNode cinkaNode1=(TreeNode)spodnaCinka1.getParentTreeNode();
			cinky.addAll(cinkaNode1.vyrobCinky(true,new ArrayList<TreeNode>()));
			TreeNode cinkaNode2=(TreeNode)spodnaCinka2.getParentTreeNode();
			cinky.addAll(cinkaNode2.vyrobCinky(true,new ArrayList<TreeNode>()));
			System.out.println(spodnaCinka1.toString()+spodnaCinka2.toString());
			cinky.add(new Cinka(new CinkaNode(spodnaCinka1),new CinkaNode(spodnaCinka2),ep.es));
		}
		if(ProblemType.P2==p){
			System.out.println("p2 Spajame:"+fInTree+fInCinka);
			ep.es.setState(State.L);
			TreeNode tn=(TreeNode)fInTree.getParentTreeNode();
			System.out.println("Here:"+fInCinka.getParentTreeNode());
			CinkaNode cn1=(CinkaNode)fInCinka.getParentTreeNode();
			Cinka c=cn1.parentCinka;
			CinkaNode cn2;
			if(c.c1!=cn1){
				cn2=c.c1;
			}else{
				cn2=c.c2;
			}
			TreeNode fromCinka1 = new TreeNode(tn, fInCinka.getOuterFlower(), ep.es);
			TreeNode fromCinka2 = new TreeNode(fromCinka1,cn2.getF().getOuterFlower(),c.es);
			cinky.remove(c);
		}
		if(ProblemType.P3==p){
			ep.es.setState(State.L);
			TreeNode tn1=(TreeNode)ep.f1.getOuterFlower().getParentTreeNode();
			TreeNode tn2=(TreeNode)ep.f2.getOuterFlower().getParentTreeNode();
			ArrayList<TreeNode> r1=tn1.getRouteToRoot();
			ArrayList<TreeNode> r2=tn2.getRouteToRoot();
			ArrayList<TreeNode> spolRoute=new ArrayList<TreeNode>();
			spolRoute.addAll(r1);spolRoute.addAll(r2);
			TreeNode tnspol=null;
			for(TreeNode t: r1){
				if (r2.contains(t)){
					tnspol=t;
					break;
				}
			}
			if(tn1==tnspol){
				tn1.addChild(tn2);
			}
			if(tn2==tnspol){
				tn2.addChild(tn1);
			}
			
			ArrayList<TreeNode> children=tn1.getChildrenOnWayToParentNode(tnspol,spolRoute);
			children.addAll(tn2.getChildrenOnWayToParentNode(tnspol,spolRoute));
			HashSet<Flower> flowers=tn1.setNeighborsInFlower(children, tnspol);
			flowers.addAll(tn2.setNeighborsInFlower(children, tnspol));
			Flower outer=new Flower();
			outer.edge1=null;
			outer.edge2=null;
			outer.innerFlowers=new ArrayList<>(flowers);
			for(Flower f:flowers){
				f.parent=outer;
			}
			outer.parent=null;
			outer.stonka=tnspol.node.stonka;
			TreeNode tnNew=new TreeNode(tnspol.parent, outer, tnspol.edgeToParent);
			tnNew.setChildren(children);
			tn1.node.edge2=new EdgeFlower(tn2.node, ep.es);
			tn2.node.edge2=new EdgeFlower(tn1.node, ep.es);
			System.out.println("************8");
			System.out.println(tnspol.parentTree.root);
			System.out.println(tnspol);
			if (tnspol.parentTree.root==tnspol){
				tnspol.parentTree.root=tnNew;
				System.out.println("TNNEW"+tnNew.toString());
			}
			
		}
	}
	
	public Cinka createCinka(Flower f1, Flower f2, Edge e){
		Cinka c=new Cinka(new CinkaNode(f1), new CinkaNode(f2), e);
		return c;
	}
	
}
