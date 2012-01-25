package org.inftel.tms.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author agumpg
 */
public class StatisticsDateUtil {

    /**
     * Obtiene el primer día del mes actual
     *
     * @return fecha del primer día del mes actual
     */
    public static Date getFirstDayToMonth() {

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMinimum(Calendar.DAY_OF_MONTH),
                cal.getMinimum(Calendar.HOUR_OF_DAY),
                cal.getMinimum(Calendar.MINUTE),
                cal.getMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * Obtiene el último día del mes actual
     *
     * @return fecha del último día del mes actual
     */
    public static Date getLastDayToMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                cal.getMaximum(Calendar.HOUR_OF_DAY),
                cal.getMaximum(Calendar.MINUTE),
                cal.getMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * Calcula la fecha 1 día anterior a la fecha del sistema
     *
     * @return fecha de ayer
     */
    public static Date getYesterday() {
        int DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

        Date toDay = Calendar.getInstance().getTime();
        Date prev = new Date(toDay.getTime() - DAY_IN_MILLIS);

        return prev;
    }
}
