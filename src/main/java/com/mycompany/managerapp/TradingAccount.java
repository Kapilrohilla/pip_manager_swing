/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import com.mycompany.managerapp.AccountDetails.AccountDetailsContainer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
public final class TradingAccount extends javax.swing.JPanel {

    /**
     * Creates new form TradingAccountTable
     */
    public TradingAccount() {
        initComponents();
        try {
            table();
        } catch (JSONException ex) {
        }

    }

    final void table() throws JSONException {
        String apiData = getData();
        ArrayList<JSONObject> usersData = new ArrayList<>();
        JSONArray users = new JSONArray(apiData);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Email");
        tableModel.addColumn("Name");
        tableModel.addColumn("Group");
        tableModel.addColumn("Country");
        tableModel.addColumn("Balance/Checked");
        System.out.println("apiData = " + users);
        System.out.println("length = " + users.length());

        for (int i = 0; i < users.length(); i++) {
            JSONObject jso = users.getJSONObject(i);
            String name, email, group, country, balance;

            try {
                name = jso.getString("name");
                System.out.println("name = " + name);
            } catch (JSONException ex) {
                name = "";
            }

            try {
                email = jso.getString("email");
                System.out.println("email = " + email);
            } catch (JSONException ex) {
                email = "";
            }
            try {
                group = jso.getString("group");
                System.out.println("group = " + group);
            } catch (JSONException ex) {
                group = "";
            }
            try {
                country = jso.getString("country");
                System.out.println("country = " + country);
            } catch (JSONException ex) {
                country = "";
            }
            try {
                balance = "" + jso.getDouble("balance");
                System.out.println("balance = " + balance);
            } catch (JSONException ex) {
                balance = 0 + "";
            }
            String[] rowData = {email, name, group, country, balance};
            usersData.add(jso);
            tableModel.addRow(rowData);
        }

        JTable jt = new JTable(tableModel);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("New Account");
        JMenuItem menuItem2 = new JMenuItem("Account Details");
        JMenuItem menuItem3 = new JMenuItem("Email");
        JMenuItem menuItem4 = new JMenuItem("Notification");
        JMenuItem menuItem5 = new JMenuItem("Journal");
        JMenuItem menuItem6 = new JMenuItem("Technical Details");
        JMenuItem menuItem7 = new JMenuItem("Drops");

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        popupMenu.addSeparator();
        popupMenu.add(menuItem3);
        popupMenu.add(menuItem4);
        popupMenu.add(menuItem5);
        popupMenu.add(menuItem6);
        popupMenu.add(menuItem7);

        menuItem2.addActionListener((ActionEvent e) -> {
            System.out.println("calling accountcontainer");
        });
        menuItem6.addActionListener((ActionEvent e) -> {
            new TechnicalDetails().setVisible(true);

        });
        jt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Show the popup menu
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        jt.setComponentPopupMenu(popupMenu);

        jt.setEnabled(false);
        JScrollPane jsp = new JScrollPane(jt);
        setLayout(new BorderLayout());

        add(jsp, BorderLayout.CENTER);
    }

    String getData() {
        OkHttpClient client = new OkHttpClient();
        String url = "http://65.0.59.137:8080/get-all-users";
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

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
