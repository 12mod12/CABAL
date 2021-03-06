/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GameMenu.java
 *
 * Created on Oct 14, 2012, 2:29:24 PM
 */

package com.mod12.cabal.common.ui.graphics;

import java.util.List;

import javax.swing.DefaultListModel;

import com.mod12.cabal.common.Cabal;
import com.mod12.cabal.common.dto.FactionDTO;
import com.mod12.cabal.common.util.Formatter;
import com.mod12.cabal.common.util.Graphics;

/**
 * @author reedh
 */
public class FactionMenu extends javax.swing.JFrame {

    private DefaultListModel lineModel;
    private Cabal cabal;
    private FactionDTO faction;
    private GameMenu gameMenu;

    FactionMenu(Cabal cabal, FactionDTO faction, GameMenu menu) {
        this.cabal = cabal;
        this.faction = faction;
        this.gameMenu = menu;
        lineModel = new DefaultListModel();
        initComponents();
        updateScreen();
        populateList();
        Graphics.centreWindow(this);
        enableScreen(gameMenu.myTurn());
    }

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelFactionName = new javax.swing.JLabel();
        labelFactionMonies = new javax.swing.JLabel();
        barPrestigeCulture = new javax.swing.JProgressBar();
        labelCulture = new javax.swing.JLabel();
        labelInfamy = new javax.swing.JLabel();
        barPrestigeInfamy = new javax.swing.JProgressBar();
        barTotalPresence = new javax.swing.JProgressBar();
        labelTotalPresence = new javax.swing.JLabel();
        labelFactionHQ = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listAgents = new javax.swing.JList();
        labelAgents = new javax.swing.JLabel();
        labelAgentInfo = new javax.swing.JLabel();
        buttonEquip = new javax.swing.JButton();
        buttonBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaAgentInfo = new javax.swing.JTextArea();
        labelPresenceValue = new javax.swing.JLabel();
        labelCultureValue = new javax.swing.JLabel();
        labelInfamyValue = new javax.swing.JLabel();
        buttonViewActiveMissions = new javax.swing.JButton();
        labelPlayerHandle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelFactionName.setText("factionName");

        labelFactionMonies.setText("factionMonies");

        labelCulture.setText("Culture");

        labelInfamy.setText("Infamy");

        labelTotalPresence.setText("Total Presence");

        labelFactionHQ.setText("factionHQ");

        listAgents.setModel(lineModel
        );
        listAgents.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listAgentsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listAgents);

        labelAgents.setText("Agents");

        labelAgentInfo.setText("Agent Info");

        buttonEquip.setText("Agent Equipment");
        buttonEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEquipActionPerformed(evt);
            }
        });

        buttonBack.setText("Back");
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackActionPerformed(evt);
            }
        });

        textAreaAgentInfo.setColumns(20);
        textAreaAgentInfo.setRows(5);
        jScrollPane2.setViewportView(textAreaAgentInfo);

        labelPresenceValue.setText("##");

        labelCultureValue.setText("##");

        labelInfamyValue.setText("##");

        buttonViewActiveMissions.setText("View Active Missions");
        buttonViewActiveMissions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonViewActiveMissionsActionPerformed(evt);
            }
        });

        labelPlayerHandle.setText("PlayerHandle");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(labelAgents)
                .addGap(292, 292, 292)
                .addComponent(labelAgentInfo))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(buttonBack))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(buttonViewActiveMissions))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(labelTotalPresence)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelPresenceValue))
                                    .addComponent(barTotalPresence, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(labelCulture)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelCultureValue))
                                    .addComponent(barPrestigeCulture, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(labelInfamy)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelInfamyValue))
                                    .addComponent(barPrestigeInfamy, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelPlayerHandle, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelFactionName, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelFactionMonies, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelFactionHQ)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonEquip)
                        .addGap(106, 106, 106)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAgents)
                    .addComponent(labelAgentInfo))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonBack)
                                .addGap(14, 14, 14)
                                .addComponent(labelPlayerHandle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFactionName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFactionMonies)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFactionHQ)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelTotalPresence)
                                    .addComponent(labelPresenceValue))
                                .addGap(6, 6, 6)
                                .addComponent(barTotalPresence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelCulture)
                                    .addComponent(labelCultureValue))
                                .addGap(6, 6, 6)
                                .addComponent(barPrestigeCulture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelInfamy)
                                    .addComponent(labelInfamyValue))
                                .addGap(6, 6, 6)
                                .addComponent(barPrestigeInfamy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonViewActiveMissions))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonEquip)
                        .addGap(34, 34, 34))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEquipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonEquipActionPerformed

    private void buttonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackActionPerformed
        gameMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_buttonBackActionPerformed

    private void listAgentsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listAgentsValueChanged
        displayAgentInfo();
    }//GEN-LAST:event_listAgentsValueChanged

    private void buttonViewActiveMissionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonViewActiveMissionsActionPerformed
        viewActiveMissions();
    }//GEN-LAST:event_buttonViewActiveMissionsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barPrestigeCulture;
    private javax.swing.JProgressBar barPrestigeInfamy;
    private javax.swing.JProgressBar barTotalPresence;
    private javax.swing.JButton buttonBack;
    private javax.swing.JButton buttonEquip;
    private javax.swing.JButton buttonViewActiveMissions;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAgentInfo;
    private javax.swing.JLabel labelAgents;
    private javax.swing.JLabel labelCulture;
    private javax.swing.JLabel labelCultureValue;
    private javax.swing.JLabel labelFactionHQ;
    private javax.swing.JLabel labelFactionMonies;
    private javax.swing.JLabel labelFactionName;
    private javax.swing.JLabel labelInfamy;
    private javax.swing.JLabel labelInfamyValue;
    private javax.swing.JLabel labelPlayerHandle;
    private javax.swing.JLabel labelPresenceValue;
    private javax.swing.JLabel labelTotalPresence;
    private javax.swing.JList listAgents;
    private javax.swing.JTextArea textAreaAgentInfo;
    // End of variables declaration//GEN-END:variables

    private void updateScreen() {
    	labelPlayerHandle.setText(faction.getPlayerHandle());
		labelFactionName.setText(faction.getName());
        labelFactionMonies.setText(faction.getWealth() + "");
        labelFactionHQ.setText(cabal.getMyFaction().getHeadquarters());
        
        double presence = faction.getPresence();
        int infamy = faction.getCulture();
        int culture = faction.getCulture();
        
        labelPresenceValue.setText(Formatter.format(2, presence) + " %");
        barTotalPresence.setValue((int) presence);
        
        barPrestigeInfamy.setValue(infamy);
        labelInfamyValue.setText(infamy + "");
        
        barPrestigeCulture.setValue(culture);
        labelCultureValue.setText(culture +"");
	}
    
    private void displayAgentInfo() {
        String agent = (String) listAgents.getSelectedValue();
        String output = cabal.getAgentInfo(faction.getName(), agent);
        
        textAreaAgentInfo.setText(output);
    }
    
    private void viewActiveMissions() {
		ActiveMissionsMenu menu = new ActiveMissionsMenu(cabal, this);
		menu.setVisible(true);
    	this.setVisible(false);
	}

	private void populateList() {
        lineModel.clear();
        List<String> agents = faction.getAgents();
        for (String agent : agents) {            
                lineModel.add(lineModel.size(), agent);
            }
    }
	
	private void enableScreen(boolean myTurn) {
		// TODO always disable as items aren't implemented fully
		buttonEquip.setEnabled(false);
	}

}
