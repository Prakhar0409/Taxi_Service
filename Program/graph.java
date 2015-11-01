import java.util.*;

public class graph{
//	String graphName;
	int currTime=0;
	ArrayList<graphNodeList> vertices=null;
	ArrayList<String> verNames=null;
	ArrayList<taxi> taxis=null;
	PriorityQueue<service> pq=new PriorityQueue<>(1000);
	PriorityQueue<Integer> taxiPos=new PriorityQueue<>(1000);
	int allTaxifree=0;
//start time to start the cab service business
	public void startTime(){
	//	System.out.println("All Taxis are free after 0 units of time and  current time is: 0");
		while(pq.isEmpty()==false || allTaxifree>0 || taxiPos.isEmpty()==false){
			update();
	//		System.out.println("All Taxis are free after "+allTaxifree+" units of time and current time is: "+currTime);
			
		}
	}

//update ---> every call of this function increases time by 1 unit
	public void update(){
		Iterator it=taxis.iterator();
		while(it.hasNext()){
			taxi tmpTaxi=(taxi)it.next();
			if(tmpTaxi.freeIn>0){tmpTaxi.freeIn--;}
		}
		if(allTaxifree>0){allTaxifree--;}
		
		
		boolean found=false;
		int t=0;
		while(taxiPos.isEmpty()==false && (taxiPos.peek())<=currTime){
			found=true;
			t=taxiPos.poll();
		}
		if(found){
			printTaxisAtTime(t);
		}


		boolean serviced=false;
		while(pq.isEmpty()==false && (pq.peek()).time<=currTime){
			service tmp=pq.poll();	
			serviced=serviceCustomer(tmp.src,tmp.dest,tmp.time);
			if(serviced==false){	//customer wasnt serviced because of unavailability of cabs
				service newService=new service(tmp.src,tmp.dest,tmp.time+1);	//try again at next instance
				pq.add(newService);
			}
		}
		currTime++;
	}
//add Service
	public void addService(String src,String dest,int time){
		if(time<currTime || time<=0){
			System.out.println("We do not have time machine to serve you in the past or at time t=0. (-_-) Kindly refrain from such nasty inputs.");
			return;
		}
		service tmp=new service(src,dest,time);
		pq.add(tmp);
	}

//add taxi
	public void addTaxi(String name,String position){
		if(taxis==null){taxis=new ArrayList<taxi>();}
		taxi tmpTaxi=new taxi(name,position);
		taxis.add(tmpTaxi);
	}	

//add print taxi at time
	public void addPrintTaxiAtTime(int t){
		taxiPos.add(t);
	}	

//print all taxis
	public void printTaxis(){
		Iterator it=taxis.iterator();
		while(it.hasNext()){
			taxi tmp=(taxi)it.next();
			System.out.println("taxi "+tmp.name+" is at "+tmp.position+" and can be made available in "+tmp.freeIn+" time units.");
		}
	}

//print available taxis at a given time
	public void printTaxisAtTime(int t){
		System.out.println("Executing the printTaxiPosition "+t);
		if(currTime==t){
			Iterator it=taxis.iterator();
			while(it.hasNext()){
				taxi tmp=(taxi)it.next();
				if(tmp.freeIn==0){
					System.out.println(tmp.name+": "+tmp.position);
				}
			}
		}
		System.out.println();
	}


//serviceing a customer
	public boolean serviceCustomer(String src,String dest,int atTime){
		System.out.println("Executing customer "+src+" "+dest+" "+atTime);
		if(verNames.contains(src)==false || verNames.contains(dest)==false){
			System.out.println("You have entered an invalid address. Kindly reenter the correct one.");
			System.out.println();
			return true;
		}
		System.out.println("Available taxis:");
		Iterator it=taxis.iterator();
		taxi appointedTaxi=null;
		int busyTime=0;
		int min=99999999;
		while(it.hasNext()){
			taxi tmp=(taxi)it.next();
			
			if(tmp.freeIn==0){		//currTime+freeIn < t for the cab to be available to that customer
				System.out.print("Path of "+tmp.name+": ");
				busyTime=shortestPath(tmp.position,src);
				if(min>busyTime){
					min=busyTime;
					appointedTaxi=tmp;
				}
			}
		}
		if(appointedTaxi!=null){
				System.out.println("**Chose "+appointedTaxi.name+" to service the customer request ***");
				System.out.print("Path of customer: ");
				min+=shortestPath(src,dest);
				appointedTaxi.freeIn=min;
				appointedTaxi.position=dest;
				if(min>allTaxifree){allTaxifree=min;}
				System.out.println();
				return true;	//customer serviced
		}else{
			System.out.println("Sorry no taxis are available. Kindly wait for a few time units.");
			System.out.println();
			return false;	//customer to be serviced at the next very instance.
		}
	}

//add an edge to a graph
	public void addEdge(String a,String b,int cost){
		if(vertices==null){
			vertices=new ArrayList<graphNodeList>();
			verNames=new ArrayList<String>();
		}

		Iterator it=vertices.iterator();
		boolean found1=false,found2=false;
		graphNode tmp1=new graphNode(b,cost);
		graphNode tmp2=new graphNode(a,cost);
		while(it.hasNext()){
			graphNodeList gnl=(graphNodeList)it.next();
			if(found1==false && gnl.equals(a)){
				found1=true;
				gnl.insert(tmp1);
			}
			if(found2==false && gnl.equals(b)){
				found2=true;
				gnl.insert(tmp2);
			}
		}
		if(found1==false){
			graphNodeList newVertex=new graphNodeList(a);
			newVertex.insert(tmp1);
			vertices.add(newVertex);
			verNames.add(a);
		}
		if(found2==false){
			graphNodeList newVertex=new graphNodeList(b);
			newVertex.insert(tmp2);
			vertices.add(newVertex);
			verNames.add(b);
		}

	}

//shortest path from src to dest
	public int shortestPath(String dest,String src){
		if(src.equals(dest)){
			System.out.println(src+". time taken is "+0+" units.");
			return 0;
		}else if(verNames.contains(src)==false || verNames.contains(dest)==false){
			System.out.println("Sorry the vertex you specified does not exist!");
			return -1;	//exception handled
		}
		int[] arr=new int[vertices.size()];
		boolean[] span=new boolean[vertices.size()];
		int[] prev=new int[verNames.size()];
		int previ=0;
		for(int i=0;i<vertices.size();i++){
			arr[i]=9999999;
			span[i]=false;
		}
		int srci=verNames.indexOf(src);
		int desti=verNames.indexOf(dest);
	//	System.out.println("Index of src:"+src+" - "+srci);
	//	System.out.println("Index of dest:"+dest+" - "+desti);
		arr[srci]=0;
		prev[srci]=0;
		int x=0;
		while(x<verNames.size()){
			//find minimum in arr[]
			int min=0;
			for(int i=0;i<verNames.size();i++){
				if(span[min]==true || (span[i]==false && arr[i]<arr[min])){
					min=i;
				}
			}
			//System.out.println("min index in x:"+x+" is "+min);
			span[min]=true;
			if(span[desti]==true){
				//String path=new String[];
			//	System.out.println("Shortest path is as follows:");
				System.out.print((String)verNames.get(desti)+", ");
				int y=desti;
				while(prev[y]!=srci){
					System.out.print((String)verNames.get(prev[desti])+", ");
					y=prev[y];
				}
				System.out.print(verNames.get(srci));
				System.out.println(". time taken is "+arr[desti]+" units.");
				return arr[desti];
			}

			graphNodeList curr=vertices.get(min);
			graphNode travGraphNode=curr.head;
			int tmp;
			while(travGraphNode!=null){
				tmp=verNames.indexOf(travGraphNode.name);
				if(arr[tmp]>(arr[min]+travGraphNode.cost)){
					arr[tmp]=arr[min]+travGraphNode.cost;
					prev[tmp]=min;
				}
				travGraphNode=travGraphNode.next;
			}
			// for(int i=0;i<vertices.size();i++){
			// 	System.out.print(arr[i]+"       ");
			// }System.out.println();
			// for(int i=0;i<vertices.size();i++){
			// 	System.out.print(span[i]+"    ");
			// }System.out.println();
			x++;
		}
		System.out.println("No such path exists. The places are not connected. Maybe you should try flying! -_-");
		return -1;		//no path exists........no connectivity

	}	

//print graph
	public void printGraph(){
		Iterator it=vertices.iterator();
		while(it.hasNext()){
			graphNodeList gnl=(graphNodeList)it.next();
			gnl.printNodeList();
		}
	}	
}