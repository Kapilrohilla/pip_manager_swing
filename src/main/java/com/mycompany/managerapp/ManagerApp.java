package com.mycompany.managerapp;

import com.mycompany.managerapp.Utility.APIs;
import com.mycompany.managerapp.Utility.Config;
import com.mycompany.metacustomer.Auth.AuthContainer;
import com.mycompany.metacustomer.Auth.Login;
import com.teamdev.jxbrowser.permission.Permissions;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import javax.swing.JSplitPane;

import javax.swing.JToolBar;

public class ManagerApp extends JFrame {

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu c1;
    private javax.swing.JMenu f3;
    public static String loginToken = null;

    public static String managerId = "";
    public static JSplitPane jSplitPane;

    public static Socket socket;

    public static void main(String[] args) {
        HandlePreference loginCredentials = new HandlePreference();
        String token = loginCredentials.retrieveToken();
        managerId = loginCredentials.retrieveManagerId();
//        loginCredentials.logout();

        System.out.println("token: " + token + "manager Id " + managerId);
//        if (token == "not found") {
////            System.out.println("baseURL: " + B );
//            new AuthContainer().setVisible(true);
//        } else {
        try {
            socket = IO.socket(Config.BASE_URL);
        } catch (URISyntaxException ex) {
            System.out.println("ex");
            System.out.println(ex.getMessage());
        }
        ManagerApp.loginToken = token;
        new ManagerApp();
//        }

    }

    private void toolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);

        JButton disconnectBtn = new JButton("Disconnect");

        ImageIcon goBack = new ImageIcon("assets/left.png");
        JButton backBtn = new JButton(goBack);

        ImageIcon forward = new ImageIcon("assets/right.png");
        JButton forwardBtn = new JButton(forward);
        forwardBtn.setEnabled(false);

        JButton refreshBtn = new JButton("Refresh");
        JButton restartBtn = new JButton("Restart Server");
        restartBtn.setEnabled(false);

        JButton exportBtn = new JButton("Export");
        JButton importBtn = new JButton("Import");
        JButton applyBtn = new JButton("Apply");

        ImageIcon plus = new ImageIcon("assets/plus.png");

        ImageIcon setting = new ImageIcon("assets/setting.png");
        ImageIcon cross = new ImageIcon("assets/cross.png");
        ImageIcon sort = new ImageIcon("assets/sort.png");
        ImageIcon up = new ImageIcon("assets/up.png");
        ImageIcon down = new ImageIcon("assets/down.png");
        ImageIcon play = new ImageIcon("assets/play.png");
        ImageIcon pause = new ImageIcon("assets/stop.png");

        JButton plusBtn = new JButton(plus);
        plusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newAccount().setVisible(true);
            }

        });

        disconnectBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                HandlePreference pref = new HandlePreference();
                try {
                    boolean isLogoutSuccess = pref.logout();
                    if (isLogoutSuccess) {
                        dispose();
                        new AuthContainer().setVisible(true);
                    } else {
                        System.out.println("Failed to logout");
                    }
                } catch (Exception ex) {
                    System.out.println("Something went wrong while disconnecting / logout");
                }
            }
        });
        JButton settingBtn = new JButton(setting);
        JButton crossBtn = new JButton(cross);
        JButton sortBtn = new JButton(sort);
        JButton upBtn = new JButton(up);
        JButton downBtn = new JButton(down);
        JButton playBtn = new JButton(play);
        JButton pauseBtn = new JButton(pause);

        toolBar.add(disconnectBtn);
        toolBar.addSeparator();
        toolBar.add(backBtn);
        toolBar.add(forwardBtn);
        toolBar.addSeparator();
        toolBar.add(refreshBtn);
        toolBar.add(restartBtn);
        toolBar.addSeparator();
        applyBtn.setEnabled(false);

        toolBar.add(exportBtn);
        toolBar.add(importBtn);
        toolBar.addSeparator();
        toolBar.add(applyBtn);
        toolBar.addSeparator();
        toolBar.add(plusBtn);
        toolBar.add(settingBtn);
        toolBar.add(crossBtn);
        toolBar.add(sortBtn);
        toolBar.add(upBtn);
        toolBar.add(downBtn);
        toolBar.add(playBtn);
        toolBar.add(pauseBtn);

        add(toolBar, BorderLayout.NORTH);
    }

    private void menubarsetup() {
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
//        f3 = new javax.swing.JMenu();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

//        jMenu2.setText("Edit");
//        jMenuBar1.add(jMenu2);
        jMenu5.setText("View");
        jMenuBar1.add(jMenu5);

//        jMenu4.setText("Tools");
//        jMenuBar1.add(jMenu4);
//        jMenu5.setText("Window");
//        jMenuBar1.add(jMenu5);
        jMenu6.setText("Help");
        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

    }

    private void framesetup() {
        LeftPanelSocket leftPanel = new LeftPanelSocket();
//        leftPanel.setBackground(Color.RED);

        RightPanel rightPanel = new RightPanel();

        MiddlePanel middle = new MiddlePanel();
        JSplitPane middlepanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, middle);
        middlepanel.setDividerLocation(300);
        TradingAccountTable tradingAccount = new TradingAccountTable();
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, middlepanel, tradingAccount);
        jSplitPane.setDividerLocation(600);

        BottomPanel bottomPanel = new BottomPanel();
        JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplitPane, bottomPanel);
        jSplitPane1.setDividerLocation(600);
        add(jSplitPane1);
        pack();

    }

    private void menusetup() {
        JMenuItem a1 = new JMenuItem("Connect");
        JMenuItem a2 = new JMenuItem("Disconnect");
        JMenuItem a3 = new JMenuItem("Start Dealing");
        JMenuItem a4 = new JMenuItem("Stop Dealing");
        JMenuItem a5 = new JMenuItem("New Account");
        JMenuItem a6 = new JMenuItem("Export");
        JMenuItem a7 = new JMenuItem("Open Data Folder");
        JMenuItem a8 = new JMenuItem("Print");
        JMenuItem a9 = new JMenuItem("Print Preview");
        JMenuItem a10 = new JMenuItem("Exit");

        jMenu1.add(a1);
        jMenu1.add(a2);
        jMenu1.add(a3);
        jMenu1.add(a4);
        jMenu1.addSeparator();
        jMenu1.add(a5);
        jMenu1.addSeparator();
        jMenu1.add(a6);
        jMenu1.addSeparator();
        jMenu1.add(a7);
        jMenu1.addSeparator();
        jMenu1.add(a8);
        jMenu1.add(a9);
        jMenu1.add(a10);

        a5.addActionListener((ActionEvent e) -> {
            try {
                new newAccount().setVisible(true);
            } catch (Exception ex) {
//                Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        a1.addActionListener((ActionEvent e) -> {
            try {
                new AuthContainer().setVisible(true);
            } catch (Exception ex) {
            }
        });
        a2.addActionListener((ActionEvent e) -> {
            HandlePreference pref = new HandlePreference();
            try {
                boolean isLogoutSuccess = pref.logout();
                if (isLogoutSuccess) {
                    dispose();
                    new AuthContainer().setVisible(true);
                } else {
                    System.out.println("Failed to logout");
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong while disconnecting / logout");
            }
        });
        JMenuItem b1 = new JMenuItem("Copy");
        JMenuItem b2 = new JMenuItem("Find");
        JMenuItem b3 = new JMenuItem("Find Next");
        JMenuItem b4 = new JMenuItem("Find Previous");

        jMenu2.add(b1);
        jMenu2.addSeparator();
        jMenu2.add(b2);
        jMenu2.add(b3);
        jMenu2.addSeparator();
        jMenu2.add(b4);

        c1 = new javax.swing.JMenu();
        c1.setText(("Languages"));
        JMenuItem c11 = new JMenuItem("Arabic");
        JMenuItem c12 = new JMenuItem("Chinese");
        JMenuItem c13 = new JMenuItem("English");
        JMenuItem c14 = new JMenuItem("German");
        JMenuItem c15 = new JMenuItem("Japanese");
        JMenuItem c16 = new JMenuItem("Polish");
        JMenuItem c17 = new JMenuItem("Russian");
        JMenuItem c18 = new JMenuItem("Turkish");
        JMenuItem c2 = new JMenuItem("Toolbar");
        JMenuItem c3 = new JMenuItem("Status bar");
        JMenuItem c4 = new JMenuItem("Depth Of Market");
        JMenuItem c5 = new JMenuItem("Market Watch");
        JMenuItem c6 = new JMenuItem("Navigator");
        JMenuItem c7 = new JMenuItem("Margin Calls");
        JMenuItem c8 = new JMenuItem("Queue");
        JMenuItem c9 = new JMenuItem("ToolBox");
        JMenuItem c10 = new JMenuItem("Fullscreen");

        jMenu3.add(c1);
        c1.add(c11);
        c1.add(c12);
        c1.add(c13);
        c1.add(c14);
        c1.add(c15);
        c1.add(c16);
        c1.add(c17);
        c1.add(c18);

        jMenu3.addSeparator();
        jMenu3.add(c2);
        jMenu3.add(c3);
        jMenu3.addSeparator();
        jMenu3.add(c4);
        jMenu3.add(c5);
        jMenu3.add(c6);
        jMenu3.add(c7);
        jMenu3.add(c8);
        jMenu3.add(c9);
        jMenu3.addSeparator();
        jMenu3.add(c10);

        JMenuItem d1 = new JMenuItem("Options");

        jMenu4.add(d1);

        JMenuItem e1 = new JMenuItem("Analytics");
        JMenuItem e2 = new JMenuItem("Server Reports");
        JMenuItem e3 = new JMenuItem("Online Users");
        JMenuItem e4 = new JMenuItem("Accounts");
        JMenuItem e5 = new JMenuItem("Clients");
        JMenuItem e6 = new JMenuItem("Positions");
        JMenuItem e7 = new JMenuItem("Orders");
        JMenuItem e8 = new JMenuItem("Active Subscriptions");
        JMenuItem e9 = new JMenuItem("Subscriptions History");
        JMenuItem e10 = new JMenuItem("Subscriptions Configuration");
        JMenuItem e11 = new JMenuItem("Dealer");
        JMenuItem e12 = new JMenuItem("Groups");
        JMenuItem e13 = new JMenuItem("Plugins");
        JMenuItem e14 = new JMenuItem("Mailbox");
        JMenuItem e15 = new JMenuItem("Journal");

        e5.addActionListener((ActionEvent e) -> {
            try {
                jSplitPane.setRightComponent(new ClientPanel());
            } catch (Exception ex) {
//                Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        e6.addActionListener((ActionEvent e) -> {
            try {
                jSplitPane.setRightComponent(new PositionPanel());
            } catch (Exception ex) {
//                Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        e7.addActionListener((ActionEvent e) -> {
            try {
                jSplitPane.setRightComponent(new Order());
            } catch (Exception ex) {
//                Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        e4.addActionListener((ActionEvent e) -> {
            try {
                jSplitPane.setRightComponent(new TradingAccountTable());
            } catch (Exception ex) {
//                Logger.getLogger(Metacustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        jMenu5.add(e1);
        jMenu5.add(e2);
        jMenu5.add(e3);
        jMenu5.add(e4);

        jMenu5.add(e5);

        jMenu5.add(e6);
        jMenu5.add(e7);
        jMenu5.add(e8);
        jMenu5.add(e9);
        jMenu5.add(e10);
        jMenu5.add(e11);
        jMenu5.add(e12);
        jMenu5.add(e13);
        jMenu5.add(e14);
        jMenu5.add(e15);

        JMenuItem f1 = new JMenuItem("Help Topics");
        JMenuItem f2 = new JMenuItem("What's New");
        f3 = new javax.swing.JMenu();
        f3.setText("Update Manager");
        JMenuItem f31 = new JMenuItem("Latest Release Version");
        JMenuItem f32 = new JMenuItem("Latest Beta Version");

        f3.add(f31);
        f3.add(f32);
        JMenuItem f4 = new JMenuItem("MySql5.community");
        JMenuItem f5 = new JMenuItem("MetaTrader 5 Platform");
        JMenuItem f6 = new JMenuItem("MetaQuotes Ltd.");
        JMenuItem f7 = new JMenuItem("MetaQuotes Support Center");
        JMenuItem f8 = new JMenuItem("About");

        jMenu6.add(f1);
        jMenu6.add(f2);
        jMenu6.addSeparator();
        jMenu6.add(f3);
        jMenu6.add(f4);
        jMenu6.add(f5);
        jMenu6.add(f6);
        jMenu6.add(f7);
        jMenu6.add(f8);

    }

    public ManagerApp() {
        setTitle("Manager App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("loginToken static: " + loginToken);
        menubarsetup();
        menusetup();
        toolbar();
        framesetup();

        setVisible(true);

    }
}
