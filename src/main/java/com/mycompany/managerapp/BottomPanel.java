/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author techninza
 */
public class BottomPanel extends javax.swing.JPanel {

    /**
     * Creates new form BottomPanel
     */
    public BottomPanel() {
        initComponents();
        tab();
    }

    private void tab() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create the tabs
        JPanel tab1 = new JPanel();
//        JButton b=new JButton("OK");
//        tab1.add(b,BorderLayout.SOUTH);
        tab1.add(new JLabel("Tab 1"));

        JPanel tab2 = new JPanel();
        tab2.add(new JLabel("Tab 2"));

        JPanel tab3 = new JPanel();
        tab3.add(new JLabel("Tab 3"));

        JPanel tab4 = new JPanel();
        tab4.add(new JLabel("Tab 4"));

        JPanel tab5 = new JPanel();
        tab5.add(new JLabel("Tab 5"));

        JPanel tab6 = new JPanel();
        tab6.add(new JLabel("Tab 6"));

        JPanel tab7 = new JPanel();
        tab7.add(new JLabel("Tab 6"));

//           JPanel tab8 = new JPanel();
        JournalPanel tab8 = new JournalPanel();
//        tab8.add(new JLabel("Tab 6"));

//           JPanel tab9 = new JPanel();
//        tab9.add(new JLabel("Tab 6"));
        // Add the tabs to the tabbed pane
//        tabbedPane.addTab("Summary", tab1);
//        tabbedPane.addTab("Exposure", tab2);
        tabbedPane.addTab("News", tab3);
//        tabbedPane.addTab("Calender", tab4);
//        tabbedPane.addTab("Dealing", tab5);
//        tabbedPane.addTab("Alerts", tab6);
//        tabbedPane.addTab("Search", tab7);
        tabbedPane.addTab("Journal", tab8);
//        tabbedPane.addTab("Sessions", tab9);
        setLayout(new BorderLayout());

        add(tabbedPane, BorderLayout.CENTER);
        // Add the tabbed pane to the frame

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
