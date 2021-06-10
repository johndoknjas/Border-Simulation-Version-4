import java.lang.Math;

// CONTINUE HERE - convert all to camel case.

public class Person {
    // Test variables:
    // These variables test the "set_how_long_overnight" method, to ensure
    // the average result is equal to "average_length_overnight".
    protected static int can_counter = 0;
    protected static int can_trials = 0;
    protected static int usa_counter = 0;
    protected static int usa_trials = 0;

    // Variables - 2 types:

    // 1. variables that are independent of which country the person is from:

    private boolean is_away = false; // Holds "true" if the person is away in the other country,
    // and "false" if the person is at home.
    private boolean staying_overnight = false; // Holds "true" if the person will be away overnight
    // during their trip, and "false" if they will just be gone for the day.
    private boolean at_border = false; // Holds "true" if the person is crossing the border
    // (either way) and "false" if the person is not.
    private int day_counter = 0; // Counts the number of days the person is away, when
    // they are going overnight.  When this var = "how_long_overnight", it is time
    // for the person to go home.

    // 2. variables that are dependent on which country the person is from:

    private final String citizen_of; // Holds "can" or "usa" for which country the person
    // is a citizen of.
    private final double chance_of_trip; // Holds the percent chance for a normal day
    // that the person will go on a trip (0.47 would be 0.47% chance).
    private final double chance_of_overnight; // Holds the percent chance that the
    // person will stay overnight in their trip (65 would be 65% chance).
    private final int average_length_overnight; // Holds the average length of days
    // that a person of this country will stay on a trip.  This variable is used to determine
    // the new value of the "how_long_overnight" variable, each time the person returns home.
    private int how_long_overnight; // Stores how many days the person will spend,
    // if they are staying overnight.

    public Person(String citizen_of, double chance_of_trip, double chance_of_overnight,
                  int average_length_overnight) {
        this.citizen_of = citizen_of;
        this.chance_of_trip = chance_of_trip;
        this.chance_of_overnight = chance_of_overnight;
        this.average_length_overnight = average_length_overnight;
        set_how_long_overnight(this.average_length_overnight);
    }

    public boolean get_is_away() {
        return is_away;
    }

    public boolean get_staying_overnight() {
        return staying_overnight;
    }

    public boolean get_at_border() {
        return at_border;
    }

    public int get_day_counter() {
        return day_counter;
    }

    public String get_citizen_of() {
        return citizen_of;
    }

    public double get_chance_of_trip() {
        return chance_of_trip;
    }

    public double get_chance_of_overnight() {
        return chance_of_overnight;
    }

    public int get_how_long_overnight() {
        return how_long_overnight;
    }

    public int get_average_length_overnight() {
        return average_length_overnight;
    }

    public void set_how_long_overnight(int average) {
        // This variable's value is determined by using the "average_length_overnight" variable.
        // It will be randomly assigned to some value in the range of
        // (0, 2 * average_length_overnight). The reason for not including 0 is that
        // a person can't stay for zero days overnight. And then the reason for not
        // including 2 * average_length_overnight is to keep the distribution fair.
        do {
            how_long_overnight = (int) (Math.round(Math.random() * 2 * average));
        }
        while (how_long_overnight == 0 || how_long_overnight == 2 * average);

        // Tests:
        if (citizen_of.equals("can")) {
            can_trials ++;
            can_counter += how_long_overnight;
        }
        else {
            usa_trials ++;
            usa_counter += how_long_overnight;
        }
    }

    public void set_is_away(boolean is_away) {
        this.is_away = is_away;
    }

    public void flip_is_away() {
        is_away = !is_away;
    }

    public void set_staying_overnight(boolean staying_overnight) {
        this.staying_overnight = staying_overnight;
    }

    public void flip_staying_overnight() {
        staying_overnight = !staying_overnight;
    }

    public void set_at_border(boolean at_border) {
        this.at_border = at_border;
    }

    public void flip_at_border() {
        at_border = !at_border;
    }

    public void set_day_counter(int day_counter) {
        this.day_counter = day_counter;
    }

    public void increase_day_counter() {
        day_counter ++;
    }
}