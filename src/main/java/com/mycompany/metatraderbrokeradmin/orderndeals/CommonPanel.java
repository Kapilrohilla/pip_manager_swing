/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metatraderbrokeradmin.orderndeals;

import com.mycompany.managerapp.Utility.APIs;
import java.io.IOException;
import javax.swing.JOptionPane;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author techninza
 */
public class CommonPanel extends javax.swing.JPanel {

    /**
     * Creates new form CommonPanel
     */
    private String cid;
    String panelType;

    public CommonPanel() {
        initComponents();

    }

    public CommonPanel(String orderId, String panelType, JSONObject js) {
        initComponents();
        this.panelType = panelType;
        if (panelType == "position") {
            //dealer
            jLabel6.setVisible(false);
            jTextField4.setVisible(false);

        } else if (panelType == "order") {
            // swap
            jTextField12.setVisible(false);
            jLabel14.setVisible(false);
            // currentPrice
//            jTextField9.setVisible(false);
//            jLabel11.setVisible(false);
            //openTime
            jTextField5.setVisible(false);
            jLabel7.setVisible(false);
            //closeTime
            jLabel17.setVisible(false);
            jTextField18.setVisible(false);
        }
        this.cid = orderId;
        double status = 99;
        try {
            status = js.getDouble("status");
        } catch (JSONException ex) {
            System.out.println("status not found in commonpanel of orderdealsPopup");
        }
        String marginRates, openPrice, currentPrice, stopLoss, swap, takeProfit, volume, position;
        String updatedAt, reason, expertId, dealerId, externalId, comment, buyOrSell, symbol;
        String closePrice = "", quantity = "", openTime = "", closeTime = "", charges = "";
        try {
            volume = js.getDouble("initialVolume") + "";
        } catch (JSONException ex) {
            volume = "        ";
        }
        try {
            position = js.getString("ticket");
        } catch (JSONException ex) {
            position = "        ";
            System.out.println("Error while fetching ticket string for Position");
        }
        try {
            int type = js.getInt("type");
            System.out.println("type = " + type);
            if (type == 0) {
                buyOrSell = "sell";
            } else if (type == 1) {
                buyOrSell = "buy";
            } else {
                buyOrSell = type + "";
            }
        } catch (JSONException ex) {
            System.out.println("Error while fetching buy/sell (type) integer");
            buyOrSell = "        ";
        }
        try {
            symbol = js.getString("symbol");
        } catch (JSONException ex) {
            System.out.println("error while fetching symbol");
            symbol = "        ";
        }
        try {
            marginRates = js.getDouble("marginRates") + "";
        } catch (JSONException ex) {
            System.out.println("Error occur while getting float marginRates");
            marginRates = "        ";
        }
        try {
            updatedAt = js.getString("updatedAt");
        } catch (JSONException ex) {
            System.out.println("Error occur while getting string updatedAt");
            updatedAt = "        ";
        }
        try {
            openPrice = js.getDouble("price") + "";
        } catch (JSONException ex) {
            openPrice = "        ";
        }
//        try {
//            currentPrice = js.getDouble("price") + "";
//        } catch (JSONException ex) {
//            currentPrice = "        ";
//        }
        try {
            reason = js.getString("reason");
        } catch (JSONException ex) {
            reason = "        ";
        }
        try {
            stopLoss = js.getDouble("stopLoss") + "";
        } catch (JSONException ex) {
            System.out.println("Error while fetching stopLoss value in stc");
            stopLoss = "        ";
        }
        try {
            expertId = js.getString("expertId");
        } catch (JSONException ex) {
            System.out.println("error while fetching string expertID ");
            expertId = "        ";
        }
        try {
            dealerId = js.getString("dealer");
        } catch (JSONException ex) {
            System.out.println("Error while fetching string dealer");
            dealerId = "        ";
        }
        try {
            externalId = js.getString("externalId");
        } catch (JSONException ex) {
            System.out.println("Error while fetching string external id");
            externalId = "        ";
        }
        try {
            comment = js.getString("comment");
        } catch (JSONException ex) {
            comment = "        ";
            System.out.println("Error while fetching string comment");
        }
        try {
            takeProfit = js.getString("takeProfit");
        } catch (JSONException ex) {
            takeProfit = "        ";
            System.out.println("Error while fetching string takeProfit");
        }
        try {
            swap = js.getString("swap");
        } catch (JSONException ex) {
            swap = "        ";
            System.out.println("Error while fetching string swap");
        }
        try {
            //String closePrice, quantity, openTime, closeTime, charges;
            closePrice = js.getString("closePrice");

        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            quantity = js.getString("volume");
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            openTime = js.getString("openTime");
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            charges = js.getString("charges");
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            closeTime = js.getString("closeTime");
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
        }
        String isopened;
        if (status != 99) {
            isopened = status == 1 ? "Opened True" : "Opened False";
        } else {
            isopened = "          ";
        }
        jComboBox1.setSelectedItem(buyOrSell);
        jTextField13.setText(symbol);
        jComboBox2.setSelectedItem(isopened);
        jTextField2.setText(updatedAt);
        jTextField3.setText(reason);
        jTextField4.setText(dealerId);
        jTextField7.setText(comment);
        jTextField8.setText(openPrice);
//        jTextField9.setText(currentPrice);
        jTextField10.setText(stopLoss);
        jTextField11.setText(takeProfit);
        jTextField12.setText(swap);
        jTextField15.setText(volume);
        jTextField16.setText(position);
        jTextField14.setText(closePrice);
        jTextField17.setText(quantity);
        jTextField5.setText(openTime);
        jTextField18.setText(closeTime);
//        jTextField6.setText(charges);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField15 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();

        jToggleButton1.setText("jToggleButton1");

        jLabel1.setText("Position : ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Buy", "Sell" }));
        jComboBox1.setEnabled(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Opened :");

        jLabel4.setText("Updated :");

        jTextField2.setEditable(false);

        jLabel5.setText("Reason :");

        jLabel6.setText("Dealer Id : ");

        jLabel9.setText("Comment :");

        jLabel10.setText("Open Price :");

        jLabel12.setText("Stop Loss :");

        jLabel13.setText("Take Profit :");

        jLabel14.setText("Swap :");

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Report");

        jTextField15.setEnabled(false);

        jTextField13.setEnabled(false);

        jTextField16.setEnabled(false);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opened True", "Opened False" }));

        jLabel15.setText("Close Price:");

        jLabel16.setText("Quantity:");

        jLabel17.setText("Close time:");

        jLabel7.setText("Open time:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField14)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(103, 103, 103)
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel16)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel12)
                                                            .addComponent(jLabel17)))
                                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                                                    .addComponent(jTextField18)
                                                    .addComponent(jTextField17)
                                                    .addComponent(jTextField10)
                                                    .addComponent(jTextField12)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))))))
                .addGap(0, 110, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                            .addComponent(jTextField3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                    .addComponent(jTextField7))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel10)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String opened = jComboBox2.getSelectedItem().toString();
        String updated = jTextField2.getText();
        String reason = jTextField3.getText();
        String dealerId = jTextField4.getText();
//        String expertId = jTextField5.getText();
//        String externalId = jTextField6.getText();
        String comment = jTextField7.getText();
        String openPrice = jTextField8.getText();
//        String currentPrice = jTextField9.getText();
        String stopLoss = jTextField10.getText();
        String takeProfit = jTextField11.getText();
        String swap = jTextField12.getText();
        String closePrice = jTextField14.getText();
        String quantity = jTextField17.getText();
        String openTime = jTextField5.getText();
        String closeTime = jTextField18.getText();
        ////String charges = jTextField6.getText();

        int status2update = opened == "Opened True" ? 1 : 0;
//        String marginRate = jTextField14.getText();
        JSONObject jso = new JSONObject();
        try {
            if (panelType == "position") {
                jso.put("positionId", cid);
                jso.put("swap", swap);
                jso.put("volume", quantity);
                jso.put("reason", reason);
                // jso.put("price", currentPrice);
                jso.put("stopLoss", stopLoss);
                jso.put("closePrice", closePrice);
                jso.put("takeProfit", takeProfit);
                //  jso.put("charges", charges);
                jso.put("price", openPrice);
                jso.put("openTime", openTime);
                jso.put("closeTime", closeTime);
                jso.put("comment", comment);
                jso.put("status", status2update);

                RequestBody body = RequestBody.create(MediaType.parse("application/json"), jso.toString());
                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder().url(APIs.UPDATE_POSITION).post(body).build();

                Call call = client.newCall(req);
                try {
                    Response res = call.execute();

                    if (res.isSuccessful()) {
                        System.out.println("res: " + res.body().string());
                        JOptionPane.showMessageDialog(this, "Order updated successfully.");
                    } else {
                        System.out.println("failed resposne, res: " + res.body().string());

                        System.out.println("UPDATE order failed");
                    }
                } catch (IOException ex) {
                    System.out.println("exception occurred while executing update order api");
                }
            } else if (panelType == "order") {
                jso.put("orderId", cid);
                jso.put("dealer", dealerId);
                jso.put("initialVolume", quantity);
                jso.put("reason", reason);
                jso.put("stopLoss", stopLoss);
                jso.put("takeProfit", takeProfit);
                jso.put("price", openPrice);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), jso.toString());
                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder().url(APIs.UPDATE_ORDER).post(body).build();

                Call call = client.newCall(req);
                try {
                    Response res = call.execute();

                    if (res.isSuccessful()) {
                        System.out.println("res: " + res.body().string());
                        JOptionPane.showMessageDialog(this, "Order updated successfully.");
                    } else {
                        System.out.println("failed resposne, res: " + res.body().string());

                        System.out.println("UPDATE order failed");
                    }
                } catch (IOException ex) {
                    System.out.println("exception occurred while executing update order api");
                }
            }
//            jso.put("orderId", cid);
//            jso.put("status", status2update);
//            jso.put("reason", reason);
//            jso.put("dealer", dealerId);
//            jso.put("comment", comment);
//            jso.put("takeProfit", Double.parseDouble(takeProfit));
//            jso.put("stopLoss", Double.parseDouble(stopLoss.trim() == "" ? "0" : stopLoss.trim()));
//            jso.put("comment", comment);
//            jso.put("currentPrice", currentPrice);
//            jso.put("price", openPrice);
//            // jso.put("swap", swap);
//            // jso.put("closePrice", closePrice);
//            jso.put("volume", quantity);
//            jso.put("openTime", openTime);
//            jso.put("closeTime", closeTime);
//            jso.put("charges", charges);
//            System.out.println("jso: " + jso.toString());
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jso.toString());
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder().url(APIs.UPDATE_POSITION).post(body).build();

            Call call = client.newCall(req);
            try {
                Response res = call.execute();

                if (res.isSuccessful()) {
                    System.out.println("res: " + res.body().string());
                    JOptionPane.showMessageDialog(this, "Order updated successfully.");
                } else {
                    System.out.println("failed resposne, res: " + res.body().string());

                    System.out.println("UPDATE order failed");
                }
            } catch (IOException ex) {
                System.out.println("exception occurred while executing update order api");
            }

        } catch (JSONException ex) {
            System.out.println("exception occurred while setting data2 json body while updating the order.");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
