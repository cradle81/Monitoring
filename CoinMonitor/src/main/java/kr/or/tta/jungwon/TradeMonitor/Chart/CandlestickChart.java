package kr.or.tta.jungwon.TradeMonitor.Chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Timestamp;
import java.text.*;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import kr.or.tta.jungwon.TradeMonitor.db.vo.KRWCoinVO;

public class CandlestickChart extends JPanel  {
	    private static final DateFormat READABLE_TIME_FORMAT = new SimpleDateFormat("kk:mm:ss");
	    private OHLCSeries ohlcSeries;
	    private TimeSeries volumeSeries;
	    
	    private static final int MIN = 60000;
	    
	    
	    private int timeInterval = 1;
	    private KRWCoinVO candelChartIntervalFirstPrint = null;
		private double open = 0.0;
		private double close = 0.0;
		private double low = 0.0;
		private double high = 0.0;
		private long volume = 0;


		public CandlestickChart(String title) {
			// Create new chart
			final JFreeChart candlestickChart = createChart(title);
			// Create new chart panel 
			final ChartPanel chartPanel = new ChartPanel(candlestickChart);
			chartPanel.setPreferredSize(new java.awt.Dimension(1200, 500));
			// Enable zooming
			chartPanel.setMouseZoomable(true);
			chartPanel.setMouseWheelEnabled(true);
			add(chartPanel, BorderLayout.CENTER);
		}
		private JFreeChart createChart(String chartTitle) {
			/**
			 * Creating candlestick subplot
			 */
			// Create OHLCSeriesCollection as a price dataset for candlestick chart
			OHLCSeriesCollection candlestickDataset = new OHLCSeriesCollection();
			ohlcSeries = new OHLCSeries("Price");
			candlestickDataset.addSeries(ohlcSeries);
			// Create candlestick chart priceAxis
			NumberAxis priceAxis = new NumberAxis("Price");
			priceAxis.setAutoRangeIncludesZero(false);
			// Create candlestick chart renderer
			CandlestickRenderer candlestickRenderer = new CandlestickRenderer(CandlestickRenderer.WIDTHMETHOD_AVERAGE,
					false, new CustomHighLowItemLabelGenerator(new SimpleDateFormat("kk:mm"), new DecimalFormat("0.000")));
			// Create candlestickSubplot
			XYPlot candlestickSubplot = new XYPlot(candlestickDataset, null, priceAxis, candlestickRenderer);
			candlestickSubplot.setBackgroundPaint(Color.white);
			
			/**
			 * Creating volume subplot
			 */
			// creates TimeSeriesCollection as a volume dataset for volume chart
			TimeSeriesCollection volumeDataset = new TimeSeriesCollection();
			volumeSeries = new TimeSeries("Volume");
			volumeDataset.addSeries(volumeSeries);
			// Create volume chart volumeAxis
			NumberAxis volumeAxis = new NumberAxis("Volume");
			volumeAxis.setAutoRangeIncludesZero(false);
			// Set to no decimal
			volumeAxis.setNumberFormatOverride(new DecimalFormat("0"));
			// Create volume chart renderer
			XYBarRenderer timeRenderer = new XYBarRenderer();
			timeRenderer.setShadowVisible(false);
			timeRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator("Volume--> Time={1} Size={2}",
					new SimpleDateFormat("kk:mm"), new DecimalFormat("0")));
			// Create volumeSubplot
			XYPlot volumeSubplot = new XYPlot(volumeDataset, null, volumeAxis, timeRenderer);
			volumeSubplot.setBackgroundPaint(Color.white);
			
			
			/**
			 * Create chart main plot with two subplots (candlestickSubplot,
			 * volumeSubplot) and one common dateAxis
			 */
			// Creating charts common dateAxis
			DateAxis dateAxis = new DateAxis("Time");
			dateAxis.setDateFormatOverride(new SimpleDateFormat("kk:mm"));
			// reduce the default left/right margin from 0.05 to 0.02
			dateAxis.setLowerMargin(0.02);
			dateAxis.setUpperMargin(0.02);
			// Create mainPlot
			CombinedDomainXYPlot mainPlot = new CombinedDomainXYPlot(dateAxis);
			mainPlot.setGap(10.0);
			mainPlot.add(candlestickSubplot, 3);
			mainPlot.add(volumeSubplot, 1);
			mainPlot.setOrientation(PlotOrientation.VERTICAL);

			JFreeChart chart = new JFreeChart(chartTitle, JFreeChart.DEFAULT_TITLE_FONT, mainPlot, true);
			chart.removeLegend();
			return chart;			
		}
		/**
		 * Fill series with data.
		 *
		 * @param t the t
		 */
		public void addCandel(Timestamp time, double o, double h, double l, double c, long v) 
		{		
				// Add bar to the data. Let's repeat the same bar
				FixedMillisecond t = new FixedMillisecond(time.getTime());
				ohlcSeries.add(t, o, h, l, c);
				volumeSeries.add(t, v);
		}	
		
		public void onTrade(KRWCoinVO t) {
			double price = t.getTradePrice();
			if (candelChartIntervalFirstPrint != null) {
				Timestamp time = t.getCandleDateTimeKst();
				if (timeInterval == (int) ((time / MIN) - (candelChartIntervalFirstPrint.getCandleDateTimeKst() / MIN)))
				{
					// Set the period close price
					close = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
					// Add new candle
					addCandel(time, open, high, low, close, volume);
					// Reset the intervalFirstPrint to null
					candelChartIntervalFirstPrint = null;
				} else {
					// Set the current low price
					if (MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT) < low)
						low = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);

					// Set the current high price
					if (MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT) > high)
						high = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);

					volume += t.getSize();
				}
			} else {
				// Set intervalFirstPrint
				candelChartIntervalFirstPrint = t;
				// the first trade price in the day (day open price)
				open = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
				// the interval low
				low = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
				// the interval high
				high = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
				// set the initial volume
				volume = t.getSize();
			}
		}		

}
