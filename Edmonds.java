import java.util.HashSet;

public class Edmonds {

	public static void main(String[] args) {
		Graph g = new Graph();
		g.nacitaj();
		HashSet<HungarianTree> trees = new HashSet<HungarianTree>();
		HashSet<Cinka> cinky = new HashSet<>();
		for (Flower f : g.vertices.values()) {
			trees.add(new HungarianTree(new TreeNode(null, f)));
		}
		double minval=9999999;
		for (HungarianTree t:trees){
			minval=Math.min(minval,t.getMinEps());
		}
		System.out.println(minval);
		for (HungarianTree t:trees){
			t.updateEps(minval);
		}
		for (HungarianTree t:trees){
			minval=Math.min(minval,t.getMinEps());
		}
		System.out.println(minval);
		for (HungarianTree t:trees){
			
		}
	}

}
