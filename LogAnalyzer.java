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
    private int[][] monthYear;
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
        monthYear = new int[10][31];
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
        yearCounts = new int[10];
        monthYear = new int[10][31];
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
    /**
     * Analyze the yearly data from the log file
     */
    public void analyzeYearlyData()
    {
        LogEntry entry = reader.next();
          int year = entry.getYear();
          yearCounts[year%10]++;
    }
    /**
     * Analyze the month and year data from the log file
     */
    public void analyzeMontYearData()
    {
        while(reader.hasNext())
        {
          LogEntry entry = reader.next();
          int year = entry.getYear();
          int month = entry.getMonth();
          int index = year %10;
          monthYear[index][month]++;
        }
    }
    /**
     * Analyze all data from the log file
     */
    public void analyzeAll()
    {
        analyzeYearlyData();
        analyzeMonthlyData();
        analyzeDailyData();
        analyzeHourlyData();
        analyzeMontYearData();
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
    /**
     * quietest day
     * @return index of quietest day
     */
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
    /**
     * busiest day
     * @return index of busiest day
     */
    public int busiestDay()
    {
        int busy =0;
        int index=0;
        for (int a : dayCounts)
        {
            if (busy <= dayCounts[a])
            {
                busy = dayCounts[a];
                index = a;
            }
        }
        return index;
    }
    /**
     * total access for month
     * @param month - specified month 
     * @return number of access at given index
     */
    public int totalAccessesPerMonth(int month)
    {
         int total = monthCounts[month];
        
        return total;
    }
    /**
     * quetest month
     * @return index of quetest month
     */
    public int quietestMonth()
    {
        
         int quiet = monthCounts[0];
        int index = 0;
        for (int a : monthCounts)
        {
            if (quiet >= monthCounts[a])
            {
                quiet = monthCounts[a];
                index = a;
            }
        }
        return index;
    }
    /**
     * busiest month
     * @return index of buisest month
     */
    public int busiestMonth()
    {
        int busy =0;
        int index=0;
        for (int a : monthCounts)
        {
            if (busy <= monthCounts[a])
            {
                busy = monthCounts[a];
                index = a;
            }
        }
        return index;
    }
    /**
     * averages access per month
     * @return average accesses 
     */
    public int averageAcccessesPerMonth()
    {
        return numberOfAccesses() / 12; 
    }
    /**
     * prints monthYearly trend
     * 
     */
    public void printTrend()
    {
        System.out.println("Year: Month");
        for (int a =0; a<10; a++)
        { 
            for (int b =0; b<31; b++)
            {
                System.out.println(""+ monthYear[a][b]);
                
            }
            System.out.println("\n");
        }   
    }
  
}
