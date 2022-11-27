package lab5;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {

    private static final int MAX_ITERATIONS = 2000;

    //установка начального диапазона в (-2 - 1.5i) - (1 + 1.5i)
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }

    //итеративная функция для фрактала Мандельброта
    @Override
    public int numIterations(double x, double y) {
        double re = 0;
        double im = 0;
        int count = 0;
        while (count < MAX_ITERATIONS) {
            count++;
            //(a+ib)^2 = a*a + 2*a*b*i + i*b*i*b =
            //= a*a + 2*a*b*i - b*b
            double newRe = re * re - im * im + x;
            double newIm = 2 * re * im + y;
            re = newRe;
            im = newIm;
            if (re * re + im * im > 4) //|z|>2
                break;
        }
        if (count == MAX_ITERATIONS)
            return -1;
        else return count;
    }

    @Override
    public String toString() {
        return "Mandelbrot";
    }
}