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

        // polygon ������ �� 3�� �̻�����̹Ƿ� 3�� ���Ϸ� �ɶ��� �׸��Ѵ�.
        while (set.size() > 2) {

            /************************* ������ �޾ƿͼ� ������ ���� ********************************/
            int FirstPoint = 0;
            for (int j = 1; j < set.size(); j++) {
                if (set.get(j).y < set.get(FirstPoint).y)
                    FirstPoint = j;
            }
            for (int j = 1; j < set.size(); j++) {
                if ((set.get(j).y == set.get(FirstPoint).y) && (set.get(j).x < set.get(FirstPoint).x))
                    FirstPoint = j;
            }
            // �������� ������ SWAP
            Collections.swap(set, 0, FirstPoint);
            FirstPoint = 0;
            set.get(0).angle = 0;

            // ������ �������� angle���
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
            // ���ؾ��ϴ� ��Ʈ���� colinear�� �κе鿡 ���Ͽ� ���� ���������� ������ ���ؾ��Ѵ�.
            // ������ ������ �̹� ���������� �ƴϹǷ� realset���ٰ� ���� ������ �����ϰ�
            // ������ ����� set�� ���� ���Ѵ�. ���� realset�� ���� ������Ű�� ���������ܽ�Ű�� �ٽý���.

            // realset�� �̸� ����.
            ArrayList<Point> realset = new ArrayList<Point>();
            for (Point p : set) {
                realset.add(p);
            }

            // �� �������� ����Ͽ� ������ �͵� ����.
            // ������ ���� �������εǾ� �����Ƿ� �������� �������� ���༱�� �ִ� ������ �����ϱ����Ͽ�

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
            // System.out.println("������" + set.get(0).toString());
            // System.out.println("==================================");
            // for (Point p : set)
            // System.out.println(p.toString());

            // ������ ���� ������ 3�� �̻��϶�
            if (set.size() > 2) {
                for (int j = 1; j < set.size() - 1; j++) {
                    // �������� ������ �������� ���ʿ� �ִ� �� ���ϱ�
                    // ���⼭ ��� ���� ������, j, k( k�� j���� ũ�� ������ ����)
                    // int plus_count = count;
                    for (int k = j + 1; k < set.size(); k++) {
                        float basic_area = (float) Direction(set.get(0), set.get(j), set.get(k)) / 2;
                        // System.out.printf("������ %d %d\n", j, k);
                        if (basic_area > 0)
                            count += findPossible(set, j, k, basic_area);

                    }
                    // System.out.printf("[������ - %d] => %d\n", j, count-plus_count);
                }
                // �����س��� realset�� �������� �����ϰ� �ٽ� ������ 0���� �ʱ�ȭ�Ͽ� set�� ����
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


//FirstPoint => start => next start-next ���� check.
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
        // �ٵ��Ҵµ� �߰��� �ɸ��°� ������ �������� 3������ �̷�.
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
        // 0���� ũ�� �ݽð�
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