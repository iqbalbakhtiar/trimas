/**
 * Feb 17, 2009 11:05:23 AM
 * com.siriuserp.sdk.utility
 * ChartGenerator.java
 */
package com.siriuserp.sdk.utility;

import java.io.File;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * @author Muhammad Khairullah
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ChartGenerator
{
	public static final String generateBarChart(String base, String fileName, String title, String category, String value, DefaultCategoryDataset dataset)
	{
		String chartPath = "/charts/" + fileName + ".png";
		try
		{
			File file = new File(base + chartPath);
			for (File files : new File(base + "/charts/").listFiles())
			{
				if (files.getName().startsWith(fileName))
					files.delete();
			}

			JFreeChart chart = ChartFactory.createBarChart(title, category, value, dataset, PlotOrientation.VERTICAL, true, true, false);
			ChartUtilities.saveChartAsPNG(file, chart, 800, 250);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return chartPath;
	}
/*
	public static final String generatePieChart(String base, String fileName, String title, DefaultPieDataset dataset)
	{
		String chartPath = "/charts/" + fileName + new Date().getTime() + ".png";
		try
		{
			File file = new File(base + chartPath);
			for (File files : new File(base + "/charts/").listFiles())
			{
				if (files.getName().startsWith(fileName))
					files.delete();
			}
			JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
			ChartUtilities.saveChartAsPNG(file, chart, 640, 250);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return chartPath;
	}
	
	public static final String generateMultiplePieChart(String base,String fileName,String title,CategoryDataset dataset)
	{
		String chartPath="/charts/"+fileName+new Date().getTime()+".png";
		try
		{
			File file = new File(base + chartPath);
			for (File files : new File(base + "/charts/").listFiles())
			{
				if (files.getName().startsWith(fileName))
					files.delete();
			}
			JFreeChart chart = ChartFactory.createMultiplePieChart(title,dataset, Table,true, false, false);
			ChartUtilities.saveChartAsPNG(file, chart, 640,480);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return chartPath;
	}
	*/
}
