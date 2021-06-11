// Note: The first "100 days" of this simulation are just to get Canadians in America and Americans in Canada.  The stuff included for the final stats
// takes place only after day 100.  So, I made sure to run the simulation for 10 years and 100 days, not just 10 years.

import java.lang.Math;
import java.util.ArrayList;

// CONTINUE HERE - try to modularize things into functions.

// Also, after you're done with all the refactoring for this project,
// make a test project that tests the output of the refactored project
// against the project before the refactoring (so commit 3315ed3). This
// will involve replacing Math.random() with reading from a pre-determined
// text file of values (each of which was already generated with Math.random()).
// The output from both programs should be identical.

class Main {
    // Total counters (keep track of which people cross which border for the entire length of the simulation):

    private static int totalCanadiansCanBorder = 0;
    private static int totalCanadiansUsaBorder = 0;
    private static int totalAmericansCanBorder = 0;
    private static int totalAmericansUsaBorder = 0;

    // The Ratio Lists:  These store all the ratios of Canadians to Americans every day at both of the borders, and overall:

    private static ArrayList <Double> canBorderRatioList = new ArrayList<>();
    // Holds all the daily ratios of Canadians to Americans at the Canada border.
    private static ArrayList <Double> usaBorderRatioList = new ArrayList<>();
    // Holds all the daily ratios of Canadians to Americans at the USA border.
    private static ArrayList <Double> totalBorderRatioList = new ArrayList<>();
    // Holds all the daily ratios of Canadians to Americans overall at the border.

    // Test variables for the above lists:

    private static double canBorderAverageRatio = 0;
    private static double usaBorderAverageRatio = 0;
    private static double totalBorderAverageRatio = 0;

    // Variable that counts how many times (if any) there were more Americans than
    // Canadians at the border, after the first 100 days:

    private static int moreAmericansCounter = 0;
    private static int dayOfException = 0; // holds the most recent day the above occurrence happened, if it ever happened.

    public static void main(String[] args) {
        ArrayList <Person> list = new ArrayList<>();

        // Now to add 306,800 "Americans" to the list (based on 2009 US pop. of 306,800,000):
        for (int i = 1; i <= 306800; i++) {
            // 0.0183288386% chance an average American will go to Canada in a normal day.
            // 56.84287454% chance an American going over the border will stay overnight.
            // An American going overnight will stay an average of 4 days in Canada.

            list.add(new Person("usa", 0.0183288386, 56.84287454, 4));
        }

        // Now to add 33,630 "Canadians" to the list (based on 2009 CAN pop. of 33,630,000):
        for (int i = 1; i <= 33630; i++) {
            // 0.31978949% chance an average Canadian will go to the USA in a normal day.
            // 45.79660672% chance a Canadian going over the border will stay overnight.
            // A Canadian going overnight will stay an average of 8 days in the USA.

            list.add(new Person("can", 0.31978949, 45.79660672, 8));
        }

        // For both countries' populations, there is one tenth in each in the lists (going by 2009 pop.).
        // Since this simulation is just meant to compare how many citizens of each country are at the border
        // in a day, proportions are the only thing that matter.

        // Now to go through the list, and run the simulation.  This will be done with a double for loop:

        // Outside for loop goes through days.  First iteration = day 1, second iteration = day 2, etc.
        // The simulation will run for "10 years and 100 days", since I'm excluding the first 100 days in my final stats report.

        for (int day = 1; day <= 3650 + 100; day ++) {
            // Create the counter variables for this day:

            int canadiansCanBorder = 0; // stores how many canadians are at the canada border for a day.
            int americansCanBorder = 0; // stores how many americans are at the canada border for a day.
            int canadiansUsaBorder = 0; // stores how many canadians are at the usa border for a day.
            int americansUsaBorder = 0; // stores how many americans are at the usa border for a day.

            // Inside for loop, which goes through each "Person" object in the list:
            for (int i = 0; i < list.size(); i++) {
                Person person = list.get(i);
                // Note that person will be modified, with the intention of changing
                // the list[i] object.

                // First to check if the person is at home:
                if (!person.getIsAway()) {
                    // So, the person is at home in their own country.

                    // Now to decide if they should go on a trip:
                    if (Math.random() * 100 <= person.getChanceOfTrip()) {
                        // The person will go on a trip by crossing the border
                        // to the other country:
                        person.setAtBorder(true);

                        // Now to add to the correct counter variable, for keeping track of which people are at which border:
                        boolean isCanadian = person.getCitizenOf().equals("can");
                        if (isCanadian) {
                            canadiansUsaBorder ++;
                        }
                        else {
                            americansCanBorder ++;
                        }

                        // Now, the person has left the border and is in the other country:
                        person.setAtBorder(false);
                        person.setIsAway(true);

                        // Now to decide if the person should stay overnight:
                        if (Math.random() * 100 <= person.getChanceOfOvernight()) {
                            person.setStayingOvernight(true);
                        }
                        else {
                            // So, the person is only away for the day and will now drive back
                            // to their country:
                            person.setAtBorder(true);
                            if (isCanadian) {
                                // So, the person is a canadian driving back to Canada (i.e., crossing
                                // the Canadian border).
                                canadiansCanBorder ++;
                            }
                            else {
                                americansUsaBorder ++;
                            }

                            // Now the person has left the border and is back in their own country.
                            // Reset all the variables that have to do with them crossing the border:
                            person.setIsAway(false);
                            person.setStayingOvernight(false);
                            person.setAtBorder(false);
                            person.setDayCounter(0);
                            person.setHowLongOvernight(person.getAverageLengthOvernight());
                            // Resetting the person's "how_long_overnight" var for their next trip.
                        }
                    }
                }
                else {
                    // Person is away in the other country. Increment the counter
                    // for the number of days they are away:
                    person.increaseDayCounter();
                    if (person.getDayCounter() >= person.getHowLongOvernight()) {
                        // The person has been away for long enough and it is time to go home.

                        // Reset the day_counter:
                        person.setDayCounter(0);

                        // Person drives back to the border...
                        if (person.getCitizenOf().equals("can")) {
                            // The person is Canadian, and they are returning to Canada:
                            canadiansCanBorder ++;
                        }
                        else {
                            americansUsaBorder ++;
                        }

                        // Person has now crossed the border and is driving home.
                        // Reset all their variables that are involved in them crossing the border:
                        person.setIsAway(false);
                        person.setStayingOvernight(false);
                        person.setAtBorder(false);
                        person.setDayCounter(0);
                        person.setHowLongOvernight(person.getAverageLengthOvernight());
                        // Resetting the person's "how_long_overnight" var for their next trip.
                    }
                }
            }
            // End of Inside For Loop, which goes through all the Person objects in
            // the list, for this current day.

            if (day >= 100 &&
                (americansCanBorder > canadiansCanBorder || americansUsaBorder > canadiansUsaBorder)) {
                moreAmericansCounter++;
                dayOfException = day;
                printDailyStats(day, canadiansUsaBorder, americansUsaBorder, canadiansCanBorder, americansCanBorder);
            }
            else if (day <= 30 || day % 100 == 0 || day >= 3650) {
                printDailyStats(day, canadiansUsaBorder, americansUsaBorder, canadiansCanBorder, americansCanBorder);
            }

            // Before the double for loop re-iterates and moves on to the next day, I want to add the daily counters
            // to the total counters.  However, I only want to do this if day >= 100. The simulation needs some time to
            // put Canadians in America and Americans in Canada, or the stats won't be accurate.

            // If day >= 100, I also want to add today's ratios of Canadians to Americans to my Ratio Lists:
            if (day >= 100) {
                totalCanadiansCanBorder += canadiansCanBorder;
                totalCanadiansUsaBorder += canadiansUsaBorder;
                totalAmericansCanBorder += americansCanBorder;
                totalAmericansUsaBorder += americansUsaBorder;

                // I also want to add the ratios of Canadians to Americans at both of the borders to my Ratio Lists:
                canBorderRatioList.add( (double) canadiansCanBorder / americansCanBorder);
                usaBorderRatioList.add( (double) canadiansUsaBorder / americansUsaBorder);

                // for the "totalBorderRatioList", I need the number of overall Canadians at both borders and number of overall Americans at both borders.
                double overallCanadians = canadiansCanBorder + canadiansUsaBorder;
                double overallAmericans = americansCanBorder + americansUsaBorder;
                totalBorderRatioList.add(overallCanadians / overallAmericans);
            }

        } // End of outside for Loop, which goes through the days.  From here it re-iterates again, or ends.

        // Now to display the total stats, from the total counters:
        System.out.println ("\n\n\n   TOTAL STATS:");
        System.out.println (totalCanadiansUsaBorder + " Canadians went to the USA.");
        System.out.println (totalCanadiansCanBorder + " Canadians went back to Canada.");
        System.out.println (totalAmericansCanBorder + " Americans went to Canada.");
        System.out.println (totalAmericansUsaBorder + " Americans went back to the USA.");
        System.out.println ("Ratio of Canadians to Americans at the CAN Border: " + (double) totalCanadiansCanBorder / totalAmericansCanBorder);
        System.out.println ("Ratio of Canadians to Americans at the USA Border: " + (double) totalCanadiansUsaBorder / totalAmericansUsaBorder);

        // Now to display the total ratio for both ways:
        double total_canadians = totalCanadiansCanBorder + totalCanadiansUsaBorder;
        double total_americans = totalAmericansCanBorder + totalAmericansUsaBorder;
        System.out.println ("Total ratio of Canadians to Americans: " + total_canadians / total_americans);

        // Now to display how many times there were more Americans than Canadians at either side of the border:
        System.out.println ("\n\n\n\n\n\nNumber of times there were more Americans than Canadians on either side of the border: " + moreAmericansCounter);
        if (moreAmericansCounter >= 1) {
            System.out.println ("The latest day this happened on was day " + dayOfException);
        }
        else {
            System.out.println ("There was never a day that there were more Americans than Canadians on either side of the border.");
        }

        // CONTINUE HERE - at around this point, there is a big chunk of duplicate
        // code that is being reused multiple times for the same general idea. Make it a function
        // that is called.

        // Now to demonstrate the probabilities of each ratio of Canadians to Americans occurring at the borders.
        // A double for loop will be used to go through all three Ratio Lists, starting with the "canBorderRatioList":
        System.out.println ("\n\n\n\n\n\nFor the Canada Border:");
        for (double i = 0; i < 20; i += 0.1) {
            // i < 20 because it's pretty much impossible there will be a 20:1 Canadian to American ratio at the border.

            // If i is something like 1.099999999999999 when it should be 1.10, I will round it now:
            i = roundDecimalPlace(i, 2);

            double i_greatest = i + 0.1;
            i_greatest = roundDecimalPlace(i_greatest, 2); // CONTINUE HERE - this variable isn't being used. Bug?

            // Before going further, I want to get i and i+0.1 in String format, rounded to two decimal places
            // (or four characters). I will be rounding the String (even though I already rounded the double)
            // because there could be run-off error (like 1.100000000000000000001):

            String i_beginning = Double.toString(i);
            if (i_beginning.length() > 5) {
                i_beginning = roundDecimalPlace(i_beginning, 2);
            }
            String i_end = Double.toString(i + 0.1);
            if (i_end.length() > 5) {
                i_end = roundDecimalPlace(i_end, 2);
            }

            // Let's work on the "canBorderRatioList".  I want to find out how many ratios there are in it that
            // are between i and i+0.1.  To do this, I'll run through the list by using a for-each loop.
            int counter = 0; // This variable keeps track of how many ratios in the list are between i and i+0.1.
            for (Double temp: canBorderRatioList) {
                if (temp >= i && temp < i+0.1) {
                    counter ++;
                }
            }

            // Now to display the results to the screen.
            // I need to know the percentage of ratios out of the entire list:
            double percentage = (double) counter / canBorderRatioList.size() * 100;
            if (percentage > 0) {
                System.out.println (percentage + "% of the ratios were between " + i_beginning + " and " + (i_end) + ", inclusive of the beginning (" + i_beginning + ")");
            }
        }

        // Now to find the average ratio at Canada border:
        for (int index = 0; index < canBorderRatioList.size(); index ++) {
            canBorderAverageRatio += canBorderRatioList.get(index);
        }
        canBorderAverageRatio /= canBorderRatioList.size();
        System.out.println ("Average ratio at Canada border: " + canBorderAverageRatio);

        // Now to work on the "usaBorderRatioList":
        System.out.println ("\n\nFor the USA Border:");
        for (double i = 0; i < 20; i += 0.1) {
            // i < 20 because it's pretty much impossible there will be a 20:1 Canadian to American ratio at the border.

            // Let's work on the "usaBorderRatioList".  I want to find out how many ratios there are in it that
            // are between i and i+0.1.  To do this, I'll run through the list by using a for-each loop.
            int counter = 0; // This variable keeps track of how many ratios in the list are between i and i+0.1.
            for (Double temp: usaBorderRatioList) {
                if (temp >= i && temp < i + 0.1) {
                    counter ++;
                }
            }

            // Now to display the results to the Screen:
            // I need to know the percentage of ratios out of the entire list:
            double percentage = (double) counter / usaBorderRatioList.size() * 100;
            if (percentage > 0) {
                System.out.println (percentage + "% of the ratios were between " + i + " and " + (i+0.1) + ", inclusive at the beginning " + "(" + i + ")");
            }
        }

        // Average ratio at USA border:
        for (int index = 0; index < usaBorderRatioList.size(); index ++) {
            usaBorderAverageRatio += usaBorderRatioList.get(index);
        }
        usaBorderAverageRatio /= usaBorderRatioList.size();
        System.out.println ("Average ratio at USA border: " + usaBorderAverageRatio);

        // Now to work on the "totalBorderRatioList":
        System.out.println ("\n\nFor everyone at the border overall:");
        for (double i = 0; i < 20; i += 0.1) {
            // i < 20 because it's pretty much impossible there will be a 20:1 Canadian to American ratio at the border.

            // Let's work on the "totalBorderRatioList".  I want to find out how many ratios there are in it that
            // are between i and i+0.1.  To do this, I'll run through the list by using a for-each loop.

            int counter = 0; // This variable keeps track of how many ratios in the list are between i and i + 0.1.
            for (Double temp: totalBorderRatioList) {
                if (temp >= i && temp < i + 0.1) {
                    counter ++;
                }
            }

            // Now to display the results to the Screen:
            // I need to know the percentage of ratios out of the entire list:
            double percentage = (double) counter / totalBorderRatioList.size() * 100;
            if (percentage > 0) {
                System.out.println (percentage + "% of the ratios were between " + i + " and " + (i+0.1) + ", inclusive at the beginning (" + i + ")");
            }
        }

        // Total Border Average Ratio:
        for (int index = 0; index < totalBorderRatioList.size(); index ++) {
            totalBorderAverageRatio += totalBorderRatioList.get(index);
        }
        totalBorderAverageRatio /= totalBorderRatioList.size();
        System.out.println ("Average ratio overall at the border: " + totalBorderAverageRatio);

        // Test:
        System.out.print ("\n\n\n");
        System.out.println ("Results for the test of the \"how_long_overnight\" method:");
        System.out.println ("Average number of nights that Canadians spent on trip: " +
                            (double) Person.getCanCounter() / Person.getCanTrials());
        System.out.println ("Average number of nights that Americans spent on trip: " +
                            (double) Person.getUsaCounter() / Person.getUsaTrials());
    } // End of main method.

    // Method that prints daily stats to the screen:
    public static void printDailyStats(int day, int canadiansUsaBorder, int americansUsaBorder, int canadiansCanBorder, int americansCanBorder) {
        String white_space = "                                         ";
        // This variable is just a long white_space I can use for outputting to screen.

        // Now to display the data graphically, showing a diagram of the border:
        System.out.println ("\n\n\n\n\n\n\n\n\nDay " + day + ":\n");
        System.out.println ("        CANADA\n");

        // Now to put in the ratio for Canadians to Americans in Canada:
        System.out.print(white_space + ((double) canadiansUsaBorder / americansUsaBorder) + " CAN to USA");
        System.out.print("\n\n");
        System.out.println (canadiansUsaBorder + " CAN\n" + americansUsaBorder + " USA");
        System.out.println ("-----------------------"); // Symbolizes the border.
        System.out.println ("               " + canadiansCanBorder + " CAN\n" + "               " + americansCanBorder + " USA");
        System.out.print("\n");

        // Now to put in the ratio for Canadians to Americans in America:
        System.out.print(white_space + ((double) canadiansCanBorder / americansCanBorder) + " CAN to USA");
        System.out.print("\n");
        System.out.println ("        AMERICA");

        // Now to output the overall ratio of Canadians to Americans, on both sides of the border:
        double overallCanadians = canadiansCanBorder + canadiansUsaBorder;
        double overallAmericans = americansCanBorder + americansUsaBorder;
        System.out.print ("Overall, the total ratio is " + overallCanadians / overallAmericans + " Canadians to Americans.");
    }

    public static String roundDecimalPlace(String num, int decimal_place) {
        if (num == null) {
            throw new IllegalArgumentException("num parameter is null");
        }
        String replacement = "";
        int decimalIndex = num.indexOf(".");
        replacement += num.substring(0, decimalIndex + 1);

        // Now to add the appropriate number of decimal places:
        replacement += (num.substring(decimalIndex + 1, decimalIndex + 1 + decimal_place));
        return replacement;
    }

    public static double roundDecimalPlace(double num, int decimal_place) {
        double replacement = num;
        replacement *= Math.pow(10, decimal_place);
        replacement = Math.round(replacement);
        replacement /= Math.pow(10, decimal_place);
        return replacement;
    }
}