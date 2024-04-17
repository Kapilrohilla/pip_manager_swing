/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp.AccountDetails;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
// import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kapil
 */
public class TradeLeft extends javax.swing.JPanel {

    /**
     * Creates new form HomeChartPanel
     */
    Engine engine = Engine.newInstance(
            EngineOptions.newBuilder(RenderingMode.OFF_SCREEN)
                    .licenseKey("6P830J66YCGSBFUHVSJ6EF1W6C8XJKFFR6HW912I9DWC7DI1AXB5ZZFGPHOJGXN3G6JQ")
                    .build());
    public static Browser browser;

    public TradeLeft(Engine engine, String url) {
        browser = engine.newBrowser();

        SwingUtilities.invokeLater(() -> {
            BrowserView view = BrowserView.newInstance(browser);
            System.out.println("url: " + url);
            browser.navigation().loadUrl(url);
            setLayout(new BorderLayout());
            add(view, BorderLayout.CENTER);
            System.out.println("called constructor");
        });

    }

    public static void chart(Engine engine) {
        Browser browser = engine.newBrowser();

        SwingUtilities.invokeLater(() -> {
            String chartUrl = "https://www.google.co.in/";

            BrowserView view = BrowserView.newInstance(browser);
            browser.navigation().loadUrl(chartUrl);
            //browser.navigation().reload();
            System.out.println("called");
//            setLayout(new BorderLayout());
//            add(view, BorderLayout.CENTER);
        });
    }
}
