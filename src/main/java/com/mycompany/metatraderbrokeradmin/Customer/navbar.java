/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.metatraderbrokeradmin.Customer;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.json.JSONObject;

/**
 *
 * @author kapilrohilla
 */
public class navbar extends javax.swing.JPanel {

    /**
     * Creates new form navbar
     */
    public navbar() {
        initComponents();
        tree();
    }

    public navbar(JSONObject js) {
        initComponents();
        tree(js);
    }

    private void tree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Administrator");
        DefaultMutableTreeNode personal = new DefaultMutableTreeNode("Personal");

        top.add(personal);

        // Create the JTree
        JTree tree = new JTree(top);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath selectedPath = e.getNewLeadSelectionPath();
                if (selectedPath != null) {
                    String selectedNode = selectedPath.getLastPathComponent().toString();
                    System.out.println("Selected node: " + selectedNode);
//                    if (selectedNode.equalsIgnoreCase("client")) {
//                        temp.splitPane.setRightComponent(new clientPanel());
//                    } 
//                    else if (selectedNode.equalsIgnoreCase("address")) {
//                        temp.splitPane.setRightComponent(new Address());
//                    } else if (selectedNode.equalsIgnoreCase("regulation")) {
//                        temp.splitPane.setRightComponent(new RegulationPanel());
//                    } else if (selectedNode.equalsIgnoreCase("documents")) {
//                        temp.splitPane.setRightComponent(new KYC());
//                    } else if (selectedNode.equalsIgnoreCase("personal")) {
//                        temp.splitPane.setRightComponent(new Personal());
//                    } else if (selectedNode.equalsIgnoreCase("")) {
////                       temp
//                    }
                }
            }
        });

        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tree);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void tree(JSONObject js) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Administrator");
//        DefaultMutableTreeNode client = new DefaultMutableTreeNode("Client");
        DefaultMutableTreeNode personal = new DefaultMutableTreeNode("Personal");
//        DefaultMutableTreeNode address = new DefaultMutableTreeNode("Address");
//        DefaultMutableTreeNode regulation = new DefaultMutableTreeNode("Regulation");
//        DefaultMutableTreeNode documents = new DefaultMutableTreeNode("Documents");
        DefaultMutableTreeNode comments = new DefaultMutableTreeNode("Trading Account");

        top.add(personal);
//        top.add(client);
//        top.add(address);
//        top.add(regulation);
//        top.add(documents);
        top.add(comments);

        // Create the JTree
        JTree tree = new JTree(top);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                try {
                    String userId = js.getString("_id");
                    TreePath selectedPath = e.getNewLeadSelectionPath();
                    if (selectedPath != null) {
                        String selectedNode = selectedPath.getLastPathComponent().toString();
                        System.out.println("Selected node: " + selectedNode);
//                    if (selectedNode.equalsIgnoreCase("client")) {
//                        temp.splitPane.setRightComponent(new clientPanel(js));
//                    } else if (selectedNode.equalsIgnoreCase("address")) {
//                        temp.splitPane.setRightComponent(new Address(js));
//                    } else if (selectedNode.equalsIgnoreCase("regulation")) {
//                        temp.splitPane.setRightComponent(new RegulationPanel(js));
//                    } else if (selectedNode.equalsIgnoreCase("documents")) {
//                        temp.splitPane.setRightComponent(new KYC());
//                    } else 
                        if (selectedNode.equalsIgnoreCase("personal")) {
                            temp.splitPane.setRightComponent(new Personal(js));
                        } else if (selectedNode.equalsIgnoreCase("trading account")) {
                            temp.splitPane.setRightComponent(new TablePanel(userId));
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
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
            .addGap(0, 419, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
