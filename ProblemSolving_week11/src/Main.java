/*
import java.io.InputStreamReader;		//11ÁÖÂ÷ 2015115907 ±è¹Î¼®
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
	static int r;
	static int n;
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(new InputStreamReader(System.in));
		n = sc.nextInt();
		r = sc.nextInt();
		BigInteger up = new BigInteger("1");
		BigInteger down = new BigInteger("1");

		for(int i = 0; i < r; i++)
		{
			BigInteger temp = new BigInteger(""+(n-i));
			BigInteger sol = up.multiply(temp);
			up = sol;
		}

		
		 for(int i = 1; i <= r; i++) 
		 {
				BigInteger temp = new BigInteger(""+i);
				BigInteger sol = down.multiply(temp);
				down = sol;
		 }	
			BigInteger sol = up.divide(down);
 	
			System.out.println(sol);
	}
}
*/