import java.io.InputStreamReader;	//11���� 3��° ��� (�������), O(n)Ÿ��. 2015115907 ��μ�
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

		numerator = f(n);	//���� n!
		denominator = f(k);		//�и� k!	// DT�� ���ֱ⿡ O(1)
		denominator2 = f(n-k);		//�и� (n-k)!    // DT�� ���ֱ⿡ O(1)
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
