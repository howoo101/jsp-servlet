import java.util.Arrays;
import java.util.StringTokenizer;

public class aa {
	public static void main(String[] args) {
		StringTokenizer a = new StringTokenizer("/Session/board.jsp","/");
		
		System.out.println(a.nextToken());
		System.out.println(a.nextToken());
	}
}
