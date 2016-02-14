import java.util.ArrayList;

public class HungarianTree extends Tree {
	TreeNode root;

	public HungarianTree(TreeNode root) {
		super();
		this.root = root;
		root.setParentTree(this);
	}

	public double getMinEps() {
		return root.getMinEps();
	}

	public void updateEps(double minval) {
		root.updateEps(minval);
	}

	public ArrayList<Problem> getProblems() {
		return root.getProblems();
	}
	
	public ArrayList<Edge> getAlternatingPathFrom(VertexFlower f){
		return getAlternatingPathFrom(f);
	}
	
	public String toString(){
		return "Tree "+root.toString();
	}
}
