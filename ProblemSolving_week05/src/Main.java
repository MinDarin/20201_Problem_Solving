import java.io.BufferedReader; //5ÁÖÂ÷ 2015115907 ±è¹Î¼® 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Read r = new Read();
		r.read();
		Point[] P = r.get_P();
		int N = r.get_N();
		Point check_point = r.get_check_point();
		IsPointInside is = new IsPointInside();
		boolean result = is._IsPointInside(check_point, P, N);
		if(result == true)
			System.out.println("³»ºÎ");
		else System.out.println("¿ÜºÎ");

	}
}

class Read
{
	int N = 0;
	Point[] P;
	Point check_point;
	public Point[] get_P()
	{
		return this.P;
	}
	public int get_N()
	{
		return this.N;
	}
	public Point get_check_point()
	{
		return this.check_point;
	}
	public void read()
	{
		FileReader fr = null;
		try {
			fr = new FileReader("input.txt");
			Scanner sc = new Scanner(fr);
			N = sc.nextInt();
			P = new Point[N+1];
			for(int i = 1; i <= N; i++)
			{
				int temp_x = sc.nextInt();
				int temp_y = sc.nextInt();
				Point p = new Point(temp_x, temp_y);
				P[i] = p;
			}
			P[0] = P[N];
			int temp_x = sc.nextInt();
			int temp_y = sc.nextInt();
			check_point = new Point(temp_x,temp_y);
		} catch (Exception e) {
		}finally
		{
			if(fr != null) try {fr.close();} catch (IOException e) {}
		}
				
	}
}
class Point
{
	int x = 0;
	int y = 0;
	Point()
	{
		
	}
	Point(int X,int Y)
	{
		this.x = X; this.y = Y;
	}
}
class Line
{
	Point P1 = new Point();
	Point P2 = new Point();
	Line()
	{}
		
	Line(Point p1, Point p2)
	{
		this.P1= p1;
		this.P2 = p2;
	}
	public void set_line(Point p1, Point p2, boolean if_need, int other)
	{
		this.P1.x = p1.x;
		this.P1.y = p1.y;
		this.P2.x = p2.x;
		this.P2.y = p2.y;
		if(if_need == true)
		{
			this.P2.x = other;
		}
	}
}

class Intersection
{
	public boolean _Intersection(Line AB, Line CD)
	{
		boolean lineCrossing = false;
		Direction d =new Direction();
		if((d._Direction(AB.P1, AB.P2, CD.P1) *
				d._Direction(AB.P1, AB.P2, CD.P2)<= 0 ) 
							&& ((d._Direction(CD.P1, CD.P2, AB.P1) *
									d._Direction(CD.P1, CD.P2, AB.P2)<= 0 )) )
			lineCrossing = true;
		else lineCrossing = false;
		return lineCrossing;
	}
}
class Direction
{
	public int _Direction(Point A, Point B, Point C)
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
class IsPointInside
{
	public boolean _IsPointInside(Point A, Point[] P, int n)
	{
		Intersection is = new Intersection();
		Direction d = new Direction();
		
		int Count = 0;
		int i;
		int LastPoint = 0;
		Line TestLine = new Line();
		Line PolygonLine = new Line();
		boolean PointOnTestLine = false;
		Count = 0;
		LastPoint = 0;
		TestLine.set_line(A, A, true, 10000);
		for(i = 1; i <= n; i++)
		{
			PolygonLine.set_line(P[i], P[i], false, 0);

			if(is._Intersection(TestLine, PolygonLine))
				PointOnTestLine = true;
			else 
			{
				PolygonLine.set_line(PolygonLine.P1,P[LastPoint],false,0);
				
				LastPoint = i ;
				if(!PointOnTestLine)
				{
					if(is._Intersection(PolygonLine, TestLine))
					{
						Count++;
						System.out.println("("+PolygonLine.P1.x+","+PolygonLine.P1.y +") "+"("+PolygonLine.P2.x+","+PolygonLine.P2.y +")");					
					}
				}
				else
				{
						if(d._Direction(TestLine.P1, TestLine.P2, PolygonLine.P1) * d._Direction(TestLine.P1, TestLine.P2, PolygonLine.P2) < 0)
						{
							Count++;
							System.out.println("("+PolygonLine.P1.x+","+PolygonLine.P1.y +") "+"("+PolygonLine.P2.x+","+PolygonLine.P2.y +")");					
//							PointOnTestLine = false;
						}
				}
				PointOnTestLine = false;
	
			}
		}
		System.out.println(Count);

		if(Count % 2 == 1)
			return true;
		else return false;
	}
}