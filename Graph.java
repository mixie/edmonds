import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class Graph {
	int n,m;
	int  [][] edges;
	boolean [][] hasEdges;
	HashMap<Integer,VertexFlower> vertices;
	ArrayList<Edge> allEdges=new ArrayList<>();
	int numberOfMs=0;
	int sumOfMs=0;
	
	void nacitaj(){
	    Scanner sc = new Scanner(System.in);
	    int n,m;
	    n=sc.nextInt();
	    m=sc.nextInt();
		this.n = n;
		this.m = m;
		vertices=new HashMap<>();
		for(int i=0;i<m;i++){
			int start=sc.nextInt();
			int end=sc.nextInt();
			int val=sc.nextInt();
			VertexFlower fStart,fEnd;
			if(!vertices.containsKey(start)){
				fStart=new VertexFlower(start);
				vertices.put(start, fStart);
			}else{
				fStart=vertices.get(start);
			}
			if(!vertices.containsKey(end)){
				fEnd=new VertexFlower(end);
				vertices.put(end, fEnd);
			}else{
				fEnd=vertices.get(end);
			}
			Edge es=new Edge(val, State.FREE,this,fStart,fEnd);
			allEdges.add(es);
			fStart.addEdge(fEnd, es);
			fEnd.addEdge(fStart, es);
		}
		sc.close();
		
	}
}

