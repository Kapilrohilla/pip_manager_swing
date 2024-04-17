package com.mycompany.managerapp;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.AccountDetails.AccountDetailsContainer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.css.Match;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
public final class TradingAccountTable extends javax.swing.JPanel {

    /**
     * Creates new form TradingAccountTable
     */
    String name, email, group, country, balance, id, margin;
    boolean isAccountEnabled, isEnabledTrading, isEnableDailyReport;
    long limitActiveOrder, limitPositions;
    DefaultTableModel tableModel;
    String acc_name, acc_email, acc_mobile;

    class over {

        String namm;
        String emm;
        String grr;
        String con;
        String bal;
        String id;
        String margin;
        JSONObject json;
        boolean isAccountEnabled, isEnabledTrading, isEnableDailyReport;
        long limitActiveOrders, limitPositions;

        public over() {

        }

        public over(String namm, String emm, String grr, String con, String bal, String id, String margin, boolean isAccountEnabled, boolean isEnabledTrading, boolean enableDailyReport, long limitActiveOrders, long limitPositions, JSONObject json) {
            this.namm = namm;
            this.emm = emm;
            this.grr = grr;
            this.con = con;
            this.bal = bal;
            this.id = id;
            this.margin = margin;
            this.isAccountEnabled = isAccountEnabled;
            this.isEnabledTrading = isEnabledTrading;
            this.isEnableDailyReport = enableDailyReport;
            this.limitActiveOrders = limitActiveOrders;
            this.limitPositions = limitPositions;
            this.json = json;
        }

        public String getNamm() {
            return namm;
        }

        public void setNamm(String namm) {
            this.namm = namm;
        }

        public void setLimitActiveOrder(long limitActiveOrders) {
            this.limitActiveOrders = limitActiveOrders;
        }

        public long getLimitActiveOrder() {
            return this.limitActiveOrders;
        }

        public void setLimitPosition(long limitPosition) {
            this.limitPositions = limitPosition;
        }

        public long getLimitPosition() {
            return this.limitPositions;
        }

        public void setIsAccountEnabled(boolean isEnabled) {
            this.isAccountEnabled = isEnabled;
        }

        public boolean getIsAccountEnabled() {
            return this.isAccountEnabled;
        }

        public void setIsEnabledTrading(boolean isEnabled) {
            this.isEnabledTrading = isEnabled;
        }

        public boolean getIsEnabledTrading() {
            return isEnabledTrading;
        }

        public void setIsEnabledDailyReport(boolean isEnabled) {
            this.isEnableDailyReport = isEnabled;
        }

        public boolean getIsEnabledDailyReport() {
            return this.isEnableDailyReport;
        }

        public String getEmm() {
            return emm;
        }

        public void setEmm(String emm) {
            this.emm = emm;
        }

        public void setMargin(String margin) {
            this.margin = margin;
        }

        public String getMargin() {
            return margin;
        }

        public String getGrr() {
            return grr;
        }

        public void setGrr(String grr) {
            this.grr = grr;
        }

        public String getCon() {
            return con;
        }

        public void setCon(String con) {
            this.con = con;
        }

        public String getBal() {
            return bal;
        }

        public void setBal(String bal) {
            this.bal = bal;
        }

        public String getId() {
            return this.id;
        }

        public JSONObject getJson() {
            return json;
        }

        public void setJson(JSONObject json) {
            this.json = json;
        }

    }

    ArrayList<over> overarray = new ArrayList();

    public TradingAccountTable() {
        initComponents();
        System.out.println("Loading... trading account table...");
        try {
            table();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    final void table() throws JSONException {
        String apiData = getData();
        JSONObject jsobject = new JSONObject(apiData);
        JSONArray jsonarray = jsobject.getJSONArray("message");
        JSONObject jsob = jsonarray.getJSONObject(0);
        JSONArray users = jsob.getJSONArray("tradingAccounts");
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Login");
        tableModel.addColumn("Group Name");
//       
        tableModel.addColumn("Name");
        tableModel.addColumn("Balance");
        tableModel.addColumn("Equity");
        tableModel.addColumn("Running Profit");
        tableModel.addColumn("Lead Campaign");
        tableModel.addColumn("Lead Source");

        for (int i = 0; i < users.length(); i++) {
            JSONObject jso = users.getJSONObject(i);
            try {
                name = jso.getString("groupName");
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
                balance = jso.getString("balance") + "";
                System.out.println("balance = " + balance);
            } catch (JSONException ex) {
                balance = 0 + "";
            }
            try {
                isAccountEnabled = jso.getBoolean("status");
            } catch (JSONException ex) {
                isAccountEnabled = false;
            }
            try {
                isEnabledTrading = jso.getBoolean("enableTrading");
            } catch (JSONException ex) {
                isEnabledTrading = false;
            }
            try {
                isEnableDailyReport = jso.getBoolean("enableDailyRepors");
            } catch (JSONException ex) {
                isEnableDailyReport = false;
            }

            try {
                this.limitActiveOrder = jso.getLong("limitActiveOrders");
            } catch (JSONException ex) {
                limitActiveOrder = 0;
            }

            try {
                this.limitPositions = jso.getLong("limitPositions");
            } catch (JSONException ex) {
                limitPositions = 0;
            }

            try {
                this.acc_name = jso.getString("name");
            } catch (JSONException ex) {
                acc_name = "";
            }
            id = jso.getString("_id");
            margin = jso.getString("margin");
            String[] rowData = {email, name, acc_name, balance, "", "", "", ""};
            overarray.add(new over(name, email, group, country, balance, id, margin, isAccountEnabled, isEnabledTrading, isEnableDailyReport, limitActiveOrder, limitPositions, jso));
            tableModel.addRow(rowData);
        }

        jTable1.setModel(tableModel);
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setEnabled(false);

        JPopupMenu jp = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("Add");
        JMenuItem menuItem2 = new JMenuItem("Edit");
        JMenuItem menuItem3 = new JMenuItem("Delete");
        JMenuItem menuItem4 = new JMenuItem("Export To File");

        jp.add(menuItem1);
        jp.add(menuItem2);
        jp.add(menuItem3);
        jp.addSeparator();
        jp.add(menuItem4);

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new com.mycompany.managerapp.AccountDetails.newAccount().setVisible(true);
            }
        });

        jScrollPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    jp.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        });

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = jTable1.rowAtPoint(e.getPoint());
                    int column = jTable1.columnAtPoint(e.getPoint());
                    over o = overarray.get(row);
                    new AccountDetailsContainer(o.getNamm(), o.getEmm(), o.getGrr(), o.getCon(), o.getBal(), o.getId(), o.getMargin(), o.getIsAccountEnabled(), o.getIsEnabledTrading(), o.getIsEnabledDailyReport(), o.getLimitActiveOrder(), o.getLimitPosition(), o.getJson()).setVisible(true);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    jp.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    String getData() {
        OkHttpClient client = new OkHttpClient();
        String url = APIs.GET_MANAGERS_ACCOUNT + "?managerId=" + ManagerApp.managerId;
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String searchString = jTextField1.getText();
        if (searchString.length() == 0) {
            ManagerApp.jSplitPane.setRightComponent(new TradingAccountTable());
            return;
        }
        ArrayList<over> newoverarray = new ArrayList();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Login");
        tableModel.addColumn("Group Name");
//       
        tableModel.addColumn("Name");
        tableModel.addColumn("Balance");
        tableModel.addColumn("Equity");
        tableModel.addColumn("Running Profit");
        tableModel.addColumn("Lead Campaign");
        tableModel.addColumn("Lead Source");

        for (int i = 0; i < overarray.size(); i++) {
            over over = overarray.get(i);
            JSONObject jso = over.getJson();
            try {
                email = jso.getString("email");

            } catch (JSONException ex) {
                email = "";
            }

            Pattern pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            boolean isMatched = matcher.find();
            if (isMatched) {
                try {
                    name = jso.getString("groupName");

                } catch (JSONException ex) {
                    name = "";
                }

                try {
                    group = jso.getString("group");

                } catch (JSONException ex) {
                    group = "";
                }
                try {
                    country = jso.getString("country");

                } catch (JSONException ex) {
                    country = "";
                }
                try {
                    balance = jso.getString("balance") + "";

                } catch (JSONException ex) {
                    balance = 0 + "";
                }
                try {
                    isAccountEnabled = jso.getBoolean("status");
                } catch (JSONException ex) {
                    isAccountEnabled = false;
                }
                try {
                    isEnabledTrading = jso.getBoolean("enableTrading");
                } catch (JSONException ex) {
                    isEnabledTrading = false;
                }
                try {
                    isEnableDailyReport = jso.getBoolean("enableDailyRepors");
                } catch (JSONException ex) {
                    isEnableDailyReport = false;
                }

                try {
                    this.limitActiveOrder = jso.getLong("limitActiveOrders");
                } catch (JSONException ex) {
                    limitActiveOrder = 0;
                }

                try {
                    this.limitPositions = jso.getLong("limitPositions");
                } catch (JSONException ex) {
                    limitPositions = 0;
                }

                try {
                    this.acc_name = jso.getString("name");
                } catch (JSONException ex) {
                    acc_name = "";
                }
                try {
                    id = jso.getString("_id");
                } catch (JSONException ex) {
                    id = "";
                }
                try {
                    margin = jso.getString("margin");
                } catch (JSONException ex) {
                    margin = "";
                }
                newoverarray.add(new over(name, email, group, country, balance, id, margin, isAccountEnabled, isEnabledTrading, isEnableDailyReport, limitActiveOrder, limitPositions, jso));
                String[] rowData = {email, name, acc_name, balance, "", "", "", ""};
                tableModel.addRow(rowData);
                System.out.println("found: " + email);
            }
        }
        overarray = newoverarray;
        jTable1.setModel(tableModel);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
