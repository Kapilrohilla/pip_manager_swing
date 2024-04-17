/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import static com.mycompany.managerapp.ManagerApp.jSplitPane;
import com.mycompany.metacustomer.Auth.Login;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
public class MiddlePanel extends javax.swing.JPanel {

    /**
     * Creates new form MiddlePanel
     */
    public MiddlePanel() {
//        initComponents();
        tree();
    }

    void tree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Manager");
        // root metatrader 5 customer nodes
//        DefaultMutableTreeNode server = new DefaultMutableTreeNode("Server");
//        DefaultMutableTreeNode analytics = new DefaultMutableTreeNode("Analytics");
        DefaultMutableTreeNode server_reports = new DefaultMutableTreeNode("Server Reports");
        DefaultMutableTreeNode client_and_order = new DefaultMutableTreeNode("Client & Order");
        DefaultMutableTreeNode subscription = new DefaultMutableTreeNode("SubScription");
        DefaultMutableTreeNode dealing = new DefaultMutableTreeNode("Dealing");
        DefaultMutableTreeNode groups = new DefaultMutableTreeNode("Groups");
//        DefaultMutableTreeNode plugins = new DefaultMutableTreeNode("Plugins");
//        DefaultMutableTreeNode mailbox = new DefaultMutableTreeNode("Mailbox");
//        DefaultMutableTreeNode support_center = new DefaultMutableTreeNode("Support Center");
//        DefaultMutableTreeNode app_store = new DefaultMutableTreeNode("App Store");

        // client & order subtree nodes
//        DefaultMutableTreeNode online_user = new DefaultMutableTreeNode("Online User");
        DefaultMutableTreeNode clients = new DefaultMutableTreeNode("Clients");
        DefaultMutableTreeNode trading_account = new DefaultMutableTreeNode("Trading Account");
        DefaultMutableTreeNode positions = new DefaultMutableTreeNode("Positions");
        DefaultMutableTreeNode orders = new DefaultMutableTreeNode("Orders");
//        client_and_order.add(online_user);
//        client_and_order.add(clients);
//        client_and_order.add(trading_account);
//        client_and_order.add(positions);
//        client_and_order.add(orders);

        // subscriptions subtree nodes
        DefaultMutableTreeNode active = new DefaultMutableTreeNode("Active");
//        DefaultMutableTreeNode history = new DefaultMutableTreeNode("History");
//        DefaultMutableTreeNode configuration = new DefaultMutableTreeNode("Configuration");
        subscription.add(active);
//        subscription.add(history);
//        subscription.add(configuration);

        // support_center subnodes
//        DefaultMutableTreeNode news = new DefaultMutableTreeNode("News");
//        DefaultMutableTreeNode articles = new DefaultMutableTreeNode("Articles");
//        DefaultMutableTreeNode platform_documentation = new DefaultMutableTreeNode("Platform Documentation");
//        DefaultMutableTreeNode api_documentation = new DefaultMutableTreeNode("API Documentation");
//        DefaultMutableTreeNode workshops = new DefaultMutableTreeNode("Worksshops");
//        DefaultMutableTreeNode certification = new DefaultMutableTreeNode("Certification");
//        DefaultMutableTreeNode service_desk = new DefaultMutableTreeNode("Service Desk");
//        DefaultMutableTreeNode online_assistant = new DefaultMutableTreeNode("Online Assistant");
//        DefaultMutableTreeNode search = new DefaultMutableTreeNode("Search");
//        support_center.add(news);
//        support_center.add(articles);
//        support_center.add(platform_documentation);
//        support_center.add(api_documentation);
//        support_center.add(workshops);
//        support_center.add(certification);
//        support_center.add(service_desk);
//        support_center.add(online_assistant);
//        support_center.add(search);
//        top.add(server);
//        top.add(analytics);
        top.add(server_reports);
        try {
            if (Login.perimission.getJSONObject(0).getBoolean("Clients")) {
                top.add(clients);
            }
            if (Login.perimission.getJSONObject(0).getBoolean("Orders")) {
                top.add(orders);
            }
            if (Login.perimission.getJSONObject(0).getBoolean("Accounts")) {
//                top.add(clients);
                top.add(positions);
            }
            if (Login.perimission.getJSONObject(0).getBoolean("Kyc")) {
                //  top.add(clients);

            }
            if (Login.perimission.getJSONObject(0).getBoolean("Trade")) {
//                top.add(clients);
                top.add(trading_account);
            }
            if (Login.perimission.getJSONObject(0).getBoolean("Subscription")) {
                top.add(subscription);
            }
            if (Login.perimission.getJSONObject(0).getBoolean("Dealing")) {
                top.add(dealing);
            }
            /*
            
             js.put("Clients", jCheckBox3.isSelected());
        js.put("Orders", jCheckBox4.isSelected());
        js.put("Accounts", jCheckBox6.isSelected());
        js.put("Kyc", jCheckBox5.isSelected());
        js.put("Trade", jCheckBox7.isSelected());
        js.put("Subscription", jCheckBox8.isSelected());
        js.put("Dealing", jCheckBox9.isSelected());

             */

            top.add(groups);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //        top.add(plugins);
//        top.add(mailbox);
//        top.add(support_center);
//        top.add(app_store);

        for (int i = 0; i < LeftPanelSocket.groupname.size(); i++) {
            DefaultMutableTreeNode certificationn = new DefaultMutableTreeNode("" + LeftPanelSocket.groupname.get(i));
            groups.add(certificationn);
        }
        // Generate the JTree
        JTree tree = new JTree(top);

        tree.addTreeSelectionListener((TreeSelectionEvent e) -> {
            TreePath selectedPath = e.getNewLeadSelectionPath();
            if (selectedPath != null) {
                String selectedNode = selectedPath.getLastPathComponent().toString();
                System.out.println("Selected node: " + selectedNode);
                if (selectedNode.equalsIgnoreCase("Clients")) {
                    jSplitPane.setRightComponent(new ClientPanel());
                } else if (selectedNode.equalsIgnoreCase("Trading Account")) {
                    jSplitPane.setRightComponent(new TradingAccountTable());
                } else if (selectedNode.equalsIgnoreCase("Dealing")) {
                    jSplitPane.setRightComponent(new TradeServerTop());
                } else if (selectedNode.equalsIgnoreCase("Positions")) {
                    jSplitPane.setRightComponent(new PositionPanel());
                } else if (selectedNode.equalsIgnoreCase("Orders")) {
                    jSplitPane.setRightComponent(new Order());
                }
            }
        });

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tree);

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
