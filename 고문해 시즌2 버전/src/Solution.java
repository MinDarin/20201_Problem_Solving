import java.io.*;    //해냈다...18점짜리... 2015115907 김민석
import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
    int N = 0;
    long M = 0;
    long[] S; 
    Scanner sc = new Scanner(new InputStreamReader (System.in));
    N = sc.nextInt();
        
        int[] T = new int[2*N+1];
        S = new long [2*N+1];              
    S[0] = sc.nextLong();

    M = sc.nextLong();
    int count = 0;
    int Point_count = 0;
    ArrayList<Point> list = new ArrayList<Point>();
    for(; count < 2*N; count+=2)   //2개가 한 쌍이라 index를 2개씩 넘긴다.
    {
        S[count+1] = (int)((long)S[count] * S[count] % M);
        S[count+2] = (int)((long)S[count+1] * S[count+1] % M);

                boolean skip = false;
                T[count+1] = (int)(S[count+1] % 2000) - 1000; // X
                T[count+2] = (int)(S[count+2] % 2000) - 1000; // Y
                for(int i = 1 ; i < count; i+=2)
                {
                    if(T[i] == T[count+1]  && T[i+1] == T[count+2])
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


class MyComparator implements Comparator<Point_ang> {
     @Override
     public int compare(Point_ang p1, Point_ang p2) {
       if (p1.angle < p2.angle) {
         return -1; 
       }
       else if(p1.angle > p2.angle)
       {
          return 1;          
       }
       else {
         if (p1.p.y < p2.p.y) {
           return -1;
         }
         else if(p1.p.y > p2.p.y)
            return 1;
         else
         {
            if (p1.p.x < p2.p.x) {
                 return -1;
               }
               else if(p1.p.x > p2.p.x)
                  return 1;
               else
           return 0;
         }
       }
     }
}

class MyComparator2 implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
        if (p1.y < p2.y) {
          return -1;
        }
        else if(p1.y > p2.y)
           return 1;
        else
        {
           if (p1.x < p2.x) {
                return -1;
              }
              else if(p1.x > p2.x)
                 return 1;
              else
          return 0;
        }
      }
}


class Point_ang
{
//   int test_idx = 0;
//   Point[] test = new Point[10]; //테스트용
   Point p = new Point();
   int sorted_idx = 0;
   float angle = 0;
    float sum = 0;
    int point_count = 1;
    Point before_p = new Point();
   Point_ang (Point p1, Point p2)
   {
      this.p = p2;
      this.angle = new run().ComputeAngle(p1, p2);
   }
   Point_ang (Point_ang o)
   {
      this.angle = o.angle;
      this.sum = o.sum;
      this.sorted_idx = o.sorted_idx;
   }
   public void _set (Point p1, Point p2)
   {
	   this.angle = new run().ComputeAngle(p1, p2);
	   this.p = p2;
   }
}

class run
{
    int sol2_num = 0;
    int size = 0;                //전체 사이즈임
  //  Point[] P;                    //point array
    ArrayList<Point> P ;
    int test_count=0;
    float max_area = 0;
    run()
    {
       
    }
    run(int s, ArrayList<Point> list)
    {
        this.size = s;
//        P = new Point[s+1];
        int idx = 0;
        /*
         * while(!list.isEmpty()) { P[idx] = list.poll(); P[idx].total_idx = idx; idx++;
         * }
         */
        this.P = list;
        this._run();
        System.out.println(sol2_num);
    }
    public void _run()
    {
       MyComparator2 myComparator2 = new MyComparator2();
       Collections.sort(P,myComparator2);

       Direction d = new Direction();
        for(int i = 0 ; i < this.size-2; i++)                            //처음 시작점
        {
           
            //P i 기준으로 ㅑ+1 ~ this.size 사이에 있는 애들을 각별로 소팅
           LinkedList<Point_ang> sorted_P = new LinkedList<Point_ang>(); //P[i] 이후의 점들이 들어있음.

           int idx = 0;
           for(int s = i; s< this.size; s++)
            {
                Point_ang p_ang = new Point_ang(P.get(i),P.get(s));
                p_ang.sorted_idx = idx++;
                sorted_P.add(p_ang);
            }
            MyComparator myComparator = new MyComparator();
            Collections.sort(sorted_P,myComparator);
            sorted_P.add(sorted_P.get(0));
            LinkedList<Point_ang> possible = new LinkedList<Point_ang>();
            
            ///////
//            sorted_P[0].test[sorted_P[0].test_idx++] = sorted_P[0].p;            
            ///////
            sorted_P.get(0).before_p = sorted_P.get(0).p;
            possible.add(sorted_P.get(0));
            while(!possible.isEmpty())
           {
            	Point_ang temp = possible.poll();
            	int from = temp.sorted_idx;
            	int to;
            	int point_count = 0;
            	for(to = from+1; to < sorted_P.size()-1; to++)
            	{
            		if(from == 0)
            		{
            			Point_ang new_temp = new Point_ang(temp);
            			new_temp._set(temp.p, sorted_P.get(to).p);  
            			new_temp.sorted_idx = to;
            			point_count = ++new_temp.point_count;
            			possible.add(new_temp);
            		}
            		else
            		{
            			Point_ang new_temp = new Point_ang(temp);    //  temp A -> B
            			new_temp._set(temp.p, sorted_P.get(to).p);   //        new_temp B->C 			            			
            			new_temp.sorted_idx = to;
            			point_count = ++new_temp.point_count;
            			boolean flag = true;
                    	for(int l = from+1; l < sorted_P.size()-1; l++)
            			{
            				int dir = d._Direction(temp.p, new_temp.p, sorted_P.get(l).p);
            				if(dir < 0 && from < l && l < to)
            				{
            					flag = false;
            					break;
            				}
            			}
        				if(flag)
        				{
        					new_temp.sum += Math.abs( ( ( (float)sorted_P.get(0).p.x*temp.p.y + (float)temp.p.x * new_temp.p.y + (float)new_temp.p.x* sorted_P.get(0).p.y )- 
                                (  (float)sorted_P.get(0).p.y*temp.p.x + (float)temp.p.y * new_temp.p.x + (float)new_temp.p.y* sorted_P.get(0).p.x) )/2);
        					
        					possible.add(new_temp);
        				}
            		}
            	}
            	if( to == sorted_P.size()-1 &&  point_count >=2)
            	{
            		sol2_num++;
            	}
            	
            		
            }
          }
            
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
class Point
{
    int total_idx;
    int x = 0;
    int y =0 ;
    Point(){   }
    Point(int X, int Y,int t)
    {
        this.x = X;
        this.y = Y;
        this.total_idx = t;
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