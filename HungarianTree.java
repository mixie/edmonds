
public class HungarianTree extends Tree{
	TreeNode root;

	public HungarianTree(TreeNode root) {
		super();
		this.root = root;
		root.setParentTree(this);
	}
	
	public Edge getMinOuterEdge(){
		return root.getMinOuterEdge();
	}
	
}
