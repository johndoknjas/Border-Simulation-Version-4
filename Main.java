// Note: The first "100 days" of this simulation are just to get Canadians in America and Americans in Canada.  The stuff included for the final stats
// takes place only after day 100.  So, I made sure to run the simulation for 10 years and 100 days, not just 10 years.

import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;

class Main
{
    // Total counters (keep track of which people cross which border for the entire length of the simulation):

    private static int total_canadians_can_border = 0;

    private static int total_canadians_usa_border = 0;

    private static int total_americans_can_border = 0;

    private static int total_americans_usa_border = 0;

    // The Ratio Lists:  These store all the ratios of Canadians to Americans every day at both of the borders, and overall:

    private static ArrayList <Double> can_border_ratio_list = new ArrayList <Double>(); // holds all the daily ratios of Canadians to Americans at the Canada border.

    private static ArrayList <Double> usa_border_ratio_list = new ArrayList <Double>(); // holds all the daily ratios of Canadians to Americans at the USA border.

    private static ArrayList <Double> total_border_ratio_list = new ArrayList <Double>(); // holds all the daily ratios of Canadians to Americans overall at the border.

    // Test variables for the above lists:

    private static double can_border_average_ratio = 0;

    private static double usa_border_average_ratio = 0;

    private static double total_border_average_ratio = 0;

    // Variable that counts how many times (if any) there were more Americans than Canadians at the border, after the first 100 days:

    private static int more_americans_counter = 0;

    private static int day_of_exception = 0; // holds the most recent day the above occurrence happened, if it ever happened.

    public static void main(String[] args)
    {
        // ArrayList of "Person" objects, called list:

        ArrayList <Person> list = new ArrayList <Person>();

        // Now to add 306,800 "Americans" to the list (based on 2009 US pop. of 306,800,000):

        for (int i = 1; i <= 306800; i++)
        {
            // Create a temporary object, and then add it to the list:

            Person temp = new Person("usa", 0.0183288386, 56.84287454, 4);
            // 0.0183288386% chance an average American will go to Canada in a normal day.
            // 56.84287454% chance an American going over the border will stay overnight.
            // An American going overnight will stay an average of 4 days in Canada.

            list.add(temp);
        }

        // Now to add 33,630 "Canadians" to the list (based on 2009 CAN pop. of 33,630,000):

        for (int i = 1; i <= 33630; i++)
        {
            // Create a temporary object, and then add it to the list:

            Person temp = new Person("can", 0.31978949, 45.79660672, 8);
            // 0.31978949% chance an average Canadian will go to the USA in a normal day.
            // 45.79660672% chance a Canadian going over the border will stay overnight.
            // A Canadian going overnight will stay an average of 8 days in the USA.

            list.add(temp);
        }

        // For both countries' populations, there is one tenth of each in the lists (going by 2009 pop.).  Since this simulation is just meant
        // to compare how many citizens of each country are at the border in a day, proportions are the only thing that
        // matters.

        // Now to go through the list, and run the simulation.  This will be done with a double for loop:

        // Outside for loop goes through days.  First iteration = day 1, second iteration = day 2, etc.

        // Inside for loop, which goes through each "Person" object in the list:

        for (int day = 1; day <= 3650 + 100; day ++) // Simulation runs for "10 years and 100 days", since I'm excluding the first 100 days in my final stats report.
        {
            // Create the counter variables for this day:

            // Canada border:

            int canadians_can_border = 0; // stores how many canadians are at the canada border for a day.

            int americans_can_border = 0; // stores how many americans are at the canada border for a day.

            // American border:

            int canadians_usa_border = 0; // stores how many canadians are at the usa border for a day.

            int americans_usa_border = 0; // stores how many americans are at the usa border for a day.


            // Inside for loop:

            for (int i = 0; i < list.size(); i++) // Goes through each object in the list.
            {
                Person temp = list.get(i);

                // "temp" is the temporary duplicate of the current object in the list.  I will be working with it.

                // NOTE:  Any changes to temp will be automatically made to the current object in list, since they
                // are both objects that share the same reference address.

                // First to check if the person is at home:

                if (!temp.get_is_away())  // Person is in their own country
                {
                    // So, the person is at home.

                    // Now to decide if they should go on a trip:

                    double random = Math.random() * 100; // generates a random number between 0 - 100:

                    if (random <= temp.get_chance_of_trip())
                    {
                        // The person will go on a trip:

                        // Person drives towards the border to the other country...

                        temp.set_at_border(true); // "at_border" is set to true for the object, since they're at the border now.

                        // Now to add to the correct counter variable, for keeping track of which people are at which border:

                        String citizen_of = temp.get_citizen_of();

                        if (citizen_of.equals("can"))
                        {
                            // Since the person is from Canada and is going on a trip to the other country:

                            canadians_usa_border ++; // increase the counter for canadians at the USA border.
                        }

                        else // meaning the citizen is from the USA:
                        {
                            // Since the person is from the USA and is going on a trip to the other country:

                            americans_can_border ++; // increase the counter for americans at the Canadian border.
                        }


                        // Now, the person has left the border and is in the other country:

                        temp.set_at_border(false);

                        temp.set_is_away(true);

                        // Now to decide if the person should stay overnight:

                        double num = Math.random() * 100;

                        if (num <= temp.get_chance_of_overnight())
                        {
                            // The person is staying overnight:

                            temp.set_staying_overnight(true);
                        }

                        else // So, the person is only away for the day and will now come back to their own country:
                        {
                            // Person drives back to the border...

                            temp.set_at_border(true);

                            if (citizen_of.equals("can"))
                            {
                                // So, the person is a canadian driving back to Canada:

                                canadians_can_border ++; // increase the counter for canadians at the Canada border.
                            }

                            else // the person is a citizen of the USA:
                            {
                                // So, the person is an american driving back to the USA:

                                americans_usa_border ++;
                            }

                            // Now the person has left the border and is back in their own country:

                            // Reset all the variables that have to do with them crossing the border:

                            temp.set_is_away(false);

                            temp.set_staying_overnight(false);

                            temp.set_at_border(false);

                            temp.set_day_counter(0);

                            temp.set_how_long_overnight(temp.get_average_length_overnight()); // resetting the person's "how_long_overnight" var for their next trip.
                        }

                    }
                }

                else // Person is away in the other country.
                {
                    // I want to increase the day_counter for the number of days the person is away:

                    temp.increase_day_counter();

                    // Now to check if the person has been gone for their "how_long_overnight" value.

                    if (temp.get_day_counter() >= temp.get_how_long_overnight())
                    {
                        // The person has been away for long enough and it is time to go home.

                        // Reset the day_counter:

                        temp.set_day_counter(0);

                        // Person drives back to the border...

                        // Person reaches the border:

                        String citizen_of = temp.get_citizen_of();

                        if (citizen_of.equals("can"))
                        {
                            // The person is Canadian, and they are returning to Canada:

                            canadians_can_border ++;
                        }

                        else // The person is american, and they are returning to USA:
                        {
                            americans_usa_border ++;
                        }

                        // Person has now crossed the border and is driving home.

                        // Reset all their variables that are involved in them crossing the border:

                        temp.set_is_away(false);

                        temp.set_staying_overnight(false);

                        temp.set_at_border(false);

                        temp.set_day_counter(0);

                        temp.set_how_long_overnight(temp.get_average_length_overnight());  // resetting the person's "how_long_overnight" var for their next trip.

                    }
                }

            } // End of Inside For Loop, which goes through all the "people".

            if ((day >= 100) && (americans_can_border > canadians_can_border || americans_usa_border > canadians_usa_border))
            {
                more_americans_counter ++;

                day_of_exception = day;

                print_daily_stats(day, canadians_usa_border, americans_usa_border, canadians_can_border, americans_can_border);
            }

            else if (day <= 30 || day % 100 == 0 || day >= 3650)
            {
                print_daily_stats(day, canadians_usa_border, americans_usa_border, canadians_can_border, americans_can_border);
            }

            // Before the double for loop re-iterates and moves on to the next day, I want to add the daily counters
            // to the total counters.  However, I only want to do this if day >= 100. The simulation needs some time to
            // put Canadians in America and Americans in Canada, or the stats won't be accurate.

            // If day >= 100, I also want to add today's ratios of Canadians to Americans to my Ratio Lists:

            if (day >= 100)
            {
                total_canadians_can_border += canadians_can_border;

                total_canadians_usa_border += canadians_usa_border;

                total_americans_can_border += americans_can_border;

                total_americans_usa_border += americans_usa_border;

                // I also want to add the ratios of Canadians to Americans at both of the borders to my Ratio Lists:

                can_border_ratio_list.add( (double) canadians_can_border / americans_can_border);

                usa_border_ratio_list.add( (double) canadians_usa_border / americans_usa_border);

                // for the "total_border_ratio_list", I need the number of overall Canadians at both borders and number of overall Americans at both borders.

                double overall_canadians = canadians_can_border + canadians_usa_border;

                double overall_americans = americans_can_border + americans_usa_border;

                total_border_ratio_list.add(overall_canadians / overall_americans);
            }

        } // End of Outside For Loop, which goes through the days.  From here it re-iterates again, or ends.

        // The double for loop has ended.  Now to display the total stats, from the total counters:

        System.out.println ("\n\n\n   TOTAL STATS:");

        System.out.println (total_canadians_usa_border + " Canadians went to the USA.");

        System.out.println (total_canadians_can_border + " Canadians went back to Canada.");

        System.out.println (total_americans_can_border + " Americans went to Canada.");

        System.out.println (total_americans_usa_border + " Americans went back to the USA.");

        System.out.println ("Ratio of Canadians to Americans at the CAN Border: " + (double) total_canadians_can_border / total_americans_can_border);

        System.out.println ("Ratio of Canadians to Americans at the USA Border: " + (double) total_canadians_usa_border / total_americans_usa_border);

        // Now to display the total ratio for both ways:

        double total_canadians = total_canadians_can_border + total_canadians_usa_border;

        double total_americans = total_americans_can_border + total_americans_usa_border;

        System.out.println ("Total ratio of Canadians to Americans: " + total_canadians / total_americans);

        // Now to display how many times there were more Americans than Canadians at either side of the border:

        System.out.println ("\n\n\n\n\n\nNumber of times there were more Americans than Canadians on either side of the border: " + more_americans_counter);

        if (more_americans_counter >= 1) // So the occurrence happened at least once:
        {
            System.out.println ("The latest day this happened on was day " + day_of_exception);
        }

        else // meaning the occurrence did not happen in the entire simulation:
        {
            System.out.println ("There was never a day that there were more Americans than Canadians on either side of the border.");
        }

        // Now to demonstrate the probabilities of each ratio of Canadians to Americans occurring at the borders.

        // A double for loop will be used to go through all three Ratio Lists, starting with the "can_border_ratio_list":

        System.out.println ("\n\n\n\n\n\nFor the Canada Border:");

        for (double i = 0; i < 20; i += 0.1) // i < 20 because it's pretty much impossible there will be a 20:1 Canadian to American ratio at the border.
        {
            // If i is something like 1.099999999999999 when it should be 1.10, I will round it now:

            i = round_decimal_place(i, 2);

            double i_greatest = i + 0.1;

            i_greatest = round_decimal_place(i_greatest, 2);

            // Before going further, I want to get i and i+0.1 in String format, rounded to two decimal places (or four characters).

            // I will be rounding the String (even though I already rounded the double) because there could be run-off error (like 1.100000000000000000001):

            String i_beginning = Double.toString(i);

            if (i_beginning.length() > 5)
            {
                i_beginning = round_decimal_place(i_beginning, 2);
            }

            String i_end = Double.toString(i + 0.1);

            if (i_end.length() > 5)
            {
                i_end = round_decimal_place(i_end, 2);
            }

            // Let's work on the "can_border_ratio_list".  I want to find out how many ratios there are in it that
            // are between i and i+0.1.  To do this, I'll run through the list by using a for-each loop:

            int counter = 0; // This variable keeps track of how many ratios in the list are between i and i+0.1.

            for (Double temp: can_border_ratio_list)
            {
                if (temp >= i && temp < i+0.1)
                {
                    counter ++;
                }
            }

            // Now to display the results to the Screen:

            // I need to know the percentage of ratios out of the entire list:

            double percentage = (double) counter / can_border_ratio_list.size() * 100;

            if (percentage > 0)
            {
                System.out.println (percentage + "% of the ratios were between " + i_beginning + " and " + (i_end) + ", inclusive of the beginning (" + i_beginning + ")");
            }
        }

        // Now to find the average ratio at Canada border:

        for (int index = 0; index < can_border_ratio_list.size(); index ++)
        {
            can_border_average_ratio += can_border_ratio_list.get(index);
        }

        can_border_average_ratio /= can_border_ratio_list.size();

        System.out.println ("Average ratio at Canada border: " + can_border_average_ratio);

        // Now to work on the "usa_border_ratio_list":

        System.out.println ("\n\nFor the USA Border:");

        for (double i = 0; i < 20; i += 0.1) // i < 20 because it's pretty much impossible there will be a 20:1 Canadian to American ratio at the border.
        {
            // Let's work on the "usa_border_ratio_list".  I want to find out how many ratios there are in it that
            // are between i and i+0.1.  To do this, I'll run through the list by using a for-each loop:

            int counter = 0; // This variable keeps track of how many ratios in the list are between i and i+0.1.

            for (Double temp: usa_border_ratio_list)
            {
                if (temp >= i && temp < i+0.1)
                {
                    counter ++;
                }
            }

            // Now to display the results to the Screen:

            // I need to know the percentage of ratios out of the entire list:

            double percentage = (double) counter / usa_border_ratio_list.size() * 100;

            if (percentage > 0)
            {
                System.out.println (percentage + "% of the ratios were between " + i + " and " + (i+0.1) + ", inclusive at the beginning " + "(" + i + ")");
            }
        }

        // Average ratio at USA border:

        for (int index = 0; index < usa_border_ratio_list.size(); index ++)
        {
            usa_border_average_ratio += usa_border_ratio_list.get(index);
        }

        usa_border_average_ratio /= usa_border_ratio_list.size();

        System.out.println ("Average ratio at USA border: " + usa_border_average_ratio);

        // Now to work on the "total_border_ratio_list":

        System.out.println ("\n\nFor everyone at the border overall:");

        for (double i = 0; i < 20; i += 0.1) // i < 20 because it's pretty much impossible there will be a 20:1 Canadian to American ratio at the border.
        {
            // Let's work on the "total_border_ratio_list".  I want to find out how many ratios there are in it that
            // are between i and i+0.1.  To do this, I'll run through the list by using a for-each loop:

            int counter = 0; // This variable keeps track of how many ratios in the list are between i and i+0.1.

            for (Double temp: total_border_ratio_list)
            {
                if (temp >= i && temp < i+0.1)
                {
                    counter ++;
                }
            }

            // Now to display the results to the Screen:

            // I need to know the percentage of ratios out of the entire list:

            double percentage = (double) counter / total_border_ratio_list.size() * 100;

            if (percentage > 0)
            {
                System.out.println (percentage + "% of the ratios were between " + i + " and " + (i+0.1) + ", inclusive at the beginning (" + i + ")");
            }
        }

        // Total Border Average Ratio:

        for (int index = 0; index < total_border_ratio_list.size(); index ++)
        {
            total_border_average_ratio += total_border_ratio_list.get(index);
        }

        total_border_average_ratio /= total_border_ratio_list.size();

        System.out.println ("Average ratio overall at the border: " + total_border_average_ratio);

        // Test:

        System.out.print ("\n\n\n");

        System.out.println ("Results for the test of the \"how_long_overnight\" method:");

        System.out.println ("Average number of nights that Canadians spent on trip: " + (double) Person.can_counter / Person.can_trials);

        System.out.println ("Average number of nights that Americans spent on trip: " + (double) Person.usa_counter / Person.usa_trials);

    } // End of Main method.

    // Method that prints daily stats to the screen:

    public static void print_daily_stats(int day, int canadians_usa_border, int americans_usa_border, int canadians_can_border, int americans_can_border)
    {
        String white_space = "                                         ";
        // This variable is just a long white_space I can use for outputting to screen.

        // Now to display the data graphically, showing a diagram of the border:

        System.out.println ("\n\n\n\n\n\n\n\n\nDay " + day + ":\n");

        System.out.println ("        CANADA\n");

        // Now to put in the ratio for Canadians to Americans in Canada, to the far right of the screen:

        System.out.print(white_space + ((double) canadians_usa_border / americans_usa_border) + " CAN to USA");

        System.out.print("\n\n");

        System.out.println (canadians_usa_border + " CAN\n" + americans_usa_border + " USA");

        System.out.println ("-----------------------"); // Symbolizes the border.

        System.out.println ("               " + canadians_can_border + " CAN\n" + "               " + americans_can_border + " USA");

        System.out.print("\n");

        // Now to put in the ratio for Canadians to Americans in America, to the far right of the screen:

        System.out.print(white_space + ((double) canadians_can_border / americans_can_border) + " CAN to USA");

        System.out.print("\n");

        System.out.println ("        AMERICA");

        // Now to output the overall ratio of Canadians to Americans, on both sides of the border:

        double overall_canadians = canadians_can_border + canadians_usa_border;

        double overall_americans = americans_can_border + americans_usa_border;

        System.out.print ("Overall, the total ratio is " + overall_canadians / overall_americans + " Canadians to Americans.");

    }  // End of "print_daily_stats" method.

    public static String round_decimal_place(String num, int decimal_place)
    {
        // Rounds a String holding a number to the decimal place given by param: "decimal_place"

        String replacement = "";

        int decimal_index = num.indexOf(".");

        replacement += num.substring(0, decimal_index + 1);

        // Now to add the appropriate number of decimal places:

        replacement += (num.substring(decimal_index + 1, decimal_index + 1 + decimal_place));

        return replacement;
    }

    public static double round_decimal_place(double num, int decimal_place)
    {
        // This method rounds a given double to the given "decimal_place" param.  Same as above method, just with doubles:

        double replacement = num;

        replacement *= Math.pow(10, decimal_place);

        replacement = Math.round(replacement);

        replacement /= Math.pow(10, decimal_place);

        return replacement;
    }
} // End of Main Class.