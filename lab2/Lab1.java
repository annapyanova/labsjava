import java.util.Scanner;
public class Lab1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("x1 = "); //ввод координаты x первой точки
        int x1 = in.nextInt();
        System.out.print("y1 = "); //ввод координаты y первой точки
        int y1 = in.nextInt();
        System.out.print("z1 = "); //ввод координаты z первой точки
        int z1 = in.nextInt();
        System.out.print("x2 = "); //ввод координаты x второй точки
        int x2 = in.nextInt();
        System.out.print("y2 = "); //ввод координаты y второй точки
        int y2 = in.nextInt();
        System.out.print("z2 = "); //ввод координаты z второй точки
        int z2 = in.nextInt();
        System.out.print("x3 = "); //ввод координаты x третьей точки
        int x3 = in.nextInt();
        System.out.print("y3 = "); //ввод координаты y третьей точки
        int y3 = in.nextInt();
        System.out.print("z3 = "); //ввод координаты z третьей точки
        int z3 = in.nextInt();
        Point3d Point1 = new Point3d (x1,y1,z1); //создание объектов Point3d
        Point3d Point2 = new Point3d (x2,y2,z2);
        Point3d Point3 = new Point3d (x3,y3,z3);
        if (Point1.compare(Point2) == false & Point1.compare(Point3) == false & Point2.compare(Point3) == false) { //проверка на равенство точек
            System.out.print("Area: ");
            System.out.printf("%.2f",computeArea(Point1, Point2, Point3)); //если точки не совпали, вычисление площади треугольника
        }
        else System.out.println("The points coincide!"); //иначе: сообщение о совпадении точек
        in.close();
    }
    public static double computeArea(Point3d one, Point3d two, Point3d three) { //метод для вычисления площади треугольника
        double a = one.distanceTo(two); //длина стороны a
        double b = one.distanceTo(three); //длина стороны b
        double c = two.distanceTo(three); //длина стороны c
        double p = (a+b+c)/2; //полупериметр треугольника
        return (Math.sqrt(p*(p-a)*(p-b)*(p-c))); //возвращение значения площади треугольника, вычисленной по формуле Герона
    }
}
