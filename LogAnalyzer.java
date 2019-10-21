/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
import java.util.ArrayList;
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    private int[] yearCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[31];
        monthCounts = new int[12];
        yearCounts = new int[21];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * Create an object to analyze all web accesses based off filename
     * @param fileName - file to analyze given as string. 
     */
    public LogAnalyzer(String fileName)
    {
        hourCounts = new int[24];
        dayCounts = new int[31];
        monthCounts = new int[12];
        yearCounts = new int[21];
        reader = new LogfileReader(fileName);
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    /**
     * Analyze the daily data from the log file
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }
    /**
     * Analyze the monthly data from the log file
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext())
        {
          LogEntry entry = reader.next();
          int month = entry.getMonth();
          monthCounts[month]++;
        }
    }
    public void analyzeYearlyData()
    {
        LogEntry entry = reader.next();
          int year = entry.getYear();
          yearCounts[year]++;
    }
    public void analyzeAll()
    {
        analyzeYearlyData();
        analyzeMonthlyData();
        analyzeDailyData();
        analyzeHourlyData();
    }
    /**
     * @returns hour counts for each element in the array 
     */
    public int numberOfAccesses ()
    {
        int total = 0; 
        for ( int a : hourCounts)
        {
            total += a;
        }
        return total;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    /**
     * index of the most recent busiest single hour 
     * @return index
     */
    public int buisiestHour ()
    {
        int busy =0;
        int index=0;
        for (int a : hourCounts)
        {
            if (busy <= hourCounts[a])
            {
                busy = hourCounts[a];
                index = a;
            }
        }
        return index;
    }
    /**
     * gives back the most recent quitest hour 
     * @return index
     */
    public int quietestHour()
    {
        int quiet = hourCounts[0];
        int index = 0;
        for (int a : hourCounts)
        {
            if (quiet >= hourCounts[a])
            {
                quiet = hourCounts[a];
                index = a;
            }
        }
        return index;
    }
    /**
     * busiest 2 hour period
     * @return index of starting hour of two hour period
     */
    public int twoHourIndex()
    {
        int index =0;
        //handle special case of 11p,
        int twoHour = 0;
        int specialCase = hourCounts[23]+hourCounts[0];
        for (int a =0; a < hourCounts.length -1;a++ )
        {
            
            if (twoHour <= hourCounts[a] + hourCounts[a+1])
            {
                twoHour = hourCounts[a];
                index = a;
            }
        }
        if (twoHour <= specialCase)
        {
            index = 23;
        } 
        return index;
    }
    public int quietestDay()
    {
         int quiet = dayCounts[0];
        int index = 0;
        for (int a : dayCounts)
        {
            if (quiet >= dayCounts[a])
            {
                quiet = dayCounts[a];
                index = a;
            }
        }
        return index;
   
    }
    public int busiestDay()
    {
        return 0;
    }
    public int totalAccessesPerMonth()
    {
        return 0;
    }
    public int quietestMonth()
    {
        return 0;
    }
    public int busiestMonth()
    {
        return 0;
    }
    public int averageAcccessesPerMonth()
    {
        return 0;
    }
    public void printTrends(){}
  
}
