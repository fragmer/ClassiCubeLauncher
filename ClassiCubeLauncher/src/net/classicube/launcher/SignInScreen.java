package net.classicube.launcher;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.prefs.Preferences;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

// Sign-in screen! First thing the user sees.
// Instantiated and first shown by EntryPoint.main
final class SignInScreen extends javax.swing.JFrame {

    static final long serialVersionUID = 1L;
    final Preferences prefs;

    public SignInScreen() {
        LogUtil.getLogger().log(Level.FINE, "SignInScreen");
        prefs = Preferences.userNodeForPackage(this.getClass());

        // add our fancy custom background
        bgPanel = new ImagePanel(null, true);
        this.setContentPane(bgPanel);
        bgPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        // create the rest of components
        initComponents();
        xRememberMe.setSelected(prefs.getBoolean("rememberMe", false));

        // some UI tweaks
        hookUpListeners();
        this.getRootPane().setDefaultButton(bSignIn);
        progressFiller.setSize(progress.getHeight(), progress.getWidth());
        progress.setVisible(false);

        // center the form on screen (initially)
        setLocationRelativeTo(null);

        // pick the appropriate game service
        if (SessionManager.getServiceType() == GameServiceType.ClassiCubeNetService) {
            selectClassiCubeNet();
        } else {
            selectMinecraftNet();
        }
        enableGUI();
    }

    // =============================================================================================
    //                                                                            GENERATED GUI CODE
    // =============================================================================================
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bClassiCubeNet = new javax.swing.JToggleButton();
        bMinecraftNet = new javax.swing.JToggleButton();
        cUsername = new javax.swing.JComboBox<String>();
        tPassword = new javax.swing.JPasswordField();
        xRememberMe = new javax.swing.JCheckBox();
        bSignIn = new javax.swing.JButton();
        ipLogo = new net.classicube.launcher.ImagePanel();
        progress = new javax.swing.JProgressBar();
        progressFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 14), new java.awt.Dimension(0, 14), new java.awt.Dimension(32767, 14));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ClassiCube Launcher");
        setBackground(new java.awt.Color(153, 128, 173));
        setName("ClassiCube Launcher"); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        bClassiCubeNet.setText("ClassiCube.net");
        bClassiCubeNet.setEnabled(false);
        bClassiCubeNet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bClassiCubeNetItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(bClassiCubeNet, gridBagConstraints);

        bMinecraftNet.setText("Minecraft.net");
        bMinecraftNet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bMinecraftNetItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(bMinecraftNet, gridBagConstraints);

        cUsername.setEditable(true);
        cUsername.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cUsernameItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(cUsername, gridBagConstraints);

        tPassword.setText("password");
        tPassword.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(tPassword, gridBagConstraints);

        xRememberMe.setForeground(new java.awt.Color(255, 255, 255));
        xRememberMe.setText("Remember me");
        xRememberMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xRememberMeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        getContentPane().add(xRememberMe, gridBagConstraints);

        bSignIn.setText("Sign In");
        bSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSignInActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        getContentPane().add(bSignIn, gridBagConstraints);

        ipLogo.setPreferredSize(new java.awt.Dimension(250, 75));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(ipLogo, gridBagConstraints);

        progress.setIndeterminate(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(progress, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(progressFiller, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // =============================================================================================
    //                                                      MINECRAFT / CLASSICUBE SERVICE SWITCHING
    // =============================================================================================
    private void bMinecraftNetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bMinecraftNetItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            LogUtil.getLogger().log(Level.INFO, "[Minecraft.Net]");
            selectMinecraftNet();
        }
    }//GEN-LAST:event_bMinecraftNetItemStateChanged

    private void bClassiCubeNetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bClassiCubeNetItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            LogUtil.getLogger().log(Level.INFO, "[ClassiCube.Net]");
            selectClassiCubeNet();
        }
    }//GEN-LAST:event_bClassiCubeNetItemStateChanged

    void selectClassiCubeNet() {
        LogUtil.getLogger().log(Level.FINE, "SignInScreen.SelectClassiCube");
        bgPanel.setImage(Resources.getClassiCubeBackground());
        ipLogo.setImage(Resources.getClassiCubeLogo());
        bMinecraftNet.setEnabled(true);
        bMinecraftNet.setSelected(false);
        bClassiCubeNet.setEnabled(false);
        buttonToDisableOnSignIn = bMinecraftNet;
        SessionManager.selectService(GameServiceType.ClassiCubeNetService);
        onAfterServiceChanged();

    }

    void selectMinecraftNet() {
        LogUtil.getLogger().log(Level.FINE, "SignInScreen.SelectMinecraftNet");
        bgPanel.setImage(Resources.getMinecraftNetBackground());
        ipLogo.setImage(Resources.getMinecraftNetLogo());
        bClassiCubeNet.setEnabled(true);
        bClassiCubeNet.setSelected(false);
        bMinecraftNet.setEnabled(false);
        buttonToDisableOnSignIn = bClassiCubeNet;
        SessionManager.selectService(GameServiceType.MinecraftNetService);
        onAfterServiceChanged();
    }

    void onAfterServiceChanged() {
        final String curUsername = (String) cUsername.getSelectedItem();
        this.cUsername.removeAllItems();
        if (xRememberMe.isSelected()) {
            final UserAccount[] accounts = SessionManager.getAccountManager().GetAccountsBySignInDate();
            for (UserAccount account : accounts) {
                this.cUsername.addItem(account.SignInUsername);
            }
            if (curUsername != null && !curUsername.isEmpty()) {
                cUsername.setSelectedItem(curUsername);
            } else if (cUsername.getItemCount() > 0) {
                cUsername.setSelectedIndex(0);
            }
        }else{
            cUsername.setSelectedItem("");
            tPassword.setText("");
        }
        this.repaint();
        this.cUsername.requestFocus();
    }

    // =============================================================================================
    //                                                                              SIGN-IN HANDLING
    // =============================================================================================
    private void bSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSignInActionPerformed
        // Grab user information from the form
        LogUtil.getLogger().log(Level.INFO, "[Sign In]");
        final String username = (String) cUsername.getSelectedItem();
        final String password = new String(tPassword.getPassword());
        final UserAccount account = SessionManager.getAccountManager().onSignInBegin(username, password);
        boolean remember = this.xRememberMe.isSelected();

        // Create an async task for signing in
        final GameSession session = SessionManager.createSession(account);
        signInTask = session.signInAsync(remember);

        // Get ready to handle the task completion
        signInTask.addPropertyChangeListener(
                new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())) {
                    if (evt.getNewValue().equals(StateValue.DONE)) {
                        onSignInDone(signInTask);
                    }
                }
            }
        });

        // Gray everything out and show a progress bar
        disableGUI();

        // Begin signing in asynchronously
        signInTask.execute();
    }//GEN-LAST:event_bSignInActionPerformed

    private void cUsernameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cUsernameItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED && xRememberMe.isSelected()) {
            String newName = (String) evt.getItem();
            UserAccount curAccount = SessionManager.getAccountManager().findAccount(newName);
            if (curAccount != null) {
                tPassword.setText(curAccount.Password);
            }
        }
    }//GEN-LAST:event_cUsernameItemStateChanged

    private void xRememberMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xRememberMeActionPerformed
        prefs.putBoolean("rememberMe", xRememberMe.isSelected());
        if(!xRememberMe.isSelected()){
            AccountManager am = SessionManager.getAccountManager();
            am.Clear();
            am.Store();
        }
    }//GEN-LAST:event_xRememberMeActionPerformed

    // Called when signInAsync finishes.
    // If we signed in, advance to the server list screen.
    // Otherwise, inform the user that something went wrong.
    private void onSignInDone(SwingWorker<SignInResult, String> signInTask) {
        LogUtil.getLogger().log(Level.FINE, "SignInScreen.onSignInDone");
        try {
            final SignInResult result = signInTask.get();
            if (result == SignInResult.SUCCESS) {
                if (this.xRememberMe.isSelected()) {
                    SessionManager.getSession().account.SignInDate = new Date();
                    SessionManager.getAccountManager().Store();
                }
                EntryPoint.ShowServerListScreen();
            } else {
                // TODO: make this less ugly
                LogUtil.showWarning(result.name(), "Sign in result.");
            }
        } catch (InterruptedException | ExecutionException ex) {
            LogUtil.getLogger().log(Level.SEVERE, "Error singing in", ex);
            LogUtil.showError(ex.toString(), "Error signing in");
        }
        enableGUI();
    }

    // Grays out the UI, and shows a progress bar
    private void disableGUI() {
        bSignIn.setEnabled(false);
        cUsername.setEditable(false);
        tPassword.setEditable(false);
        buttonToDisableOnSignIn.setEnabled(false);
        xRememberMe.setEnabled(false);
        progress.setVisible(true);
    }

    // Re-enabled the UI, and hides the progress bar
    private void enableGUI() {
        checkIfSignInAllowed();
        cUsername.setEditable(true);
        tPassword.setEditable(true);
        buttonToDisableOnSignIn.setEnabled(true);
        xRememberMe.setEnabled(true);
        progress.setVisible(false);
    }

    // =============================================================================================
    //                                                                           GUI EVENT LISTENERS
    // =============================================================================================
    private void hookUpListeners() {
        // hook up listeners for username/password field changes
        fieldChangeListener = new UsernameOrPasswordChangedListener();
        final JTextComponent usernameEditor = (JTextComponent) cUsername.getEditor().getEditorComponent();
        usernameEditor.getDocument().addDocumentListener(fieldChangeListener);
        tPassword.getDocument().addDocumentListener(fieldChangeListener);
        cUsername.addActionListener(fieldChangeListener);
        tPassword.addActionListener(fieldChangeListener);

        // allow hitting <Enter> in the password field
        tPassword.addKeyListener(new PasswordEnterListener());
    }

    // Allows pressing "Enter" while focused on the password textbox to sign in
    class PasswordEnterListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                if (bSignIn.isEnabled()) {
                    bSignIn.doClick();
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) { // do nothing
        }

        @Override
        public void keyReleased(KeyEvent e) { // do nothing
        }
    }

    // Allows enabling/disabling [Sign In] button dynamically,
    // depending on whether username/password fields are empty,
    // while user is still focused on those fields.
    class UsernameOrPasswordChangedListener implements DocumentListener, ActionListener {

        public int realPasswordLength,
                realUsernameLength;

        public UsernameOrPasswordChangedListener() {
            realPasswordLength = tPassword.getPassword().length;
            final String username = (String) cUsername.getSelectedItem();
            if (username == null) {
                realUsernameLength = 0;
            } else {
                realUsernameLength = username.length();
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            somethingEdited(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            somethingEdited(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            somethingEdited(e);
        }

        private void somethingEdited(DocumentEvent e) {
            final Document doc = e.getDocument();
            if (doc.equals(tPassword.getDocument())) {
                realPasswordLength = doc.getLength();
            } else {
                realUsernameLength = doc.getLength();
            }
            checkIfSignInAllowed();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            realPasswordLength = tPassword.getPassword().length;
            final String username = (String) cUsername.getSelectedItem();
            if (username == null) {
                realUsernameLength = 0;
            } else {
                realUsernameLength = username.length();
            }
            checkIfSignInAllowed();
        }
    }

    // Enable/disable [Sign In] depending on whether username/password are given.
    void checkIfSignInAllowed() {
        final boolean enableSignIn = (fieldChangeListener.realUsernameLength > 0)
                && (fieldChangeListener.realPasswordLength > 0);
        bSignIn.setEnabled(enableSignIn);
    }
    // =============================================================================================
    //                                                                          GUI COMPONENT FIELDS
    // =============================================================================================
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton bClassiCubeNet;
    private javax.swing.JToggleButton bMinecraftNet;
    private javax.swing.JButton bSignIn;
    private javax.swing.JComboBox<String> cUsername;
    private net.classicube.launcher.ImagePanel ipLogo;
    private javax.swing.JProgressBar progress;
    private javax.swing.Box.Filler progressFiller;
    private javax.swing.JPasswordField tPassword;
    private javax.swing.JCheckBox xRememberMe;
    // End of variables declaration//GEN-END:variables
    // =============================================================================================
    //                                                                            APPLICATION FIELDS
    // =============================================================================================
    private ImagePanel bgPanel;
    private JToggleButton buttonToDisableOnSignIn;
    private UsernameOrPasswordChangedListener fieldChangeListener;
    private GameSession.SignInTask signInTask;
}
