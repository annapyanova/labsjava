public class Point2d {
    public double xCoord; //координата x
    public double yCoord; //координата y
    //конструктор
    public Point2d ( double x, double y) { //создание нового объекта Point3d с 2 значениями с плавающей точкой
        xCoord = x;
        yCoord = y;
    }
    public Point2d () { //значения (0.0, 0.0) по умолчанию
        this(0, 0);
    }
    public double getX () { //получение значения координаты x
        return xCoord;
    }
    public double getY () { //получение значения координаты y
        return yCoord;
    }
    public void setX ( double val) { //изменение значения координаты x
        xCoord = val;
    }
    public void setY ( double val) { //изменение значения координаты y
        yCoord = val;
    }
}
