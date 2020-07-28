//2015115907_김민석
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Week03 {
	public static void main(String[] args)
	{
		String[] input_data = new String[2];
		ArrayList<Integer> start_point = new ArrayList<Integer>();
		ArrayList<Integer> start_point2 = new ArrayList<Integer>();
		int[] failure_f;
		
		input_data = new Read().data_read();
		
		start_point = new N_Find().nfind(input_data);
		result_print r = new result_print();
		r.nlist_result_print(start_point);

		KMP kmp = new KMP(input_data);
		kmp.run_kmp();
		start_point2 = kmp.get_start_position();
		failure_f = kmp.get_f();
		r.KMP_result_print(start_point2,failure_f);
	}
}
class result_print
{
	public void nlist_result_print(	ArrayList<Integer> point)
	{
		for(int i = 0; i < point.size(); i++)
		{
			System.out.println("matching position "+(i+1)+" : "+point.get(i));
		}
		System.out.println("the number of pattern mathching : "+point.size());
	}

	public void KMP_result_print(ArrayList<Integer> point, int[] f)
	{
		System.out.print("\nf : ");
		for(int i = 0; i <f.length;i++)
		{
			System.out.print(f[i]+" ");
		}
		System.out.println();
		this.nlist_result_print(point);
	}

}
class Read
{
	String[] Return_String_arr = new String[2];
	String Pattern;
	String Text;
	public String[] data_read()
	{
	FileReader s_fp = null;
	FileReader p_fp = null;
	try {
		s_fp = new FileReader("string.txt");
		p_fp = new FileReader("pattern.txt");
		BufferedReader br = new BufferedReader(s_fp);
		Text = br.readLine();
		br = new BufferedReader(p_fp);
		Pattern = br.readLine();
		Return_String_arr[0] = Text;
		Return_String_arr[1] = Pattern;
	} catch (Exception e) {
	}
	try {					//파일 close
		if(s_fp != null)
			s_fp.close();
		if(p_fp != null)
			p_fp.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return Return_String_arr;
	}
}

class N_Find
{
	int i = 0;
	int j = 0;
	int start = 0;
	int lasts;
	int lastp;
	int endmatch;
	char[] string;
	char[] pattern;
	ArrayList<Integer> p_start_point = new ArrayList<Integer>();
	public ArrayList<Integer> nfind(String[] input)
	{
		string = input[0].toCharArray();
		pattern = input[1].toCharArray();
		lasts = string.length -1;
		lastp = pattern.length -1;
		endmatch = lastp;
		for(i = 0; endmatch <=lasts; endmatch++, start++ )	//i = 0 초기화 없어도 상관없음.
		{
			if(string[endmatch] == pattern[lastp])
			{
				for(j = 0, i = start; j< lastp && string[i] == pattern[j] ; i++ ,j++);
				if( j == lastp)
				{
					p_start_point.add(start);
				}
			}
		}
		return p_start_point;
	}
}

class KMP
{
	char[] T;
	char[] P;
	int[] F;
	int m;
	int n;
	int j = 0;
	ArrayList<Integer> start_point = new ArrayList<Integer>();

	KMP(String[] input)
	{
		this.T = input[0].toCharArray();
		this.P = input[1].toCharArray();
		this.F = new int[P.length];
		this.n = T.length;
		this.m = P.length;
	}
	public void run_kmp()
	{
/*		initializeF();
		j = 0;
		for(int i = 0; i <= n-1; i++)
			{
				while(j>= 0 && P[j] != T[i]) j = F[j];
					if(j >= 0)
					{
						j++;
						if(j == m)
						{
							start_point.add(i-(m-1));
							j = F[j-1];			//얘도 F[j]면 배열 인덱스 범위 초과로 터집니다.
						}
					}
					else
					{
						j = 0;
					}
			}*/ //교재의 수도코드가 잘못 된거 같아 다르게 고쳐서 제출합니다.
		//본 교재의 예제의 경우 S: aabaab P: aab의 경우 두번째 aab패턴을 찾아내지 못합니다
		//첫번째 aab를 찾았다면  j=F[j-1]에 의해 j는 -1이 됩니다. 이때 i는 2입니다.
		//for 반복동작에 의해 i는 4가되지만, j는 -1이므로 while문이 동작하지 않아 P[j]와 T[i]를 비교하는 작업이 이루어지지 않고,
		//i는 5로 넘어가고 P[0]와  T[5]를 비교하는 동작을 하므로 잘못되었다고 생각합니다.
		//이는 패턴을 찾은 후 j = F[j-1]을 하여 패턴의 접두사 다음 character와 T[i+1]이 이루어 지지 않아 발생하는 문제라고 생각되어집니다.
		//즉, 예제의 S : aabaaab P : aab의 패턴 찾기가 올바르게 이루어진 이유는
		//우연에 의해 S의 aab다음의 a를 읽지 않아 가능했다고 생각되어집니다.
		//그렇게 생각하는 이유는 위 경우를 해결하기 위해 j = F[i-1] +1를 하게 될 경우 
		//첫번째 패턴 aab를 찾은후 뒤에 이어지는 aa는 S[i]와 P[j]가 같아서 진행됩니다.
		//문제는 이 이후 S[i] = a, P[j] = b인 상황입니다. (i==6, j == 3인 상황입니다.)
		//S[i]와 P[j]는 다르기에 j = F[j]의 작업이 이루어져 P[j]는 첫번째 a와 a를  비교하게됩니다.
		//즉, i == 6부터 새로이 패턴을 찾기 시작하여 i == 5일때의 a는 버려버리기에 두번째 패턴을 찾아내지 못합니다.
		//이러한 문제점들로 인해 아래와 같이 코드를 수정하여 제출합니다.
		//////////////////////////////////////////////수정한 코드
		initializeF();				
		j = -1;
		for(int i = 0; i <= n-1; i++)
			{
				while(j>= 0 && P[j+1] != T[i])	//j+1(다음걸 봤을때) 안맞으면 J까지의 Sub Pattern의 접두사 만큼은 생각 안하겠다! 의 의미가된다.
					j = F[j];
				if(P[j+1] == T[i])			// j를 일치했을 때만 증가시킴 -> 이까지 일치했다!의 의미임
					j++;
				if(j == m-1)
				{
					start_point.add(i-(m-1)); 
					j = F[j];	
				}
			}
		/////////////////////////////////////////////
	}
	public void initializeF()
	{
		this.F[0] = -1;
		int k = 0;
		for(int i = 1; i < m; i++)
		{
			k = F[i-1];
			while(k >= 0 && P[k+1] != P[i])
				k = F[k];
			if(P[i] == P[k+1])
				F[i] = k+1;
			else F[i] = -1;
		}
	}
	public int[] get_f()
	{
     	return this.F;
	}
	public ArrayList<Integer> get_start_position()
	{
		return this.start_point;
	}

}