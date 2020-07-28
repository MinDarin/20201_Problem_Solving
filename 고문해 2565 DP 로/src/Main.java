import java.io.BufferedReader; //10ÁÖÂ÷ °úÁ¦ 2015115907 ±è¹Î¼®
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

class Point implements Comparable<Point>
{
   int l;
   int r;
   Point(int left, int right){
      this.l = left;
      this.r = right;
   }
   @Override
   public int compareTo(Point o){
      if(this.l < o.l)
         return -1;
      else if(this.l > o.l)
         return 1;
      return 0;
   }
}

public class Main {
   static int N;
   static Point[] arr ;
   public static void main(String[] args) throws IOException
   {
	  read();
	  int[] max_n = new int[N];
	  max_n[0] = 1;
	  for(int i=1; i<N; i++)
      {
         max_n[i] = 1;
         for(int j=0; j<i; j++)
         {
            if(arr[i].r > arr[j].r)
            {
               if(max_n[i] < max_n[j] + 1)
                  max_n[i] = max_n[j] + 1;
            }
         }
      }
      int find_max = -1;
      for(int i=0; i<N; i++)
      {
        if(find_max < max_n[i])
        	find_max = max_n[i];
       }
      System.out.println(N-find_max);
   }

   public static void read()
   {	   
		FileReader fp;
		try {
   		fp = new FileReader("input.txt");
	    Scanner sc = new Scanner(fp);
//		Scanner sc = new Scanner(new InputStreamReader (System.in));
		  N = sc.nextInt();
	      
	      arr = new Point[N];
	      for(int i=0; i<N; i++){
	    	 int temp0 = sc.nextInt();
	    	 int temp1 = sc.nextInt();
	    	 arr[i] = new Point(temp0, temp1);
	      }

	      Arrays.sort(arr);
		}
		catch (Exception e) {
			
		}   
   }
}