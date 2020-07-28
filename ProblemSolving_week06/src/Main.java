import java.io.BufferedReader; //6주차 2015115907 김민석
import java.io.FileReader;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Read read = new Read();
		read._read();
		Run run = new Run();
		node[] P = read.get_P();
		int find =run.PointWrapping(P,read.get_N());
		System.out.println(find);
		for(int i = 0 ; i < find; i++)
		{
			System.out.println(P[i].x + " "+ P[i].y);
		}
	}
}
class Run
{
	public int PointWrapping(node[] P, int n)
	{
		int i, NumVertex = 0;
		float MinAngle, MaxAngle, Angle;
		int FirstPoint = 0;
		for(i = 1; i < n; i++)
		{
			if(P[i].y < P[FirstPoint].y)
				FirstPoint = i;
		}
		NumVertex = -1;
		P[n] = P[FirstPoint];
		MaxAngle = 0.0f;
		int NextPoint = FirstPoint;
		
		do {
			NumVertex++;
			
			{			//swap
				node temp = P[NumVertex];
				P[NumVertex] = P[NextPoint];
				P[NextPoint] = temp;
			}
			
			NextPoint = n;
			MinAngle = MaxAngle;
			MaxAngle = 360.0f;
			for(i = NumVertex+1; i <=n; i++)
			{
				Angle = this.ComputeAngle(P[NumVertex],P[i]);
				if(Angle > MinAngle && Angle < MaxAngle)
				{
					NextPoint = i;
					MaxAngle = Angle;
				}
			}
		}while(NextPoint != n);
		return ++NumVertex;
	}
	
	
	public float ComputeAngle(node A, node B)
	{
		int Dx, Dy;
		float Angle = 0.0f;
		Dx = B.x - A.x;
		Dy = B.y - A.y;
		if ((Dx >=0) && (Dy == 0)) Angle = 0; //반직선 위에 점
		else {
			Angle = (float)Math.abs(Dy)/ (Math.abs(Dx) + Math.abs(Dy));
			if((Dx < 0) && (Dy >= 0)) Angle = 2 - Angle;
			else if((Dx <= 0)&& (Dy < 0)) Angle = 2 + Angle;
			else if((Dx > 0) && (Dy < 0)) Angle = 4 - Angle;
		}
		return Angle * 90.0f;
	}

}
class Read
{
	int N;
	node[] P;
	public void _read()
	{
		FileReader fr = null;
		Scanner sc= null;
		try {
			fr = new FileReader("input.txt");
			sc = new Scanner(fr);
			this.N = sc.nextInt();
			P = new node[N+1];
			for(int i = 0 ; i < this.N; i++)
			{
			int temp_x = sc.nextInt();
			int temp_y = sc.nextInt();
			node n = new node(temp_x, temp_y);
			P[i] = n;
			}
		} 
		catch (Exception e) { }
		finally {
			if(fr != null) try {fr.close();} catch(Exception e) {}
	
		}
	}
	public int get_N()
	{
		return this.N;
	}
	public node[] get_P()
	{
		return this.P;
	}

}
class node
{
	int x;
	int y;
	node(int a, int b)
	{
		this.x = a;
		this.y = b;
	}
}