abstract class Tree {

}

public class Cinka extends Tree{
	Flower f1,f2;
	Edge es;

	public Cinka(Flower f1, Flower f2,Edge es){
		super();
		this.f1 = f1;
		f1.parentTree=this;
		this.f2 = f2;
		f2.parentTree=this;
		this.es=es;
		this.es.setState(State.M);
	}
	
}