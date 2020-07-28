//the end ��� �����ذ� �⸻ ��ü ���� _ 2015115907 ��μ�
import java.util.Scanner;

public class Solution {
    private int direction[][] = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // ������ �� �ִ� ����, �����¿� 1ĭ���� �ǹ���
    private boolean[][] board; // board ���̺�, �湮�� ���� true, �湮���� ���� ���� false
    private long remainedBoardCount; // board ���̺� �� �湮���� �ʴ� �� ī��Ʈ, ���� �� ���� 0�̶�� ��� board�� �湮�� ��
    private long tCount; // board�� ��� �湮�ϰ� end(���� �ϴ�) ������ ������ ��� �����ϴ� T ī��Ʈ
    private static final long MOD = (long) Math.pow(10, 9) + 7; // S ī��Ʈ�� �ʹ� ũ�� ������ �� �ֱ� ������ MOD ������ ���� �������� ���
    private long T[][] = new long[9][];
    
    static int num = 0;
    
  /*
    static void mat_print(long a[][])
    {
        for(int i = 0; i<a.length; i++)
        {
            for(int j = 0; j<a[0].length; j++)
            {
                System.out.print(a[i][j]);
   
            }
            System.out.println();
        }
    }
*/
    static long[][] pow(long[][] data, long b) {    //��� A^n�� ��ӿ��� �ϱ� ���� �Լ�, A^4 = (A^2)^2���� �̿��Ѵ�.

        if (b == 0) {
            long[][] tmp = new long[num][num];
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    tmp[i][j] = 1;
                }
            }
            return tmp;
        }
        if (b == 1)
            return data;
        if (b % 2 == 1) {
            //Ȧ��
            long[][] tmp = pow(data, b - 1);
            return countPow(data, tmp);
        } else {
            //¦��
            long[][] tmp = pow(data, b / 2);
            return countPow(tmp, tmp);
        }
    }

    static long[][] countPow(long[][] dataA, long[][] dataB) {
        long[][] result = new long[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                long tmp = 0;
                for (int k = 0; k < num; k++) {
                    tmp += Math.floorMod(dataA[i][k],MOD) * Math.floorMod(dataB[k][j],MOD);
                }
                result[i][j] = Math.floorMod(tmp,MOD);
            }
        }
        return result;
    }
    
    public long process(int m, long n) { // m�� n�� �Է¹޾� S(m, n)�� ���ϱ� ���� �޼ҵ�

        T[4] = new long[]{0, 1, 1, 4, 8};
        T[5] = new long[]{0, 1, 0, 8, 0, 86, 0};
        long S4[] = {1,2,6,14};
//        long S5[] = {1,1,9,9,95,95,-4};
        long S5[] = {95,9,1,1};
        long A4[][] = {{2,2,-2,1},{1,0,0,0},{0,1,0,0},{0,0,1,0}};
  //      long A5[][] = {{0,11,0,0,0,2,-4},{1,0,0,0,0,0,0},{0,1,0,0,0,0,0},{0,0,1,0,0,0,0},{0,0,0,1,0,0,0},{0,0,0,0,1,0,0},{0,0,0,0,0,0,1}};
        long A5[][] ={{11,0,2,-4},{1,0,0,0},{0,1,0,0},{0,0,0,1}};
        long after_A4[][] = new long[4][4];
        long after_A5[][] = new long[7][7];

        long sumOfCount = 0; // S ī��Ʈ�� ������ ����
//        mat_print(A4);
        if (m == 4) 
        {
            this.num = 4;
            {
                after_A4 = this.pow(A4, n-1);
//                mat_print(after_A4);
                for(int i = 3; i >= 0; i--)
                {
                    sumOfCount += Math.floorMod(after_A4[3][3-i],MOD) *Math.floorMod(S4[i],MOD);
                    sumOfCount = Math.floorMod(sumOfCount, MOD);
                }
            }
        }
        else if (m == 5) 
        {
            this.num = 4;
            if(n%2 == 1) n++;
            n = n/2;
            long count = n - (long)num;
            count++;
            
            after_A5 = this.pow(A5, count);
//            mat_print(after_A5);
            for(int i = 0; i <= 3; i++)
            {
              sumOfCount += Math.floorMod(after_A5[0][i],MOD) * Math.floorMod(S5[i],MOD);
              sumOfCount = Math.floorMod(sumOfCount, MOD);
            }
        }
        else
        {
            for (int subN = 1; subN <= n; subN++) 
            { 
            board = new boolean[m][subN]; // m * subN ��ŭ board �ʱ�ȭ, �ʱⰪ�� false�� ���õǾ� ����
            remainedBoardCount = m * subN; // �����ִ� ���� ĭ ī��Ʈ m * subN���� �ʱ�ȭ
            tCount = 0; // T ī��Ʈ 0���� �ʱ�ȭ
            board[0][0] = true; // start ������ ���� ��ܿ��� ����, �湮 ǥ�� true
            remainedBoardCount--; // 1ĭ �湮�����Ƿ� �����ִ� ���� ĭ 1 ����
            dfs(0, 0); // dfs�� ���� ���(0,0) �������� ����
            sumOfCount += tCount; // S ī��Ʈ�� T ī��Ʈ�� �������� ����
            sumOfCount = Math.floorMod(sumOfCount, MOD); // S ī��Ʈ�� S ī��Ʈ�� MOD�� ���� ������ ���
            }
        }
        return sumOfCount; // S ī��Ʈ ��ȯ
    }

    private void dfs(int x, int y) { // ��� board�� �湮�ϱ� ���� ��� DFS �޼ҵ�, x, y�� ���� ��ġ�� ��Ÿ��
        if (remainedBoardCount == 0 && x == board.length - 1 && y == 0) { // ���� �����ִ� ���尡 ���� ���� �ϴܿ� �����ߴٸ� T ī��Ʈ ����
            tCount++; // T ī��Ʈ ����
            return; // �ڷ� ���ư���
        } else if (x == board.length - 1 && y == 0) { // ���尡 �����ִ� ���·� ���� �ϴܿ� ������ ��� �ڷ� ���ư���
            return; // �ڷ� ���ư���
        }
        for (int i = 0; i < direction.length; i++) { // ���� ��ġ���� �� �� �ִ� �����¿� 4���� ������ Ž��
            int nextX = x + direction[i][0]; // ���� x ��ǥ
            int nextY = y + direction[i][1]; // ���� y ��ǥ
            if (nextX < 0 || nextX >= board.length || nextY < 0 || nextY >= board[0].length) { // ���� x, y ��ǥ�� board �� ��ġ��� ��ŵ
                continue;
            } else if (board[nextX][nextY]) { // ���� ���� x, y ��ǥ�� �̹� �湮�� ���̶�� ��ŵ
                continue;
            }
            remainedBoardCount--; // �����ִ� ���� ī��Ʈ ����
            board[nextX][nextY] = true; // ���� x, y ��ǥ �湮 üũ
            dfs(nextX, nextY); // ���� ��ǥ�� �̵�
            remainedBoardCount++; // �ٽ� �ǵ��� �Դٸ� �����ִ� ���� ī��Ʈ ����
            board[nextX][nextY] = false; // ���� x, y ��ǥ �湮 ���� üũ
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        long n = scanner.nextLong();
        long result = new Solution().process(m, n);
        System.out.println(result);

    }
}
