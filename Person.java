import java.util.Scanner;
import java.lang.Math;
import java.util.ArrayList;

// CONTINUE HERE - Update code style for this file:

public class Person
{
    // Test variables:

    // These variables test the "set_how_long_overnight" method, to ensure the average result is equal to "average_length_overnight".

    protected static int can_counter = 0;

    protected static int can_trials = 0;

    protected static int usa_counter = 0;

    protected static int usa_trials = 0;

    // Variables - 2 types:

    // 1. variables that are independent of which country the person is from:

    private boolean is_away = false; // Holds "true" if the person is away in the other country, and "false" if the person is at home.

    private boolean staying_overnight = false; // Holds "true" if the person will be away overnight during their trip, and "false" if they will just be gone for the day.

    private boolean at_border = false; // Holds "true" if the person is crossing the border (either way) and "false" if the person is not.

    private int day_counter = 0; // Counts the number of days the person is away, when they are going overnight.  When this var = "how_long_overnight", it is time
    // for the person to go home.

    // 2. variables that are dependent on which country the person is from:

    private String citizen_of; // Holds "can" or "usa" for which country the person is a citizen of.

    private double chance_of_trip; // Holds the percent chance for a normal day that the person will go on a trip (0.47 would be 0.47% chance). 

    private double chance_of_overnight; // Holds the percent chance that the person will stay overnight in their trip (65 would be 65% chance).

    private int average_length_overnight; // Holds the average length of days that a person of this country will stay on a trip.  This variable is used to determine
    // the new value of the "how_long_overnight" variable, each time the person returns home.

    private int how_long_overnight; // Stores how many days the person will spend, if they are staying overnight.

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Constructor:

    public Person(String citizen_of0, double chance_of_trip0, double chance_of_overnight0, int average_length_overnight0)
    {
        citizen_of = citizen_of0;

        chance_of_trip = chance_of_trip0;

        chance_of_overnight = chance_of_overnight0;

        average_length_overnight = average_length_overnight0;

        set_how_long_overnight(average_length_overnight); // sets the how_long_overnight variable.
    }

    // ACCESSOR METHODS:

    public boolean get_is_away()
    {
        // returns the value of the "is_away" variable:

        return is_away;
    }

    public boolean get_staying_overnight()
    {
        // returns the value of the "staying_overnight" variable:

        return staying_overnight;
    }

    public boolean get_at_border()
    {
        // returns the value of the "at_border" variable:

        return at_border;
    }

    public int get_day_counter()
    {
        // returns the value of the "day_counter" variable:

        return day_counter;
    }

    public String get_citizen_of()
    {
        // returns the value of the "citizen_of" variable:

        return citizen_of;
    }

    public double get_chance_of_trip()
    {
        // returns the value of the "chance_of_trip" variable:

        return chance_of_trip;
    }

    public double get_chance_of_overnight()
    {
        // returns the value of the "chance_of_overnight" variable:

        return chance_of_overnight;
    }

    public int get_how_long_overnight()
    {
        // returns the value of the "how_long_overnight" variable:

        return how_long_overnight;
    }

    public int get_average_length_overnight()
    {
        // returns the value of the "average_length_overnight" variable:

        return average_length_overnight;
    }

    // MUTATOR METHODS:

    public void set_how_long_overnight(int average)
    {
        // This variable's value is determined by using the "average_length_overnight" variable:

        double temp = Math.round(Math.random() * (2 * average));

        how_long_overnight = (int) temp;

        // Now, if "how_long_overnight" equals 0 or the max, I want to redo the method.  This is because someone staying
        // "overnight" cannot stay for zero days.

        if (how_long_overnight == 0 || how_long_overnight == 2 * average)
        {
            // Call the method again:

            set_how_long_overnight(average);
        }

        else
        {

            // Tests:

            if (citizen_of.equals("can"))
            {
                can_trials ++;

                can_counter += how_long_overnight;
            }

            else // meaning citizen_of = "usa":
            {
                usa_trials ++;

                usa_counter += how_long_overnight;
            }
        }
    }

    public void set_is_away(boolean value)
    {
        // sets the "is_away" variable to value:

        is_away = value;
    }

    public void flip_is_away()
    {
        // flips the value of the "is_away" boolean variable:

        is_away = flip(is_away);
    }

    public void set_staying_overnight(boolean value)
    {
        // sets the "staying_overnight" variable to value:

        staying_overnight = value;
    }

    public void flip_staying_overnight()
    {
        // flips the value of the "staying_overnight" boolean variable:

        staying_overnight = flip(staying_overnight);
    }

    public void set_at_border(boolean value)
    {
        // sets the "at_border" variable to value:

        at_border = value;
    }

    public void flip_at_border()
    {
        // flips the value of the "at_border" boolean variable:

        at_border = flip(at_border);
    }

    public void set_day_counter(int value)
    {
        // sets the "day_counter" variable to value:

        day_counter = value;
    }

    public void increase_day_counter()
    {
        // increases the value of day_counter by 1:

        day_counter ++;
    }

    // Methods used by this class:

    public boolean flip(boolean variable)
    {
        // This method returns the opposite value of the boolean variable sent in:

        return !variable;
    }
}