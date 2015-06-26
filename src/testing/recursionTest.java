package testing;

public class recursionTest {
	public static void main(String[] args) {
		
		Path a = new Path("A");
		Path b = new Path("B");
		Path c = new Path("C");
		
		ZeroOrOnePath b1 = new ZeroOrOnePath(b);

		ZeroOrMorePath c1 = new ZeroOrMorePath(c);
		 
		AltPath pa = new AltPath(b1,c1);

		SeqPath p = new SeqPath(new GroupPath(pa),a);
		System.out.println(parsePath(p));
	}
	
	public static String parsePath(Path p){
		if(p instanceof AltPath){
			p.value = parsePath(p.p1) +"|"+ parsePath(p.p2);
		}
		else if(p instanceof SeqPath){
			p.value = parsePath(p.p1) +"/"+ parsePath(p.p2);
		}		
		else if(p instanceof ZeroOrMorePath){
			p.value = parsePath(p.p1) +"*";
		}
		else if(p instanceof ZeroOrOnePath){
			p.value = parsePath(p.p1) +"?";
		}
		else if(p instanceof OneOrMorePath){
			p.value = parsePath(p.p1) +"+";
		}
		else if(p instanceof GroupPath){
			p.value = "("+parsePath(p.p1) +")";
		}
		
		return p.value;
	}
	
	public static class Path {
		public Path p1;
		public Path p2;
		
		public String value;

		public Path(Path p1,Path p2){
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public Path(Path p1){
			this.p1 = p1;
			this.value = p1.value;
		}
		

		public Path(String value){
			this.value = value;
		}
	}
	
	public static class AltPath extends Path {

		public AltPath(Path p1,Path p2){
			super(p1, p2);
			//this.value = p1.value +"|"+ p2.value;
		}
		
	}
	
	public static class SeqPath extends Path {

		public SeqPath(Path p1,Path p2){
			super(p1, p2);
			//this.value = p1.value +"/"+ p2.value;
		}
		
	}
	
	public static class ZeroOrMorePath extends Path {
		
		public ZeroOrMorePath(Path p1){
			super(p1);
//			super(value+"*");
		}
	}
	
	public static class OneOrMorePath extends Path {
		
		public OneOrMorePath(Path p1){
			super(p1);
//			super(value+"+");
		}
	}
	
	public static class ZeroOrOnePath extends Path {
		
		public ZeroOrOnePath(Path p1){
			super(p1);
//			super(value+"?");
		}
	}
	
	public static class GroupPath extends Path {

		public GroupPath(Path p1){
			super(p1);
//			this.value = "("+p1.value+")";
		}
		
	}
}
