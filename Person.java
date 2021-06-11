import java.lang.Math;

public class Person {
    // Test variables:
    // These variables test the "setHowLongOvernight" method, to ensure
    // the average result is equal to "averageLengthOvernight".
    private static int canCounter = 0;
    private static int canTrials = 0;
    private static int usaCounter = 0;
    private static int usaTrials = 0;

    // Variables - 2 types:

    // 1. variables that are independent of which country the person is from:

    private boolean isAway = false; // Holds "true" if the person is away in the other country,
    // and "false" if the person is at home.
    private boolean stayingOvernight = false; // Holds "true" if the person will be away overnight
    // during their trip, and "false" if they will just be gone for the day.
    private boolean atBorder = false; // Holds "true" if the person is crossing the border
    // (either way) and "false" if the person is not.
    private int dayCounter = 0; // Counts the number of days the person is away, when
    // they are going overnight.  When this var = "howLongOvernight", it is time
    // for the person to go home.

    // 2. variables that are dependent on which country the person is from:

    private final String citizenOf; // Holds "can" or "usa" for which country the person
    // is a citizen of.
    private final double chanceOfTrip; // Holds the percent chance for a normal day
    // that the person will go on a trip (0.47 would be 0.47% chance).
    private final double chanceOfOvernight; // Holds the percent chance that the
    // person will stay overnight in their trip (65 would be 65% chance).
    private final int averageLengthOvernight; // Holds the average length of days
    // that a person of this country will stay on a trip.  This variable is used to determine
    // the new value of the "howLongOvernight" variable, each time the person returns home.
    private int howLongOvernight; // Stores how many days the person will spend,
    // if they are staying overnight.

    public Person(String citizenOf, double chanceOfTrip, double chanceOfOvernight,
                  int averageLengthOvernight) {
        this.citizenOf = citizenOf;
        this.chanceOfTrip = chanceOfTrip;
        this.chanceOfOvernight = chanceOfOvernight;
        this.averageLengthOvernight = averageLengthOvernight;
        setHowLongOvernight(this.averageLengthOvernight);
    }

    public static int getCanCounter() {
        return canCounter;
    }

    public static int getCanTrials() {
        return canTrials;
    }

    public static int getUsaCounter() {
        return usaCounter;
    }

    public static int getUsaTrials() {
        return usaTrials;
    }

    public boolean getIsAway() {
        return isAway;
    }

    public boolean getStayingOvernight() {
        return stayingOvernight;
    }

    public boolean getAtBorder() {
        return atBorder;
    }

    public int getDayCounter() {
        return dayCounter;
    }

    public String getCitizenOf() {
        return citizenOf;
    }

    public double getChanceOfTrip() {
        return chanceOfTrip;
    }

    public double getChanceOfOvernight() {
        return chanceOfOvernight;
    }

    public int getHowLongOvernight() {
        return howLongOvernight;
    }

    public int getAverageLengthOvernight() {
        return averageLengthOvernight;
    }

    public void setHowLongOvernight(int average) {
        // This variable's value is determined by using the "averageLengthOvernight" variable.
        // It will be randomly assigned to some value in the range of
        // (0, 2 * averageLengthOvernight). The reason for not including 0 is that
        // a person can't stay for zero days overnight. And then the reason for not
        // including 2 * averageLengthOvernight is to keep the distribution fair.
        do {
            howLongOvernight = (int) (Math.round(Math.random() * 2 * average));
        }
        while (howLongOvernight == 0 || howLongOvernight == 2 * average);

        // Tests:
        if (citizenOf.equals("can")) {
            canTrials ++;
            canCounter += howLongOvernight;
        }
        else {
            usaTrials ++;
            usaCounter += howLongOvernight;
        }
    }

    public void setIsAway(boolean isAway) {
        this.isAway = isAway;
    }

    public void flipIsAway() {
        isAway = !isAway;
    }

    public void setStayingOvernight(boolean stayingOvernight) {
        this.stayingOvernight = stayingOvernight;
    }

    public void flipStayingOvernight() {
        stayingOvernight = !stayingOvernight;
    }

    public void setAtBorder(boolean atBorder) {
        this.atBorder = atBorder;
    }

    public void flipAtBorder() {
        atBorder = !atBorder;
    }

    public void setDayCounter(int dayCounter) {
        this.dayCounter = dayCounter;
    }

    public void increaseDayCounter() {
        dayCounter ++;
    }
}