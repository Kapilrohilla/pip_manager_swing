/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

//import com.mycompany.metatraderbrokeradmin.Customer.temp;
import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.Utility.Config;
import com.mycompany.metatraderbrokeradmin.Customer.temp;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
 * @author techninza
 */
public class ClientPanel extends javax.swing.JPanel {

    /**
     * Creates new form ClientPanel
     */
    public ClientPanel() {
        initComponents();
        try {
            tabledata();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getData() {
        OkHttpClient client = new OkHttpClient();
        String url = Config.BASE_URL + "get-all-clients";

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

    private void tabledata() throws JSONException {
        ArrayList<String[]> arrayList = new ArrayList<>();
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"Id", "Name", "City", "Status", "Country"};
        tableModel.addColumn(columnNames[0]);
        tableModel.addColumn(columnNames[1]);
        tableModel.addColumn(columnNames[2]);
        tableModel.addColumn(columnNames[3]);
        tableModel.addColumn(columnNames[4]);

        String apiData = getData();
        JSONObject response = new JSONObject(apiData);
        System.out.println("data = " + apiData);
        JSONArray users = response.getJSONArray("clients");

        for (int i = 0; i < users.length(); i++) {
            JSONObject client = users.getJSONObject(i);
            String id, status, name, city, country;
            try {
                id = client.getString("_id");
            } catch (JSONException ex) {
                id = "";
            }
            try {
                name = client.getString("name");
            } catch (JSONException ex) {
                name = "";
            }
            try {
                status = client.getString("status");
            } catch (JSONException ex) {
                status = "";
            }
            try {
                city = client.getString("city");
            } catch (JSONException ex) {
                city = "";
            }
            try {
                country = client.getString("country");
            } catch (JSONException ex) {
                country = "";
            }
            String[] rowData = {id, name, city, status, country};
            arrayList.add(rowData);
            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        JScrollPane jsp = new JScrollPane(table);
        table.setEnabled(false);
        setLayout(new BorderLayout());
        add(jsp, BorderLayout.CENTER);

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
                new temp();
            }
        });

        jsp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    jp.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());
                String id = arrayList.get(row)[0];
                System.out.println("client id = " + id);
//                Object selectedData = table.getValueAt(row, column);
                // Get the value of the clicked cell
                Object value = table.getValueAt(row, column);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (e.getClickCount() == 2) {
                        new temp(id);
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    jp.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

//         */ 
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
