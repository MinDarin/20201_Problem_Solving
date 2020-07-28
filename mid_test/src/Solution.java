import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collection;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.Stack;

public class Solution {

    static float MAX = 0;

    public static void main(String[] args) throws NumberFormatException, IOException {

         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       // BufferedReader br = new BufferedReader(new FileReader("src/input.txt"));
        int N = Integer.parseInt(br.readLine());
        long S = Long.parseLong(br.readLine());
        long M = Long.parseLong(br.readLine());
        br.close();
        int count = 0;

        // System.out.println("N: " + N + " ,S : " + S + " M : " + M);
        ArrayList<Point> set = generatePoint(N, M, S);

        //System.out.println("size :  " + set.size());

        // polygon 구성은 점 3개 이상부터이므로 3개 이하로 될때는 그만한다.
        while (set.size() > 2) {

            /************************* 기준점 받아와서 각으로 소팅 ********************************/
            int FirstPoint = 0;
            for (int j = 1; j < set.size(); j++) {
                if (set.get(j).y < set.get(FirstPoint).y)
                    FirstPoint = j;
            }
            for (int j = 1; j < set.size(); j++) {
                if ((set.get(j).y == set.get(FirstPoint).y) && (set.get(j).x < set.get(FirstPoint).x))
                    FirstPoint = j;
            }
            // 기준점을 앞으로 SWAP
            Collections.swap(set, 0, FirstPoint);
            FirstPoint = 0;
            set.get(0).angle = 0;

            // 기준점 기준으로 angle계산
            for (int j = 1; j < set.size(); j++)
                set.get(j).angle = ComputeAngle(set.get(FirstPoint), set.get(j));

            Collections.sort(set, new comparator());
//
//         Collections.sort(set, new Comparator<Point>() {
//            @Override
//            public int compare(Point o1, Point o2) {
//               // TODO Auto-generated method stub
//               if (o1.angle < o2.angle)
//                  return -1;
//                else
//                  return 1;
//            }
//         });
            // 구해야하는 세트에서 colinear인 부분들에 대하여 가장 먼저만나는 점만을 구해야한다.
            // 하지만 이점이 이미 구해진점은 아니므로 realset에다가 기존 점들을 저장하고
            // 실제로 계산할 set를 따로 구한다. 이후 realset를 통해 복구시키고 기준점제외시키고 다시시작.

            // realset에 미리 저장.
            ArrayList<Point> realset = new ArrayList<Point>();
            for (Point p : set) {
                realset.add(p);
            }

            // 각 기준으로 계산하여 제외할 것들 제외.
            // 정렬이 각도 기준으로되어 있으므로 기준점을 기준으로 평행선에 있는 점들을 제외하기위하여

            float check_angle = -1;
            for (int i = 1; i < set.size(); i++) {
                float point_angle = set.get(i).angle;
                if (check_angle != point_angle) {
                    check_angle = point_angle;
                } else {
                    set.remove(i);
                    i--;
                }
            }
            // System.out.println("시작점" + set.get(0).toString());
            // System.out.println("==================================");
            // for (Point p : set)
            // System.out.println(p.toString());

            // 실제로 구할 점들이 3개 이상일때
            if (set.size() > 2) {
                for (int j = 1; j < set.size() - 1; j++) {
                    // 기준점과 시작점 기준으로 왼쪽에 있는 것 구하기
                    // 여기서 찍는 점은 기준점, j, k( k는 j보다 크고 끝까지 가능)
                    // int plus_count = count;
                    for (int k = j + 1; k < set.size(); k++) {
                        float basic_area = (float) Direction(set.get(0), set.get(j), set.get(k)) / 2;
                        // System.out.printf("기준점 %d %d\n", j, k);
                        if (basic_area > 0)
                            count += findPossible(set, j, k, basic_area);

                    }
                    // System.out.printf("[기준점 - %d] => %d\n", j, count-plus_count);
                }
                // 저장해놓은 realset을 기준점을 제외하고 다시 각도를 0으로 초기화하여 set에 복구
                realset.remove(0);
                set.clear();
                for (Point p : realset) {
                    p.angle = 0;
                    set.add(p);
                    // System.out.println(p.toString());
                }

            }


        }
        System.out.println(MAX);
        System.out.println(count);
    }


//FirstPoint => start => next start-next 기준 check.
    // 12
    public static int findPossible(ArrayList<Point> set, int start, int next, float area) {
        int result = 0;
        int S = 0;
        int i;
        float compute_area = 0;
        for (i = start + 1; i < set.size(); i++) {
            S = Direction(set.get(start), set.get(next), set.get(i));
            if (S > 0) {
                if (i < next)
                    return 0;
                else {
                    compute_area = area + (float) Direction(set.get(0), set.get(next), set.get(i)) / 2;
                    result += findPossible(set, next, i, compute_area);
                }
            } else if (S == 0 && i < next)
                return 0;

        }
        // 다돌았는데 중간에 걸리는게 없으면 기준점과 3각형을 이룸.
        if (i == set.size()) {
            if (area > MAX) {
                MAX = area;
                // System.out.println("MAXMAX in start : " + start + "next: " + next + " result"
                // + result);
            }
            result++;
            // System.out.println(" in start : " + start + "next: " + next + " result: "
            // +result);
        }
        return result;
    }

    static int Direction(Point A, Point B, Point C) {
        // 0보다 크면 반시계
        int dxAB, dxAC, dyAB, dyAC;
        dxAB = B.x - A.x;
        dyAB = B.y - A.y;
        dxAC = C.x - A.x;
        dyAC = C.y - A.y;
        return dxAB * dyAC - dyAB * dxAC;
        /*
         * if (dxAB * dyAC < dyAB * dxAC) Dir = 1; if (dxAB * dyAC > dyAB * dxAC) Dir =
         * -1; if (dxAB * dyAC == dyAB * dxAC) { if (dxAB == 0 && dyAB == 0) { Dir = 0;
         * } if ((dxAB * dxAC < 0) || (dyAB * dyAC < 0)) Dir = -1; else if ((dxAB * dxAB
         * + dyAB * dyAB) >= (dxAC * dxAC + dyAC * dyAC)) Dir = 0; else Dir = 1; }
         * return Dir;
         */

    }

    static float ComputeAngle(Point basic, Point P) {
        int Dx, Dy;
        float angle;

        Dx = P.x - basic.x;
        Dy = P.y - basic.y;
        if ((Dx >= 0) && (Dy == 0))
            angle = 0;
        else {
            angle = (float) Math.abs(Dy) / (float) ((float) Math.abs(Dx) + (float) Math.abs(Dy));
            if ((Dx < 0) && (Dy >= 0))
                angle = 2 - angle;
            else if ((Dx <= 0) && (Dy < 0))
                angle = 2 + angle;
            else if ((Dx >= 0) && (Dy < 0))
                angle = 4 - angle;
        }
        return angle;
    }

    public static ArrayList<Point> generatePoint(int N, long M, long S) {
        ArrayList<Point> set = new ArrayList<Point>();
        ArrayList<Point> real = new ArrayList<Point>();

        long Sn[] = new long[2 * N + 1];
        Sn[0] = S;

        for (int i = 1; i <= 2 * N; i++) {
            Sn[i] = (long) (Sn[i - 1] * Sn[i - 1]) % M;
         //   System.out.println(i + "th" + Sn[i]);
        }
        for (int i = 1; i <= N; i++) {
            double x = (int) (Sn[2 * i - 1] % 2000) - 1000 + (int)Math.random()%(10*1e-6);
            double y = (int) (Sn[2 * i] % 2000) - 1000;
            set.add(new Point((int)x, (int)y));
        }
        boolean check_push = true;
        for (Point p : set) {
            for (int i = 0; i < real.size(); i++)
                if ((p.x == real.get(i).x) && (p.y == real.get(i).y))
                    check_push = false;
            if (check_push)
                real.add(p);
        }
        //System.out.println("real size :" + real.size());
        return real;
    }
}

class Point {
    public int x, y;
    public float angle = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "[" + this.x + "][" + this.y + "]" + " : " + this.angle;
    }

}

class comparator implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
        // TODO Auto-generated method stub
        if (p1.angle < p2.angle)
            return -1;
        else if (p1.angle > p2.angle)
            return 1;
        else {
            if (p1.y < p2.y)
                return -1;
            else if (p1.y > p2.y)
                return 1;
            else {
                if (p1.x < p2.x)
                    return -1;
                else if (p1.x > p2.x)
                    return 1;
                else
                    return 0;
            }
        }

    }
}