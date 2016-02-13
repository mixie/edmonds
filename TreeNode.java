import java.util.ArrayList;


public class TreeNode {
	EdgeNode parent;
	ArrayList<EdgeNode> children;
	Flower node;	
	HungarianTree parentTree;
	
	void setParentTree(HungarianTree parentTree){
		this.parentTree=parentTree;
	}

	public TreeNode(EdgeNode parent, Flower node) {
		super();
		this.parent = parent;
		this.node = node;
		children=new ArrayList<>();
	}
	
	Edge getMinOuterEdge(){
		Edge min=node.getEdgeWithMinEps();
		int minval=min.getMinForEdge();
		for(EdgeNode e:children){
			Edge act=e.node.getMinOuterEdge();
			int actval=act.getMinForEdge();
			if(actval<minval){
				minval=actval;
				min=act;
			}
		}
		return min;
	}
	
}
