import java.util.ArrayList;
import java.util.Collection;


public class HungarianTree extends Tree{
	TreeNode root;

	public HungarianTree(TreeNode root) {
		super();
		this.root = root;
		root.setParentTree(this);
	}
	
	public double getMinEps(){
		return root.getMinEps();
	}

	public void updateEps(double minval) {
		root.updateEps(minval);
	}

	public ArrayList<Problem> getProblems() {
		return root.getProblems();
	}
	
}
