import java.io.FileReader; //13주차 과제 상향식 2015115907 김민석 (백준 확인 완료, 수정완료)
import java.io.InputStreamReader;
import java.util.Scanner;

public class problem2 {
	static int N = 0;
	static int M = 0;
	static int[] m;
	static int[] c;
	static int V;
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
			for(int i = 1; i<=N ; i++)
				m[i] = sc.nextInt();
			int temp = 0;
			for(int i = 1; i<=N ; i++)
			{
				 temp = sc.nextInt();
				 c[i] = temp;
				 V+=temp;
			}	
			DT= new int[N+1][V+1];
			for(int i = 1 ; i<=N; i++)
			{
				for(int j = 0; j <= V; j++)
				{
					if(j >= c[i]) DT[i][j] =  Math.max(DT[i-1][j],DT[i-1][j-c[i]]+m[i]);
					else DT[i][j] = DT[i-1][j];
				}
			}
			int sol = 0;
			boolean stop = false;
			for(int j = 0; j <=V; j++)
			{
				for(int i = 1; i <= N; i++)
				{
					if(DT[i][j] >= M)
					{
						sol = j;
						stop = true;
						break;						
					}	
				}
				if(stop)
					break;
			}
			System.out.println(sol);
			}catch(Exception e){}
	}
}
