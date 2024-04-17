/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.AccountDetails.AccountDetailsContainer;
import com.mycompany.managerapp.OrderPopups.BulkClosingFrame;
import com.mycompany.metatraderbrokeradmin.orderndeals.Orderndealspopup;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author techninza
 */
public class Order extends javax.swing.JPanel {

    /**
     * Creates new form OrderPanel
     */
    private int selectedRow = -1;
    ArrayList<String> ids = new ArrayList<>();

    public Order() {
        initComponents();
        try {
            tabledata();
        } catch (JSONException ex) {
            System.out.println("json exception occurred");
        }

    }

    private String getData() {
        OkHttpClient client = new OkHttpClient();
        String url = APIs.GET_MANAGERS_ORDERS + "?managerId=" + ManagerApp.managerId;
        // Create a Request object
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("API Error");
            return "";
        }
    }

    private void tabledata() throws JSONException {
        String apiData = getData();
        JSONArray js = new JSONObject(apiData).getJSONArray("message");
        ArrayList<JSONObject> arraylist = new ArrayList<>();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Time");
        tableModel.addColumn("Login");
        tableModel.addColumn("Order");
        tableModel.addColumn("Symbol");
        tableModel.addColumn("Type");
        tableModel.addColumn("Volume");

        for (int i = 0; i < js.length(); i++) {
            JSONObject jso = js.getJSONObject(i);
            String time = jso.getString("createdAt");
            String login = jso.getString("user_id");
            String order = jso.getString("ticket");
            String symbol = jso.getString("symbol");
            String type = jso.getDouble("type") + "";
            String volume = jso.getDouble("initialVolume") + "";
            String orderId = jso.getString("_id");
            String rowData[] = {time, login, order, symbol, type, volume};
            arraylist.add(jso);
            ids.add(orderId);
            tableModel.addRow(rowData);
        }

        //JTable jTable1 = new JTable(tableModel);
        JTable jt = new JTable(tableModel);
        jt.setAutoCreateRowSorter(true);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Account Details ");
        JMenuItem menuItem2 = new JMenuItem("Bulk Closing");
        JMenuItem menuItem3 = new JMenuItem("Edit");
        JMenuItem menuItem4 = new JMenuItem("Delete");
        JMenuItem menuItem5 = new JMenuItem("Export");
        JMenuItem menuItem6 = new JMenuItem("Find");
        JMenuItem menuItem7 = new JMenuItem("Volumes");
        JMenuItem menuItem8 = new JMenuItem("Show Milestones");
        JMenuItem menuItem9 = new JMenuItem("Auto Arrange");
        JMenuItem menuItem10 = new JMenuItem("Grid");
        JMenuItem menuItem11 = new JMenuItem("Columns");

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        popupMenu.addSeparator();

        popupMenu.add(menuItem3);
        popupMenu.add(menuItem4);
        popupMenu.addSeparator();

//        popupMenu.add(menuItem5);
//        popupMenu.add(menuItem6);
//        popupMenu.addSeparator();
//        popupMenu.add(menuItem7);
//        popupMenu.add(menuItem8);
//        popupMenu.add(menuItem9);
//        popupMenu.add(menuItem10);
//        popupMenu.add(menuItem11);
        menuItem1.addActionListener((ActionEvent e) -> {
            System.out.println("");
            new AccountDetailsContainer("", "", "", "", "", "", "", false, false, false, 0, 0, new JSONObject()).setVisible(true);
        });
        menuItem2.addActionListener((ActionEvent e) -> {
            System.out.println("");
            new BulkClosingFrame().setVisible(true);
        });
        menuItem6.addActionListener((ActionEvent e) -> {
            new TechnicalDetails().setVisible(true);

        });

        menuItem4.addActionListener((ActionEvent e) -> {
//            System.out.println("selectedRow: " + selectedRow);
            if (selectedRow != -1) {
                String apiUrl = APIs.DELETE_ORDER;
                OkHttpClient client = new OkHttpClient();
                // Create a Request object
                JSONObject requestJSON = new JSONObject();
                String selectedId = ids.get(selectedRow);
                System.out.println("row: " + selectedRow + ", id=> " + selectedId);
                try {
                    requestJSON.put("orderId", selectedId);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestJSON.toString());

                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .post(body)
                            .build();
                    Call call = client.newCall(request);

                    try {
                        Response res = call.execute();
                        System.out.println("___response___");
                        System.out.println(res.body().string());
                        JOptionPane.showMessageDialog(Order.this, "Order deleted.");
                        tableModel.removeRow(selectedRow);
                        tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
                        System.out.println("___response___");
                    } catch (IOException ex) {
                        System.out.println("API Error: " + ex.getMessage());
//                        return "";
                    }
                } catch (JSONException ex) {
                    System.out.println("Error: " + ex.getMessage());
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

//        jt.setEnabled(false);
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(jt);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          

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
    }// </editor-fold>                        

}
