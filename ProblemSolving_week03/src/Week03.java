//2015115907_��μ�
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
	try {					//���� close
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
		for(i = 0; endmatch <=lasts; endmatch++, start++ )	//i = 0 �ʱ�ȭ ��� �������.
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
							j = F[j-1];			//�굵 F[j]�� �迭 �ε��� ���� �ʰ��� �����ϴ�.
						}
					}
					else
					{
						j = 0;
					}
			}*/ //������ �����ڵ尡 �߸� �Ȱ� ���� �ٸ��� ���ļ� �����մϴ�.
		//�� ������ ������ ��� S: aabaab P: aab�� ��� �ι�° aab������ ã�Ƴ��� ���մϴ�
		//ù��° aab�� ã�Ҵٸ�  j=F[j-1]�� ���� j�� -1�� �˴ϴ�. �̶� i�� 2�Դϴ�.
		//for �ݺ����ۿ� ���� i�� 4��������, j�� -1�̹Ƿ� while���� �������� �ʾ� P[j]�� T[i]�� ���ϴ� �۾��� �̷������ �ʰ�,
		//i�� 5�� �Ѿ�� P[0]��  T[5]�� ���ϴ� ������ �ϹǷ� �߸��Ǿ��ٰ� �����մϴ�.
		//�̴� ������ ã�� �� j = F[j-1]�� �Ͽ� ������ ���λ� ���� character�� T[i+1]�� �̷�� ���� �ʾ� �߻��ϴ� ������� �����Ǿ����ϴ�.
		//��, ������ S : aabaaab P : aab�� ���� ã�Ⱑ �ùٸ��� �̷���� ������
		//�쿬�� ���� S�� aab������ a�� ���� �ʾ� �����ߴٰ� �����Ǿ����ϴ�.
		//�׷��� �����ϴ� ������ �� ��츦 �ذ��ϱ� ���� j = F[i-1] +1�� �ϰ� �� ��� 
		//ù��° ���� aab�� ã���� �ڿ� �̾����� aa�� S[i]�� P[j]�� ���Ƽ� ����˴ϴ�.
		//������ �� ���� S[i] = a, P[j] = b�� ��Ȳ�Դϴ�. (i==6, j == 3�� ��Ȳ�Դϴ�.)
		//S[i]�� P[j]�� �ٸ��⿡ j = F[j]�� �۾��� �̷���� P[j]�� ù��° a�� a��  ���ϰԵ˴ϴ�.
		//��, i == 6���� ������ ������ ã�� �����Ͽ� i == 5�϶��� a�� ���������⿡ �ι�° ������ ã�Ƴ��� ���մϴ�.
		//�̷��� ��������� ���� �Ʒ��� ���� �ڵ带 �����Ͽ� �����մϴ�.
		//////////////////////////////////////////////������ �ڵ�
		initializeF();				
		j = -1;
		for(int i = 0; i <= n-1; i++)
			{
				while(j>= 0 && P[j+1] != T[i])	//j+1(������ ������) �ȸ����� J������ Sub Pattern�� ���λ� ��ŭ�� ���� ���ϰڴ�! �� �ǹ̰��ȴ�.
					j = F[j];
				if(P[j+1] == T[i])			// j�� ��ġ���� ���� ������Ŵ -> �̱��� ��ġ�ߴ�!�� �ǹ���
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