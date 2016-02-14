import java.util.ArrayList;


public class TreeNode extends TNode{
	EdgeNode parent;
	ArrayList<EdgeNode> children;
	Flower node;	
	HungarianTree parentTree;
	boolean evenLevel;
	
	void setParentTree(HungarianTree parentTree){
		this.parentTree=parentTree;
	}

	public TreeNode(EdgeNode parent, Flower node) {
		super();
		this.parent = parent;
		if(parent==null){
			evenLevel=true;
		}else{
			evenLevel=!parent.node.evenLevel;
		}
		this.node = node;
		node.parentTreeNode=this;
		children=new ArrayList<>();
	}
	
	double getMinEps(){
		double min;
		if(evenLevel){
			double minval=node.getMinEpsForFlower();
			for(EdgeNode e:children){
				double actval=e.node.getMinEps();
				if(actval<minval){
					minval=actval;
				}
			}
			min= minval;
		}else{
			min= node.getCharge();
		}
		for (EdgeNode child:children){
			min=Math.min(min, child.node.getMinEps());
		}
		return min;
	}

	public void updateEps(double minval) {
		if(evenLevel){
			node.increaseCharge(minval);
		}else{
			node.increaseCharge(-minval);
		}
	}
}
