/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.managerapp.AccountDetails;

import com.mycompany.managerapp.LeftPanelSocket;
import com.mycompany.managerapp.ManagerApp;
import com.mycompany.managerapp.Utility.APIs;
import com.teamdev.jxbrowser.deps.org.checkerframework.checker.units.qual.A;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import io.socket.client.Socket;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kapil
 */
public class AccountDetailsContainer extends javax.swing.JFrame {

    String name;
    String email;
    String group;
    String country;
    String balance;
    String id;
    String margin;
    JSONObject jsonx;
    boolean isAccountEnabled, isEnabledTrading, isEnableDailyReport;
    long limitActiveOrders, limitPositions;
    public static JTabbedPane tabsPane;

    final String getData(String url) {

        OkHttpClient client = new OkHttpClient();

        // Create a Request object
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response res = null;
        try {
            res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            System.out.println("error Occurred at groupPanel : " + res);
            return "";
        }
    }

    public AccountDetailsContainer(String name, String email, String group, String country, String balance, String id, String margin, boolean isAccountEnabled, boolean isEnabledTrading, boolean isEnableDailyReport, long limitActiveOrders, long limitPositions, JSONObject json) {
        System.out.println("Account container called");
        setTitle("Account Details");
        this.name = name;
        this.email = email;
        this.group = group;
        this.country = country;
        this.balance = balance;
        this.id = id;
        this.margin = margin;
        this.isAccountEnabled = isAccountEnabled;
        this.isEnabledTrading = isEnabledTrading;
        this.isEnableDailyReport = isEnableDailyReport;
        this.limitActiveOrders = limitActiveOrders;
        this.limitPositions = limitPositions;
        this.jsonx = json;
        initComponents();
        try {
            makeUserLive();
        } catch (JSONException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        try {
            tabbedPane();
        } catch (JSONException ex) {
            System.out.println("exception occurred in account defailt container tabbed pane while fetching data");
        }
        this.setLocationRelativeTo(null);
        pack();
//        setModal(true);
    }

    private void tabbedPane() throws JSONException {
        tabsPane = new JTabbedPane();

        long lo = System.currentTimeMillis();
        String apiUrl = APIs.GET_ALL_GROUPS + "?timestamp=" + lo;
        String apiData = getData(apiUrl);
        String responseData = apiData;
        System.out.println(responseData);
        JSONObject json = new JSONObject(responseData);
        JSONArray jsa = json.getJSONArray("message");

        Overview overViewPanel = new Overview(name, email, group, country, balance, id, margin);
        Personal personalPanel = new Personal(jsa, this.group, name, email);
        Tradingpersonal personal = new Tradingpersonal(name, balance, email, id, group, this.jsonx);
        Balance balance = new Balance(id);
        TradeRight tradePanel = new TradeRight(ManagerApp.managerId, id);
        OrderRightPanel orderright = new OrderRightPanel(id, ManagerApp.managerId);
        Account accountPanel = new Account(jsa, this.group, id, isAccountEnabled, name, this.jsonx);
        Security securityPanel = new Security(id);
        tabsPane.addTab("Overview", overViewPanel);
        tabsPane.addTab("Personal", personal);
        tabsPane.addTab("Account", accountPanel);
        tabsPane.addTab("Limit", new Limits(isEnabledTrading, isEnableDailyReport, id, limitActiveOrders, limitPositions));
        tabsPane.addTab("Profile", new JPanel());
        tabsPane.addTab("Subscription", new JPanel());
        tabsPane.addTab("Balance", balance);
        tabsPane.addTab("Trade", orderright);
        tabsPane.addTab("History", new History(id));
        tabsPane.addTab("Security", securityPanel);

        tabsPane.addChangeListener(e -> {
            int selectedIndex = tabsPane.getSelectedIndex();
            String selectedTitle = tabsPane.getTitleAt(selectedIndex);
            System.out.println("selectedTitle: " + selectedTitle);
            if ("History".equals(selectedTitle)) {
                System.out.println("history clickd");

            }
        });
        setLayout(new BorderLayout());

        add(tabsPane);
    }

    private void makeUserLive() throws JSONException {
        Socket socket = LeftPanelSocket.socket;

        JSONObject userpositionsPayload = new JSONObject();
        userpositionsPayload.put("userId", this.id);
        socket.emit("userpositions", userpositionsPayload);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountDetailsContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountDetailsContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountDetailsContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountDetailsContainer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new AccountDetailsContainer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
