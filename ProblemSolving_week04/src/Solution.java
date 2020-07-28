/*
import java.io.BufferedReader; //2015115907 김민석 4주차 과제
import java.io.FileReader;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Solution {
	public static void main(String[] args)
	{
		point_arr p_arr = new point_arr();
		@SuppressWarnings("unused")
		data_read dr = new data_read(p_arr);
		@SuppressWarnings("unused")
		simple_poly sp = new simple_poly(p_arr,dr.get_N());		
	}
}

class data_read
{
	int N = 0;
	data_read(point_arr linkedlist)
	{
		this.file_read(linkedlist);
	}
	void file_read(point_arr linked)
	{
		FileReader fr = null;
		BufferedReader br = null;
		String[] temp;
		try {
			fr = new FileReader("input.txt");
			br = new BufferedReader(fr);
			this.N = Integer.parseInt(br.readLine());
			for(int i = 0 ; i < N ; i++)
			{
				temp = br.readLine().split(" ");
				point p = new point();
				p.set_point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
				linked.add(p);
			}
		}
		catch (Exception e) { }
		finally {
			
		}
	}
	int get_N()
	{
		return this.N;
	}
}

class point_arr
{
	LinkedList<point> p_arr = new LinkedList<point>();
	void add(point n)
	{
		this.p_arr.add(n);
	}
	point pop()
	{
		return(p_arr.pop());
	}
}

class point implements Comparable<point>
{
	int x = 0;
	int y = 0;
	float relative_angle = 0;
	point()
	{}
	point(float f, point n)
	{
		this.relative_angle =f;
		this.x= n.x;
		this.y = n.y;
	}
	void set_point(int a, int b)
	{
		this.x = a;
		this.y = b;
	}
	int get_point_x()
	{
		return this.x;
	}
	int get_point_y()
	{
		return this.y;
	}
	void set_angle(float r)
	{
		this.relative_angle = r;
	}
	@Override
	public int compareTo(point p) {
		if(this.relative_angle > p.relative_angle) return 1;
		else if(this.relative_angle < p.relative_angle) return -1;
		return 0;
	}
}

class simple_poly
{
	int N;
	simple_poly(point_arr linked, int n)
	{
		this.N = n;
		this.compute(linked);
	}
	@SuppressWarnings("null")
	void compute(point_arr linked)
	{
		PriorityQueue<point> pq = new PriorityQueue<point>();
		point standard = this.find_smallest_point(linked);
		for(int i = 0 ; i < N; i++)
		{
			float temp_angle = this.ComputeAngle(standard,linked.p_arr.get(i));
			point tp = linked.p_arr.get(i);		//new point()로 받아서 처리하면 안되는거 같던데..
			tp.set_angle(temp_angle);
			pq.offer(tp);
		}
		point temp_p;
		while(!pq.isEmpty())
			{
				temp_p = pq.poll();
				System.out.println(temp_p.x+" "+temp_p.y);
			}
		System.out.println(standard.x+" "+standard.y);		

	}
	point find_smallest_point(point_arr linked)
	{
		point standard = null;
		point temp;
		int min_x = 99999, min_y = 99999;
		for(int i = 0 ; i < linked.p_arr.size(); i++)
		{
			temp = linked.p_arr.get(i);
			if(min_y > temp.y)
			{
				min_y = temp.y;
				min_x = temp.x;
				standard = temp;

			}
			else if(min_y == temp.y)
			{
				if(min_x > temp.x)
				{
					min_y = temp.y;
					min_x = temp.x;
					standard = temp;
				}				
			}
		}
			return standard;
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
}*/