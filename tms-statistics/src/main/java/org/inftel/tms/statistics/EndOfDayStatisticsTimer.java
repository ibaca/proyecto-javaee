package org.inftel.tms.statistics;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.inftel.tms.statistics.StatisticDataPeriod.ANNUAL;
import static org.inftel.tms.statistics.StatisticDataPeriod.DAYLY;
import static org.inftel.tms.statistics.StatisticDataPeriod.MONTHLY;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.apache.commons.lang3.time.DateUtils;
import org.inftel.tms.domain.AffectedType;
import org.inftel.tms.domain.AlertType;
import org.inftel.tms.services.AffectedFacadeRemote;
import org.inftel.tms.services.AlertFacadeRemote;
import org.inftel.tms.utils.StatisticsDateUtil;

/**
 * Algunas estadisticas podrian generarse en el End Of Day, por ejemplo podrian registarse
 * diariamente el numero de alertas por tipo que se hay activas y que hay inactivas.
 * 
 * @author agumpg
 */
@Stateless
@LocalBean
public class EndOfDayStatisticsTimer {

    @EJB
    private StatisticDataFacade statisticsDataFacade;
    @EJB
    private AlertFacadeRemote alertFacade;
    @EJB
    private AffectedFacadeRemote affectedFacade;

    @Schedule(minute = "0", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "0", dayOfWeek = "*")
    public void processDialyStatistics() {

        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(StatisticsDateUtil.getYesterday());

        System.out.println("Timer event: " + new Date());

        // Generar estadisticas diarias de tipo de alertas recibidas ayer
        for (AlertType type : AlertType.values()) {
            Date from = DAYLY.beginsAt(yesterday).getTime();
            Date to = DAYLY.endsAt(yesterday).getTime();
            int statCount = alertFacade.countByType(type, from, to);
            String statName = "alert.type." + type.name().toLowerCase();

            updateStatistic(statName, yesterday, statCount);
        }

        // Generar estadisticas diarias de tipo de afectados registrados en el sistema
        for (AffectedType type : AffectedType.values()) {
            int statCount = affectedFacade.countByType(type);
            String statName = "affected.type." + type.name().toLowerCase();

            updateStatistic(statName, yesterday, statCount);
        }

    }

    private void updateStatistic(String statisticName, Calendar date, int value) {
        // Se calcula el dia de hoy para comparar
        Calendar today = Calendar.getInstance();

        // Actualización de diarios de Alertas
        saveStatisticData(statisticName, DAYLY, date, value);

        // Si ayer fue un mes diferente
        if (today.get(MONTH) != date.get(MONTH)) {
            int count = statisticsDataFacade.sumStatictics(statisticName, DAYLY,
                    MONTHLY.endsAt(date).getTime(), MONTHLY.beginsAt(date).getTime());
            saveStatisticData(statisticName, MONTHLY, date, (long) count);
        }

        // Si se ha producido un cambio de año actualizamos históricos anuales
        if (today.get(YEAR) != date.get(YEAR)) {
            int count = statisticsDataFacade.sumStatictics(statisticName, MONTHLY,
                    ANNUAL.endsAt(date).getTime(), ANNUAL.beginsAt(date).getTime());
            saveStatisticData(statisticName, ANNUAL, date, (long) count);
        }
    }

    private void saveStatisticData(String name, StatisticDataPeriod period, Calendar date,
            long value) {
        StatisticData sd = new StatisticData();

        sd.setName(name);
        sd.setPeriodType(period);
        sd.setPeriodDate(period.beginsAt(date).getTime());
        sd.setDataCount(value);

        statisticsDataFacade.create(sd);
    }
}
