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
		node.setParentTreeNode(this);
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
			if(node instanceof VertexFlower){
				min = 999999999;
			}else{
				min = node.getCharge();
			}
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

	public ArrayList<Problem> getProblems() {
		ArrayList<Problem> a=new ArrayList<Problem>();
		if(evenLevel){
			a.addAll(node.getProblems());
		}else{
			if (node.getCharge()==0){
				a.add(new Problem(node));
			}
		}
		return a;
	}
	
	public TreeNode getRoot(){
		if(parent==null){
			return this;
		}else{
			return parent.node.getRoot();
		}
	}
}
