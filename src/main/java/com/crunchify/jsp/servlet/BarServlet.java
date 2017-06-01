/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;


import edu.co.sergio.mundo.dao.DepartamentoDAO;
import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class BarServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        	response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);

	}

	public JFreeChart getChart() {
		
            DepartamentoDAO dao= new DepartamentoDAO();
            ArrayList Result= new ArrayList();
            Result=dao.consulta1();
            int x= (Integer)Result.get(0);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue((Integer)Result.get(0), (String)Result.get(1), "451");
        dataset.addValue((Integer)Result.get(2), (String)Result.get(3), "851");
//        dataset.addValue(10, "2", "362");
//        dataset.addValue(5,  "2",  "142"); 
        
 JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart Demo 3",       // chart title
            "Colmena",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // the plot orientation
            false,                    // include legend
            true,
            false
        );

        chart.setBackgroundPaint(Color.lightGray);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setNoDataMessage("NO DATA!");

        CategoryItemRenderer renderer = new CustomRenderer(
            new Paint[] {Color.red, Color.blue, Color.green,
                Color.yellow, Color.orange, Color.cyan,
                Color.magenta, Color.blue}
        );

        renderer.setItemLabelsVisible(true);
        ItemLabelPosition p = new ItemLabelPosition(
            ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, 45.0
        );
        renderer.setPositiveItemLabelPosition(p);
        plot.setRenderer(renderer);

        // change the margin at the top of the range axis...
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0.15);
        rangeAxis.setUpperMargin(0.15);

        return chart;
		
	}

}
