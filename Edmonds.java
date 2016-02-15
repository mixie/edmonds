import java.util.ArrayList;

public class Edmonds {

	public static void main(String[] args) throws Exception {
		Graph g = new Graph();
		g.nacitaj();
		ArrayList<HungarianTree> trees = new ArrayList<HungarianTree>();
		ArrayList<Cinka> cinky = new ArrayList<>();
		for (Flower f : g.vertices.values()) {
			trees.add(new HungarianTree(new TreeNode(null, f, null)));
		}
		while (g.numberOfMs!=g.vertices.size()/2) {
			double minval = 9999999;
			for (HungarianTree t : trees) {
				minval = Math.min(minval, t.getMinEps());
			}
			for (HungarianTree t : trees) {
				t.updateEps(minval);
			}
			ArrayList<Problem> probs = new ArrayList<>();
			for (HungarianTree t : trees) {
				probs.addAll(t.getProblems());
			}
			Problem p = probs.get(0);
			if (((p.p == Problem.ProblemType.P2) || (p.p == Problem.ProblemType.P4))
					|| (p.p == Problem.ProblemType.P3)) {
				if (p.p == Problem.ProblemType.P2) {
				}
				p.fix(trees, cinky);
				
			} else {
				break;
			}
		}
		int sum=0;
		for(Edge e:g.allEdges){
			if(e.getState()==State.M){
				sum+=e.edgeVal;	
			}
		}
		System.out.println(sum);
		for(Edge e:g.allEdges){
			if(e.getState()==State.M){
				System.out.println(e.f1.id+" "+e.f2.id);
			}
		}

	}

}
