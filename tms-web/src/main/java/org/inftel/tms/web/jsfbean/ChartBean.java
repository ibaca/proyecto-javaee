/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.inftel.tms.web.jsfbean;

/**
 *
 * @author Administrador
 */
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;

import org.inftel.tms.domain.AffectedType;
import org.inftel.tms.services.AffectedFacadeRemote;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
  
public class ChartBean implements Serializable { 
    
    @EJB
    private AffectedFacadeRemote affectedFacade;
  
    private CartesianChartModel categoryModel;  
  
    private CartesianChartModel linearModel;  
  
    public ChartBean() {  
        createCategoryModel();  
        createLinearModel();  
    }  
  
    public CartesianChartModel getCategoryModel() {  
        return categoryModel;  
    }  
  
    public CartesianChartModel getLinearModel() {  
        return linearModel;  
    }  
  
    private void createCategoryModel() {  
        categoryModel = new CartesianChartModel();  
  
        ChartSeries boys = new ChartSeries();  
        boys.setLabel("Boys");  
  
        boys.set("2004", 120);  
        boys.set("2005", 100);  
        boys.set("2006", 44);  
        boys.set("2007", 150);  
        boys.set("2008", 25);  
  
        ChartSeries girls = new ChartSeries();  
        girls.setLabel("Girls");  
  
        girls.set("2004", 52);  
        girls.set("2005", 60);  
        girls.set("2006", 110);  
        girls.set("2007", 135);  
        girls.set("2008", 120);  
  
        categoryModel.addSeries(boys);  
        categoryModel.addSeries(girls);  
    }  
  
    private void createLinearModel() {  
        linearModel = new CartesianChartModel();  
  
        LineChartSeries series1 = new LineChartSeries();  
        series1.setLabel("Series 1");  
  
        series1.set(1, 2);  
        series1.set(2, 1);  
        series1.set(3, 3);  
        series1.set(4, 6);  
        series1.set(5, 8);  
  
        LineChartSeries series2 = new LineChartSeries();  
        series2.setLabel("Series 2");  
        series2.setMarkerStyle("diamond");  
  
        series2.set(1, 6);  
        series2.set(2, 3);  
        series2.set(3, 2);  
        series2.set(4, 7);  
        series2.set(5, 9);  
  
        linearModel.addSeries(series1);  
        linearModel.addSeries(series2);  
    } 
    
    /**
     * Devuelve la lista de tipos de afectados junto el valor porcentual registrados en el sistema.
     */
    private Map<String, Long> calculateAffectedPieData() {
        Map<String, Long> result = new TreeMap<String, Long>();
        Long total = 0l;
        for (AffectedType type : AffectedType.values()) {
            long count = affectedFacade.countByType(type);
            total = total + count;
            result.put(type.name().toLowerCase(), count);
        }
        if (total > 0) {
            for (String name : result.keySet()) {
                result.put(name, result.get(name) / total);

            }
        }
        return result;
    }
}  