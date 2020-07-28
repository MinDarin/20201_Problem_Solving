import java.io.FileNotFoundException; //12���� ���� 2015115907 ��μ�(���� Ȯ�� �Ϸ�)
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
	static int OVER = -1000001;
	static int N = 0;
	static int W = 0;
	static int[] wi;
	static int[] vi;
	static int[][] DT;
	public static void main(String[] args) 
	{
		FileReader fp = null;
		try {
			fp = new FileReader("input.txt");
			Scanner sc = new Scanner(fp);
//			Scanner sc = new Scanner(new InputStreamReader(System.in));

			N = sc.nextInt();
			W = sc.nextInt();

			wi = new int[N];
			vi = new int[N];
			DT = new int[N][W+1];
			for(int i = 0 ; i < N ; i++)
			{	
				wi[i] = sc.nextInt();
				vi[i] = sc.nextInt();
			}
		}
		catch (Exception e) { } 
		finally {if(fp != null) try {	fp.close(); } catch (IOException e) {} }
		System.out.println(f(0,0));
	}
	static int f(int n, int w)
	{
		if( w > W) return OVER; //������ ��ġ�� ����
		if(n==N) return 0;
		if(DT[n][w] == 0)
		{
		int n0 = f(n+1,w);  // n��° ������ �ȳִ� ���
		int n1 = vi[n] + f(n+1,w+wi[n]); // n��° ������ �ִ� ���
		return DT[n][w] = Math.max(n0, n1);
		}
		return DT[n][w];
	}
}