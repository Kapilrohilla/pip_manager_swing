/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author kapilrohilla
 */
public class SpecificationLeftPanel extends javax.swing.JPanel {

    /**
     * Creates new form SpecificationRightPanel
     */
    public SpecificationLeftPanel() {
//        initComponents();
        tree();
    }

    void tree() {
        DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("Meta Trader 5");
        JTree jt = new JTree(treeRoot);
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Forex");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Metal");
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("Indexes");
        DefaultMutableTreeNode node4 = new DefaultMutableTreeNode("Custom");
        treeRoot.add(node1);
        treeRoot.add(node2);
        treeRoot.add(node3);
        treeRoot.add(node4);

        JScrollPane scrollPane = new JScrollPane(jt);

        setLayout(new BorderLayout());

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
            .addGap(0, 439, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
