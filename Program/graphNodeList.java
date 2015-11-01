import java.util.*;

public class graphNodeList{
	String name;
	graphNode head;

//Constructor methods
	public graphNodeList(String n){
		this.name=n;
		head=null;
	}

	public graphNodeList(String n,graphNode ne){
		this.name=n;
		this.head=ne;
	}

//Equality testing
	public boolean equals(graphNodeList ne){
		return (this.name.equals(ne.name));
	}

	public boolean equals(String ne){
		return (this.name.equals(ne));
	}

//check if such a node already exists
	public graphNode isMember(graphNode ne){
		if(this.head==null){return null;}
		graphNode travgraphNode=this.head;
		while(travgraphNode!=null){
			if(travgraphNode.equals(ne)){
				return travgraphNode;
			}
			travgraphNode=travgraphNode.next;
		}
		return null;
	}	

//insert graphNode
	public void insert(graphNode ne){
		graphNode tmp=null;
		if((tmp=isMember(ne))!=null){
			tmp.cost=ne.cost;
			return;
		}
		tmp=ne;
		tmp.next=head;
		head=tmp;
		return;
	}

//print node list
	public void printNodeList(){
		System.out.println("Adjacency List of vertex "+this.name);
		System.out.print(this.name);
		if(head==null){System.out.println();return;}
		graphNode travgraphNode=this.head;
		while(travgraphNode!=null){
			System.out.print(" --> "+travgraphNode.name+"("+travgraphNode.cost+")");
			travgraphNode=travgraphNode.next;
		}
		System.out.println();
		System.out.println();
	}
}