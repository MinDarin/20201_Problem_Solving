import java.io.*;    //14점 그 이상을 위해 아마 이게 최종일듯 2015115907 김민석
import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
    int N = 0 , M = 0;
    int[] S;
    Scanner sc = new Scanner(new InputStreamReader (System.in));
    N = sc.nextInt();
        
        int[] T = new int[3*N];
        Point[] PA = new Point[N];
        S = new
        int[3*N];
             
    S[0] = sc.nextInt();

    M = sc.nextInt();
    int count = 0;
    int Point_count = 0;
    PriorityQueue<Point> list = new PriorityQueue<Point>();
    for(; count < 2*N; count+=2)	//2개가 한 쌍이라 index를 2개씩 넘긴다.
    {
        S[count+1] = (int)((long)S[count] * S[count] % M);
        S[count+2] = (int)((long)S[count+1] * S[count+1] % M);

                boolean skip = false;
                T[count+1] = (S[count+1] % 2000) - 1000; // X
                T[count+2] = (S[count+2] % 2000) - 1000; // Y
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
                list.add(temp_n);
//                PA[Point_count] = temp_n;
                Point_count++;
                }
    }
    run r = new run(Point_count, list);
    }
    
}
class run
{
    static int sol2_num = 0;
    int size = 0;                //전체 사이즈임
    Point[] P;                    //point array
    int test_count=0;
    float max_area = 0;
    run(int s, PriorityQueue<Point> list)
    {
        this.size = s;
        P = new Point[s+1];
        int idx = 0;
        while(!list.isEmpty())
        {
            P[idx] = list.poll();
            P[idx].total_idx = idx;
            idx++;
        }
        this._run();
        System.out.println(sol2_num);
    }
    public int _run()
    {
        LinkedList<Point_log> possible = new LinkedList<Point_log>();

        for(int i = 0 ; i < this.size-2; i++)                            //처음 시작점
        {
            Point_log pl = new Point_log(this.P[i],this.size,0,null);    //비교할 점

            pl.max_y = P[i].y;
            pl.min_y = P[i].y;
            pl.max_y_idx = i;
            pl.min_y_idx = i;
            pl.included_log[i] = true;
            possible.add(pl);

            while(!possible.isEmpty())
            {
                Point_log temp = possible.poll();    

                float min_ang = temp.min_ang;
                for(int j = temp.log[0].total_idx; j < this.size; j++)
                {
                    
                      {
                        float ang = this.ComputeAngle(temp.p,P[j]);
                        boolean total_flag = true;
                        
                        if(ang >min_ang && ang <min_ang+180) //에러뜨면 360으로 바꾸자.
                        {
                            if(temp.log[0] == P[j]) //겉운 점이 아닌데 값이 같다 --> poly 형성
                            {
                                if(temp.last_idx <=1)
                                    continue;
								
								  test_count++; for(int k1 = temp.min_y_idx; k1 <=temp.max_y_idx; k1++) //내부에 점이 있는지 체크하기 위한 for문 
								  { 
									  if(!temp.included_log[k1]) {
								  
										  boolean inside_outside = new IsPointInside()._IsPointInside(P[k1],temp.log,temp.last_idx+1); //이거 체크해야함.
										  //inside_outside가 true면 내부의점임. 
										  if(inside_outside) //내부에 점이 하나라도 있는걸 확인했다. 
										  {
											  total_flag = false; //추가 안해주기 위한 flag break; 
										  }
								    
									  }
								  }
								 
								  if(total_flag)
	                              {
                                    int temp_n = temp.last_idx + 1;
                                    Point[] area = new Point[temp_n+1];
                                    float temp_sum = 0;
                                    for(int e = 0 ; e < temp_n; e++)
                                    	area[e] = temp.log[e];
                                    area[temp_n] = area[0];

                                    for(int a = 0 ; a <temp_n ; a++)
                                        temp_sum +=  (((float)area[a].x * area[a + 1].y) / 2 - ((float)area[a + 1].x * area[a].y) / 2);
                                    if(temp_sum > max_area)
                                        max_area = Math.abs(temp_sum);
                                    
                                    sol2_num++;
                                }
                                
                            }
                            else
                            {
                                
                                if(temp.last_idx >=2)
                                {    
                                test_count++;
                                for(int k1 = temp.min_y_idx; k1 <=temp.max_y_idx; k1++)        //내부에 점이 있는지 체크하기 위한 for문
                                {
                                    if(!temp.included_log[k1])
                                    {
                                            /*
                                             * if(P[k1].y > temp.max_y || P[k1].y < temp.min_y) { continue; }
                                             */                      
                                        boolean    inside_outside = new IsPointInside()._IsPointInside(P[k1],temp.log,temp.last_idx+1);    //이거 체크해야함. P[i]가 아니라 P 전체 확인해아함.
                                        //inside_outside가 true면 내부의점임.
                                        if(inside_outside)            //내부에 점이 하나라도 있는걸 확인했다.
                                        {
                                            total_flag = false;        //추가 안해주기 위한 flag
                                            break;
                                        }
                                    }
                                }
                                }
    
                                
                            if(total_flag)
                            {
                            Point_log new_temp = new Point_log(P[j], this.size, temp.last_idx+1, temp.log, temp.max_y, temp.min_y);
                            
                            new_temp.included_log = Arrays.copyOf(temp.included_log, this.size);
                            new_temp.included_log[j] = true;
                            
                            new_temp.max_y_idx = temp.max_y_idx;
                            new_temp.min_y_idx = temp.min_y_idx;
                            
                            if(new_temp.max_y < P[j].y)
                            {
                                new_temp.max_y =P[j].y;
                                new_temp.max_y_idx = j;
                            }
                            if(new_temp.min_y > P[j].y)
                            {
                                new_temp.min_y = j;
                                new_temp.min_y_idx = P[j].y;
                            }
                            possible.add(new_temp);
                            new_temp.min_ang = ang;
                            }
                            }
                        }
                    }
                }
            }
            
        }
        System.out.println(max_area);
        return sol2_num;
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
    boolean[] included_log;
    int max_y = -9999999;
    int min_y = 9999999;
    int max_y_idx = 0;
    int min_y_idx = 0;
    Point p;
    int last_idx = 0;
    Point[] log;
    float min_ang = 0;
    Point_log(Point P,int n,int last_idx, Point[] last_log, int max, int min)
    {
        this.p = P;
        log = new Point[n];
        this.last_idx = last_idx;
        if(last_log !=null)
        this.log = Arrays.copyOf(last_log, n);        
        /*
         * for(int i = 0; i < this.last_idx; i++) log[i] = last_log[i];
         */
        log[last_idx] = P;
        this.max_y = max;
        this.min_y = min;
        included_log = new boolean[n];
    }
  

    Point_log(Point P,int n,int last_idx, Point[] last_log)
    {
        this.p = P;
        log = new Point[n];
        this.last_idx = last_idx;
        if(last_log !=null)
        this.log = Arrays.copyOf(last_log, n);        
        /*
         * for(int i = 0; i < this.last_idx; i++) log[i] = last_log[i];
         */
        log[last_idx] = P;
        included_log = new boolean[n];
    }
}

class Point implements Comparable<Point>
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
    @Override
    public int compareTo(Point o) {
        if(this.y < o.y) return -1;
        else if(this.y > o.y) return 1;
        else
        {
            if(this.x < o.x) return -1;
            else if(this.x > o.x) return 1;
            else
                return 0;
        }
        // TODO Auto-generated method stub
    }

    
}

class IsPointInside
{
    public boolean _IsPointInside(Point A, Point[] log, int n)
    {
        Intersection is = new Intersection();
        Direction d = new Direction();
        Point[] P = new Point[n+2];
        for(int r = 0 ; r < n ; r++)
        {
            P[r+1] = log[r];
        }
        P[0] = log[n-1];

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
                    }
                }
                else
                {
                        if(d._Direction(TestLine.P1, TestLine.P2, PolygonLine.P1) * d._Direction(TestLine.P1, TestLine.P2, PolygonLine.P2) < 0)
                        {
                            Count++;
                        }
                }
                PointOnTestLine = false;
    
            }
        }
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