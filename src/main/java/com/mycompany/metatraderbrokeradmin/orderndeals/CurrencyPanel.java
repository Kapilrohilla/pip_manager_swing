/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metatraderbrokeradmin.orderndeals;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.Timeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author techninza
 */
public class CurrencyPanel extends javax.swing.JPanel {

    /**
     * Creates new form CurrencyPanel
     */
    public CurrencyPanel() {
        initComponents();
    }

    public CurrencyPanel(String stockSymbol, JSONArray data) {
        initComponents();
//    }

//    void CandlestickDemo2(String stockSymbol) {
        //  super("CandlestickDemo2");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final DateAxis domainAxis = new DateAxis("");
        NumberAxis rangeAxis = new NumberAxis("");
        CandlestickRenderer renderer = new CandlestickRenderer();
        XYDataset dataset = getDataSet(stockSymbol, data);
        XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        //Do some setting up, see the API Doc
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setDrawVolume(false);
        rangeAxis.setAutoRangeIncludesZero(false);
        //Now create the chart and chart panel
        JFreeChart chart = new JFreeChart(stockSymbol, null, mainPlot, false);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(600, 300));
//        mainPlot.setDomainPannable(true);
//        mainPlot.setRangePannable(true);
        setLayout(new BorderLayout());
        this.add(chartPanel);
        // Add tiemline toggle
        final Timeline oldTimeline = domainAxis.getTimeline();
        final Timeline newTimeline = domainAxis.getTimeline();
//        this.add(new JCheckBox(new AbstractAction("Segmented Timeline") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JCheckBox jcb = (JCheckBox) e.getSource();
//                if (jcb.isSelected()) {
//                    domainAxis.setTimeline(newTimeline);
//                } else {
//                    domainAxis.setTimeline(oldTimeline);
//                }
//            }
//        }), BorderLayout.SOUTH);
//        this.pack();
//        this.setLocationRelativeTo(null);
    }

    private AbstractXYDataset getDataSet(String stockSymbol, JSONArray js) {
        //This is the dataset we are going to create
        DefaultOHLCDataset result;
        //This is the data needed for the dataset
        OHLCDataItem[] data;
        //This is where we go get the data, replace with your own data source
        data = getData(stockSymbol, js);
        //Create a dataset, an Open, High, Low, Close dataset
        result = new DefaultOHLCDataset(stockSymbol, data);
        return result;
    }
    //This method uses yahoo finance to get the OHLC data

    protected OHLCDataItem[] getData(String stockSymbol, JSONArray js) {
        List<OHLCDataItem> dataItems = new ArrayList<>();

        String apiUrl
                = "https://shopninja.in/meta/chartdata.json";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            if (response.statusCode() == 200) {
//                String responseData = response.body();
//                System.out.println(responseData);
//                System.out.println("chart response = " + responseData);
//                JSONArray js = new JSONArray(responseData);
            for (int i = 0; i < js.length(); i++) {
                JSONObject jsonobject = js.getJSONObject(i);
                String date_str = jsonobject.getString("createdAt");
                Instant instant = Instant.parse(date_str);
                long timestamp = instant.toEpochMilli();
                Date d = new Date(timestamp);
                double open = Double.parseDouble(jsonobject.getDouble("open") + "");
                double high = Double.parseDouble(jsonobject.getDouble("high") + "");
                double low = Double.parseDouble(jsonobject.getDouble("low") + "");
                double close = Double.parseDouble(jsonobject.getDouble("close") + "");
                double volume = Double.parseDouble(jsonobject.getDouble("volume") + "");
                OHLCDataItem item = new OHLCDataItem(d, open, high, low, close, volume);
//                    OHLCDataItem item = new OHLCDataItem(d, volume);
                dataItems.add(item);
            }
        } catch (JSONException ex) {
        }
        System.out.println(dataItems.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //Data from Yahoo is from newest to oldest. Reverse so it is oldest to newest
        Collections.reverse(dataItems);
        //Convert the list into an array
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        return data;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
