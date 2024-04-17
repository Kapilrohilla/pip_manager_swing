/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp.AccountDetails;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.LeftPanel;
import com.mycompany.managerapp.LeftPanelSocket;
import static com.mycompany.managerapp.PositionPanel.selectedPositionId;
import com.mycompany.metatraderbrokeradmin.orderndeals.Orderndealspopup;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kapil
 */
public class Overview extends javax.swing.JPanel {

    private ArrayList<JSONObject> watchlistData = new ArrayList<>();
    DefaultTableModel tablemodel;

    public static double margin;
    public static double balance;
    public static double freeMarginValue;
    public static double equity;
    public static double marginLevel;
    public static double commission;
    public double runningProfit = 0;
    private int selectedRow = -1;

    /**
     * Creates new form Overview
     */
    final String getData(String id) {
        OkHttpClient client = new OkHttpClient();
        String url = APIs.GET_OPEN_POSITIONS + "?userId=" + id;
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);

        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("Error occurred while fetching trading account specific overview");
            return "";
        }
    }

    public Overview(String name, String email, String group, String country, String balance, String id, String margin) {
        initComponents();

        System.out.println("watchlist data");
        System.out.println(LeftPanelSocket.watchlistData);
        System.out.println("watchlist data");
        jLabel2.setText("UserId : " + email);
        jLabel3.setText("Group : " + group);
        jLabel4.setText("Country : " + country);
        jLabel5.setText("Balance : " + balance);

        this.socketListen();
        this.margin = Double.parseDouble(margin);
        try {
            this.balance = Double.parseDouble(balance);
        } catch (NumberFormatException ex) {
            System.out.println("Error:" + ex.getMessage());
        }
        String apiData = getData(id);
        jLabel9.setText(balance);
        jLabel17.setText(margin);
        // System.out.println("id : " + id);

        tablemodel = new DefaultTableModel();
        String[] columns = {"Symbol", "Ticket", "Time", "Type", "Volume", "Price", "StopLoss", "Take Profit", "Swap", "Comment", "Profit"};
        for (String column : columns) {
            tablemodel.addColumn(column);
        }
        try {
            JSONObject data2recieve = new JSONObject(apiData);
            this.margin = Double.parseDouble(data2recieve.getString("totalMargin"));

            JSONArray jsa = data2recieve.getJSONArray("positions");
            for (int i = 0; i < jsa.length(); i++) {
                JSONObject jso = jsa.getJSONObject(i);
                System.out.println("jso: " + jso);

                String time = jso.getString("createdAt");
                String login = jso.getString("user_id");
                String position = jso.getString("_id");
                String symbol = jso.getString("symbol");
                String type = jso.getInt("type") == 0 ? "Sell" : "Buy";
                String stopLoss;
                System.out.println("working3");
                try {
                    stopLoss = jso.getDouble("stopLoss") + "";
                } catch (JSONException ex) {
                    stopLoss = "";
                }
                String takeProfit;
                try {
                    takeProfit = jso.getDouble("takeProfit") + "";
                } catch (JSONException ex) {
                    takeProfit = "";
                }
                String volume;
                try {
                    volume = jso.getDouble("volume") + "";
                } catch (JSONException ex) {
                    volume = "";
                }

                String price = jso.getDouble("price") + "";
                String data4row[] = {symbol, position, time, type, volume, price, stopLoss, takeProfit, "", ""};
                watchlistData.add(jso);
                tablemodel.addRow(data4row);
            }
            jTable1.setModel(tablemodel);
            System.out.println("watchlistData: " + watchlistData);

            JPopupMenu popup = new JPopupMenu();
            JMenuItem edit = new JMenuItem("Edit");
            JMenuItem close = new JMenuItem("Close");
            JMenuItem modify = new JMenuItem("Modify");
            popup.add(edit);
            popup.add(close);
            popup.add(modify);
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("edit btn clicked");
                    System.out.println("selectedRow: " + selectedRow);
                    if (selectedRow != -1) {
                        JSONObject watchlistRow = watchlistData.get(selectedRow);
                        try {
                            String id = watchlistRow.getString("_id");
                            System.out.println("id: " + id);
                            new Orderndealspopup("position", id).setVisible(true);
                        } catch (JSONException ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }

                    }
                }
            });
            modify.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("modify popup called.");
                    if (selectedRow != -1) {
                        JSONObject watchlistRow = watchlistData.get(selectedRow);
                        try {
                            String id = watchlistRow.getString("_id");
                            System.out.println("id: " + id);
                            //new Orderndealspopup("position", id).setVisible(true);
                        } catch (JSONException ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }

                    }
                }
            });

            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("modify popup called.");
                    if (selectedRow != -1) {
                        try {
                            JSONObject watchlistRow = watchlistData.get(selectedRow);
                            OkHttpClient client = new OkHttpClient();
                            JSONObject requestBOdy = new JSONObject();
                            String selectedPositionId = watchlistRow.getString("_id");
                            requestBOdy.put("positionId", selectedPositionId);
                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBOdy.toString());

                            Request req = new Request.Builder().url(APIs.CLOSE_POSITION).post(body).build();
                            Call newCall = client.newCall(req);
                            try {
                                Response res = newCall.execute();
                                if (res.isSuccessful()) {
                                    tablemodel.removeRow(selectedRow);
                                    tablemodel.fireTableRowsDeleted(selectedRow, selectedRow);
                                    JOptionPane.showMessageDialog(Overview.this, "Position closed");
                                }
                            } catch (IOException ex) {
                                System.out.println("exception occurred while executing close single position api");
                            }
                        } catch (JSONException ex) {
                            System.out.println("Exception: " + ex.getMessage());
                        }

                    }
                }
            });

            jTable1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedRow = jTable1.rowAtPoint(e.getPoint());
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popup.show(jTable1, e.getX(), e.getY());
                    }

                }
            });

            try {
                double numericBalance = Double.parseDouble(balance);
//                copiedSocket(numericBalance);
            } catch (Exception ex) {
                System.out.println("exception occurred ");
                System.out.println(ex);
            }

        } catch (JSONException ex) {
        }
    }

    final void socketListen() {
        Socket socket = LeftPanelSocket.socket;
        if (socket.connected()) {
            socket.on("posData", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    JSONObject socketResponse = (JSONObject) os[0];
                    System.out.println("PosData: " + socketResponse);
                    try {
                        freeMarginValue = socketResponse.getDouble("freeMargin");
                    } catch (JSONException ex) {
                        ex.getStackTrace();
                    }
                    try {
                        margin = socketResponse.getDouble("margin");
                    } catch (JSONException ex) {
                        ex.getStackTrace();
                    }
                    try {
                        balance = socketResponse.getDouble("balance");
                    } catch (JSONException ex) {
                        ex.getStackTrace();
                    }
                    try {
                        marginLevel = socketResponse.getDouble("level");
                    } catch (JSONException ex) {
                        ex.getStackTrace();
                    }
                    try {
                        runningProfit = socketResponse.getDouble("runningProfit");
                    } catch (JSONException ex) {
                        ex.getStackTrace();
                    }
                    try {
                        equity = socketResponse.getDouble("equity");
                    } catch (JSONException ex) {
                        ex.getStackTrace();
                    }
                    jLabel9.setText(String.format("%.2f", balance) + "");
                    jLabel12.setText(String.format("%.2f", equity) + "");
                    jLabel17.setText(String.format("%.2f", margin) + "");
                    jLabel19.setText(String.format("%.2f", freeMarginValue) + "");
                    jLabel20.setText(String.format("%.2f", marginLevel) + "");
                }
            });

            socket.on("posProfit", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    JSONObject posProfitResponse = (JSONObject) os[0];
                    System.out.println("PosProfit: " + posProfitResponse);
                    try {
                        // {"positionId":"660fee244519b73be64bac76","currentPrice":2289.29,"profit":-5.760000000000218,"status":1}
                        String positionId = posProfitResponse.getString("positionId");
                        double cp = posProfitResponse.getDouble("currentPrice");
                        double p = posProfitResponse.getDouble("profit");
                        String currentPrice = String.format("%.2f", cp);
                        String profit = String.format("%.2f", p);

                        for (int i = 0; i < watchlistData.size(); i++) {
                            String rowPosId = watchlistData.get(i).getString("_id");
                            if (rowPosId == null ? positionId == null : rowPosId.equals(positionId)) {
                                jTable1.setValueAt(currentPrice, i, 5);
                                jTable1.setValueAt(profit, i, 10);
                                break;
                            }
                        }
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });

            socket.on("getOrder", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    JSONObject getOrderResponse = (JSONObject) os[0];
                    System.out.println("getOrderResponse" + getOrderResponse);
                    try {
                        JSONObject getOrderResponse1 = getOrderResponse.getJSONObject("getOrder");
                        boolean isValid = getOrderResponse1.getBoolean("valid");
                        if (isValid) {
                            JSONObject jso = getOrderResponse1.getJSONObject("position");
                            watchlistData.add(jso);
                            String time = jso.getString("createdAt");
                            String login = jso.getString("user_id");
                            String position = jso.getString("_id");
                            String symbol = jso.getString("symbol");
                            String type = jso.getInt("type") == 0 ? "Sell" : "Buy";
                            String stopLoss;
                            System.out.println("working3");
                            try {
                                stopLoss = jso.getDouble("stopLoss") + "";
                            } catch (JSONException ex) {
                                stopLoss = "";
                            }
                            String takeProfit;
                            try {
                                takeProfit = jso.getDouble("takeProfit") + "";
                            } catch (JSONException ex) {
                                takeProfit = "";
                            }
                            String volume;
                            try {
                                volume = jso.getDouble("volume") + "";
                            } catch (JSONException ex) {
                                volume = "";
                            }

                            String price = jso.getDouble("price") + "";
                            String data4row[] = {symbol, position, time, type, volume, price, stopLoss, takeProfit, "", ""};
                            tablemodel.addRow(data4row);
                            System.out.println("new row inserted, firing data changed.");
                            tablemodel.fireTableDataChanged();
                        } else {
                            System.out.println("Invalid isValid: " + isValid);
                            String response = getOrderResponse1.getString("message");
                            JOptionPane.showMessageDialog(Overview.this, response);
                        }

                    } catch (JSONException ex) {
//                        System.out.println("err: " + ex.getMessage());
                        ex.getStackTrace();
                    }
//                    watchlistData.add();
                }
            });

            socket.on("closePosition", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
//                    try {
                    JSONObject closePositionResponse = (JSONObject) os[0];
                    try {
                        String positionId = closePositionResponse.getString("positionId");
                        for (int i = 0; i < watchlistData.size(); i++) {
                            JSONObject position = watchlistData.get(i);
                            String watchPositionId = position.getString("_id");
                            if (watchPositionId.equals(positionId)) {
                                watchlistData.remove(i);
                                tablemodel.removeRow(i);
                                tablemodel.fireTableRowsDeleted(i, i);
                            }
                        }
                    } catch (JSONException ex) {
                        System.out.println(ex.getMessage());
                    }
//                    } catch (JSONException ex) {
//                        System.out.println(ex.getMessage());
//                    }

                }
            });
        } else {
            System.out.println("socket isn't connected");
        }

    }

    /*
    void updateData(double balance) {
        int rows = jTable1.getRowCount();
        int lastColumn = 10;
        int runningProfit = 0;
        for (int i = 0; i < rows; i++) {
            try {
                runningProfit += Double.parseDouble(jTable1.getValueAt(i, lastColumn).toString());
            } catch (NumberFormatException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        }
        this.equity = balance + runningProfit;
        System.out.println("margin: " + Overview.margin);
        freeMarginValue = equity - this.margin;
        double levelper = (equity / margin) * 100;
//        System.out.println("margin: " + freeMarginValue);
        jLabel12.setText(equity + "");
        jLabel17.setText(margin + "");
        jLabel19.setText(freeMarginValue + "");
        jLabel20.setText(levelper + "");
//        double 
    }
     */
 /*
    final void copiedSocket(double numbericBalance) throws Exception {
        Socket socket = LeftPanelSocket.socket;
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server.");
            }
        }).on("newMessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jso = (JSONObject) args[0];
                JSONObject response;
                try {

                    response = jso.getJSONObject("newMessage");
                    for (int i = 0; i < watchlistData.size(); i++) {
                        String watchlistsymbol = watchlistData.get(i).getString("symbol");
                        String responseSymbol = response.getString("symbol");
                        double type = watchlistData.get(i).getDouble("type");

                        if (watchlistsymbol.equals(responseSymbol)) {
                            double currentPrice = response.getDouble("ask");
                            double price = watchlistData.get(i).getDouble("price");
                            double volume = watchlistData.get(i).getDouble("volume");

                            int status = watchlistData.get(i).getInt("status");

                            long contractSize = 100;
                            for (int j = 0; j < LeftPanelSocket.watchlistData.size(); j++) {
                                JSONObject watchlistSymbolData = LeftPanelSocket.watchlistData.get(i);

                                if (watchlistSymbolData.getString("Symbol").equals(watchlistsymbol)) {
                                    contractSize = Long.parseLong(watchlistSymbolData.getString("ContractSize"));

                                    break;
                                }
                            }

                            String profitWithoutFormat;
                            if (type == 1) {
                                profitWithoutFormat = ((currentPrice * volume * contractSize) - (price * volume * contractSize)) + "";
                            } else {
                                profitWithoutFormat = ((price * volume * contractSize) - (currentPrice * volume * contractSize)) + "";
                            }

                            if (status == 0) {
                                profitWithoutFormat = "placed";
                            }
                            tablemodel.setValueAt(profitWithoutFormat, i, 10);
                        }
                    }
                    updateData(numbericBalance);
                } catch (JSONException ex) {
                    Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        socket.connect();
    }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();

        jLabel2.setText("06101, Turkey, Ankara, Celal Bayar Blv");

        jLabel3.setText("+918287842425");

        jLabel4.setText("Jackson.Brown@testmail.net");

        jLabel5.setText("Registerd: 2016. 11. 24 Last Access: 2016.11.24 13:23 Last Address: 192.168.0.1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Symbol", "Time", "Type", "Volume", "Price", "S / L", "T / P", "Price", "Swap", "Profit"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel8.setText("Balance:");

        jLabel9.setText("0.0");

        jLabel11.setText("Equity:");

        jLabel12.setText("0.0");

        jLabel16.setText("Margin:");

        jLabel17.setText("0.0");

        jLabel6.setText("Free Margin:");

        jLabel18.setText("Level Percent :");

        jLabel19.setText("           ");

        jLabel20.setText("          ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(165, 165, 165)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(34, 34, 34))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
