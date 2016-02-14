abstract class Tree {

}

abstract class TNode{
	
}

public class Cinka extends Tree{
	CinkaNode c1,c2;
	Edge es;

	public Cinka(CinkaNode c1, CinkaNode c2,Edge es){
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.es=es;
		this.es.setState(State.M);
	}
	
}