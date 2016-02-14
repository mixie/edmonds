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
		this.c1.parentCinka=this;
		this.c2.parentCinka=this;
		this.es=es;
		this.es.setState(State.M);
	}
	
	public String toString(){
		return "Cinka "+" "+c1.getF().toString()+" "+c2.getF().toString();
	}
}