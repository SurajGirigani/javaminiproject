import java.util.Scanner;
public class Main{
    // checking if a given date is valid or not
    public static boolean validity(int day,int month,int year){
        switch (month){
            case 1,3,5,7,8,10,12:
                if(day<1 || day>31 || year<=0){
                    return false; 
                }
                break;
            //for February 28/29 days case
            case 2:
                if(leapYear(year)){ 
                    // If leap year days are 29
                    if (day<1 || day>29 || year<=0){
                        return false; 
                    }
                } else{ 
                    // if Non-leap year days are 28
                    if (day<1 || day>28 || year<=0){
                        return false; 
                    }
                }
                break;
            case 4,6,9,11:
                if (day<1 || day>30 || year<=0){
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }
    // checking for leap year
    public static boolean leapYear(int year){
        if(year%4 == 0){
            if(year%100 != 0 || year%400 == 0){
                return true;
            }
        }
        return false;
    }
    //calculateing age based on DOB and current date
    public static void calculateAge(int dobDay, int dobMonth, int dobYear, int refDay, int refMonth, int refYear){
        int years = refYear - dobYear;
        int months = refMonth - dobMonth; 
        int days = refDay - dobDay; 
        // getting days and months for -ve difference
        if (days < 0){
            months--;
            days += 30; // Approximate days in a month
        }
        //year if months diff is -ve
        if (months < 0){
            years--;
            months += 12; //months increase and  years reduce
        }
        // Print the calculated age
        System.out.println("Age is: " + years + " years, " + months + " months, " + days + " days.");
    }
    // calculate DOB based on age and current date
    public static void calculateDOB(int ageDay, int ageMonth, int ageYear, int refDay, int refMonth, int refYear){
        int dobYear = refYear - ageYear; 
        int dobMonth = refMonth - ageMonth; 
        int dobDay = refDay - ageDay;
        // months and days if day difference is negative
        if (dobDay < 0){
            dobMonth--;
            dobDay += 30; // days in a month increment
        }
        // year if the month difference is negative
        if (dobMonth < 0){
            dobYear--;
            dobMonth += 12; // year - and months +
        }
        //leap year correction if 29th Feb falls in a non-leap year
        if (dobDay == 29 && dobMonth == 2 && !leapYear(dobYear)){
            dobDay = 1; // Change as 1st March
            dobMonth = 3;
        }
        // Print the calculated DOB
        System.out.println("Date of Birth is: " + dobDay + "-" + dobMonth + "-" + dobYear);
    }
    //parseing a date string based on the provided format and delimiter
    public static int[] parseDate(String dateStr, String format, String delimiter){
        String[] dateParts = dateStr.split(delimiter); 
        int day = 0, month = 0, year = 0; 
        // Parse date on given format
        if (format.equals("DDdlcMMdlcYYYY")){
            day = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } else if (format.equals("YYYYdlcMMdlcDD")){
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            day = Integer.parseInt(dateParts[2]);
        } else if (format.equals("MMdlcDDdlcYYYY")){
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        }
        return new int[]{day, month, year}; 
    }
    // Main method: start point of the program
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in); 
        // Input 1: User provides  DOB or AGE
        System.out.println("Enter the input(e.g.,DOB=27-02-2001 or AGE=12-05-0020 or EXIT)\n(Enter the date and months in format) : ");
        String input = sc.nextLine(); 
        // Input 2:  current date
        System.out.println("Enter the reference date or current date (e.g., 27-02-2024): ");
        String refDate = sc.nextLine(); 
        // Input 3: Date format
        System.out.println("Enter the date format (e.g., DDdlcMMdlcYYYY, YYYYdlcMMdlcDD, MMdlcDDdlcYYYY): ");
        String format = sc.nextLine();
        // Input 4: Delimiter for in the date 
        System.out.println("Enter the delimiter (e.g., -, /, .): ");
        String delimiter = sc.nextLine();
        // Parseing current date using the given format and delimiter
        int[] refDateParts = parseDate(refDate, format, delimiter);
        int refDay = refDateParts[0];
        int refMonth = refDateParts[1];
        int refYear = refDateParts[2];
        // checking the currentdate
        if (!validity(refDay, refMonth, refYear)){
            System.out.println("current date is invalid. Please enter valid details.");
            sc.close();
            return; // Exit if the current date is wrong
        }
        // Check for "DOB" or "AGE" in given input
        if (input.startsWith("DOB")){
            // If input is DOB then calculate the age
            String dobStr = input.split("=")[1]; // Extract the DOB
            int[] dobParts = parseDate(dobStr, format, delimiter);
            int dobDay = dobParts[0];
            int dobMonth = dobParts[1];
            int dobYear = dobParts[2];
            // checking the DOB
            if (!validity(dobDay, dobMonth, dobYear)){
                System.out.println("DOB is wrong. Please enter valid details.");
                sc.close();
                return; 
            }
            calculateAge(dobDay, dobMonth, dobYear, refDay, refMonth, refYear); // Calculate the age
        } else if (input.startsWith("AGE")){
            // If input is AGE then calculate the DOB
            String agestr = input.split("=")[1];
            int[] ageParts = parseDate(agestr, format, delimiter);
            int ageDay = ageParts[0];
            int ageMonth = ageParts[1];
            int ageYear = ageParts[2];
            calculateDOB(ageDay, ageMonth, ageYear, refDay, refMonth, refYear); // Calculate the DOB
        } else {
            System.out.println("wrong input. Please provide either DOB or AGE."); // Handling wrong input
        }    
        sc.close();
    }
}