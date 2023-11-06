// GREFIEL, IBANEZ, REBONANZA, DELA CRUZ,BELIGANIO, PANDI, EGIDA, SEDORIFA
package converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Converter {
    private JFrame frame;
    private JPanel panel;
    private JTextField inputField;
    private JLabel resultLabel;
    private JComboBox<String> unitCategorySelector;
    private JComboBox<String> fromUnitSelector;
    private JComboBox<String> toUnitSelector;

    private Map<String, Map<String, Double>> conversionFactors;

    public Converter() {
        frame = new JFrame("Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int frameWidth = 400;
        int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);

        panel = new JPanel(new GridLayout(4, 1)); 

        inputField = new JTextField();
        resultLabel = new JLabel("Result: ");

        JPanel unitSelectionPanel = new JPanel(new FlowLayout());
        
        String[] unitCategories = {"","Mass", "Length", "Area", "Volume"};
        unitCategorySelector = new JComboBox<>(unitCategories);
        fromUnitSelector = new JComboBox<>();
        toUnitSelector = new JComboBox<>();

        unitCategorySelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = unitCategorySelector.getSelectedItem().toString();
                updateUnitSelectors(selectedCategory);
            }
        });

        unitSelectionPanel.add(new JLabel("Category: "));
        unitSelectionPanel.add(unitCategorySelector);
        unitSelectionPanel.add(new JLabel("From Unit: "));
        unitSelectionPanel.add(fromUnitSelector);
        unitSelectionPanel.add(new JLabel("To Unit: "));
        unitSelectionPanel.add(toUnitSelector);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double inputValue = Double.parseDouble(inputField.getText());
                    String selectedCategory = unitCategorySelector.getSelectedItem().toString();
                    String fromUnit = fromUnitSelector.getSelectedItem().toString();
                    String toUnit = toUnitSelector.getSelectedItem().toString();
                    double convertedValue = convert(inputValue, selectedCategory, fromUnit, toUnit);
                    resultLabel.setText("Result: " + formatResult(convertedValue));
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter a valid number.");
                } catch (IllegalArgumentException ex) {
                    resultLabel.setText("Conversion error: " + ex.getMessage());
                }
            }
        });
        
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(clearButton.getFont().deriveFont(Font.PLAIN, 12)); 

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");  
                resultLabel.setText("Result: ");  
            }
        });

        panel.add(inputField);
        panel.add(unitSelectionPanel); 
        panel.add(convertButton);
        panel.add(resultLabel);
        
        panel.add(clearButton);

        initializeConversionFactors();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

     private void initializeConversionFactors() {
        conversionFactors = new HashMap<>();

        Map<String, Double> massFactors = new HashMap<>();
        massFactors.put("Kilogram", 1.0);
        massFactors.put("Gram", 1000.0);
        massFactors.put("Milligram", 1_000_000.0);
        massFactors.put("Ton(Metric)", 0.001);
        massFactors.put("Pound", 2.20462);
        massFactors.put("Ounce", 35.27396);
        conversionFactors.put("Mass", massFactors);

        Map<String, Double> lengthFactors = new HashMap<>();
        lengthFactors.put("Meter", 1.0);
        lengthFactors.put("Kilometer", 0.001);
        lengthFactors.put("Centimeter", 100.0);
        lengthFactors.put("Millimeter", 1000.0);
        lengthFactors.put("Mile", 0.000621371);
        lengthFactors.put("Yard", 1.0936);
        lengthFactors.put("Foot", 3.28084);
        lengthFactors.put("Inch", 39.3701);
        conversionFactors.put("Length", lengthFactors);

        Map<String, Double> areaFactors = new HashMap<>();
        areaFactors.put("Square Meter", 1.0);
        areaFactors.put("Square Kilometer", 0.000001);
        areaFactors.put("Square Mile", 3.861e-7);
        areaFactors.put("Square Yard", 1.19599);
        areaFactors.put("Square Foot", 10.7639);
        areaFactors.put("Square Inch", 1550.0031);
        areaFactors.put("Hectare", 0.0001);
        areaFactors.put("Acre", 0.000247105);
        conversionFactors.put("Area", areaFactors);

        Map<String, Double> volumeFactors = new HashMap<>();
        volumeFactors.put("Liter", 1.0);
        volumeFactors.put("Milliliter", 1000.0);
        volumeFactors.put("Cubic Foot", 0.0353147);
        volumeFactors.put("Cubic Inch", 61.0237);
        volumeFactors.put("Cubic Meter", 0.001);
        conversionFactors.put("Volume", volumeFactors);
    }

     private void updateUnitSelectors(String selectedCategory) {
         fromUnitSelector.removeAllItems();
         toUnitSelector.removeAllItems();

         Map<String, Double> units = conversionFactors.get(selectedCategory);
         if (units != null) {
             for (String unit : units.keySet()) {
                 fromUnitSelector.addItem(unit);
                 toUnitSelector.addItem(unit);
             }
         }
     }

    private double convert(double inputValue, String selectedCategory, String fromUnit, String toUnit) {
        Map<String, Double> units = conversionFactors.get(selectedCategory);
        if (units != null) {
            double fromFactor = units.get(fromUnit);
            double toFactor = units.get(toUnit);
            if (fromFactor != 0 && toFactor != 0) {
                return inputValue * fromFactor / toFactor;
            }
        }
        throw new IllegalArgumentException("Invalid unit selection.");
    }

    private String formatResult(double result) {
        DecimalFormat df = new DecimalFormat("#.##########"); 
        return df.format(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Converter();
            }
        });
    }
}
