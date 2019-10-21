import java.io.*;
import java.util.*;

/**
 * A class for creating log files of random data.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version    2016.02.29
 * @author Nicholas Lindgren
 * @version 20/21/2019
 */
public class LogfileCreator
{
    private Random rand;

    /**
     * Create log files.
     */
    public LogfileCreator()
    {
        rand = new Random();
    }
    
    /**
     * Create a file of random log entries.
     * @param filename The file to write.
     * @param numEntries How many entries.
     * @return true if successful, false otherwise.
     */
    public boolean createFile(String filename, int numEntries)
    {
        boolean success = false;
        
        if(numEntries > 0) {
            try (FileWriter writer = new FileWriter(filename)) {
                LogEntry[] entries = new LogEntry[numEntries];
                for(int i = 0; i < numEntries; i++) {
                    entries[i] = createEntry();
                }
                Arrays.sort(entries);
                for(int i = 0; i < numEntries; i++) {
                    writer.write(entries[i].toString());
                    writer.write('\n');
                }
                
                success = true;
            }
            catch(IOException e) {
                System.err.println("There was a problem writing to " + filename);
            }
                
        }
        return success;
    }
    
    /**
     * Create a single (random) entry for a log file.
     * @return A log entry containing random data.
     */
    public LogEntry createEntry()
    {
        int year = 2016;
        int month = 1 + rand.nextInt(12);
        // Avoid the complexities of days-per-month.
        int day = 1 + rand.nextInt(28);
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);
        return new LogEntry(year, month, day, hour, minute);
    }
    /**
     * Create a single (random) entry for a log file.
     * @param yearMin
     * @param yearMax
     * @return A log entry containing random data.
     */
    private LogEntry createEntry(int yearMin, int yearMax)
    {
      
        int year = rand.nextInt(yearMax - yearMin) +yearMin;// 2009inclusive - 2019 exclusive
        int month = 1 + rand.nextInt(12);
        // Avoid the complexities of days-per-month.
        int day = 1 + rand.nextInt(28);
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);
        return new LogEntry(year, month, day, hour, minute);
    }
  /**
     * Create a file of random log entries.
     * @param filename The file to write.
     * @param yearMin the min year to evaluate
     * @param yearMax the max year to evaluate
     * @param numYears how many times to repeat
     * @return true if successful, false otherwise.
     */
    public boolean createFile(String filename, int numEntries, int yearMin, int yearMax)
    {
        boolean success = false;
        int numYears = yearMax - yearMin;
        if(numEntries > 0&& numYears>0 && numYears <=10 ) {
            try (FileWriter writer = new FileWriter(filename)) {
                LogEntry[] entries = new LogEntry[numEntries*numYears];
                
                    for(int i = 0; i < numEntries*numYears; i++) 
                    {
                        entries[i] = createEntry(yearMin, yearMax);
                    }
                
                Arrays.sort(entries);
                
                   for(int i = 0; i < numEntries; i++) {
                    writer.write(entries[i].toString());
                    writer.write('\n');
                } 
                
                
                
                success = true;
            }
            catch(IOException e) {
                System.err.println("There was a problem writing to " + filename);
            }
                
        }
        return success;
    }

}
