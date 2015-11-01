import java.util.*;

public class driverGraph{
	public static void main(String[] args){
		graph g1=new graph();
		g1.addEdge("indiagate","chanakyapuri",30);
		g1.addEdge("indiagate","AIIMS",30);
		g1.addEdge("indiagate","lajpatnagar",50);
		g1.addEdge("AIIMS","chanakyapuri",20);
		g1.addEdge("AIIMS","lajpatnagar",15);
		g1.addEdge("iitmaingate","chanakyapuri",50);
		g1.addEdge("iitmaingate","AIIMS",30);
		g1.addEdge("iitmaingate","lajpatnagar",40);
		g1.printGraph();

		System.out.println("Shortest Path between iitmaingate,lajpatnagar");
		g1.shortestPath("iitmaingate","lajpatnagar");
		System.out.println();
		System.out.println("Shortest Path between iitmaingate,indiagate");
		g1.shortestPath("iitmaingate","indiagate");
		System.out.println();

		System.out.println("adding a few taxis to my company");
		g1.addTaxi("Superman","iitmaingate");
		g1.addTaxi("Batman","indiagate");
		g1.addTaxi("KingKong","chanakyapuri");

		g1.printTaxis();
		System.out.println();

		System.out.println("Customer service testing");
		g1.addService("lajpatnagar","chanakyapuri",5);
		g1.addService("lajpatnagar","chanakyapuri",5);
//		g1.addService("lajpatnagar","chanakyapuri",15);

		System.out.println();


		g1.printTaxis();
		System.out.println();

		System.out.println("Time will be started now!");
		g1.startTime();
		System.out.println();

		g1.printTaxis();
		System.out.println();


	}
}