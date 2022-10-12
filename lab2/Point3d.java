public class Point3d {
    private double xCoord; //координата x
    private double yCoord; //координата y
    private double zCoord; //координата z
    public Point3d ( double x, double y, double z) { //создание нового объекта Point3d с 3 значениями с плавающей точкой
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3d () { //значения (0.0, 0.0, 0.0) по умолчанию
        this(0, 0, 0);
    }
    public double getX () { //получение значения координаты x
        return xCoord;
    }
    public double getY () { //получение значения координаты y
        return yCoord;
    }
    public double getZ () { //получение значения координаты z
        return zCoord;
    }
    public void setX ( double val) { //изменение значения координаты x
        xCoord = val;
    }
    public void setY ( double val) { //изменение значения координаты y
        yCoord = val;
    }
    public void setZ ( double val) { //изменение значения координаты z
        zCoord = val;
    }
    public boolean compare (Point3d a) { //сравнение текущей точки с точкой a
        if (a.getX() == xCoord & a.getY() == yCoord & a.getZ() == zCoord) return true; else return false;
    }
    public double distanceTo (Point3d a) { //вычисление расстояния между текущей точкой и точкой a
        return (Math.sqrt(Math.pow((a.getX()-xCoord), 2) + Math.pow((a.getY()-yCoord), 2) + Math.pow((a.getZ()-zCoord), 2)));
    }
}
