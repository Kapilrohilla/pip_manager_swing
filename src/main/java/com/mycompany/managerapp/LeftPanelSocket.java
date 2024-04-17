/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
//package Level1Socket;
//
//import Level1Socket.Level2Socket.livedata;
package com.mycompany.managerapp;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.Utility.Config;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import io.socket.client.IO;
import static io.socket.client.IO.socket;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;

/**
 *
 * @author techninza
 */
public class LeftPanelSocket extends javax.swing.JPanel {

    private DefaultTableModel tableModel = new DefaultTableModel();
    public static ArrayList<String> groupname = new ArrayList();
    // boolean b = false;
    public static ArrayList<JSONObject> watchlistData = new ArrayList<>();
    String selectedSymbol = null;
    public static Socket socket;

    public LeftPanelSocket() {
        try {
            tabledata();
            copiedSocket();

        } catch (Exception ex) {
            System.out.println("exception occurred in construcotr");
            System.out.println(ex);
        }
    }

    void copiedSocket() throws Exception {
        socket = IO.socket(Config.BASE_URL);
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
                        String watchlistsymbol = watchlistData.get(i).getString("Symbol");
                        // System.out.println(watchlistsymbol);
                        String responseSymbol = response.getString("symbol");
                        if (watchlistsymbol.equals(responseSymbol)) {
                            String bid = response.getString("bid");
                            String ask = response.getString("ask");
                            String volume = response.getString("volume");
                            String[] newRowData = {responseSymbol, bid, ask, volume};
                            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                                try {
                                    tableModel.setValueAt(newRowData[j], i, j);
                                } catch (ArrayIndexOutOfBoundsException ex) {
//                                    System.out.println("index out of bound: ");
//                                    System.out.println("indexes: " + "i = " + i + ", j = " + j);

                                }

                            }
                        }
//                        System.out.println("jso: " + response);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(LeftPanelSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        socket.connect();
    }

//    public void framesetup() {
//
//        NavigationTree bottomPanel_navigator = new NavigationTree();
//        TopPanelMarketData topPanel_marketData = new TopPanelMarketData();
//        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel_marketData, bottomPanel_navigator);
//        jSplitPane.setDividerLocation(0.5);
//
//        add(jSplitPane);
//    }
    String getData() {
        System.out.println("managerID " + ManagerApp.managerId);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
//        long lo = System.currentTimeMillis();
//        arraylist = new ArrayList();
        String apiUrl = APIs.GET_MANAGERS_ACCOUNT + "?managerId=" + ManagerApp.managerId;

//        String token = Metacustomer.loginToken;
//        if (token == null) {
//            return "";
//        }
        Request request = new Request.Builder()
                .url(apiUrl)
                //                .header("Authorization", token)
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

    public void tabledata() throws JSONException {

        String[] columns = {"Symbol", "Bid", "Ask", "Volume"};
        String apiData = getData();
        JSONObject jso = new JSONObject(apiData);

        for (String column : columns) {
            tableModel.addColumn(column);
        }

        JSONArray jsa = jso.getJSONArray("message");
        System.out.println("js: " + jsa);
        for (int i = 0; i < jsa.length(); i++) {
            JSONObject obj = jsa.getJSONObject(i);
            String groupxname = obj.getString("Name");
            groupname.add(groupxname);
            JSONArray symboljs = obj.getJSONArray("symbols");
            for (int j = 0; j < symboljs.length(); j++) {
                JSONObject symbolobj = symboljs.getJSONObject(j);
                String symbol = symbolobj.getString("Symbol");
                String bid = "";
                String ask = "";
                String volume = "";

                if (checksymbol(symbol)) {
                    String[] rowData = {symbol, bid, ask, volume};
                    watchlistData.add(symbolobj);
                    tableModel.addRow(rowData);
                }
            }
        }

//        JPopupMenu popup = new JPopupMenu();
//        JMenuItem deleteoption = new JMenuItem("Delete Symbol");
//
//        deleteoption.addActionListener((e) -> {
//            try {
//                if (selectedSymbol != null) {
//                    deleteSymbol(selectedSymbol);
//                }
//            } catch (JSONException ex) {
//                Logger.getLogger(LeftPanelSocket.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(LeftPanelSocket.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//        popup.add(deleteoption);
        JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int row = table.getSelectedRow();
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    try {
//
//                        // System.out.println("row : " + row);
//                        String selectedSymbol = watchlistData.get(row).getString("symbol");
//                        // System.out.println("selectedSymbol: " + selectedSymbol);
////                        String[] urlArray = Metacustomer.HomeChartUrl.split("symbol=");
//                        String[] resplit = urlArray[1].split("&");
//                        resplit[0] = selectedSymbol;
//
//                        urlArray[1] = String.join("&", resplit);
//                        String newUrl = String.join("symbol=", urlArray);
//
//                        System.out.println("newUrl: " + newUrl);
////                        HomeChartPanel.browser.navigation().loadUrl(newUrl);
//                    } catch (JSONException ex) {
//                        System.out.println("exception occurred");
//                    }
//                } else {
//                    popup.show(table, e.getX(), e.getY());
//                    System.out.println("row: " + row);
//                    try {
//                        String symbol = watchlistData.get(row).getString("symbol");
//                        System.out.println("symbol : " + symbol);
//                        selectedSymbol = symbol;
//
//                    } catch (JSONException ex) {
//                        Logger.getLogger(LeftPanelSocket.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            }
//        });
        JScrollPane scrollPane = new JScrollPane(table);
        //table.setEnabled(false);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private boolean checksymbol(String symbol) {
        try {
            for (int i = 0; i < watchlistData.size(); i++) {
                if (watchlistData.get(i).getString("Symbol").equalsIgnoreCase(symbol)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    final void deleteSymbol(String symbolName) throws JSONException, IOException {

        OkHttpClient client = new OkHttpClient();
//        String token = Metacustomer.loginToken;

        // groups/symbol
        String symbolApiUrl = "http://65.0.59.137:8080/delete-symbol/";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jso = new JSONObject();
        jso.put("symbol", symbolName);
        RequestBody body = RequestBody.create(JSON, jso.toString());

        Request request = new Request.Builder()
                .url(symbolApiUrl)
                .post(body)
                //                .header("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println("resonse body" + responseBody);
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
