import java.util.HashSet;

public class Edmonds {

	public static void main(String[] args) {
		Graph g = new Graph();
		g.nacitaj();
		HashSet<HungarianTree> trees = new HashSet<HungarianTree>();
		HashSet<Cinka> cinky = new HashSet<>();
		for (Flower f : g.vertices.values()) {
			Edge ep = f.getEdgeWithMinEps();
			System.out.println(ep.getMinForEdge());
		}
		for (Flower f : g.vertices.values()) {
			trees.add(new HungarianTree(new TreeNode(null, f)));
		}
		Edge min=null;
		int minval=9999999;
		for (HungarianTree t:trees){
			Edge act=t.getMinOuterEdge();
			int val=act.getMinForEdge();
			if (val<minval){
				min=act;
				minval=val;
			}
		}
		
		
	}
	
	

}
