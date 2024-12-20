/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.managerapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
// import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;

/**
 *
 * @author Kapil
 */
public class Visualization extends javax.swing.JPanel {

    /**
     * Creates new form HomeChartPanel
     */
    private static final long serialVersionUID = -5570653778104813836L;

    private CefApp cefApp_;
    public static CefClient client_;
    private CefBrowser browser_;
    private Component browerUI_;
    private boolean browserFocus_ = true;
    public static boolean fu = true;

    /**
     * To display a simple browser window, it suffices completely to create an
     * instance of the class CefBrowser and to assign its UI component to your
     * application (e.g. to your content pane). But to be more verbose, this
     * CTOR keeps an instance of each object on the way to the browser UI.
     */
    public Visualization(String startURL, boolean useOSR, boolean isTransparent) {
        try {
            // (0) Initialize CEF using the maven loader
            CefAppBuilder builder = new CefAppBuilder();
            // windowless_rendering_enabled must be set to false if not wanted. 
            if (fu) {
                builder.getCefSettings().windowless_rendering_enabled = useOSR;

                // USE builder.setAppHandler INSTEAD OF CefApp.addAppHandler!
                // Fixes compatibility issues with MacOSX
//        if(client_==null){
                // (1) The entry point to JCEF is always the class CefApp. There is only one
                //     instance per application and therefore you have to call the method
                //     "getInstance()" instead of a CTOR.
                //
                //     CefApp is responsible for the global CEF context. It loads all
                //     required native libraries, initializes CEF accordingly, starts a
                //     background task to handle CEF's message loop and takes care of
                //     shutting down CEF after disposing it.
                //
                //     WHEN WORKING WITH MAVEN: Use the builder.build() method to
                //     build the CefApp on first run and fetch the instance on all consecutive
                //     runs. This method is thread-safe and will always return a valid app
                //     instance.
                cefApp_ = builder.build();
//        }
                // (2) JCEF can handle one to many browser instances simultaneous. These
                //     browser instances are logically grouped together by an instance of
                //     the class CefClient. In your application you can create one to many
                //     instances of CefClient with one to many CefBrowser instances per
                //     client. To get an instance of CefClient you have to use the method
                //     "createClient()" of your CefApp instance. Calling an CTOR of
                //     CefClient is not supported.
                //
                //     CefClient is a connector to all possible events which come from the
                //     CefBrowser instances. Those events could be simple things like the
                //     change of the browser title or more complex ones like context menu
                //     events. By assigning handlers to CefClient you can control the
                //     behavior of the browser. See tests.detailed.MainFrame for an example
                //     of how to use these handlers.
                client_ = cefApp_.createClient();

                // (3) Create a simple message router to receive messages from CEF.
                CefMessageRouter msgRouter = CefMessageRouter.create();
                client_.addMessageRouter(msgRouter);
            }
            fu = false;
            // (4) One CefBrowser instance is responsible to control what you'll see on
            //     the UI component of the instance. It can be displayed off-screen
            //     rendered or windowed rendered. To get an instance of CefBrowser you
            //     have to call the method "createBrowser()" of your CefClient
            //     instances.
            //
            //     CefBrowser has methods like "goBack()", "goForward()", "loadURL()",
            //     and many more which are used to control the behavior of the displayed
            //     content. The UI is held within a UI-Compontent which can be accessed
            //     by calling the method "getUIComponent()" on the instance of CefBrowser.
            //     The UI component is inherited from a java.awt.Component and therefore
            //     it can be embedded into any AWT UI.

            browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
            browerUI_ = browser_.getUIComponent();

            // (5) For this minimal browser, we need only a text field to enter an URL
            //     we want to navigate to and a CefBrowser window to display the content
            //     of the URL. To respond to the input of the user, we're registering an
            //     anonymous ActionListener. This listener is performed each time the
            //     user presses the "ENTER" key within the address field.
            //     If this happens, the entered value is passed to the CefBrowser
            //     instance to be loaded as URL.
//        address_ = new JTextField(startURL, 100);
//        address_.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                browser_.loadURL(address_.getText());
//            }
//        });
            // Update the address field when the browser URL changes.
//        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
//            @Override
//            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
//                address_.setText(url);
//            }
//        });
            // Clear focus from the browser when the address field gains focus.
//        address_.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (!browserFocus_) return;
//                browserFocus_ = false;
//                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
//                address_.requestFocus();
//            }
//        });
            // Clear focus from the address field when the browser gains focus.
            client_.addFocusHandler(new CefFocusHandlerAdapter() {
                @Override
                public void onGotFocus(CefBrowser browser) {
                    if (browserFocus_) {
                        return;
                    }
                    browserFocus_ = true;
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                    browser.setFocus(true);
                }

                @Override
                public void onTakeFocus(CefBrowser browser, boolean next) {
                    browserFocus_ = false;
                }
            });

            setLayout(new BorderLayout());
            // (6) All UI components are assigned to the default content pane of this
            //     JFrame and afterwards the frame is made visible to the user.
//        getContentPane().add(address_, BorderLayout.NORTH);
            add(browerUI_, BorderLayout.CENTER);

            // (7) To take care of shutting down CEF accordingly, it's important to call
            //     the method "dispose()" of the CefApp instance if the Java
            //     application will be closed. Otherwise you'll get asserts from CEF.
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.out.println("I have been called");
//                CefApp.getInstance().dispose();
////                dispose();
//            }
//        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
