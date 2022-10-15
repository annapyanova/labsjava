public class Point3d extends Point2d{
    //поля экземпляра
    private double zCoord; //координата z
    //конструктор
    public Point3d (double x, double y, double z) { //создание нового объекта Point3d с 3 значениями с плавающей точкой
        super (x,y);
        zCoord = z;
    }
    public Point3d () { //значения (0.0, 0.0, 0.0) по умолчанию
        super(0, 0);
        this.zCoord = 0;
    }
    public double getZ () { //получение значения координаты z
        return zCoord;
    }
    public void setZ ( double val) { //изменение значения координаты z
        zCoord = val;
    }
    public boolean compare (Point3d a) { //сравнение текущей точки с точкой a
        if (a.getX() == xCoord & a.getY() == yCoord & a.getZ() == zCoord) return true; 
        else return false;
    }
    public double distanceTo (Point3d a) { //вычисление расстояния между текущей точкой и точкой a
        return (Math.sqrt(Math.pow((a.getX()-xCoord), 2) + Math.pow((a.getY()-yCoord), 2) + Math.pow((a.getZ()-zCoord), 2)));
    }
}
