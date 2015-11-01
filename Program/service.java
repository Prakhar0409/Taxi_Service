import java.util.*;

public class service implements Comparable{
	int time;
	String src;
	String dest;

	public service(String s, String d, int t){
		this.src=s;
		this.dest=d;
		this.time=t;
	}

	public int compareTo(Object o){
		service a=(service) o;
		if(this.time>a.time){
			return 1;
		}else if(this.time<a.time){
			return -1;
		}else{
			return 0;
		}
	}
}