import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubContrastIJTheme;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class UnitConverter {
    public static void main(String[] args) {
        FlatGitHubContrastIJTheme.install(new FlatGitHubContrastIJTheme());
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        
        ImageIcon info = new ImageIcon("images/info.gif");
        ImageIcon question = new ImageIcon("images/question.gif");
        ImageIcon warning = new ImageIcon("images/warning.gif");
        
        String[] units = {"Meters", "Centimeters", "Kilograms", "Grams", "MPH", "KPH", "Decimal", "Binary", "Hexadecimal", "Celsius", "Fahrenheit", "Kelvin"};
        String[] abv = {"M", "CM", "KG", "G", "MPH", "KPH", "\u2081\u2080", "\u2082", "\u2081\u2086", "°C", "°F", "°K"};
        double[] denominators = {100, 0, 1000, 0, 1.6092};
        
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        format.setMaximumFractionDigits(4);
        format.setMinimumFractionDigits(0);
        format.setGroupingUsed(true);
        
        boolean repeatOuter = true;
        while (repeatOuter) {
            boolean repeatInner = true;
            String choicesFrom = (String) JOptionPane.showInputDialog(null, "Convert From", "UnitConverter", JOptionPane.PLAIN_MESSAGE, null, units, units[0]);
            if (choicesFrom == null) {
                break;
            }
            ArrayList<String> newUnits = new ArrayList<>();
            try {
                if (choicesFrom.equals(units[0])) {
                    newUnits.add(units[1]);
                }
                else if (choicesFrom.equals(units[1])) {
                    newUnits.add(units[0]);
                }
                else if (choicesFrom.equals(units[2])) {
                    newUnits.add(units[3]);
                }
                else if (choicesFrom.equals(units[3])) {
                    newUnits.add(units[2]);
                }
                else if (choicesFrom.equals(units[4])) {
                    newUnits.add(units[5]);
                }
                else if (choicesFrom.equals(units[5])) {
                    newUnits.add(units[4]);
                }
                //Number Systems
                for (int i = 9; i < 12; i++) {
                    if (choicesFrom.equals(units[i])) {
                        for (int j = 9; j < 12; j++) {
                            if (!choicesFrom.equals(units[j])) {
                                newUnits.add(units[j]);
                            }
                        }
                    }
                }
                //Temperatures
                for (int i = 6; i < 9; i++) {
                    if (choicesFrom.equals(units[i])) {
                        for (int j = 6; j < 9; j++) {
                            if (!choicesFrom.equals(units[j])) {
                                newUnits.add(units[j]);
                            }
                        }
                    }
                }
            }
            catch (NullPointerException e) {
                continue;
            }
            
            String choicesTo = (String) JOptionPane.showInputDialog(null, "To", "UnitConverter", JOptionPane.PLAIN_MESSAGE, null, newUnits.toArray(), newUnits.get(0));
            if (choicesTo == null) {
                break;
            }
            while (repeatInner) {
                String inputs = JOptionPane.showInputDialog(null, "Your input: ", "UnitConverter", JOptionPane.PLAIN_MESSAGE);
                if (inputs == null) {
                    repeatOuter = false;
                    break;
                }
                else if (inputs.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please do not leave it empty", "Error", JOptionPane.PLAIN_MESSAGE, warning);
                    continue;
                }
                try {
                    inputs = inputs.replaceAll(" ", "");
                    Object[] methods = {bigToSmall(units, denominators, choicesFrom, choicesTo, format, inputs), smallToBig(units, denominators, choicesFrom, choicesTo, format, inputs), decToE(units, choicesFrom, choicesTo, inputs), binToE(units, choicesFrom, choicesTo, inputs), hexToE(units, choicesFrom, choicesTo, inputs), celToE(units, choicesFrom, choicesTo, format, inputs), fahToE(units, choicesFrom, choicesTo, format, inputs), kelToE(units, choicesFrom, choicesTo, format, inputs)};
                    caller(methods, units, choicesFrom, choicesTo, inputs, abv, info);
                }
                catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please do not input only: \n1.Letters \n2.Spaces", "Error", JOptionPane.ERROR_MESSAGE, warning);
                    continue;
                }
                
                int inner = JOptionPane.showConfirmDialog(null, "Do you want to change the units?    (Cancel to Exit) \nCurrent: " + choicesFrom + "\u290D" + choicesTo, "Yes | No", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, question);
                if (inner == JOptionPane.YES_OPTION) {
                    repeatInner = false;
                }
                else if (inner == JOptionPane.NO_OPTION) {
                    repeatInner = true;
                }
                else {
                    repeatOuter = repeatInner = false;
                }
            }
        }
    }
    
    static void caller(Object[] methods, String[] units, String choicesFrom, String choicesTo, String inputs, String[] abv, ImageIcon padding) {
        int abvIndex = 0;
        for (int i = 0; i < abv.length; i++) {
            if (choicesTo.equals(units[i])) {
                abvIndex = i;
            }
        }
        
        for (int i = 0; i < units.length - 6; i += 2) {
            if (choicesFrom.equals(units[i])) {
                JOptionPane.showMessageDialog(null, inputs + abv[Arrays.asList(units).indexOf(choicesFrom)] + "\u290D" + choicesTo + " = " + methods[0] + " " + abv[abvIndex], "Result", JOptionPane.INFORMATION_MESSAGE, padding);
            }
        }
        for (int i = 1; i < units.length - 6; i += 2) {
            if (choicesFrom.equals(units[i])) {
                JOptionPane.showMessageDialog(null, inputs + abv[Arrays.asList(units).indexOf(choicesFrom)] + "\u290D" + choicesTo + " = " + methods[1] + " " + abv[abvIndex], "Result", JOptionPane.INFORMATION_MESSAGE, padding);
                break;
            }
        }
        for (int i = 6; i < units.length - 2; i++) {
            if (choicesFrom.equals(units[i])) {
                JOptionPane.showMessageDialog(null, inputs + abv[Arrays.asList(units).indexOf(choicesFrom)] + "\u290D" + choicesTo + " = " + methods[i - 4] + " " + abv[abvIndex], "Result", JOptionPane.INFORMATION_MESSAGE, padding);
            }
        }
        for (int i = 9; i < units.length; i++) {
            if (choicesFrom.equals(units[i])) {
                JOptionPane.showMessageDialog(null, inputs + abv[Arrays.asList(units).indexOf(choicesFrom)] + "\u290D" + choicesTo + " = " + methods[i - 4] + " " + abv[abvIndex], "Result", JOptionPane.INFORMATION_MESSAGE, padding);
            }
        }
    }
    
    static String bigToSmall(String[] units, double[] denominators, String choicesFrom, String choicesTo, DecimalFormat format, String inputs) {
        String result = "";
        for (int i = 0; i < units.length - 6; i++) {
            if (choicesFrom.equals(units[i]) && choicesTo.equals(units[i + 1])) {
                result = (String.valueOf(format.format(Double.parseDouble(inputs) * denominators[i])));
            }
        }
        return result;
    }
    static String smallToBig(String[] units, double[] denominators, String choicesFrom, String choicesTo, DecimalFormat format, String inputs) {
        String result = "";
        for (int i = 0; i < units.length - 6; i++) {
            if (i > 0) {
                if (String.valueOf(choicesFrom).equals(units[i]) && String.valueOf(choicesTo).equals(units[i - 1])) {
                    result = (String.valueOf(format.format(Double.parseDouble(inputs) / denominators[i - 1])));
                }
            }
        }
        return result;
    }
    
    static String decToE(String[] units, String choicesFrom, String choicesTo, String inputs) {
        String result = "";
        if (String.valueOf(choicesFrom).equals(units[6])) {
            if (inputs.length() < 11) {
                if (String.valueOf(choicesTo).equals(units[7])) {
                    result = (Integer.toBinaryString(Integer.parseInt(inputs)));
                }
                if (String.valueOf(choicesTo).equals(units[8])) {
                    result = (Integer.toHexString(Integer.parseInt(inputs)).toUpperCase(Locale.ROOT));
                }
            }
            else {
                result = ("Input should be < 11");
            }
        }
        return result;
    }
    static String binToE(String[] units, String choicesFrom, String choicesTo, String inputs) {
        String result = "";
        if (String.valueOf(choicesFrom).equals(units[7])) {
            if (inputs.length() < 32) {
                if (String.valueOf(choicesTo).equals(units[6])) {
                    result = (String.valueOf(Integer.parseInt(inputs, 2)));
                }
                if (String.valueOf(choicesTo).equals(units[8])) {
                    int input = Integer.parseInt(inputs, 2);
                    result = (Integer.toHexString(input).toUpperCase());
                }
            }
            else {
                result = ("Input should be < 32");
            }
            
        }
        return result;
    }
    static String hexToE(String[] units, String choicesFrom, String choicesTo, String inputs) {
        String result = "";
        if (String.valueOf(choicesFrom).equals(units[8])) {
            if (inputs.length() < 8) {
                if (String.valueOf(choicesTo).equals(units[7])) {
                    int input = Integer.parseInt(inputs, 16);
                    result = (Integer.toBinaryString(input));
                }
                if (String.valueOf(choicesTo).equals(units[6])) {
                    result = (String.valueOf(Integer.parseInt(inputs, 16)));
                }
            }
            else {
                result = ("Input should be < 8");
            }
        }
        return result;
    }
    
    static String celToE(String[] units, String choicesFrom, String choicesTo, DecimalFormat format, String inputs) {
        String result = "";
        if (String.valueOf(choicesFrom).equals(units[9])) {
            double input = Double.parseDouble(inputs);
            if (String.valueOf(choicesTo).equals(units[10])) {
                double results = (input * 9 / 5) + 32;
                result = (format.format(results));
            }
            else if (String.valueOf(choicesTo).equals(units[11])) {
                double results = input + 273.15;
                result = (format.format(results));
            }
        }
        return result;
    }
    static String fahToE(String[] units, String choicesFrom, String choicesTo, DecimalFormat format, String inputs) {
        String result = "";
        if (String.valueOf(choicesFrom).equals(units[10])) {
            double input = Double.parseDouble(inputs);
            if (String.valueOf(choicesTo).equals(units[9])) {
                double results = (input - 32) * 5 / 9;
                result = (format.format(results));
            }
            else if (String.valueOf(choicesTo).equals(units[11])) {
                double results = (input - 32) * 5 / 9 + 273.15;
                result = (format.format(results));
            }
        }
        return result;
    }
    static String kelToE(String[] units, String choicesFrom, String choicesTo, DecimalFormat format, String inputs) {
        String result = "";
        if (String.valueOf(choicesFrom).equals(units[11])) {
            double input = Double.parseDouble(inputs);
            if (String.valueOf(choicesTo).equals(units[9])) {
                double results = input - 273.15;
                result = (format.format(results));
            }
            else if (String.valueOf(choicesTo).equals(units[10])) {
                double results = (input - 273.15) * 9 / 5 + 32;
                result = (format.format(results));
            }
        }
        return result;
    }
}