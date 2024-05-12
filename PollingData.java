
/**
 * Class to hold each Polling Data.
 * @author Dennis Yiaile
 */
public class PollingData implements Comparable<PollingData> {
    // instance variables for the PollingData.
    /** Last Name. */
    private String lastName;
    /** Full Name. */
    private String fullName;
    /** Percent. */
    private double percent;

    /** Constructor for the Polling Data class. 
     * @param lastName - candidate last Name
     * @param fullName - candidate full Name
     * @param percent - candidate polling data percentage
    */
    public PollingData(String lastName, String fullName, double percent) {
        this.lastName = lastName;
        this.fullName = fullName;
        this.percent = percent;
    }

    /**
     * Compare two last names.
     * @param otherPolling - other name that's passed in.
     * @return int - 0 if equals, 1 if greater than else -1 
     */
    public int compareTo(PollingData otherPolling) {
        double diff = this.percent - otherPolling.percent;
        boolean found = this.lastName.compareTo(otherPolling.lastName) == 0;
        if (found) {
            return 0;
        } else if (diff < 0) {
            return -1;
        } else {
            return 1;
        }
    }
    
    /**
     * Return the last name.
     * @return String - last name
     */
    public String lastName() {
        return lastName;
    }

    /**
     * Return the full name.
     * @return - full name
     */
    public String fullName() {
        return fullName;
    }

    /**
     * Return the polling data percent.
     * @return double - polling data percentage
     */
    public double percent() {
        return percent;
    }

    /**
     * Return the fullName and percentage as a string.
     * @return String - fullName and percent
     */
    @Override
    public String toString() {
        return fullName() + ":" + Double.toString(percent());  
    }
}
