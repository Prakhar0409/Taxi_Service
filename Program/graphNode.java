import java.util.*;

public class graphNode{
	String name;
	int cost;
	graphNode next;

//Constructor methods	
	public graphNode(String n, int c){
		this.name=n;
		cost=c;
		this.next=null;
	}
	
	public graphNode(String n, int c, graphNode ne){
		this.name=n;
		this.cost=c;
		this.next=ne;
	}

//Equality testing
	public boolean equals(graphNode ne){
		return (this.name.equals(ne.name));
	}

}