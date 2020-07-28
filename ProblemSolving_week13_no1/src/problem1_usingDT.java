import java.io.FileNotFoundException; //13ÁÖÂ÷ °úÁ¦ 2015115907 ±è¹Î¼® (DT»ç¿ë)
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class problem1_usingDT {
	static int INF = 1000001;
	static int N = 0;
	static int M = 0;
	static int[] m;
	static int[] c;
	static int[][] DT;
	public static void main(String[] args) 
	{
		FileReader fp = null;
		try {
			fp = new FileReader("input.txt");
			Scanner sc = new Scanner(fp);
//			Scanner sc = new Scanner(new InputStreamReader(System.in));

			N = sc.nextInt();
			M = sc.nextInt();
			m = new int[N+1];
			c = new int[N+1];
			DT = new int[N+1][M+1];
			for(int i = 1; i<=N ; i++)
				{
				m[i] = sc.nextInt();
				}
			for(int i = 1; i<=N ; i++)
				c[i] = sc.nextInt();
			for(int i = 1; i<=N ; i++)
				for(int j = 1; j<=M; j++)
				DT[i][j] = -1;
			System.out.println(f(1,M));
			
		}
		catch(Exception e)
		{

		}
		finally {
			if(fp!=null) { try{fp.close();}catch(Exception e){}}
		}
	}
	static int f(int i, int r)
	{
		if(r <= 0) 
			return 0; 
		else if(i == N+1) return INF;
		else if(DT[i][r] != -1) return DT[i][r];
		else return DT[i][r] = Math.min(f(i+1,r), f(i+1,r-m[i])+c[i]);
	}
}
