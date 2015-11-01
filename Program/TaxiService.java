public class TaxiService{
	static graph g1=null;
	public TaxiService() {
		g1=new graph();
	}

	public void performAction(String actionMessage) {
		System.out.println("action to be performed: " + actionMessage);
		//....
		//if(g1==null){g1=new graph();}
		String[] arr=actionMessage.split(" ");

		switch(arr[0]){
			case "edge":{
				this.g1.addEdge((String)(arr[1]),(String)(arr[2]),(int)Integer.parseInt(arr[3]));
				break;
			}
			case "taxi":{
				g1.addTaxi(arr[1],arr[2]);	
				break;
			}
			case "printTaxiPosition":{
				int t=Integer.parseInt(arr[1]);
				g1.addPrintTaxiAtTime(t);
				while(g1.currTime<t){
					g1.update();
				}
				break;
			}
			case "customer":{
				int t=Integer.parseInt(arr[3]);
				g1.addService(arr[1],arr[2],t);
				while(g1.currTime<t){
					g1.update();
				}
				break;
			}
			default:{
				g1.printGraph();
				g1.startTime();
			
				break;
			}
		}
	}
}
