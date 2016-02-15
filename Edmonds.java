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
		while (cinky.size() != g.vertices.size() / 2) {
			double minval = 9999999;
			for (HungarianTree t : trees) {
				minval = Math.min(minval, t.getMinEps());
			}
			for (HungarianTree t : trees) {
				t.updateEps(minval);
			}
			System.out.println(minval);
			for (HungarianTree t : trees) {
				minval = Math.min(minval, t.getMinEps());
			}
			System.out.println(minval);
			ArrayList<Problem> probs = new ArrayList<>();
			for (HungarianTree t : trees) {
				probs.addAll(t.getProblems());
			}
			System.out.println(probs.toString());
			Problem p = probs.get(0);
			if (((p.p == Problem.ProblemType.P2) || (p.p == Problem.ProblemType.P4))
					|| (p.p == Problem.ProblemType.P3)) {
				if (p.p == Problem.ProblemType.P2) {
					System.out.println(p.fInCinka.getParentTreeNode());
				}
				p.fix(trees, cinky);
				for (Cinka c : cinky) {
					System.out.println(c);
				}
				for (Tree t : trees) {
					System.out.println(t);
				}
			} else {
				break;
			}
		}

	}

}
