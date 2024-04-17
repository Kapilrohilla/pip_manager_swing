/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp.AccountDetails;

import com.mycompany.managerapp.Utility.APIs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
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
public class Balance extends javax.swing.JPanel {

    /**
     * Creates new form Balance
     */
    String userid;
    int selectedBalanceRow = -1;
    ArrayList<String> idArray = new ArrayList<>();

    public Balance(String userid) {
        initComponents();
        jComboBox1.setEditable(true);
        jComboBox2.setEditable(true);
        jComboBox3.setEditable(true);
        this.userid = userid;
        tableData();
    }

    final String getData() {
        OkHttpClient client = new OkHttpClient();
        // groups/symbol
        String symbolApiUrl = APIs.GET_TRANSACTIONS + "?userId=" + userid;
        Request symbolRequest = new Request.Builder()
                .url(symbolApiUrl)
                .build();
        Call call2 = client.newCall(symbolRequest);
        try {
            Response symbolResponse = call2.execute();
            String groupSymbol = symbolResponse.body().string();
            String returns = groupSymbol;
            return returns;
        } catch (IOException e) {
            System.out.println("group config");

            return "";
        }
    }

    private void tableData() {
        DefaultTableModel model = new DefaultTableModel();
        String apiData = getData();
        System.out.println("apiData " + apiData);
        String[] columnNames = {"Id", "Type", "Description", "Amount", "Time"};
        for (String column : columnNames) {
            model.addColumn(column);
        }
        System.out.println("working");
        try {
            JSONObject json = new JSONObject(apiData);

            JSONArray jsa = json.getJSONArray(("message"));
            System.out.println("working");
            for (int i = 0; i < jsa.length(); i++) {
                JSONObject jso = jsa.getJSONObject(i);

                String id = jso.getString("_id");
                String type = jso.getString("type");
                String amount = jso.getString("amount");
                String description = jso.getString("description");
                String time = jso.getString("createdAt");
                System.out.println("createdAT:" + time);
                String[] rowData = {id, type, description, amount, time};
                System.out.println("creating rows");
                idArray.add(id);
                model.addRow(rowData);
            }

            JPopupMenu popup = new JPopupMenu();
            JMenuItem menuItem2 = new JMenuItem("Edit");
            JMenuItem menuItem3 = new JMenuItem("Delete");

            popup.add(menuItem2);
            popup.add(menuItem3);

            menuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (selectedBalanceRow != -1) {
                            String id = idArray.get(selectedBalanceRow);
                            System.out.println("id: " + id);
                            String apiUrl = APIs.DELETE_BALANCE;
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
                                    JOptionPane.showMessageDialog(Balance.this, message);
                                    jTable1.setModel(new DefaultTableModel());
                                    tableData();

                                }
                            }
                        }
                    } catch (JSONException | IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            });
            jTable1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedBalanceRow = jTable1.rowAtPoint(e.getPoint());
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
            System.out.println("rendring");
            jTable1.setModel(model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    final void update(String balance, String type) throws IOException {
        String apiUrl = APIs.ADD_BALANCE;

        JSONObject jsnObj = new JSONObject();
        try {

            jsnObj.put("userId", userid);
            jsnObj.put("type", type);
            jsnObj.put("amount", balance);

        } catch (JSONException ex) {
            System.out.println("Exception occurred in account panel while adding fields to jsonobj for body :  " + ex.getMessage());
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsnObj.toString());

        System.out.println(jsnObj.toString());
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            JOptionPane.showMessageDialog(this, "Account Updated");
            String responseBody = response.body().string();
            tableData();
            System.out.println("_____________________*****************_________________");
            System.out.println(responseBody);
            System.out.println("_____________________*****************_________________");
        } else {
            // Handle error
            System.out.println("code: " + response.code());
            System.out.println("Error: " + response.body().toString());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setText("Operation:");

        jLabel2.setText("Amount:");

        jLabel3.setText("Comments:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Balance", "Credit", "Charge", "Collection", "Bonus", "Commission", "Dividend", "Franked Dividend", "Tax", "So Compensation", "So Credit Compensation " }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "100", "500", "1000", "2000" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Adding", "Deposit", "Withdrawl", "Deposit From put your bank", "Withdrawl From put your bank", "Credit IN", "Credit Out", "Put Your Comment Here" }));

        jCheckBox1.setText("Contact balance operation without checking free margin and the current balance on the account");

        jButton1.setText("Deposit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Withdrawl");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Time", "Deal", "Type", "Amount"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            update(jComboBox2.getSelectedItem().toString(), jComboBox1.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
