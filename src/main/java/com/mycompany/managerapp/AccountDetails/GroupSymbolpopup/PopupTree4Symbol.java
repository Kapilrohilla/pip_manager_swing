package com.mycompany.managerapp.AccountDetails.GroupSymbolpopup;

import com.mycompany.managerapp.AccountDetails.TradeRight;
import com.mycompany.managerapp.Utility.APIs;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.event.TreeSelectionEvent;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Kapil
 */
public class PopupTree4Symbol extends javax.swing.JDialog {

    /**
     * Creates new form PopupTree4Symbol
     */
    JTree tree;
    ArrayList<JSONObject> symbolList = new ArrayList<>();

    String symbolname = "";
    String pathofsymbol = "";

    public PopupTree4Symbol(String from) {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Add Symbol");
        long lo = System.currentTimeMillis();

        String apiUrl = APIs.GET_SYMBOL_CATEGORY + "?timestamp=" + lo;
        String responseData = getData(apiUrl);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
        try {
            JSONObject json = new JSONObject(responseData);
            JSONArray js = json.getJSONArray("message");

            for (int i = 0; i < js.length(); i++) {
                System.out.println("working....");
                JSONObject jsonobject = js.getJSONObject(i);
                String symbol = jsonobject.getString("name");
                String symbolId = jsonobject.getString("_id");
                JSONArray symbolarray = jsonobject.getJSONArray("symbols");

                DefaultMutableTreeNode group = new DefaultMutableTreeNode(symbol);

                if (symbolarray.length() > 0) {
                    for (int j = 0; j < symbolarray.length(); j++) {
                        JSONObject symbolData = symbolarray.getJSONObject(j);
                        symbolList.add(symbolData);
                        final String symbolName = symbolData.getString("Symbol");
                        DefaultMutableTreeNode symbolNode = new DefaultMutableTreeNode(symbolName);
                        group.add(symbolNode);
                    }
                }
                root.add(group);
                recursionsymbol(jsonobject, group);
            }
        } catch (JSONException ex) {
            System.out.println("a");
        }
        tree = new JTree(root);

        tree.addTreeSelectionListener((TreeSelectionEvent e) -> {
            TreePath selectedPath = e.getNewLeadSelectionPath();
            if (selectedPath != null) {
                pathofsymbol = selectedPath + "";
                pathofsymbol = pathofsymbol.substring(1, pathofsymbol.lastIndexOf("]"));
                System.out.println(pathofsymbol + "");
                String selectedNode = selectedPath.getLastPathComponent().toString();
                System.out.println("Selected node: " + selectedNode);
//                if (selectedNode.equalsIgnoreCase("groups")) {
//                }
                try {
                    for (int i = 0; i < symbolList.size(); i++) {
                        String symName = symbolList.get(i).getString("Symbol");
                        if (selectedNode.equalsIgnoreCase(symName)) {
                            symbolname = symName;

                            System.out.println("tapped");

                            break;
                        }
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        });
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("updateBtn pressed");

//                if (from.equalsIgnoreCase("TradeRight")) {
                TradeRight.jLabel14.setText(symbolname);
//                }

                dispose();
            }

        });
        setLayout(new BorderLayout());
        pack();
        setModal(true);
        add(update, BorderLayout.NORTH);
        add(tree, BorderLayout.CENTER);
    }

    boolean issymbol(String name) {
//        System.out.println(name+"namdsf");
        name = name.trim();
        try {
            for (int i = 0; i < symbolList.size(); i++) {
                String symName = symbolList.get(i).getString("Symbol");
                if (name.equalsIgnoreCase(symName)) {
//                            symbolname=symName;
//                            String symbolId = symbolList.get(i).getString("_id");
                    System.out.println("tapped");
//                            groupmodel=new GroupModel(symbolId,symName);
//                            isFound = true;
                    return true;

                }
            }
        } catch (JSONException ex) {
            System.out.println("exception occurred");
        }
        return false;
    }

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

    private void recursionsymbol(JSONObject jsonobject, DefaultMutableTreeNode parentNode) {
        try {

            JSONArray jxa = jsonobject.getJSONArray("nestedCategories");

            if (jxa.length() != 0) {
                for (int i = 0; i < jxa.length(); i++) {
                    JSONObject jsoa = jxa.getJSONObject(i);
                    String symbolName = jsoa.getString("name");
                    DefaultMutableTreeNode symbol = new DefaultMutableTreeNode(symbolName);

                    parentNode.add(symbol);
                    System.out.println("symbol added successfully");

                    if (jsoa.getJSONArray("nestedCategories").length() != 0) {
                        recursionsymbol(jsoa, symbol);
                    }

                }
            }
        } catch (JSONException ex) {
            System.out.println("exception occurred");
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
            java.util.logging.Logger.getLogger(PopupTree4Symbol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopupTree4Symbol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopupTree4Symbol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopupTree4Symbol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
////                new PopupTree4Symbol().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
