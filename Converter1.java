// GREFIEL, IBANEZ, REBONANZA, DELA CRUZ, BELIGANIO, PANDI, EGIDA, SEDORIFA

package converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Converter1 extends JFrame{
	
	private JTextField inputField;
	private JComboBox<String> categoryComboBox;
	private JComboBox<String> fromUnitComboBox;
	private JComboBox<String> toUnitComboBox;
	private JButton convertButton;
	private JButton clearButton;
	private JLabel resultLabel;
	
	//private JPanel panel;
	
	//map to store units corresponding to each category
	private Map<String, String[]> categoryToUnits = new HashMap<>();
	
	public Converter1() {
		setTitle("Converter");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Categories();
		UI();
	}
	
	private void Categories() {
		//populate the map with units for each category
		categoryToUnits.put("Mass", new String[] {"", "Tonne", "Kilogram", "Gram", "Milligram", "Pound", "Ounce"});
		categoryToUnits.put("Length", new String[] {"", "Kilometer", "Meter", "Centimeter", "Millimeter", "Mile", "Yard", "Foot", "Inch"});
		categoryToUnits.put("Area", new String[] {"", "Square Kilometer", "Square Meter", "Square Mile", "Square Yard", "Square Foot", "Square Inch", "Hectare", "Acre"});
		categoryToUnits.put("Volume", new String[] {"", "Cubic Meter", "Liter", "Milliliter", "Cubic Foot", "Cubic Inch"});
	}
	
	private void UI() {
		JPanel panel = new JPanel();
		add(panel);
		placeComponents(panel);
		
		setLocationRelativeTo(null);
	}
	
	private void placeComponents(JPanel panel) {
		panel.setLayout(null);
		
		JLabel lblCategory = new JLabel("Select Category:");
		lblCategory.setBounds(10, 20, 120, 25);
		panel.add(lblCategory);
		
		categoryComboBox = new JComboBox<>(new String[] {"-----------------------", "Mass", "Length", "Area", "Volume"});
		categoryComboBox.setBounds(140, 20, 120, 25);
		panel.add(categoryComboBox);
		
		JLabel inputLabel = new JLabel("Enter Value:");
		inputLabel.setBounds(10, 50, 120, 25);
		panel.add(inputLabel);
		
		inputField = new JTextField(20);
		inputField.setBounds(140, 50, 120, 25);
		panel.add(inputField);
		
		fromUnitComboBox = new JComboBox<>();
		fromUnitComboBox.setBounds(10, 80, 120, 25);
		panel.add(fromUnitComboBox);
		
		toUnitComboBox = new JComboBox<>();
		toUnitComboBox.setBounds(140, 80, 120, 25);
		panel.add(toUnitComboBox);
		
		convertButton = new JButton("Convert");
		convertButton.setBounds(10, 110, 120, 25);
		panel.add(convertButton);
		
		clearButton = new JButton("Clear");
		clearButton.setBounds(140, 110, 120, 25);
		panel.add(clearButton);
		
		resultLabel = new JLabel("Result: ");
		resultLabel.setBounds(10, 140, 250, 25);
		panel.add(resultLabel);
		
		categoryComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateUnitComboBoxes();
			}
		});
		
		convertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				convert();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		
		//initialize unit combo based on the default category
		updateUnitComboBoxes();
	}
	
	private void updateUnitComboBoxes() {
		//clear existing items
		fromUnitComboBox.removeAllItems();
		
		//get selected category
		String selectedCategory = categoryComboBox.getSelectedItem().toString();
		
		//populate unit combos based on the selected category
		String[] units = categoryToUnits.get(selectedCategory);
		if(units != null) {
			for(String unit : units) {
				fromUnitComboBox.addItem(unit);
				toUnitComboBox.addItem(unit);
			}
		}
	}
	
	private void convert() {
		try {
			double inputValue = Double.parseDouble(inputField.getText());
			String fromUnit = fromUnitComboBox.getSelectedItem().toString();
			String toUnit = toUnitComboBox.getSelectedItem().toString();
			
			double result = performConversion(inputValue, fromUnit, toUnit);
			resultLabel.setText("Result: " + result + " " + toUnit);
		}catch(NumberFormatException ex) {
			resultLabel.setText("Invalid input. Please enter a number." );
		}
	}
	
	private void clearFields() {
		inputField.setText("");
		resultLabel.setText("Result: ");
		
		fromUnitComboBox.setSelectedIndex(0);
		toUnitComboBox.setSelectedIndex(0);
	}
	
	private double performConversion(double value, String fromUnit, String toUnit) {
		
		//conversion logic for Mass Category
		if(fromUnit.equals("Kilograms") && toUnit.equals("Pounds")) {
			return value * 2.205;
		}else if(fromUnit.equals("Kilograms") && toUnit.equals("Grams")) {
			return value * 1000;
		}else if(fromUnit.equals("Kilograms") && toUnit.equals("Milligrams")) {
			return value * 1e+6;
		}else if(fromUnit.equals("Kilograms") && toUnit.equals("Ounce")) {
			return value * 35.274;
		}else if(fromUnit.equals("Kilograms") && toUnit.equals("Tonne")) {
			return value / 1000;
		}else if(fromUnit.equals("Pounds") && toUnit.equals("Kilograms")) {
			return value / 2.205;
		}else if(fromUnit.equals("Pounds") && toUnit.equals("Grams")) {
			return value * 453.6;
		}else if(fromUnit.equals("Pounds") && toUnit.equals("Milligrams")) {
			return value * 453600;
		}else if(fromUnit.equals("Pounds") && toUnit.equals("Ounce")) {
			return value * 16;
		}else if(fromUnit.equals("Pounds") && toUnit.equals("Tonne")) {
			return value / 2205;
		}else if(fromUnit.equals("Grams") && toUnit.equals("Kilograms")) {
			return value / 1000;
		}else if(fromUnit.equals("Grams") && toUnit.equals("Pounds")) {
			return value / 453.6;
		}else if(fromUnit.equals("Grams") && toUnit.equals("Milligrams")) {
			return value * 1000;
		}else if(fromUnit.equals("Grams") && toUnit.equals("Ounce")) {
			return value / 28.35;
		}else if(fromUnit.equals("Grams") && toUnit.equals("Tonne")) {
			return value / 1e+6;
		}else if(fromUnit.equals("Milligrams") && toUnit.equals("Kilograms")) {
			return value / 1e+6;
		}else if(fromUnit.equals("Milligrams") && toUnit.equals("Pounds")) {
			return value / 453600;
		}else if(fromUnit.equals("Milligrams") && toUnit.equals("Grams")) {
			return value / 1000;
		}else if(fromUnit.equals("Milligrams") && toUnit.equals("Ounce")) {
			return value / 28350;
		}else if(fromUnit.equals("Milligrams") && toUnit.equals("Tonne")) {
			return value / 1e+9;
		}else if(fromUnit.equals("Ounce") && toUnit.equals("Kilograms")) {
			return value / 35.274;
		}else if(fromUnit.equals("Ounce") && toUnit.equals("Pounds")) {
			return value / 16;
		}else if(fromUnit.equals("Ounce") && toUnit.equals("Grams")) {
			return value * 28.35;
		}else if(fromUnit.equals("Ounce") && toUnit.equals("Milligrams")) {
			return value * 28350;
		}else if(fromUnit.equals("Ounce") && toUnit.equals("Tonne")) {
			return value / 35270;
		}else if(fromUnit.equals("Tonne") && toUnit.equals("Kilograms")) {
			return value * 1000;
		}else if(fromUnit.equals("Tonne") && toUnit.equals("Pounds")) {
			return value * 2205;
		}else if(fromUnit.equals("Tonne") && toUnit.equals("Grams")) {
			return value * 1e+6;
		}else if(fromUnit.equals("Tonne") && toUnit.equals("Milligrams")) {
			return value * 1e+9;
		}else if(fromUnit.equals("Tonne") && toUnit.equals("Ounce")) {
			return value * 35270;
		}
		//conversion logic for Length Category
		else if(fromUnit.equals("Meter") && toUnit.equals("Kilometer")) {
			return value / 1000;
		}else if(fromUnit.equals("Meter") && toUnit.equals("Inch")) {
			return value * 39.37;
		}else if(fromUnit.equals("Meter") && toUnit.equals("Centimeter")) {
			return value * 100;
		}else if(fromUnit.equals("Meter") && toUnit.equals("Mile")) {
			return value / 1609;
		}else if(fromUnit.equals("Meter") && toUnit.equals("Yard")) {
			return value * 1.094;
		}else if(fromUnit.equals("Meter") && toUnit.equals("Foot")) {
			return value * 3.281;
		}else if(fromUnit.equals("Meter") && toUnit.equals("Millimeter")) {
			return value * 1000;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Meter")) {
			return value * 1000;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Inch")) {
			return value * 39370;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Centimeter")) {
			return value / 100000;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Mile")) {
			return value / 1.609;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Yard")) {
			return value * 1094;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Foot")) {
			return value * 3281;
		}else if(fromUnit.equals("Kilometer") && toUnit.equals("Millimeter")) {
			return value * 1e+6;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Kilometer")) {
			return value / 100000;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Meter")) {
			return value / 100;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Inch")) {
			return value / 2.54;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Mile")) {
			return value / 160900;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Yard")) {
			return value / 91.44;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Foot")) {
			return value / 30.48;
		}else if(fromUnit.equals("Centimeter") && toUnit.equals("Millimeter")) {
			return value * 10;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Kilometer")) {
			return value / 39370;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Meter")) {
			return value / 39.37;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Centimeter")) {
			return value * 2.54;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Millimeter")) {
			return value * 25.4;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Mile")) {
			return value / 63360;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Yard")) {
			return value / 36;
		}else if(fromUnit.equals("Inch") && toUnit.equals("Foot")) {
			return value / 12;
		}
		//conversion logic for Area Category
		else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Square Meter")) {
			return value * 1e+6;
		}else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Square Mile")) {
			return value / 2.59;
		}else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Square Yard")) {
			return value * 1.196e+6;
		}else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Square Foot")) {
			return value * 1.076e+6;
		}else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Square Inch")) {
			return value * 1.55e+9;
		}else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Hectare")) {
			return value * 100;
		}else if(fromUnit.equals("Square Kilometer") && toUnit.equals("Acre")) {
			return value * 247.1;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Square Kilometer")) {
			return value / 1e+6;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Square Mile")) {
			return value / 2.59e+6;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Square Yard")) {
			return value * 1.1969;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Square Foot")) {
			return value * 10.764;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Square Inch")) {
			return value * 1550;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Hectare")) {
			return value / 10000;
		}else if(fromUnit.equals("Square Meter") && toUnit.equals("Acre")) {
			return value / 4047;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Square Kilometer")) {
			return value * 2.59;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Square Meter")) {
			return value * 2.59e+6;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Square Yard")) {
			return value * 3.098e+6;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Square Foot")) {
			return value * 2.788e+7;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Square Inch")) {
			return value * 4.014e+9;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Hectare")) {
			return value * 259;
		}else if(fromUnit.equals("Square Mile") && toUnit.equals("Acre")) {
			return value * 640;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Square Kilometer")) {
			return value / 1.196e+6;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Square Meter")) {
			return value / 1.196;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Square Mile")) {
			return value / 3.098e+6;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Square Foot")) {
			return value * 9;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Square Inch")) {
			return value * 1296;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Hectare")) {
			return value / 11960;
		}else if(fromUnit.equals("Square Yard") && toUnit.equals("Acre")) {
			return value / 4840;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Square Kilometer")) {
			return value / 1.076e+7;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Square Meter")) {
			return value / 10.764;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Square Mile")) {
			return value / 2.788e+7;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Square Yard")) {
			return value / 9;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Square Inch")) {
			return value * 144;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Hectare")) {
			return value / 107600;
		}else if(fromUnit.equals("Square Foot") && toUnit.equals("Acre")) {
			return value / 43560;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Square Kilometer")) {
			return value / 1.55e+9;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Square Meter")) {
			return value / 1550;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Square Mile")) {
			return value / 4.014e+9;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Square Yard")) {
			return value / 1296;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Square Foot")) {
			return value / 144;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Hectare")) {
			return value / 1.55e+7;
		}else if(fromUnit.equals("Square Inch") && toUnit.equals("Acre")) {
			return value / 6.273e+6;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Square Kilometer")) {
			return value / 100;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Square Meter")) {
			return value * 10000;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Square Mile")) {
			return value / 259;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Square Yard")) {
			return value * 11960;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Square Foot")) {
			return value * 107600;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Square Inch")) {
			return value * 1.55e+7;
		}else if(fromUnit.equals("Hectare") && toUnit.equals("Acre")) {
			return value * 2.471;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Square Kilometer")) {
			return value / 247.1;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Square Meter")) {
			return value * 4047;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Square Mile")) {
			return value / 640;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Square Yard")) {
			return value * 4840;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Square Foot")) {
			return value * 43560;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Square Inch")) {
			return value * 6.273e+6;
		}else if(fromUnit.equals("Acre") && toUnit.equals("Hectare")) {
			return value / 2.471;
		}
		//conversion logic for Volume Category
		else if(fromUnit.equals("Liter") && toUnit.equals("Milliliter")) {
			return value * 1000;
		}else if(fromUnit.equals("Liter") && toUnit.equals("Cubic Meter")) {
			return value / 1000;
		}else if(fromUnit.equals("Liter") && toUnit.equals("Cubic Foot")) {
			return value / 28.317;
		}else if(fromUnit.equals("Liter") && toUnit.equals("Cubic Inch")) {
			return value * 61.024;
		}else if(fromUnit.equals("Milliliter") && toUnit.equals("Liter")) {
			return value / 1000;
		}else if(fromUnit.equals("Milliliter") && toUnit.equals("Cubic Meter")) {
			return value / 1e+6;
		}else if(fromUnit.equals("Milliliter") && toUnit.equals("Cubic Foot")) {
			return value / 28320;
		}else if(fromUnit.equals("Milliliter") && toUnit.equals("Cubic Inch")) {
			return value / 16.387;
		}else if(fromUnit.equals("Cubic Meter") && toUnit.equals("Liter")) {
			return value * 1000;
		}else if(fromUnit.equals("Cubic Meter") && toUnit.equals("Milliliter")) {
			return value * 1e+6;
		}else if(fromUnit.equals("Cubic Meter") && toUnit.equals("Cubic Foot")) {
			return value * 35.315;
		}else if(fromUnit.equals("Cubic Meter") && toUnit.equals("Cubic Inch")) {
			return value * 61020;
		}else if(fromUnit.equals("Cubic Foot") && toUnit.equals("Liter")) {
			return value * 28.317;
		}else if(fromUnit.equals("Cubic Foot") && toUnit.equals("Milliliter")) {
			return value * 28320;
		}else if(fromUnit.equals("Cubic Foot") && toUnit.equals("Cubic Meter")) {
			return value / 35.315;
		}else if(fromUnit.equals("Cubic Foot") && toUnit.equals("Cubic Inch")) {
			return value * 1728;
		}else if(fromUnit.equals("Cubic Inch") && toUnit.equals("Liter")) {
			return value / 61.024;
		}else if(fromUnit.equals("Cubic Inch") && toUnit.equals("Milliliter")) {
			return value * 16.387;
		}else if(fromUnit.equals("Cubic Inch") && toUnit.equals("Cubic Meter")) {
			return value / 61020;
		}else if(fromUnit.equals("Cubic Inch") && toUnit.equals("Cubic Foot")) {
			return value / 1728;
		}
		else {
			//conversion not supported, return the input value
			return value;
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			Converter1 converter1 = new Converter1();
			converter1.setVisible(true);
		});
	}
}

