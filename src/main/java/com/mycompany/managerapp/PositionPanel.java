/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.AccountDetails.AccountDetailsContainer;
import com.mycompany.managerapp.AccountDetails.History;
import com.mycompany.managerapp.OrderPopups.BulkClosingFrame;
import com.mycompany.metatraderbrokeradmin.orderndeals.Orderndealspopup;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import okio.ByteString;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author techninza
 */
public class PositionPanel extends javax.swing.JPanel {

    private ArrayList<JSONObject> watchlistData = new ArrayList<>();
    DefaultTableModel tableModel;
    public static String selectedPositionId = "";

    /**
     * Creates new form PositionPanel
     */
    public PositionPanel() {
        initComponents();
        try {
            tabledata();
            try {
                copiedSocket();
            } catch (Exception ex) {
                System.out.println("exception occurred ");
                System.out.println(ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getData() {
        OkHttpClient client = new OkHttpClient();
        String url = APIs.GET_MANAGERS_POSITIONS + "?managerId=" + ManagerApp.managerId;
        // Create a Request object
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("Runtime error");
            return "";
        }
    }

    void copiedSocket() throws Exception {
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
                            String profitWithoutFormat;
                            if (type == 1) {
                                profitWithoutFormat = String.format("%.2f", (currentPrice * volume * 100 - (price * volume * 100)));
                            } else {
                                profitWithoutFormat = String.format("%.2f", (price * volume * 100 - currentPrice * volume * 100));
                            }

                            if (status == 0) {
                                profitWithoutFormat = "placed";
                            }

                            tableModel.setValueAt(profitWithoutFormat, i, 10);
                        }
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        socket.connect();
    }

    private void tabledata() throws JSONException {
        ArrayList<JSONObject> arraylist = new ArrayList<>();
        tableModel = new DefaultTableModel();
        String[] columns = {"Symbol", "Ticket", "Time", "type", "Volume", "Price", "StopLoss", "TakeProfit", "Swap", "Comment", "Profit"};
        for (String column : columns) {
            tableModel.addColumn(column);
        }

        String apiData = getData();
        JSONArray js = new JSONObject(apiData).getJSONArray("message");
        for (int i = 0; i < js.length(); i++) {
            JSONObject jsonobject = js.getJSONObject(i);
            String time = jsonobject.getString("createdAt");
            String login = jsonobject.getString("user_id");
            String position = jsonobject.getString("_id");
            String symbol = jsonobject.getString("symbol");
            String type = jsonobject.getInt("type") == 0 ? "Sell" : "Buy";
            String stopLoss;

            try {
                stopLoss = jsonobject.getDouble("stopLoss") + "";
            } catch (JSONException ex) {
                stopLoss = "";
            }
            String takeProfit;
            try {
                takeProfit = jsonobject.getDouble("takeProfit") + "";
            } catch (JSONException ex) {
                takeProfit = "";
            }
            String volume;
            try {
                volume = jsonobject.getDouble("volume") + "";
            } catch (JSONException ex) {
                volume = "";
            }

            String price = jsonobject.getDouble("price") + "";
            String data4row[] = {symbol, position, time, type, volume, price, stopLoss, takeProfit, "", ""};
            watchlistData.add(jsonobject);
            tableModel.addRow(data4row);
            arraylist.add(jsonobject);
        }

        JTable jTable1 = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
//                return super.isCellEditable(row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                return false;
            }

        };
//        jTable1.setEnabled(false);
        jTable1.setAutoCreateRowSorter(true);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Account Details ");
        JMenuItem menuItem2 = new JMenuItem("Bulk Closing");
        JMenuItem menuItem3 = new JMenuItem("Edit");
        JMenuItem menuItem4 = new JMenuItem("Close Position");
        JMenuItem menuItem5 = new JMenuItem("Delete");
//        JMenuItem menuItem6 = new JMenuItem("Find");
//        JMenuItem menuItem7 = new JMenuItem("Volumes");
//        JMenuItem menuItem8 = new JMenuItem("Show Milestones");
//        JMenuItem menuItem9 = new JMenuItem("Auto Arrange");
//        JMenuItem menuItem10 = new JMenuItem("Grid");
//        JMenuItem menuItem11 = new JMenuItem("Columns");

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        popupMenu.addSeparator();

        popupMenu.add(menuItem3);
        popupMenu.add(menuItem4);
        popupMenu.addSeparator();
        popupMenu.add(menuItem5);

        menuItem1.addActionListener((ActionEvent e) -> {
            System.out.println("");
            new AccountDetailsContainer("", "", "", "", "", "", "", false, false, false, 0, 0, new JSONObject()).setVisible(true);
        });
        menuItem2.addActionListener((ActionEvent e) -> {
            System.out.println("");
            new BulkClosingFrame().setVisible(true);
        });
        menuItem4.addActionListener((ActionEvent e) -> {
            JSONObject requestBOdy = new JSONObject();
            try {
                OkHttpClient client = new OkHttpClient();
                requestBOdy.put("positionId", selectedPositionId);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBOdy.toString());

                Request req = new Request.Builder().url(APIs.CLOSE_POSITION).post(body).build();
                Call newCall = client.newCall(req);
                try {
                    Response res = newCall.execute();
                    System.out.println("resbody: " + res.body().string());
                    if (res.isSuccessful()) {
                        JOptionPane.showMessageDialog(this, "Position closed");
                    }
                } catch (IOException ex) {
                    System.out.println("exception occurred while executing close single position api");
                }

            } catch (JSONException ex) {

                System.out.println("exception occurred while setting close position body key value in positon panel");
            }

        });
//        menuItem4.addActionListener((ActionEvent e) -> {
//            new TechnicalDetails().setVisible(true);
//
//        });

        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("selectedRow: " + selectedRow);
                try {
                    if (selectedPositionId != "") {
//                        String id = arraylist.get(selectedPositionId).getString("_id");
                        String id = selectedPositionId;
                        System.out.println("id: " + id);
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
                            System.out.println("res: " + resJSON);
                            boolean isSuccess = resJSON.getBoolean("valid");
                            String message = resJSON.getString("message");
                            if (isSuccess) {
                                JOptionPane.showMessageDialog(PositionPanel.this, message);
//                                jTable1.setModel(new DefaultTableModel());
//                                table("user-orders");
                            }
                        }
                    }
                } catch (JSONException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        menuItem3.addActionListener((ActionEvent e) -> {
            try {
                new Orderndealspopup("position", "1");
            } catch (Exception ex) {
            }

        });

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.rowAtPoint(e.getPoint());
                try {
                    String positionId = arraylist.get(row).getString("_id");
                    if (SwingUtilities.isRightMouseButton(e)) {
                        // Show the popup menu
                        PositionPanel.selectedPositionId = positionId;
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    } else if (e.getClickCount() == 2) {
                        new Orderndealspopup("position", positionId).setVisible(true);
                    }
                } catch (JSONException ex) {
                    System.out.println("exception occurred");
                }

            }
        });

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(jTable1);
        add(scrollPane, BorderLayout.CENTER);
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
