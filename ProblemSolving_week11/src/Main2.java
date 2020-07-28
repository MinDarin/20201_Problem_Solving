import java.io.InputStreamReader;	//11주차 3번째 방법 (교재버전), O(n)타임. 2015115907 김민석
import java.math.BigInteger;
import java.util.Scanner;

public class Main2 {
	static BigInteger[] DT;
	static int n;
	static int k;
	public static void main(String[] args)
	{
		BigInteger numerator = new BigInteger("1");
		BigInteger denominator = new BigInteger("1");
		BigInteger denominator2 = new BigInteger("1");

		Scanner sc = new Scanner(new InputStreamReader(System.in));

		n = sc.nextInt();
		k = sc.nextInt();
		DT = new BigInteger[n+1];

		numerator = f(n);	//분자 n!
		denominator = f(k);		//분모 k!	// DT가 차있기에 O(1)
		denominator2 = f(n-k);		//분모 (n-k)!    // DT가 차있기에 O(1)
		System.out.println( numerator.divide( (denominator.multiply(denominator2))));

	}
	static BigInteger f(int n)
	{
		if(n==1) return new BigInteger("1");
		if(DT[n] == null) 
		{
			DT[n] = new BigInteger("0");
			DT[n] = f(n-1).multiply(new BigInteger(""+n));
		}
		return DT[n];
	}
}
