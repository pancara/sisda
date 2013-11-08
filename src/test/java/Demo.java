import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: pancara
 * Date: 9/21/12
 * Time: 11:22 PM
 */
public class Demo {

    public static void main(String[] args) {

        // set the date
        Calendar cal = Calendar.getInstance();
        Date today = new Date();
        cal.setTime(today);
        System.out.println(cal.get(Calendar.MONTH));

        // "calculate" the start date of the week
        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK,
                first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

        // and add six days to the end date
        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        // print the result
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(first.getTime()) + " -> " +
                df.format(last.getTime()));
    }
}