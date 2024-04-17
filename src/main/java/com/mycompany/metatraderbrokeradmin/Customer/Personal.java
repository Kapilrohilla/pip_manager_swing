/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metatraderbrokeradmin.Customer;

import com.jogamp.nativewindow.swt.SWTAccessor;
import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.Utility.DatePicker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;

import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
public class Personal extends javax.swing.JPanel {

    /**
     * Creates new form Personal
     */
    public Personal() {
        initComponents();

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    String title = jComboBox1.getSelectedItem().toString();
                    String firstName = jTextField1.getText();
//                    String middleName = jTextField2.getText();
                    String gender = jTextField2.getText().toString();
                    String language = jTextField5.getText().toString();
//                   String preferredMethod = jComboBox4.getSelectedItem().toString();
                    String email = jTextField3.getText();
//                    String countryCode = jComboBox5.getSelectedItem().toString();
                    String phoneNumber = jTextField4.getText();
//                    String messenger = jTextField5.getText();
//                    String socialNetwork = jTextField6.getText();
//                    String externalId = jTextField7.getText();
                    String password = jTextField8.getText();

                    JSONObject js = new JSONObject();
//                    js.put("title", title);
                    js.put("name", firstName);
                    js.put("gender", gender);
                    js.put("language", language);
//                    js.put("prefferedMode", preferredMethod);
                    js.put("email", email);
                    js.put("mobile", phoneNumber);
                    js.put("password", password);
//                    js.put("socialNetwork", socialNetwork);
//                    js.put("externalId", externalId);
//                    js.put("countryCode", countryCode);
//                    js.put("messenger", messenger);

                    postData(js);
                } catch (Exception ex) {
                    Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public Personal(JSONObject js) {
        initComponents();
        try {
            System.out.println("clientDetails = " + js);

            // setting field with previous submitted data
            String name = js.getString("name");

//        String[] nameArray = name.split(" ", 3);
//
//        System.out.println("title = " + nameArray[0]);
//        System.out.println("firstName= " + nameArray[1]);
////        System.out.println("middleName = " + nameArray[2]);
//        String title = nameArray[0];
//        String firstName = nameArray[1];
//        String middleName = nameArray[2];
            String gender = js.getString("gender");
            String language = js.getString("language");
//        String preferredMethod = js.getString("prefferedMode");
            String email = js.getString("email");
            String mobile = js.getString("mobile");
//        String password = js.getString("password");
//        String socialNetwork = js.getString("socialNetwork");
//        String externalId = js.getString("externalId");
//        String countryCode = js.getString("countryCode");
//        String messenger = js.getString("messenger");

//        jComboBox1.setSelectedItem(title);
            jTextField1.setText(name);
//        jTextField2.setText(middleName);
            jTextField2.setText(gender);
            jTextField4.setText(language);
//        jComboBox4.setSelectedItem(preferredMethod);
//        jComboBox5.setSelectedItem(countryCode);
            jTextField3.setText(email);
            jTextField4.setText(mobile);
//        jTextField5.setText(messenger);
//        jTextField6.setText(socialNetwork);
//        jTextField7.setText(externalId);

            // hiding password field
            jLabel15.setVisible(false);
            jTextField8.setVisible(false);

        } catch (Exception e) {

        }
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // updating fields
                try {
//                    String title = jComboBox1.getSelectedItem().toString();
                    String firstName = jTextField1.getText();
//                    String middleName = jTextField2.getText();
                    String gender = jTextField2.getText().toString();
                    String language = jTextField5.getText().toString();
//                    String preferredMethod = jComboBox4.getSelectedItem().toString();
                    String email = jTextField3.getText();
//                    String countryCode = jComboBox5.getSelectedItem().toString();
                    String phoneNumber = jTextField4.getText();
//                    String messenger = jTextField5.getText();
//                    String socialNetwork = jTextField6.getText();
//                    String externalId = jTextField7.getText();
                    String dob = jButton2.getText();
//                    String password = jTextField8.getText();

                    JSONObject js = new JSONObject();

                    js.put("name", firstName);
                    js.put("gender", gender);
                    js.put("language", language);
//                    js.put("prefferedMode", preferredMethod);
                    js.put("email", email);
                    js.put("mobile", phoneNumber);
                    js.put("birthDay", dob);
//                    js.put("password", password);
//                    js.put("socialNetwork", socialNetwork);
//                    js.put("externalId", externalId);
//                    js.put("countryCode", countryCode);
//                    js.put("messenger", messenger);

                    postData(js);
                } catch (Exception ex) {
                    Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    void postData(JSONObject js) throws Exception {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String apiUrl = APIs.CREATE_CLIENT;
        System.out.println(js);
        RequestBody body = RequestBody.create(JSON, js.toString());
        System.out.println(body);
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
//            BrokerAdmin.splitPane.setRightComponent(new ClientPanel());

            JOptionPane.showMessageDialog(Personal.this, "SuccessFully Done");
            System.out.print("Data Submitted ");
            System.out.println(response.code());
            System.out.println(response.body().toString());
        } else {
            System.out.println("Submit Request failed with " + response.body().toString());
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
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        updateBtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Personal");

        jLabel3.setText("Name");

        jLabel5.setText("Gender");

        jLabel6.setText("Birthdate");

        jLabel8.setText("Language");

        jLabel10.setText("Email");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel11.setText("Mobile");

        updateBtn.setText("Submit");

        jLabel15.setText("Password");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jButton2.setText("choose date");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateBtn)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField3)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3)
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel5))
                                .addComponent(jLabel10)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(121, 121, 121)))
                    .addComponent(jLabel11)
                    .addComponent(jLabel15)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(210, 210, 210)
                .addComponent(updateBtn)
                .addContainerGap(10, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton2.setText(new DatePicker(this).setPickedDate());

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
