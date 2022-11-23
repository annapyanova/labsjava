package lab5;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay displayImage;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double complexPlaneRange;
    private JComboBox<FractalGenerator> comboBox;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.initGUI();
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
    public void initGUI() {
        //настройки отображения
        displayImage = new JImageDisplay(displaySize, displaySize);
        displayImage.addMouseListener(new MouseListener());
        
        //кнопка для сброса изображения
        JButton buttonReset = new JButton("Reset");
        buttonReset.addActionListener(new ResetActionListener());
        //кнопка для сохранения изображения
        JButton buttonSave = new JButton("Save image");
        buttonSave.addActionListener(new SaveActionListener());
        
        //настройки для выбора фрактала
        JLabel label = new JLabel("Fractal:");
        comboBox = new JComboBox<>();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        comboBox.addActionListener(new ComboActionListener());

        //размещение панелей
        JPanel jPanelFractal = new JPanel();
        JPanel jPanelButtons = new JPanel();
        jPanelFractal.add(label, BorderLayout.CENTER);
        jPanelFractal.add(comboBox, BorderLayout.CENTER);
        jPanelButtons.add(buttonReset, BorderLayout.CENTER);
        jPanelButtons.add(buttonSave, BorderLayout.CENTER);
        
        //размещение содержимого окна
        JFrame frame = new JFrame("Fractal generation");
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER);
        frame.add(jPanelFractal, BorderLayout.NORTH);
        frame.add(jPanelButtons, BorderLayout.SOUTH);
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

    //восстановление масштаба, перерисовка фрактала (кнопка Reset)
    private class ResetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayImage.clearImage();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal();
        }
    }

    //сохранение изображения (кнопка Save Image)
    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int t = fileChooser.showSaveDialog(displayImage);
            if (t == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(displayImage.picture, "png", fileChooser.getSelectedFile());
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(displayImage, ee.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);                        
                }
            }
        }
    }

    //восстановление масштаба, перерисовка фрактала (combobox)
    private class ComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fractalGenerator = (FractalGenerator) comboBox.getSelectedItem();
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
