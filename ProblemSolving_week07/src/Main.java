import java.io.BufferedReader; //7주차 2015115907 김민석
import java.io.FileReader;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Read read = new Read();
		read._read();
		Run run = new Run();
		node[] P = read.get_P();
		int find =run.GrahamScan(P,read.get_N());

		System.out.println(find);
		for(int i = 1 ; i <= find; i++)
		{
			System.out.println(P[i].x + " "+ P[i].y);
		}
	}
}
class Run
{
	public int GrahamScan(node[] P, int n)
	{
		int i = 0, FirstPoint = 0, NextPoint = 0;
		FirstPoint = 1;
		for(int j = 2; j <= n ; j++)
			if(P[j].y < P[FirstPoint].y)
				FirstPoint = j;
		for(int j = 1; j <= n ; j++)
			if(P[j].y  == P[FirstPoint].y && P[j].x < P[FirstPoint].x) 
				FirstPoint = j;
		{		
		node temp = P[FirstPoint];
		P[FirstPoint] = P[1];
		P[1] = temp;
		}
		FindSimplePolygon FSP = new FindSimplePolygon(P,n);
		
		P[0] = P[n];
		NextPoint = 3;
		for(i = 4 ; i <= n ; i++)
		{
			Direction d = new Direction();
			while(d._Direction(P[NextPoint-1], P[NextPoint], P[i]) >=0)
				NextPoint--;
			NextPoint++;
			{
				node temp = P[NextPoint];
				P[NextPoint] = P[i];
				P[i] = temp;
			}
		}
		return NextPoint;
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
			for(int i = 1 ; i <= this.N; i++)
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
class node implements Comparable<node>
{
	int x;
	int y;
	float relative_angle;
	int distance;
	node(int a, int b)
	{
		this.x = a;
		this.y = b;
	}
	void set_angle(float r,int dis)
	{
		this.relative_angle = r;
		this.distance = dis;
	}
	@Override
	public int compareTo(node p) {
		if(this.relative_angle > p.relative_angle) return 1;
		else if(this.relative_angle < p.relative_angle) return -1;
		else
		{
			if(this.distance > p.distance) return 1;
			else if(this.distance < p.distance) return -1;
			else
				return 0;
		}
	}
}

class FindSimplePolygon
{
	int N;
	FindSimplePolygon(node[] P, int n)
	{
		this.N = n;
		this.compute(P);
	}
	@SuppressWarnings("null")
	void compute(node[] P)
	{
		PriorityQueue<node> pq = new PriorityQueue<node>();
		node standard = P[1];
		for(int i = 1 ; i <= N; i++)
		{
			float temp_angle = this.ComputeAngle(standard,P[i]);
			node tp = P[i];		//new point()로 받아서 처리하면 안되는거 같던데..
			int temp_dis = (int)(Math.pow(tp.x-standard.x,2)+Math.pow(tp.y-standard.y,2));
			tp.set_angle(temp_angle,temp_dis);
			pq.offer(tp);
		}
		node temp_p;
		int insert_idx = 1;
//		System.out.println("////test시작 : 단순 다각형 그리기 확인");
		while(!pq.isEmpty())
			{
				temp_p = pq.poll();
				P[insert_idx++] = temp_p;
//				System.out.println(temp_p.x+" "+temp_p.y);
			}
//		System.out.println(standard.x+" "+standard.y);		

//		System.out.println("////test종료 : 단순 다각형 그리기 확인 종료\n");	
	}
	float ComputeAngle(node A, node B)
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

class Direction
{
	public int _Direction(node A, node B, node C)
	{
		int dxAB ,dxAC, dyAB, dyAC, Dir = -2;
		dxAB = B.x - A.x;
		dyAB = B.y - A.y;
		dxAC = C.x - A.x;
		dyAC = C.y - A.y;
		if( dxAB*dyAC < dyAB*dxAC) Dir = 1;
		if( dxAB*dyAC > dyAB*dxAC) Dir = -1;
		if( dxAB*dyAC == dyAB*dxAC)
		{
			if(dxAB == 0 && dyAB == 0)
				Dir = 0;
			else if(dxAB*dxAC < 0 || dyAB*dyAC < 0) Dir = -1;
			else if((dxAB*dxAB + dyAB*dyAB) >= (dxAC*dxAC + dyAC *dyAC))  Dir = 0;
			else Dir = 1;
		}
		return Dir;
	}
}
