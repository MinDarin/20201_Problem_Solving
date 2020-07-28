import java.io.BufferedReader; //2015115907 김민석 4주차 과제
import java.io.FileReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		point standard = new point();
		LinkedList<point> p_arr = new LinkedList<point>();
		int T = 0;
		int min_y = 999999;
		int min_x = 999999;
		Scanner sc = new Scanner(System.in);
		T = sc.nextInt();
		simple_poly sp;
		int N = 0;
		for(int i = 0 ;i < T; i++)
		{
			min_y = 99999;
			min_x = 99999;
			int pointnumber = 0;
			N = sc.nextInt();
			int count = 0;
			for(int j = 0; j < N; j++)
			{
				point p = new point();
				int x = sc.nextInt();
				int y = sc.nextInt();
				if(min_y > y)
				{
					standard.set_point(x, y, j);
					min_y = y;
					min_x = x;						
				}
				else if(min_y == y)
				{
						if(min_x > x)
						{
							standard.set_point(x, y, j);
							min_y = y;
							min_x = x;
						}

				}
				
				p.set_point(x,y,count++);
				p_arr.add(p);
			}
			sp = new simple_poly(p_arr,N,standard);		
		}
	}
}



class point implements Comparable<point>
{
	int number = 0;
	int x = 0;
	int y = 0;
	int distance = 0;
	float relative_angle = 0;
	point()
	{}
	point(float f, point n, int num)
	{
		this.relative_angle =f;
		this.x= n.x;
		this.y = n.y;
		this.number = num;
	}
	void set_point(int a, int b, int num)
	{
		this.x = a;
		this.y = b;
		this.number = num;
	}
	int get_point_x()
	{
		return this.x;
	}
	int get_point_y()
	{
		return this.y;
	}
	void set_angle(float r,int dis)
	{
		this.relative_angle = r;
		this.distance = dis;
	}
	@Override
	public int compareTo(point p) {
		if(this.relative_angle > p.relative_angle) return 1;
		else if(this.relative_angle < p.relative_angle) return -1;
		else
		{
			if(this.relative_angle < 90)
			{
			if(this.distance > p.distance) return 1;
			else if(this.distance < p.distance) return -1;
			else return 0;
			}
			else
			{
			if(this.distance > p.distance) return -1;
			else if(this.distance < p.distance) return 1;
			else return 0;				
			}
		}
	}
}

class simple_poly
{
	int N;
	point standard;
	simple_poly(LinkedList<point> p_arr, int n,point s)
	{
		this.N = n;
		standard = new point();
		this.standard = s;
		this.compute(p_arr);
	}
	@SuppressWarnings("null")
	void compute(LinkedList<point> p_arr)
	{
		PriorityQueue<point> pq = new PriorityQueue<point>();
		for(int i = 0 ; i < N; i++)
		{
			float temp_angle = this.ComputeAngle(standard,p_arr.get(i));
			point tp = p_arr.get(i);		//new point()로 받아서 처리하면 안되는거 같던데..
			int temp_dis = (int)(Math.pow(tp.x-standard.x,2)+Math.pow(tp.x-standard.x,2));
			tp.set_angle(temp_angle,temp_dis);
			pq.offer(tp);
		}
		point temp_p = pq.poll();
		p_arr.remove();
		System.out.print(temp_p.number);
		while(!pq.isEmpty())
			{
				p_arr.remove();
				temp_p = pq.poll();
				System.out.print(" "+temp_p.number);
			}
		System.out.println();
	}
	float ComputeAngle(point A, point B)
	{
		int Dx, Dy;
		float Angle = 0.0f;
		Dx = B.get_point_x() - A.get_point_x();
		Dy = B.get_point_y() - A.get_point_y();
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