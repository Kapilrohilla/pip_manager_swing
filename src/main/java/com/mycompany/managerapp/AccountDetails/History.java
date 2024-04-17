/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp.AccountDetails;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.ManagerApp;
import com.mycompany.managerapp.Utility.Config;
import com.mycompany.managerapp.Utility.DatePicker;
import com.mycompany.metatraderbrokeradmin.orderndeals.Orderndealspopup;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
public class History extends javax.swing.JPanel {

    String user_id;

    ArrayList<JSONObject> arraylist = new ArrayList();
    int selectedRow = -1;

    String getJSOString(JSONObject jso, String key) {
        String result;
        try {
            result = jso.getString(key);
        } catch (JSONException ex) {
            System.out.println(ex.getMessage());
            result = "";
        }
        return result;
    }

    public History(String user_id) {
        initComponents();
        this.user_id = user_id;
        try {
            table("user-positions");
        } catch (JSONException ex) {
            System.out.println("exception occurreed in history panel");
            System.out.println(ex.getMessage());
        }
    }

    String getData(String xc) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        String url = Config.BASE_URL + xc + "?userId=" + user_id;
        System.out.println("URL" + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);

        try {
            Response res = call.execute();
            System.out.println("res: " + res.message());
            return res.body().string();
        } catch (IOException e) {
            System.out.println("Runtime error: deals");
            System.out.println("e" + e);
            return "";
        }
    }

    final void table(String xc) throws JSONException {
        DefaultTableModel model = new DefaultTableModel();
        String apiData = getData(xc);
        JSONArray jsnArray = new JSONArray(apiData);
        arraylist.clear();
//        String columns[];
        ArrayList<String> columns = new ArrayList<>();
        if (xc == "user-orders") {
            columns.add("Time");
            columns.add("Order");
            columns.add("Id");
            columns.add("Symbol");
            columns.add("Type");
            columns.add("Volume");
            columns.add("Price");
            columns.add("StopLoss");
            columns.add("TakeProfit");
            columns.add("Time");
            columns.add("Reason");
            columns.add("State");
            columns.add("Comment");

        } else if (xc == "user-positions") {
            columns.add("Time");
            columns.add("Ticket");
            columns.add("Type");
            columns.add("Volume");
            columns.add("Symbol");
            columns.add("Price");
            columns.add("StopLoss");
            columns.add("TakeProfit");
            columns.add("Time");
            columns.add("Price");
            columns.add("Reason");
            columns.add("Commission");
            columns.add("Fee");
            columns.add("Swap");
            columns.add("Profit");
            columns.add("Comment");

        } else if (xc == "user-deals") {
            columns.add("Time");
            columns.add("Deal");
            columns.add("Id");
            columns.add("Order");
            columns.add("Position");
            columns.add("Symbol");
            columns.add("Type");
            columns.add("Entry");
            columns.add("Volume");
            columns.add("Volume Closed");
            columns.add("Price");
            columns.add("StopLoss");
            columns.add("TakeProfit");
            columns.add("Reason");
            columns.add("Commission");
            columns.add("Fee");
            columns.add("Swap");
            columns.add("Profit");
            columns.add("Comment");
        }
        for (String column : columns) {
            model.addColumn(column);
        }
        arraylist.clear();
        for (int i = 0; i < jsnArray.length(); i++) {
            JSONObject jso = jsnArray.getJSONObject(i);
            System.out.println("History, jso: " + jso);
            if (xc == "user-orders") {
                String time = getJSOString(jso, "createdAt");
                String order = getJSOString(jso, "ticket");
                String id = getJSOString(jso, "_id");
                String symbol = getJSOString(jso, "symbol");
                int typeNum = jso.getInt("type");
                String type = "";
                switch (typeNum) {
                    case 0:
                        type = "Sell";
                        break;
                    case 1:
                        type = "Buy";
                        break;
                    case 2:
                        type = "Buy-Limit";
                        break;
                    case 3:
                        type = "Sell-Limit";
                        break;
                    case 4:
                        type = "Buy-Stop";
                        break;
                    case 5:
                        type = "Sell-Stop";
                        break;
                    case 6:
                        type = "Buy-Stop-Limit";
                        break;
                    case 7:
                        type = "Sell-Stop-Limit";
                        break;
                    default:
                        break;
                };
                String vol = getJSOString(jso, "initialVolume");
                String price = getJSOString(jso, "price");
                String stopLoss = getJSOString(jso, "stopLoss");
                String takeProfit = getJSOString(jso, "takeProfit");
                String Stime = "";
                String reason = getJSOString(jso, "reason");
                String state = "";
                String comment = getJSOString(jso, "comment");

                String[] rowData = {time, order, id, symbol, type, vol, price, stopLoss, takeProfit, Stime, reason, state, comment};
                model.addRow(rowData);
                arraylist.add(jso);

            } else if (xc == "user-positions") {
                String time = getJSOString(jso, "createdAt");
                String ticket = getJSOString(jso, "ticket");
//                String type = getJSOString(jso, "type");
                int typeNum = jso.getInt("type");
                String type = "";
                switch (typeNum) {
                    case 0:
                        type = "Sell";
                        break;
                    case 1:
                        type = "Buy";
                        break;
                    case 2:
                        type = "Buy-Limit";
                        break;
                    case 3:
                        type = "Sell-Limit";
                        break;
                    case 4:
                        type = "Buy-Stop";
                        break;
                    case 5:
                        type = "Sell-Stop";
                        break;
                    case 6:
                        type = "Buy-Stop-Limit";
                        break;
                    case 7:
                        type = "Sell-Stop-Limit";
                        break;
                    default:
                        break;
                };
                String volume = "";
                try {
                    volume = jso.getDouble("volume") + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String symbol = getJSOString(jso, "symbol");
                String price = "";
                try {
                    price = jso.getDouble("price") + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String stopLoss = getJSOString(jso, "stopLoss");
                String takeProfit = getJSOString(jso, "takeProfit");
                String Stime = getJSOString(jso, "closeTime");
                String Sprice = getJSOString(jso, "closePrice");
                String reason = getJSOString(jso, "");
                String commission = getJSOString(jso, "");
                String fee = "";
                try {
                    fee = jso.getDouble("fee") + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String swap = getJSOString(jso, "swap");
//                String profit = getJSOString(jso, "profit");
                String profit;
                try {
                    profit = jso.getDouble("profit") + "";
                } catch (JSONException ex) {
                    profit = "";
                    System.out.println(ex.getMessage());
                }

                String comment = getJSOString(jso, "comment");

                String[] rowData = {time, ticket, type, volume, symbol, price, stopLoss, takeProfit, Stime, Sprice, reason, commission, fee, swap, profit, comment};

                model.addRow(rowData);

                arraylist.add(jso);
            } else if (xc == "user-deals") {
                String time = getJSOString(jso, "createdAt");
                String deal = "";
                String id = getJSOString(jso, "ticket");
                String order = "";
                String position = getJSOString(jso, "positionId");
                String symbol = getJSOString(jso, "symbol");
                int typeNum = jso.getInt("type");
                String type = "";
                switch (typeNum) {
                    case 0:
                        type = "Sell";
                        break;
                    case 1:
                        type = "Buy";
                        break;
                    case 2:
                        type = "Buy-Limit";
                        break;
                    case 3:
                        type = "Sell-Limit";
                        break;
                    case 4:
                        type = "Buy-Stop";
                        break;
                    case 5:
                        type = "Sell-Stop";
                        break;
                    case 6:
                        type = "Buy-Stop-Limit";
                        break;
                    case 7:
                        type = "Sell-Stop-Limit";
                        break;
                    default:
                        break;
                };
                String entry = "";
                try {
                    int entryNum = jso.getInt("entry");
                    entry = entryNum + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String volume = "";
                try {
                    double volumeInt = jso.getDouble("volume");
                    volume = volumeInt + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String volumeClosed = "";
                String price = "";
                try {
                    double priceNum = jso.getDouble(price);
                    price = priceNum + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String stopLoss = getJSOString(jso, "stopLoss");
                String takeProfit = getJSOString(jso, "takeProfit");
                String reason = getJSOString(jso, "reason");
                String commission = "";
                String fee = "";
                try {
                    double feeNum = jso.getDouble("fee");
                    fee = feeNum + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }
                String swap = getJSOString(jso, "swap");
                String profit = "";
                try {
                    double profitN = jso.getDouble("profit");
                    profit = profitN + "";
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }

                String comment = getJSOString(jso, "comment");

                String rowData[] = {time, deal, id, order, position, symbol, type, entry, volume, volumeClosed, stopLoss, takeProfit, reason, commission, fee, swap, profit, comment};
                model.addRow(rowData);

                arraylist.add(jso);
            }
//            String user_id = jso.getString("user_id");
//            String time = jso.getString("createdAt");
//            String ticket = jso.getString("ticket");
//            String symbol = jso.getString("symbol");
//            String reason = jso.getString("reason");
//            String volume = "";
//            if (xc.equalsIgnoreCase("user-orders")) {
//                volume = jso.getDouble("initialVolume") + "";
//            } else {
//                volume = jso.getDouble("volume") + "";
//            }
//            String price = jso.getDouble("price") + "";
//            String profit;
//            try {
//                profit = String.format("%.2f", jso.getDouble("profit"));
//                System.out.println("History, profit: " + profit);
//            } catch (JSONException ex) {
//                System.out.println("Profit, ex: " + ex);
//                profit = "";
//            }
//            int status = jso.getInt("status");
//            String statusText = status == 1 ? "Fulfilled" : "Canceled";
//
//            String stopLoss;
//            try {
//                stopLoss = jso.getString("stopLoss");
//
//                if (stopLoss == "null") {
//                    stopLoss = "";
//                }
//            } catch (JSONException ex) {
//                stopLoss = "";
//            }
//            String takeProfit = jso.getString("takeProfit");
//            if (takeProfit == "null") {
//                takeProfit = "";
//            }
//
//            String type = jso.getString("type");
//
//            switch (type) {
//                case "0":
//                    type = "Sell";
//                    break;
//                case "1":
//                    type = "Buy";
//                    break;
//                case "2":
//                    type = "Buy-Limit";
//                    break;
//                case "3":
//                    type = "Sell-Limit";
//                    break;
//                case "4":
//                    type = "Buy-Stop";
//                    break;
//                case "5":
//                    type = "Sell-Stop";
//                    break;
//                case "6":
//                    type = "Buy-Stop-Limit";
//                    break;
//                case "7":
//                    type = "Sell-Stop-Limit";
//                    break;
//                default:
//                    break;
//            }
//            String swap = jso.getString("swap");
//            String comment = jso.getString("comment");
//
//            arraylist.add(jso);
//
//            String[] rowData = {time, ticket, type, volume, symbol, price, stopLoss, takeProfit, "", "", reason, "", "", swap, profit, comment};
//
//            model.addRow(rowData);
        }

        jt.setModel(model);
        jt.setAutoCreateRowSorter(true);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem3 = new JMenuItem("Edit");
        JMenuItem menuItem4 = new JMenuItem("Delete");
        JMenuItem menuItem5 = new JMenuItem("Position");
        JMenuItem menuItem6 = new JMenuItem("Orders");
        JMenuItem menuItem7 = new JMenuItem("Deals");
        JMenuItem menuItem8 = new JMenuItem("Show Milestones");
        JMenuItem menuItem9 = new JMenuItem("Auto Arrange");
        JMenuItem menuItem10 = new JMenuItem("Grid");
        JMenuItem menuItem11 = new JMenuItem("Columns");

        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectedRow != -1) {
                        String id = arraylist.get(selectedRow).getString("_id");
                        new Orderndealspopup("position", id).setVisible(true);
                    }
                } catch (Exception ex) {

                }
            }
        });

        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectedRow != -1) {
                        String id = arraylist.get(selectedRow).getString("_id");
                        String apiUrl = APIs.DELETE_HISTORY;
                        OkHttpClient client = new OkHttpClient();
                        JSONObject jsnObj = new JSONObject();
                        jsnObj.put("id", id);
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        RequestBody body = RequestBody.create(JSON, jsnObj.toString());
                        Request req = new Request.Builder().url(apiUrl).post(body).build();
                        Response res = client.newCall(req).execute();
                        if (res.isSuccessful()) {
                            String resBody = res.body().string();
                            JSONObject resJSON = new JSONObject(resBody);
                            System.out.println("reJSON: " + resJSON);
                            boolean isSuccess = resJSON.getBoolean("valid");
                            String message = resJSON.getString("message");
                            if (isSuccess) {
                                JOptionPane.showMessageDialog(History.this, message);
                                jt.setModel(new DefaultTableModel());
                                table("user-positions");
                            }
                        }
                    }
                } catch (JSONException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        popupMenu.add(menuItem3);
        popupMenu.add(menuItem4);
        popupMenu.addSeparator();

        popupMenu.add(menuItem5);
        popupMenu.add(menuItem6);
        popupMenu.add(menuItem7);
        popupMenu.addSeparator();

        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    table("user-positions");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        });

        menuItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    table("user-orders");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        menuItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    table("user-deals");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedRow = jt.rowAtPoint(e.getPoint());
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Show the popup menu
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else {
                    if (e.getClickCount() == 2) {
                        int row = jt.rowAtPoint(e.getPoint());
                        try {

                            String orderid = arraylist.get(row).getString("_id");
                            new Orderndealspopup("order", orderid);
                        } catch (JSONException ex) {
                            System.out.println("failed to fetch orderId through arraylist");
                            System.out.println("message: " + ex.getMessage());
                        }
                    }
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jt = new javax.swing.JTable();

        jt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jt;
    // End of variables declaration//GEN-END:variables
}
