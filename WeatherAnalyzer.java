import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WeatherAnalyzer {
    public static void main(String[] args) {
        String fileName = args[0];
        double sum = 0.0;
        int count = 0;
        Scanner scnr = new Scanner(System.in);

        System.out.println("Select a column:");
        System.out.println("1. High Temperature");
        System.out.println("2. Low Temperature");
        System.out.println("3. Humidty");
        System.out.println("4. Wind Speed");
        System.out.println("5. Precipitation");
        int choice = scnr.nextInt();

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int skip = 0;

        if (choice < 1 || choice > 5) {
            System.out.println("Invalid choice.");
            return;
        }

        int columns = choice;
        String label;
        String unit;
        int decimals;

        if (choice == 1){
            label = "High Temperature";
            unit = "°F";
            decimals = 1;
        } else if (choice == 2){
            label = "Low Temperature";
            unit = "°F";
            decimals = 1;
        } else if (choice == 3){
            label = "Humidity";
            unit = "%";
            decimals = 0;
        } else if (choice == 4){
            label = "Wind Speed";
            unit = "mph";
            decimals = 1;
        } else if (choice == 5){
            label = "Precipitation";
            unit = "in/mm";
            decimals = 2;
        } else {
            System.out.println("Invalid choice.");
            return;
        }



        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            fileScanner.nextLine();
            
            while (fileScanner.hasNextLine()){
                String [] fields = fileScanner.nextLine().split(",");
                try{
                    double value = Double.parseDouble(fields[columns]);
                    sum += value;
                    count++;

                    if (value < min){
                        min = value;
                    }
                    if (value > max){
                        max = value;
                    }
                } catch (NumberFormatException e) {
                    skip++;
                }
            }

            fileScanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
                return;
        }
        if(count == 0){
            System.out.println("No valid data.");
            return;
        }
        double average = sum / count;

        System.out.printf("Average %s: %." + decimals + "f %s%n", label, average, unit);
        System.out.printf("Minimum %s: %." + decimals + "f %s%n", label, min, unit);
        System.out.printf("Maximum %s: %." + decimals + "f %s%n", label, max, unit);
        System.out.println("Data points processed: " + count);
        System.out.println("Rows skipped due to invalid data: " + skip);
    }
}