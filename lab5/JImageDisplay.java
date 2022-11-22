package lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    public BufferedImage picture;

    //конструктор, объявляющий новое изображение и его параметры
    public JImageDisplay(int width, int height) {
        picture = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension dimension = new Dimension(width, height);
        super.setPreferredSize(dimension);
    }

    //метод отрисовки изображения
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(picture, 0, 0, picture.getWidth(), picture.getHeight(), null);
    }

    //метод. устанавливающий каждый пиксель в чёрный цвет
    public void clearImage() {
        for (int i = 0; i < picture.getWidth(); i++) {
            for (int j = 0; j < picture.getHeight(); j++) {
                drawPixel(i, j, 0);
            }
        }
    }

    //метод. устанавливающий пиксель в определённый цвет
    public void drawPixel(int x, int y, int rgbColor) {
        picture.setRGB(x, y, rgbColor);
    }
}
