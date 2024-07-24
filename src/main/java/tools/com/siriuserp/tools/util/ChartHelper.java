package com.siriuserp.tools.util;

import java.awt.Paint;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.siriuserp.sdk.dm.Party;

@SuppressWarnings("unchecked")
public class ChartHelper
{
	public static final void processSalesDailyCompanyFacility(String basePath, Map<String, Object> model)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		List<Map<String, Object>> list = (List<Map<String, Object>>) model.get("reports");

		for (Map<String, Object> map : list)
		{
			Party org = (Party) map.get("organization");
			BigDecimal totalSales = (BigDecimal) map.get("total");
			dataset.addValue(totalSales, "Total Sales", org.getCode());
		}

		try
		{

			String chartPath = "/charts/" + "salesdaily" + ".png";
			try
			{
				File file = new File(basePath + chartPath);
				for (File files : new File(basePath + "/charts/").listFiles())
				{
					if (files.getName().startsWith("salesdaily"))
						files.delete();
				}

				JFreeChart chart = ChartFactory.createBarChart3D("Sales Daily", " ", "Sales", dataset, PlotOrientation.HORIZONTAL, false, true, false);

				CategoryPlot plot = chart.getCategoryPlot();
				CategoryItemRenderer renderer = new CustomRenderer();

				plot.setRenderer(renderer);

				ChartUtilities.saveChartAsPNG(file, chart, 1024, 480);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static final void processProfitLoss(String basePath, Map<String, Object> model)
	{
	}

	public static final void processTotalIncome(String basePath, Map<String, Object> model)
	{
	}
}

class CustomRenderer extends BarRenderer3D
{
	private static final long serialVersionUID = -7198267198966124566L;

	public CustomRenderer()
	{
	}

	public Paint getItemPaint(final int row, final int column)
	{
		// returns color depending on y coordinate.
		CategoryDataset dataset = getPlot().getDataset();
		double value = dataset.getValue(row, column).doubleValue();

		if (value <= 0)
		{
			return ChartColor.LIGHT_RED;
		} else
		{
			return ChartColor.LIGHT_BLUE;
		}
	}
}
