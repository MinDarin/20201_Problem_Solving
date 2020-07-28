import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	
	public static void main(String[] args) {
    int N = 0 , M = 0;
    int[] S;
    Scanner sc = new Scanner(new InputStreamReader (System.in));
	N = sc.nextInt();
		/*
		 * int T_N = N; int[] T = new int[2*N+1]; Point[] PA = new Point[N]; S = new
		 * int[2*N+1];
		 */	
	int T_N = N/2;
	int[] T = new int[N+1];
	Point[] PA = new Point[N/2];
	S = new int[N+1];

	S[0] = sc.nextInt();

	M = sc.nextInt();
	int count = 0;
	int minus_count= 0;
	int Point_count = 0;
	for(; count < N; Point_count++, count+=2)
	{
		S[count+1] = (int)((long)S[count] * S[count] % M);
		S[count+2] = (int)((long)S[count+1] * S[count+1] % M);

				boolean skip = false;
				T[count+1] = (S[count+1] % 2000) - 1000; // Y
				T[count+2] = (S[count+2] % 2000) - 1000; // X
				for(int i = 1 ; i <= count; i+=2)
				{
					if(i!= count+1 &&T[i] == T[count+1]  && T[i+1] == T[count+2])
					{
					skip  = true;
					break;
					}
				}
				if(skip)
					continue;
				else
				{
				Point temp_n = new Point(T[count+1],T[count+2], Point_count);
				PA[Point_count] = temp_n;
				}
	}
	run r = new run(Point_count, PA);
	}
	
}
class run
{
	static int sol2_num = 0;
    int size = 0;				//전체 사이즈임
    Point[] P;					//point array
    int test_count=0;
    int[] select_idx;
	int total_num = 0;

    run(int s, Point[] n_arr)
	{
		this.size = s;
		Point[] selected = new Point[s];
		Point[] total_selected = new Point[s];
		P = n_arr;
		select_idx = new int[s];
		this._run();
		System.out.println(sol2_num);
	}
    
    public int _run()
    {
		int total_in_count = 0; //test용
		int total_out_count = 0; // test용
    	for(int poly_num = 2 ; poly_num < this.size; poly_num++)	//임시 다각형의 점 갯수
    	{
    		int in_count = 0; //test용
    		int out_count = 0; // test용
    		for(int i = 0 ; i <= poly_num; i++)
    		{
    			select_idx[i] = i;
    		}
    		int find = poly_num;		
    		int idx_last_count = 0;
			select_idx[poly_num]--;

		while(true)
		{
			find = poly_num;
			idx_last_count = 0;
			while(find >= 0)
			{
			if(select_idx[find]+1 >= this.size-idx_last_count);
			else
			{
				select_idx[find]++;
				break;
			}
			find--;
			idx_last_count++;
			}
			
			if(find == -1)
				break;
			else
			{
				for(int i = find+1; i <=poly_num; i++)
					select_idx[i] = select_idx[i-1]+1;
			}

			int semi_idx = 0;
			Point[] semi_P = new Point[poly_num+2];
			for(int i = 0; i <= poly_num; i++)
			{
				semi_P[semi_idx++] = P[select_idx[i]];
			}

			int poly_last = this.PointWrapping(semi_P, poly_num+1);
//			int poly_last = poly_num+1; //test한다고 잠시...
			Point[] semi_poly = new Point[poly_last+2];
			for(int i = 1; i<= poly_last; i++ )
			{
				semi_poly[i] = semi_P[i-1];
			}
			if(poly_last < poly_num + 1 ) // poly만들었는데 점을 다 안썼다 --> 안에 점이 들어감.
			{
				continue;
			}
			int selected_idx = 0;
			boolean flag = true;
			boolean in_and_out = true;
			for(int i = 0;  i< this.size; i++)	// 모든점 중 내부에 있는 점이 있는지 체쿠
			{
				flag = true;
				for(int j = 1 ; j <= poly_last; j++)
				{
					if(semi_poly[j] == P[i])		//같다 --> poly 위의 점이다. 이 점들은 빼고 나머지 점들 내부 외부 체크해야함.
					{
						flag = false;
						break;
					}
				}
				if(flag == true)
				{
//					System.out.println(poly_num);
					if(new IsPointInside()._IsPointInside(P[i], semi_poly, poly_last)) 	// true면 내부에 점이 있다일걸.
					{
						in_and_out = false;
						break;
					}
				}
			}
			
			if(in_and_out)
			{
				out_count++;
						if(poly_num == 2)
						{
					  System.out.println("안에가 비었음"); for(int j = 0 ; j < poly_last; j++)
					  System.out.print(semi_poly[j].total_idx+" "); System.out.println();
						}			sol2_num++;
			}
				
			else {
				in_count++;
				/*
					 * if(poly_num == 9) { out System.out.println("안에 점이 있음"); for(int j = 0 ; j <
					 * poly_last; j++) System.out.print(semi_poly[j].total_idx+" ");
					 * System.out.println(); }
					 */			
			}
		}
		System.out.println("점 개수 : "+(poly_num+1)+" 내부: "+in_count+" 외부: "+out_count);
		total_in_count+=in_count;
		total_out_count+=out_count;
    }
//		System.out.println("총 내부: "+total_in_count+"총 외부: "+total_out_count);
//		System.out.println("총 : "+(total_in_count+total_out_count));

		return sol2_num;
	}
    
	public int PointWrapping(Point[] P, int n)
	{
		int i, NumVertex = 0;
		float MinAngle, MaxAngle, Angle;
		int FirstPoint = 0;
		for(i = 1 ; i < n ; i++)
			if(P[FirstPoint].y >P[i].y)
				FirstPoint = i;
		for(i = 0 ; i < n ; i++)
			if(P[FirstPoint].y == P[i].y && P[FirstPoint].x > P[i].x)
				FirstPoint = i;
		NumVertex = -1;
		P[n] = P[FirstPoint];
		MaxAngle = 0.0f;
		int NextPoint = FirstPoint;
		
		do {
			NumVertex++;
			
			{			//swap
				Point temp = P[NumVertex];
				P[NumVertex] = P[NextPoint];
				P[NextPoint] = temp;
			}
			
			NextPoint = n;
			MinAngle = MaxAngle;
			MaxAngle = 360.0f;
			for(i = NumVertex+1; i <=n; i++)
			{
				boolean update_flag = false;
				Angle = this.ComputeAngle(P[NumVertex],P[i]);
					if(Angle >= MinAngle && Angle <= MaxAngle)
					{
						if(NumVertex == 0 && i == n)
						{
							update_flag = false;
							continue;
						}
						
						if(Angle == MaxAngle)
						{
							if((Angle >= 0 && Angle < 90) || (Angle >270 && Angle <= 360))
							{
								if(P[NextPoint].x < P[i].x)
									update_flag = true;
							}
							else if(Angle == 90)
							{
								if(P[NextPoint].y < P[i].y)
									update_flag = true;
								
							}
							else if(Angle > 90 && Angle < 270)
							{
								if(P[NextPoint].x > P[i].x)
									update_flag = true;
								
							}
							else if(Angle == 270)
							{
								if(P[NextPoint].y > P[i].y)
									update_flag = true;
								
							}
						}
						else update_flag = true;

						if(update_flag)
						{
						NextPoint = i;
						MaxAngle = Angle;
						}
					}
			}
		}while(NextPoint != n);
		return ++NumVertex;
	}

    
    
	public float ComputeAngle(Point A, Point B)
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

class Point_log
{
	Point p;
	int last_idx = 0;
	Point[] log;
	float min_ang = 0;
	Point_log(Point P,int n,int last_idx, Point[] last_log)
	{
		this.p = P;
		log = new Point[n];
		this.last_idx = last_idx;
		for(int i = 0; i < this.last_idx; i++)
		log[i] = last_log[i];
		log[last_idx] = P;
	}
	/*
	 * public void set_ang(Point[] arr) { for(int i = 0; i < this.last_idx; i++) {
	 * this.log[i] = arr[i]; } }
	 */}

class Point
{
	int total_idx;
	int x = 0;
	int y =0 ;
	Point(){}
	Point(int X, int Y,int t)
	{
		this.x = X;
		this.y = Y;
		this.total_idx = t;
	}

	
}

class IsPointInside
{
	public boolean _IsPointInside(Point A, Point[] P, int n)
	{
		Intersection is = new Intersection();
		Direction d = new Direction();
		P[0] = P[n];

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
//						System.out.println("("+PolygonLine.P1.x+","+PolygonLine.P1.y +") "+"("+PolygonLine.P2.x+","+PolygonLine.P2.y +")");					
					}
				}
				else
				{
						if(d._Direction(TestLine.P1, TestLine.P2, PolygonLine.P1) * d._Direction(TestLine.P1, TestLine.P2, PolygonLine.P2) < 0)
						{
							Count++;
//							System.out.println("("+PolygonLine.P1.x+","+PolygonLine.P1.y +") "+"("+PolygonLine.P2.x+","+PolygonLine.P2.y +")");					
//							PointOnTestLine = false;
						}
				}
				PointOnTestLine = false;
				
			}
		}
//		System.out.println(Count);

		if(Count % 2 == 1)
			return true;
		else return false;
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