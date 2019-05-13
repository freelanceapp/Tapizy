package infobite.ibt.tapizy.utils;

import java.util.Calendar;

public class TimeUtils {

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month += 1;

        /*"27-04-2019 07:00:00";*/
        String strDate = String.valueOf(date);
        String strMonth = String.valueOf(month);

        if (date < 10) {
            strDate = "0" + strDate;
        }
        if (month < 10) {
            strMonth = "0" + strMonth;
        }

        String strHour = String.valueOf(currentHour);
        String strMinute = String.valueOf(currentMinute);
        String strSecond = String.valueOf(second);
        if (currentHour < 10) {
            strHour = "0" + strHour;
        }
        if (currentMinute < 10) {
            strMinute = "0" + strMinute;
        }
        if (second < 10) {
            strSecond = "0" + strSecond;
        }
        String dateTime = strDate + "-" + strMonth + "-" + year + " " + strHour + ":" + strMinute + ":" + strSecond;
        return dateTime;
    }

    public static String getSecondDateTime() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        month += 1;

        /*"27-04-2019 07:00:00";*/
        String strDate = String.valueOf(date);
        String strMonth = String.valueOf(month);

        if (date < 10) {
            strDate = "0" + strDate;
        }
        if (month < 10) {
            strMonth = "0" + strMonth;
        }

        String strHour = String.valueOf(currentHour);
        String strMinute = String.valueOf(currentMinute);
        String strSecond = String.valueOf(second);
        if (currentHour < 10) {
            strHour = "0" + strHour;
        }
        if (currentMinute < 10) {
            strMinute = "0" + strMinute;
        }
        if (second < 10) {
            strSecond = "0" + strSecond;
        }
        String dateTime = year + "-" + strMonth + "-" + strDate + " " + strHour + ":" + strMinute + ":" + strSecond;
        return dateTime;
    }
}
