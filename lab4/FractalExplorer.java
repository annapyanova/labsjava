package lab4;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay displayImage;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double complexPlaneRange;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    //конструктор, сохраняющий размер отображения, инициализирующий 
    //объекты диапазона и фрактального генератора
    private FractalExplorer(int sizeDisplay) {
        this.displaySize = sizeDisplay;
        this.fractalGenerator = new Mandelbrot();
        this.complexPlaneRange = new Rectangle2D.Double(0, 0, 0, 0);
        fractalGenerator.getInitialRange(this.complexPlaneRange);
    }

    //метод, инициализирующий графический интерфейс Swing
    public void createAndShowGUI() {
        //настройки отображения
        displayImage = new JImageDisplay(displaySize, displaySize);
        displayImage.addMouseListener(new MouseListener());
        //кнопка для сброса отображения
        JButton button = new JButton("Reset");
        button.addActionListener(new ResetActionListener());
        //размещение содержимого окна
        JFrame frame = new JFrame("Fractal generator");
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    //метод для вывода на экран фрактала
    private void drawFractal() {
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {
                //вычисление количества итераций
                int count = fractalGenerator.numIterations(
                        FractalGenerator.getCoord(
                                complexPlaneRange.x,
                                complexPlaneRange.x + complexPlaneRange.width,
                                displaySize,
                                x
                        ),
                        FractalGenerator.getCoord(
                                complexPlaneRange.y,
                                complexPlaneRange.y + complexPlaneRange.width,
                                displaySize,
                                y
                        )
                );
                int rgbColor;
                if (count == -1) rgbColor = 0; //точка не выходит за границы: цвет - чёрный
                else { //иначе, значение цвета = кол-ву итераций
                    float hue = 0.7f + (float) count / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                displayImage.drawPixel(x, y, rgbColor);
            }
        }
        //обновление JimageDisplay
        displayImage.repaint();
    }

    //сброс дапазона к начальному и перерисовка фрактала
    private class ResetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayImage.clearImage();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal();
        }
    }

    //обработка события о щелчке мышью (нажимая на какое-либо 
    //место на фрактальном отображении, мы увеличиваем его)
    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            double x = FractalGenerator.getCoord(
                    complexPlaneRange.x,
                    complexPlaneRange.x + complexPlaneRange.width, 
                    displaySize, 
                    e.getX()
            );
            double y = FractalGenerator.getCoord(
                    complexPlaneRange.y,
                    complexPlaneRange.y + complexPlaneRange.width,
                    displaySize,
                    e.getY()
            );
            fractalGenerator.recenterAndZoomRange(complexPlaneRange, x, y, 0.5);
            drawFractal();
        }
    }
}