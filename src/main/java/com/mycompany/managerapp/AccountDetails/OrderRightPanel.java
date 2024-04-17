package com.mycompany.managerapp.AccountDetails;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
import static com.mycompany.managerapp.AccountDetails.TradeRight.jLabel14;
import com.mycompany.managerapp.LeftPanelSocket;
import com.mycompany.managerapp.ManagerApp;
import com.mycompany.managerapp.Utility.APIs;
import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
 * @author kapilrohilla
 */
public class OrderRightPanel extends javax.swing.JPanel {

    Socket socket;
    boolean isStopLossFirstChange = false;
    boolean isTakeProfitFirstChange = false;
    String selectedSymbol;
    boolean isPriceFirstChange = false;

    String userId;
    String managerId;

    public OrderRightPanel(String userId, String managerId) {
        initComponents();
        hideSecondType();
        this.userId = userId;
        this.managerId = managerId;
        hideExpirationAndExpirationDate();
        jLabel9.setText("0.00 / 0.00");

        this.selectedSymbol = jComboBox1.getSelectedItem().toString();

        socket = ManagerApp.socket;
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server.");
            }
        }).on("newMessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jso = (JSONObject) args[0];
                JSONObject response;

                try {
                    response = jso.getJSONObject("newMessage");
                    String responseSymbol = response.getString("symbol");
                    if (selectedSymbol.equalsIgnoreCase(responseSymbol)) {
                        String bid = response.getString("bid");
                        String ask = response.getString("ask");
                        bid = String.format("%.2f", Double.parseDouble(bid));
                        ask = String.format("%.2f", Double.parseDouble(ask));
                        if (isStopLossFirstChange == false) {
                            jSpinner2.setValue((double) Double.parseDouble(ask));
                            isStopLossFirstChange = true;
                        }
                        if (isTakeProfitFirstChange == false) {
                            jSpinner3.setValue((double) Double.parseDouble(ask));
                            isTakeProfitFirstChange = true;
                        }
                        if (isPriceFirstChange == false) {
                            jSpinner4.setValue((double) Double.parseDouble(ask));
                            isPriceFirstChange = true;
                        }
                        jLabel9.setText(bid + " / " + ask);
                    }
                } catch (JSONException ex) {
//                    Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        ArrayList<String> symbolNames = getWatchlistData4symbolName();
        DefaultComboBoxModel<String> symbolNameModel = new DefaultComboBoxModel<>();
        jSpinner1.setValue((double) 0.1);
//        for (int i = 0; i < symbolNames.size(); i++) {
//            symbolNameModel.addElement(symbolNames.get(i));
//        }
//        jComboBox1.setModel(symbolNameModel);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jLabel12.setVisible(false);
        jSpinner5.setVisible(false);
        try {
            placeOrder();
            sellOrBuy();
        } catch (IOException ex) {
            System.out.println("Error occurred");
        }
        jComboBox1.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                Object item = event.getItem();
                System.out.println("Selected: " + item.toString());
//                String selectedSymbol = item.toString();
//                String initialUrl = OrderFrame.chartUrl;
//                String[] urlArr = initialUrl.split("=");
//                urlArr[1] = selectedSymbol;
//                String newUrl = String.join("=", urlArr);
//                System.out.println("newUrl: " + newUrl);
                this.selectedSymbol = item.toString();
                isTakeProfitFirstChange = false;
                isStopLossFirstChange = false;
            }
        });
    }

    public OrderRightPanel(String symbol) {
        initComponents();
        hideSecondType();
        hideExpirationAndExpirationDate();

        socket = ManagerApp.socket;
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server.");
            }
        }).on("newMessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jso = (JSONObject) args[0];
                JSONObject response;
                try {
                    response = jso.getJSONObject("newMessage");
                    String responseSymbol = response.getString("symbol");
                    String selectedSymbol = jComboBox1.getSelectedItem().toString();
                    if (selectedSymbol.equals(responseSymbol)) {
                        String bid = response.getString("bid");
                        String ask = response.getString("ask");
                        //socketAsk = ask;
//                        SwingUtilities.invokeLater(new Runnable() {
//                            @Override
//                            public void run() {
                        jLabel9.setText(bid + " / " + ask);
//                            }
//                        });
                    }
                } catch (JSONException ex) {
//                    Logger.getLogger(LeftPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        ArrayList<String> symbolNames = getWatchlistData4symbolName();
        DefaultComboBoxModel<String> symbolNameModel = new DefaultComboBoxModel<>();
//        for (int i = 0; i < symbolNames.size(); i++) {
//            symbolNameModel.addElement(symbolNames.get(i));
//        }
//        symbolNameModel.setSelectedItem(symbol);

        jComboBox1.setModel(symbolNameModel);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jLabel12.setVisible(false);
        jSpinner5.setVisible(false);
        try {
            placeOrder();
            sellOrBuy();
        } catch (IOException ex) {
            System.out.println("Error occurred");
        }
//        jComboBox1.addItemListener((ItemEvent event) -> {
//            if (event.getStateChange() == ItemEvent.SELECTED) {
//                Object item = event.getItem();
//                System.out.println("Selected: " + item.toString());
//                String selectedSymbol = item.toString();
////                String initialUrl = OrderFrame.chartUrl;
////                String[] urlArr = initialUrl.split("=");
//                urlArr[1] = selectedSymbol;
//                String newUrl = String.join("=", urlArr);
//                System.out.println("newUrl: " + newUrl);
//            }
//        });

    }

//    ArrayList<String> getWatchlistData4symbolName() {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        String url = APIs.GET_WATCHLIST_DATA;
//        ArrayList<String> symbolsNames = new ArrayList<>();
//        String token = Metacustomer.loginToken;
//        if (token == null) {
//            return symbolsNames;
//        }
//        Request request = new Request.Builder()
//                .url(url)
//                .header("Authorization", token)
//                .build();
//
//        Call call = client.newCall(request);
//
//        try {
//            Response res = call.execute();
//            String body = res.body().string();
//            System.out.println("body: " + body);
//            JSONObject jso = new JSONObject(body);
//            System.out.println("jso: " + jso);
//            JSONArray jsa = jso.getJSONArray("message");
////            JSONArray jsa = new JSONArray(body);
//            for (int i = 0; i < jsa.length(); i++) {
//                JSONObject singleData = jsa.getJSONObject(i);
//                String symbolName = singleData.getString("symbol");
//                System.out.println("symbolName: " + symbolName);
//                symbolsNames.add(symbolName);
//            }
//        } catch (IOException | JSONException e) {
//            System.out.println("err: \n" + e);
//
//        }
//        return symbolsNames;
//    }
    final protected void hideSecondType() {
        jComboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Object item = event.getItem().toString();
                    if (item != "Pending order") {
                        jLabel10.setVisible(false);
                        jSpinner4.setVisible(false);
                        jLabel13.setVisible(false);
                        jButton1.setVisible(false);
                        jButton2.setVisible(false);
                        jComboBox4.setVisible(false);
                        jButton3.setVisible(false);
                        jButton2.setVisible(true);
                        jButton1.setVisible(true);
                        jLabel11.setVisible(false);
                        jComboBox5.setVisible(false);
                    } else {

                        jLabel11.setVisible(true);
                        jComboBox5.setVisible(true);
                        jLabel12.setVisible(false);
                        jSpinner5.setVisible(false);
                        jLabel10.setVisible(true);
                        jSpinner4.setVisible(true);
                        jLabel13.setVisible(true);
                        jComboBox4.setVisible(true);
                        jButton3.setVisible(true);
                        jButton2.setVisible(false);
                        jButton1.setVisible(false);
                    }
                }
            }
        });
    }

    final protected void hideExpirationAndExpirationDate() {
        jComboBox4.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    Object item = event.getItem().toString();
                    if ((item == "Buy Stop Limit" || item == "Sell Stop Limit")) {
                        jLabel12.setVisible(true);
                        jSpinner5.setVisible(true);
                    } else {
                        jLabel12.setVisible(false);
                        jSpinner5.setVisible(false);
                    }
//                    if (!(item == "Buy Stop Limit" || item == "Sell Stop Limit")) {
//                        jLabel12.setVisible(true);
//                        jLabel11.setVisible(false);
//                        jComboBox5.setVisible(false);
//                        jLabel12.setVisible(false);
//                        jSpinner5.setVisible(true);
//
//                    } else {
//                        jSpinner5.setVisible(false);
//                        jLabel12.setVisible(false);
//                        jLabel11.setVisible(true);
//                        jComboBox5.setVisible(true);
//                        jLabel12.setVisible(true);
//                    }
                }
            }
        });
    }

    final void placeOrder() throws IOException {
        jButton3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String apiUrl = APIs.CREATE_ORDER;
//                    String symbol = jLabel14.getText();
                    String symbol = jComboBox1.getSelectedItem().toString();
                    //String typeValue = jComboBox2.getSelectedItem().toString();
                    //String typeValue2 = jComboBox4.getSelectedItem().toString();
                    int typeValue = jComboBox4.getSelectedIndex();
                    // System.out.println("typeValue: " + typeValue2 + "");
                    int type = typeValue + 2;
//                    type = 2;
//
//                    if(typeValue == 0){
//                        type = 2;
//                    }else if(typeValue == 1){
//                        type = 3;
//                    }else if(typeValue == 2){
//                        type = 4;
//                    }else if(typeValue == 3){
//                        type = 5;
//                    }else if(typeValue == 4){
//                        type === 6;
//                    }


//                    if ("Buy Limit".equals(typeValue2)) {
//                        type = 2;
//                    } else if ("Sell Limit".equals(typeValue2)) {
//                        type = 3;
//                    } else if ("Buy Stop".equals(typeValue2)) {
//                        type = 4;
//                    } else if ("Sell Stop".equals(typeValue2)) {
//                        type = 5;
//                    } else if ("Buy Stop Limit".equals(typeValue2)) {
//                        type = 6;
//                    } else if ("Sell Stop Limit".equals(typeValue2)) {
//                        type = 7;
//                    }

                    int exc_type = 1;
                    double qty = (double) jSpinner1.getValue();
                    double price = (double) jSpinner4.getValue();

                    double limit = (double) jSpinner5.getValue();
                    double stop_loss = (double) jSpinner2.getValue();
                    double take_profit = (double) jSpinner3.getValue();
                    String comment = jTextField1.getText();
                    String expiration = jComboBox5.getSelectedItem().toString();

                    JSONObject jso = new JSONObject();
                    jso.put("userId", userId);
                    jso.put("reason", managerId);
                    jso.put("symbol", symbol);
                    jso.put("type", type);
                    jso.put("exc_type", exc_type);
                    jso.put("qty", qty);
                    jso.put("price", price);
                    jso.put("limit", limit);
                    jso.put("stop_loss", stop_loss);
                    jso.put("take_profit", take_profit);
                    jso.put("comment", comment);
                    jso.put("expiration", expiration);

                    System.out.println("placeorderjson: " + jso);
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jso.toString());
                    System.out.println(ManagerApp.loginToken);
                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .post(body)
                            .header("Authorization", ManagerApp.loginToken)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    System.out.println(body);
                    Response response = client.newCall(request).execute();

                    System.out.println("Status: " + response.message());

                    if (response.isSuccessful()) {
                        System.out.println("Api hit successful");
                        // Get response body as strings
                        String responseBody = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBody);
                        System.out.println("responseJSON: " + responseJSON);
                        boolean isValid = responseJSON.getBoolean("valid");

                        String message = "Failed to create order.";
                        try {
                            message = responseJSON.getString("message");
                        } catch (JSONException ex) {
                            ex.getStackTrace();
                        }

                        if (isValid) {
                            JOptionPane.showMessageDialog(OrderRightPanel.this, "Order created successfully.");
                        } else {
                            JOptionPane.showMessageDialog(OrderRightPanel.this, message);
                        }
                        System.out.println("_____________________*****************_________________");
                        System.out.println(responseBody);
                        System.out.println("_____________________*****************_________________");
                    } else {
                        System.out.println("Error: " + response.body().string());
                    }
                } catch (JSONException | IOException ex) {
                    Logger.getLogger(TradeRight.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    final void sellOrBuy() {
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Sell Button clicked");
                    String apiUrl = APIs.CREATE_ORDER;
                    String symbol = jComboBox1.getSelectedItem().toString();

                    int type = 0;
                    int exc_type = 0;
                    double qty = (double) jSpinner1.getValue();
                    double stop_loss = (double) jSpinner2.getValue();
                    double take_profit = (double) jSpinner3.getValue();
                    String comment = jTextField1.getText();

                    JSONObject jso = new JSONObject();
                    jso.put("userId", userId);
                    jso.put("reason", managerId);
                    jso.put("symbol", symbol);
                    jso.put("type", type);
                    jso.put("exc_type", exc_type);
                    jso.put("qty", qty);
                    jso.put("stop_loss", stop_loss);
                    jso.put("take_profit", take_profit);
                    jso.put("comment", comment);
                    System.out.println("sell send: " + jso);
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jso.toString());

                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .post(body)
                            .header("Authorization", ManagerApp.loginToken)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    System.out.println(body);
                    Response response = client.newCall(request).execute();

                    // Check for successful response
                    if (response.isSuccessful()) {
                        // Get response body as strings
                        String responseBody = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBody);
                        boolean isValid = responseJSON.getBoolean("valid");
                        String message = "Failed to create order.";
                        try {
                            message = responseJSON.getString("message");
                        } catch (JSONException ex) {
                            ex.getStackTrace();
                        }
                        if (isValid) {
                            JOptionPane.showMessageDialog(OrderRightPanel.this, "Order created successfully.");
                        } else {
                            JOptionPane.showMessageDialog(OrderRightPanel.this, message);
                        }
                        System.out.println("_____________________*****************_________________");
                        System.out.println(responseBody);
                        System.out.println("_____________________*****************_________________");
                    } else {
                        System.out.println("Error: " + response.body().string());
                    }
                } catch (JSONException | IOException ex) {
                    Logger.getLogger(TradeRight.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("buy btn clicked");
                    String apiUrl = APIs.CREATE_ORDER;
//                    String symbol = jLabel14.getText();
                    String symbol = jComboBox1.getSelectedItem().toString();
                    int type = 1;
                    int exc_type = 0;
                    double qty = (double) jSpinner1.getValue();
                    double stop_loss = (double) jSpinner2.getValue();
                    double take_profit = (double) jSpinner3.getValue();
                    String comment = jTextField1.getText();
                    // String expiration = jComboBox5.getSelectedItem().toString();

                    JSONObject jso = new JSONObject();
                    jso.put("userId", userId);
                    jso.put("reason", managerId);
                    jso.put("symbol", symbol);
                    jso.put("type", type);
                    jso.put("exc_type", exc_type);
                    jso.put("qty", qty);
//                    jso.put("price", price);
//                    jso.put("limit", limit);
                    jso.put("stop_loss", stop_loss);
                    jso.put("take_profit", take_profit);
                    jso.put("comment", comment);
                    // jso.put("expiration", expiration);

                    System.out.println("buy json: " + jso);
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jso.toString());

                    Request request = new Request.Builder()
                            .url(apiUrl)
                            .post(body)
                            .header("Authorization", ManagerApp.loginToken)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    System.out.println(body);
                    Response response = client.newCall(request).execute();
                    System.out.println("Buy Btn clicked");
                    // Check for successful response
                    if (response.isSuccessful()) {
                        // Get response body as strings
                        String responseBody = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBody);
                        boolean isValid = responseJSON.getBoolean("valid");
                        String message = "Failed to create order.";
                        try {
                            message = responseJSON.getString("message");
                        } catch (JSONException ex) {
                            ex.getStackTrace();
                        }
                        if (isValid) {
                            JOptionPane.showMessageDialog(OrderRightPanel.this, "Order created successfully.");
                        } else {
                            JOptionPane.showMessageDialog(OrderRightPanel.this, message);
                        }
                        System.out.println("_____________________*****************_________________");
                        System.out.println(responseBody);
                        System.out.println("_____________________*****************_________________");
                    } else {
                        System.out.println("Error: " + response.body().string());
                    }
                } catch (JSONException | IOException ex) {
                    Logger.getLogger(TradeRight.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private boolean validationforInstantBuy() {
        double stopLoss = Double.parseDouble(jSpinner2.getValue().toString());
        double takeProfit = Double.parseDouble(jSpinner3.getValue().toString());
        double volume = Double.parseDouble(jSpinner1.getValue().toString());
        if (takeProfit == 0 || stopLoss == 0) {
            return true;
        }
        if (takeProfit == stopLoss) {
            validationFailedAlert("StopLoss and TakeProfit should not be same");
            return false;
        }
        if (takeProfit < stopLoss) {
            validationFailedAlert("Take Profit should be greater than Stop Loss");
            return false;
        }
        if (volume == 0 || volume == 0.0) {
            validationFailedAlert("volume should be greter then 0");
            return false;
        }
        return true;
    }

    private boolean validationforInstantSell() {
        double stopLoss = Double.parseDouble(jSpinner2.getValue().toString());
        double takeProfit = Double.parseDouble(jSpinner3.getValue().toString());
        double volume = Double.parseDouble(jSpinner1.getValue().toString());

        if (takeProfit == 0 || stopLoss == 0) {
            return true;
        }

        if (takeProfit == stopLoss) {
            validationFailedAlert("StopLoss and TakeProfit should not be same");
            return false;
        }

        if (takeProfit > stopLoss) {
            validationFailedAlert("Stop Loss should be greater than take profit");
            return false;
        }
        if (volume == 0 || volume == 0.0) {
            validationFailedAlert("volume should be greter then 0");
            return false;
        }
        return true;
    }

    private boolean validationforPendingOrders() {
        int orderTypeIndex = jComboBox4.getSelectedIndex();
        double volume = Double.parseDouble(jSpinner1.getValue().toString());
        double stopLoss = Double.parseDouble(jSpinner2.getValue().toString());
        double takeProfit = Double.parseDouble(jSpinner3.getValue().toString());
        double price = Double.parseDouble(jSpinner4.getValue().toString());
        double limit = Double.parseDouble(jSpinner5.getValue().toString());

        if (volume == 0 || volume == 0.0) {
            validationFailedAlert("volume should be greter then 0");
            return false;
        }

        if (price == 0 || price == 0.0) {
            validationFailedAlert("price should not be equal to 0");
            return false;
        }
        if (stopLoss == 0 || stopLoss == 0.0) {
            validationFailedAlert("StopLoss should not be equal to 0");
            return false;
        }
        if (takeProfit == 0 || takeProfit == 0.0) {
            validationFailedAlert("TakeProfit should not be equal to 0");
            return false;
        }
        if (takeProfit == stopLoss) {
            validationFailedAlert("StopLoss and Take Profit should be different");
            return false;
        }
        if (orderTypeIndex == 4 || orderTypeIndex == 5) {
            if (limit == 0 || limit == 0.0) {
                validationFailedAlert("Limit should not be equal to 0");
                return false;
            }
        }
        // buy 0 || 2|| 4 
        if (orderTypeIndex == 0 || orderTypeIndex == 2 || orderTypeIndex == 4) {
            if (takeProfit < stopLoss) {
                validationFailedAlert("Take Profit should be greater than Stop Loss");
                return false;
            }
        } else {
            if (takeProfit > stopLoss) {
                validationFailedAlert("Stop Loss should be greater than take profit");
                return false;
            }
        }
        return true;
    }

    final void alert(boolean isSuccess) {
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Order created");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create order");
        }
    }

    final void validationFailedAlert(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jSpinner4 = new javax.swing.JSpinner();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();

        jLabel1.setText("Symbol:");

//        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"XAUUSD", "EURUSD"}));
        int watchlistDataSize = LeftPanelSocket.watchlistData.size();
        String[] symbolList = new String[watchlistDataSize];
        for (int i = 0; i < watchlistDataSize; i++) {
            ArrayList<JSONObject> arry = LeftPanelSocket.watchlistData;
            try {
                String symbolName = arry.get(i).getString("Symbol");
                symbolList[i] = symbolName;
            } catch (JSONException ex) {
                System.out.println("ex: " + ex.getMessage());
            }
        }
//        jComboBox1.setModel(DefaultComboBoxModel <> (symbolList));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(symbolList));
        jLabel2.setText("Type:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Pending order", "Instant Execution"}));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Volume:");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0.0f, 0.0f, null, 0.1f));
        jSpinner1.setMinimumSize(new java.awt.Dimension(80, 22));

        jLabel4.setText("EUR");

        jLabel5.setText("Stop Loss:");

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        jSpinner2.setMinimumSize(new java.awt.Dimension(80, 22));

        jLabel6.setText("Take Profit:");

        jSpinner3.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        jSpinner3.setMinimumSize(new java.awt.Dimension(80, 22));

        jLabel7.setText("Comment:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Deviation:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("1.0761o / 1.0761o");

        jButton1.setText("Sell");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buy");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setText("Type:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Buy Limit", "Sell Limit", "Buy Stop", "Sell Stop", "Buy Stop Limit", "Sell Stop Limit"}));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel11.setText("Expiration:");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"GTC", "Specified"}));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel13.setText("Price");

        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        jSpinner4.setMinimumSize(new java.awt.Dimension(80, 22));

        jButton3.setText("Place Order");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setText("Limit");

        jSpinner5.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        jSpinner5.setMinimumSize(new java.awt.Dimension(80, 22));

        jSpinner6.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel10)
                                                        .addComponent(jLabel11)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel8))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(jSpinner6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                        .addComponent(jSpinner2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(jComboBox5, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                                .addGap(4, 4, 4)
                                                                                .addComponent(jLabel4)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jSpinner4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                        .addComponent(jSpinner5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                                                                        .addComponent(jSpinner3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel13)
                                                                        .addComponent(jLabel12))
                                                                .addContainerGap(112, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel13)
                                        .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12)
                                        .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(jSpinner6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}