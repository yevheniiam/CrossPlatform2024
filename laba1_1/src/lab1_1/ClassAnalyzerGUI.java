package lab1_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClassAnalyzerGUI extends JFrame {

    private JTextField classNameField;
    private JTextArea resultArea;

    public ClassAnalyzerGUI() {
        setTitle("Class Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Верхня панель з полем для вводу та кнопкою "Аналіз"
        JPanel inputPanel = new JPanel(new BorderLayout());
        classNameField = new JTextField();
        inputPanel.add(classNameField, BorderLayout.CENTER);
        JButton analyzeButton = new JButton("Аналіз");
        analyzeButton.addActionListener(new AnalyzeButtonListener());
        inputPanel.add(analyzeButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Область для виведення результату
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Нижня панель з кнопками "Очистити" та "Вийти"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clearButton = new JButton("Очистити");
        clearButton.addActionListener(new ClearButtonListener());
        bottomPanel.add(clearButton);
        JButton exitButton = new JButton("Вийти");
        exitButton.addActionListener(new ExitButtonListener());
        bottomPanel.add(exitButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Обробник події для кнопки "Аналіз"
    private class AnalyzeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String className = classNameField.getText();
            try {
                Class<?> clazz = Class.forName(className);
                String result = ClassAnalyzer.analyzeClass(clazz);
                resultArea.setText(result);
            } catch (ClassNotFoundException ex) {
                resultArea.setText("Клас не знайдено: " + className);
            }
        }
    }

    // Обробник події для кнопки "Очистити"
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            classNameField.setText("");
            resultArea.setText("");
        }
    }

    // Обробник події для кнопки "Вийти"
    private class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClassAnalyzerGUI().setVisible(true);
            }
        });
    }
}