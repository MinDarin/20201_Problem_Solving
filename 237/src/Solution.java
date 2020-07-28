//the end 고급 문제해결 기말 대체 과제 _ 2015115907 김민석
import java.util.Scanner;

public class Solution {
    private int direction[][] = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 움직일 수 있는 방향, 상하좌우 1칸씩을 의미함
    private boolean[][] board; // board 테이블, 방문한 곳은 true, 방문하지 않은 곳은 false
    private long remainedBoardCount; // board 테이블 중 방문하지 않는 곳 카운트, 만약 이 값이 0이라면 모든 board를 방문한 것
    private long tCount; // board를 모두 방문하고 end(좌측 하단) 지점에 도착한 경우 증가하는 T 카운트
    private static final long MOD = (long) Math.pow(10, 9) + 7; // S 카운트가 너무 크게 증가할 수 있기 때문에 MOD 값으로 나눈 나머지를 사용
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
    static long[][] pow(long[][] data, long b) {    //행렬 A^n을 고속연산 하기 위한 함수, A^4 = (A^2)^2임을 이용한다.

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
            //홀수
            long[][] tmp = pow(data, b - 1);
            return countPow(data, tmp);
        } else {
            //짝수
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
    
    public long process(int m, long n) { // m과 n을 입력받아 S(m, n)을 구하기 위한 메소드

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

        long sumOfCount = 0; // S 카운트를 저장할 변수
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
            board = new boolean[m][subN]; // m * subN 만큼 board 초기화, 초기값은 false로 세팅되어 있음
            remainedBoardCount = m * subN; // 남아있는 보드 칸 카운트 m * subN으로 초기화
            tCount = 0; // T 카운트 0으로 초기화
            board[0][0] = true; // start 지점인 좌측 상단에서 시작, 방문 표시 true
            remainedBoardCount--; // 1칸 방문했으므로 남아있는 보드 칸 1 감소
            dfs(0, 0); // dfs는 좌측 상단(0,0) 지점부터 시작
            sumOfCount += tCount; // S 카운트에 T 카운트를 누적시켜 저장
            sumOfCount = Math.floorMod(sumOfCount, MOD); // S 카운트는 S 카운트를 MOD로 나눈 나머지 사용
            }
        }
        return sumOfCount; // S 카운트 반환
    }

    private void dfs(int x, int y) { // 모든 board를 방문하기 위한 재귀 DFS 메소드, x, y는 현재 위치를 나타냄
        if (remainedBoardCount == 0 && x == board.length - 1 && y == 0) { // 만약 남아있는 보드가 없고 좌측 하단에 도착했다면 T 카운트 증가
            tCount++; // T 카운트 증가
            return; // 뒤로 돌아가기
        } else if (x == board.length - 1 && y == 0) { // 보드가 남아있는 상태로 좌측 하단에 도착한 경우 뒤로 돌아가기
            return; // 뒤로 돌아가기
        }
        for (int i = 0; i < direction.length; i++) { // 현재 위치에서 갈 수 있는 상하좌우 4가지 방향을 탐색
            int nextX = x + direction[i][0]; // 다음 x 좌표
            int nextY = y + direction[i][1]; // 다음 y 좌표
            if (nextX < 0 || nextX >= board.length || nextY < 0 || nextY >= board[0].length) { // 다음 x, y 좌표가 board 밖 위치라면 스킵
                continue;
            } else if (board[nextX][nextY]) { // 만약 다음 x, y 좌표가 이미 방문한 곳이라면 스킵
                continue;
            }
            remainedBoardCount--; // 남아있는 보드 카운트 감소
            board[nextX][nextY] = true; // 다음 x, y 좌표 방문 체크
            dfs(nextX, nextY); // 다음 좌표로 이동
            remainedBoardCount++; // 다시 되돌아 왔다면 남아있는 보드 카운트 증가
            board[nextX][nextY] = false; // 다음 x, y 좌표 방문 해제 체크
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
