/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.managerapp.AccountDetails.GroupSymbolpopup;

//import com.mycompany.metatraderbrokeradmin.GroupConfig.GroupConfig;
//import com.mycompany.metatraderbrokeradmin.Manager.GroupAssignPanel;
//import com.mycompany.metatraderbrokeradmin.Utility.APIs;
import com.mycompany.managerapp.AccountDetails.Account;
import com.mycompany.managerapp.AccountDetails.Personal;
import com.mycompany.managerapp.AccountDetails.newAccount;
import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.Utility.GroupModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
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
public class PopupTree4Group extends javax.swing.JDialog {

    /**
     * Creates new form PopupTree
     */
    GroupModel groupmodel;

    final String getData(String url) {
        OkHttpClient client = new OkHttpClient();
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

    ArrayList<JSONObject> groupList = new ArrayList<>();

    public PopupTree4Group(String from) throws JSONException {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Add Group");
        this.setLocationRelativeTo(null);
        setModal(true);

        long lo = System.currentTimeMillis();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Groups");

        String apiUrl = APIs.GET_CATEGORY_GROUPS + "?timestamp=" + lo;

        JSONObject jso = new JSONObject(getData(apiUrl));
//         System.out.println("jso: " + jso);
        JSONArray groupData = jso.getJSONArray("message");

        for (int i = 0; i < groupData.length(); i++) {
            JSONObject jsonobject = groupData.getJSONObject(i);
            // System.out.println("groupLength: " + groupData);
            String groupName = jsonobject.getString("Name");
            String groupId = jsonobject.getString("_id");
            groupList.add(jsonobject);
            DefaultMutableTreeNode level1Node = new DefaultMutableTreeNode(groupName);
//            break;
            root.add(level1Node);

            recursiongroup(jsonobject, level1Node);
        }

        JTree jt = new JTree(root);

        setLayout(new BorderLayout());

        jt.addTreeSelectionListener((TreeSelectionEvent e) -> {
            TreePath selectedPath = e.getNewLeadSelectionPath();
            if (selectedPath != null) {
                String selectedNode = selectedPath.getLastPathComponent().toString();
                System.out.println("Selected node: " + selectedNode);

                for (int i = 0; i < groupList.size(); i++) {
                    try {
                        String symName = groupList.get(i).getString("Name");
                        if (selectedNode.equalsIgnoreCase(symName)) {
                            String symbolId = groupList.get(i).getString("_id");
                            System.out.println("tapped");
                            groupmodel = new GroupModel(symbolId, symName);

                            break;
                        }
                    } catch (JSONException ex) {
                        System.out.println("exception occurred while fetching Name or _id from gorup list");
                    }

                }

            }
        });
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("found");

                if (from.equalsIgnoreCase("Personal")) {
                    System.out.println("found");
                    Personal.jLabel19.setText(groupmodel.getName());
                    Personal.groupid = groupmodel.getId();
                } else if (from.equalsIgnoreCase("Account")) {
                    System.out.println("found");
                    Account.jLabel7.setText(groupmodel.getName());
                    Account.groupid = groupmodel.getId();
                } else if (from.equalsIgnoreCase("newAccount")) {
                    newAccount.selectedGroupId = groupmodel.getId();
                    newAccount.jButton3.setText(groupmodel.getName());
                }

                dispose();
            }

        });
        add(update, BorderLayout.NORTH);
        add(jt, BorderLayout.CENTER);

    }

    private void recursiongroup(JSONObject jsonobject, DefaultMutableTreeNode groups) {
        /*
        JSONArray jxa = jsonobject.getJSONArray("nestedSymbols");
        if (jxa.length() != 0) {
            for (int i = 0; i < jxa.length(); i++) {
                JSONObject jsoa = jxa.getJSONObject(i);
                groupList.add(jsoa);
                String groupId = jsoa.getString("_id");
                String groupName = jsoa.getString("Name");
                DefaultMutableTreeNode group = new DefaultMutableTreeNode(groupName);
//                        groupList.add(jsonobject);
                groups.add(group);

                if (jsoa.getJSONArray("nestedSymbols").length() != 0) {
                    recursiongroup(jsoa, group);
                }
            }
        }
         */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(PopupTree4Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopupTree4Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopupTree4Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopupTree4Group.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
