package it.unibz.inf.ontop.protege.panels;

/*
 * #%L
 * ontop-protege4
 * %%
 * Copyright (C) 2009 - 2013 KRDB Research Centre. Free University of Bozen Bolzano.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.injection.QuestCoreSettings;
import it.unibz.inf.ontop.protege.core.DisposableProperties;

import java.awt.*;

import static it.unibz.inf.ontop.model.impl.DeprecatedConstants.*;

public class QuestConfigPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 602382682995021070L;
    private DisposableProperties preference;

    /**
     * The constructor.
     */
    public QuestConfigPanel(final DisposableProperties preference) {
        this.preference = preference;
        initComponents();
        setSelections(preference);
        this.setMaximumSize(new Dimension(1024,768));
        this.setMinimumSize(new Dimension(1024,768));
    }

    private void setSelections(final DisposableProperties preference) {

//        String value = (String) preference.getProperty(QuestPreferences.REFORMULATION_TECHNIQUE);
//        if (value.equals(UCQBASED)) {
//            cmbReformulationMethods.setSelectedIndex(0);
//        }
//        else if (value.equals(PERFECTREFORMULATION)) {
//            cmbReformulationMethods.setSelectedIndex(1);
//        }

        boolean bChecked = preference.getBoolean(QuestCoreSettings.REWRITE);
        chkRewrite.setSelected(bChecked);
        
        bChecked = preference.getBoolean(QuestCoreSettings.QUERY_ONTOLOGY_ANNOTATIONS);
        chkAnnotations.setSelected(bChecked);

        String value = preference.getProperty(ABOX_MODE);
        if (value == null || value.equals(VIRTUAL)) {
            virtualModeSelected();
        }
        else if (value.equals(CLASSIC)) {
            classicModeSelected();
        }

//        value = (String) preference.getProperty(ReformulationPlatformPreferences.DATA_LOCATION);
//        if (value.equals(PROVIDED)) {
//            radRemoteDatabase.setSelected(true);
//        }
//        else if (value.equals(INMEMORY)) {
            radInMemoryDatabase.setSelected(true);
//        }

        value = preference.getProperty(DBTYPE);
        if (value != null) {
            switch (value) {
                case DIRECT:
                    radDirect.setSelected(true);
                    break;
                case UNIVERSAL:
                    radUniversal.setSelected(true);
                    break;
                case SEMANTIC_INDEX:
                    radSemanticIndex.setSelected(true);
                    break;
            }
        }

        bChecked = preference.getBoolean(OBTAIN_FROM_ONTOLOGY);
        chkObtainFromOntology.setSelected(bChecked);

        bChecked = preference.getBoolean(OBTAIN_FROM_MAPPINGS);
        chkObtainFromMappings.setSelected(bChecked);

        bChecked = preference.getBoolean(QuestCoreSettings.SAME_AS);
        chkSameAs.setSelected(bChecked);
    }

    private void virtualModeSelected() {
        radVirtualObda.setSelected(true);
        radClassicObda.setSelected(false);

        lblDataStrategy.setEnabled(false);
        radDirect.setEnabled(false);
        radUniversal.setEnabled(false);
        radSemanticIndex.setEnabled(false);

        lblDataLocation.setEnabled(false);
        radRemoteDatabase.setEnabled(false);
        radInMemoryDatabase.setEnabled(false);

        lblDataSource.setEnabled(false);
        chkObtainFromOntology.setEnabled(false);
        chkObtainFromMappings.setEnabled(false);
    }

    private void classicModeSelected() {
        radVirtualObda.setSelected(false);
        radClassicObda.setSelected(true);

        lblDataStrategy.setEnabled(true);
        radDirect.setEnabled(true);
        radUniversal.setEnabled(false);  // not implemented yet!
        radSemanticIndex.setEnabled(true);
        radSemanticIndex.setSelected(true);  // by default

        lblDataLocation.setEnabled(true);
        radRemoteDatabase.setEnabled(false);  // not implemented yet!
        radInMemoryDatabase.setEnabled(true);
        radInMemoryDatabase.setSelected(true);  // by default

        lblDataSource.setEnabled(true);
        chkObtainFromOntology.setEnabled(true);
        chkObtainFromMappings.setEnabled(true);
        chkObtainFromOntology.setSelected(true);  // by default
        chkObtainFromMappings.setSelected(false);  // by default

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mappingMode = new javax.swing.ButtonGroup();
        mapper = new javax.swing.ButtonGroup();
        datalocationGroup = new javax.swing.ButtonGroup();
        AboxMode = new javax.swing.ButtonGroup();
        labelNote = new javax.swing.JLabel();
        pnlReformulationMethods = new javax.swing.JPanel();
        chkRewrite = new javax.swing.JCheckBox();
        chkAnnotations = new javax.swing.JCheckBox();
        chkSameAs = new javax.swing.JCheckBox();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        pnlABoxConfiguration = new javax.swing.JPanel();
        radVirtualObda = new javax.swing.JRadioButton();
        radClassicObda = new javax.swing.JRadioButton();
        lblDataStrategy = new javax.swing.JLabel();
        radDirect = new javax.swing.JRadioButton();
        radUniversal = new javax.swing.JRadioButton();
        radSemanticIndex = new javax.swing.JRadioButton();
        lblDataLocation = new javax.swing.JLabel();
        radRemoteDatabase = new javax.swing.JRadioButton();
        radInMemoryDatabase = new javax.swing.JRadioButton();
        lblDataSource = new javax.swing.JLabel();
        chkObtainFromOntology = new javax.swing.JCheckBox();
        chkObtainFromMappings = new javax.swing.JCheckBox();

        setMinimumSize(new java.awt.Dimension(620, 560));
        setPreferredSize(new java.awt.Dimension(620, 560));
        setLayout(new java.awt.GridBagLayout());

        labelNote.setText("<html><b>Note:</b> You will need to restart Ontop Reasoner for any changes to take effect.<p/>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; (i.e., select \"Reasoner-> None\" and then \"Reasoner -> Ontop\" in Protege's menu)</html>");
        labelNote.setAlignmentX(0.5F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 5, 5);
        add(labelNote, gridBagConstraints);

        pnlReformulationMethods.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray), "First Order reformulation"));
        pnlReformulationMethods.setMinimumSize(new java.awt.Dimension(620, 120));
        pnlReformulationMethods.setPreferredSize(new java.awt.Dimension(620, 120));
        pnlReformulationMethods.setLayout(new java.awt.GridBagLayout());

        chkRewrite.setText("Enable reasoning over anonymous individuals (tree-witness rewriting) ");
        chkRewrite.setToolTipText("Enable only if your application requires reasoning w.r.t. to existential constants in the queries");
        chkRewrite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRewrite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRewriteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 5);
        pnlReformulationMethods.add(chkRewrite, gridBagConstraints);

        chkAnnotations.setText("Enable querying annotations in the ontology");
        chkAnnotations.setToolTipText("Enable only if your application requires querying annotation properties defined in the ontology.");
        chkAnnotations.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAnnotations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAnnotationsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 5);
        pnlReformulationMethods.add(chkAnnotations, gridBagConstraints);

        chkSameAs.setText("Enable reasoning with owl:sameAs from mappings");
        chkSameAs.setToolTipText("Enable only if your application requires reasoning with owl:sameAs from mappings");
        chkSameAs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkSameAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSameAsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 5);
        pnlReformulationMethods.add(chkSameAs, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        pnlReformulationMethods.add(filler2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 6, 5, 6);
        add(pnlReformulationMethods, gridBagConstraints);

        pnlABoxConfiguration.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray), "Data Configuration"));
        pnlABoxConfiguration.setMinimumSize(new java.awt.Dimension(620, 350));
        pnlABoxConfiguration.setPreferredSize(new java.awt.Dimension(620, 480));
        pnlABoxConfiguration.setLayout(new java.awt.GridBagLayout());

        AboxMode.add(radVirtualObda);
        radVirtualObda.setText("Virtual ABox (virtual RDF graph)");
        radVirtualObda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        radVirtualObda.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        radVirtualObda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radVirtualObdaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 0);
        pnlABoxConfiguration.add(radVirtualObda, gridBagConstraints);

        AboxMode.add(radClassicObda);
        radClassicObda.setText("Classic ABox (Deprecated)");
        radClassicObda.setActionCommand("Classic ABox (= uses pre-defined ABox in the ontology)");
        radClassicObda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radClassicObdaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 0);
        pnlABoxConfiguration.add(radClassicObda, gridBagConstraints);
        radClassicObda.getAccessibleContext().setAccessibleName("Classic ABox. Uses pre-defined ABox in the ontology.");

        lblDataStrategy.setText("(1) Strategy for the database schema organization:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 27, 3, 0);
        pnlABoxConfiguration.add(lblDataStrategy, gridBagConstraints);

        mapper.add(radDirect);
        radDirect.setText("Direct");
        radDirect.setPreferredSize(new java.awt.Dimension(53, 20));
        radDirect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radDirectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(radDirect, gridBagConstraints);

        mapper.add(radUniversal);
        radUniversal.setText("Universal");
        radUniversal.setPreferredSize(new java.awt.Dimension(69, 20));
        radUniversal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radUniversalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(radUniversal, gridBagConstraints);

        mapper.add(radSemanticIndex);
        radSemanticIndex.setText("Semantic Index (recommended)");
        radSemanticIndex.setToolTipText("SPOG schema optimised for very large hierarchies.");
        radSemanticIndex.setPreferredSize(new java.awt.Dimension(177, 20));
        radSemanticIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radSemanticIndexActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(radSemanticIndex, gridBagConstraints);

        lblDataLocation.setText("(2) Database location:");
        lblDataLocation.setPreferredSize(new java.awt.Dimension(66, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 27, 3, 0);
        pnlABoxConfiguration.add(lblDataLocation, gridBagConstraints);

        datalocationGroup.add(radRemoteDatabase);
        radRemoteDatabase.setText("Remote server");
        radRemoteDatabase.setToolTipText("ABox assertions/triples are stored in a remote relational database. Quest creates the schema and manages the data, however, the database is under control of the user.");
        radRemoteDatabase.setPreferredSize(new java.awt.Dimension(97, 20));
        radRemoteDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radRemoteDatabaseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(radRemoteDatabase, gridBagConstraints);
        radRemoteDatabase.getAccessibleContext().setAccessibleName("Remote database (the user provides the JDBC connection)");

        datalocationGroup.add(radInMemoryDatabase);
        radInMemoryDatabase.setText("In-Memory (H2-DB)");
        radInMemoryDatabase.setToolTipText("ABox assertions/triples are stored in a in-memory relational database created and managed by Quest.");
        radInMemoryDatabase.setPreferredSize(new java.awt.Dimension(97, 20));
        radInMemoryDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radInMemoryDatabaseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(radInMemoryDatabase, gridBagConstraints);
        radInMemoryDatabase.getAccessibleContext().setAccessibleName("In-memory database (i.e., the system will obtain the ABox from locations below)");

        lblDataSource.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDataSource.setText("(3) Data source(s):");
        lblDataSource.setPreferredSize(new java.awt.Dimension(98, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 27, 3, 0);
        pnlABoxConfiguration.add(lblDataSource, gridBagConstraints);

        chkObtainFromOntology.setSelected(true);
        chkObtainFromOntology.setText("From the active ontology's ABox.");
        chkObtainFromOntology.setPreferredSize(new java.awt.Dimension(150, 23));
        chkObtainFromOntology.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkObtainFromOntologyActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(chkObtainFromOntology, gridBagConstraints);

        chkObtainFromMappings.setText("From the current mappings in the OBDA model");
        chkObtainFromMappings.setMaximumSize(new java.awt.Dimension(193, 23));
        chkObtainFromMappings.setMinimumSize(new java.awt.Dimension(193, 23));
        chkObtainFromMappings.setPreferredSize(new java.awt.Dimension(168, 23));
        chkObtainFromMappings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkObtainFromMappingsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 0);
        pnlABoxConfiguration.add(chkObtainFromMappings, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 6, 15, 6);
        add(pnlABoxConfiguration, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void chkObtainFromMappingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkObtainFromMappingsActionPerformed
        if (chkObtainFromMappings.isSelected()) {
            preference.put(OBTAIN_FROM_MAPPINGS, Boolean.TRUE.toString());
        } else {
            preference.put(OBTAIN_FROM_MAPPINGS, Boolean.FALSE.toString());
        }
    }//GEN-LAST:event_chkObtainFromMappingsActionPerformed

    private void chkObtainFromOntologyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkObtainFromOntologyActionPerformed
        if (chkObtainFromOntology.isSelected()) {
            preference.put(OBTAIN_FROM_ONTOLOGY, Boolean.TRUE.toString());
        } else {
            preference.put(OBTAIN_FROM_ONTOLOGY, Boolean.FALSE.toString());
        }
    }//GEN-LAST:event_chkObtainFromOntologyActionPerformed

    private void radInMemoryDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radInMemoryDatabaseActionPerformed
//    preference.newProperties(ReformulationPlatformPreferences.DATA_LOCATION, INMEMORY);
    }//GEN-LAST:event_radInMemoryDatabaseActionPerformed

    private void radRemoteDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radRemoteDatabaseActionPerformed
//    preference.newProperties(ReformulationPlatformPreferences.DATA_LOCATION, PROVIDED);
    }//GEN-LAST:event_radRemoteDatabaseActionPerformed

    private void radSemanticIndexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radSemanticIndexActionPerformed
        preference.put(DBTYPE, SEMANTIC_INDEX);
    }//GEN-LAST:event_radSemanticIndexActionPerformed

    private void radUniversalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radUniversalActionPerformed
        preference.put(DBTYPE, UNIVERSAL);
    }//GEN-LAST:event_radUniversalActionPerformed

    private void radDirectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radDirectActionPerformed
        preference.put(DBTYPE, DIRECT);
    }//GEN-LAST:event_radDirectActionPerformed

    private void radClassicObdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radClassicObdaActionPerformed
        classicModeSelected();
        preference.put(ABOX_MODE, CLASSIC);
        preference.put(DBTYPE, SEMANTIC_INDEX);
//    preference.newProperties(ReformulationPlatformPreferences.DATA_LOCATION, INMEMORY);
    }//GEN-LAST:event_radClassicObdaActionPerformed

    private void radVirtualObdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radVirtualObdaActionPerformed
        virtualModeSelected();
        preference.put(ABOX_MODE, VIRTUAL);
    }//GEN-LAST:event_radVirtualObdaActionPerformed

    private void chkRewriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRewriteActionPerformed
        preference.put(QuestCoreSettings.REWRITE, String.valueOf(chkRewrite.isSelected()));

    }//GEN-LAST:event_chkRewriteActionPerformed

    private void chkAnnotationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAnnotationsActionPerformed
        preference.put(QuestCoreSettings.QUERY_ONTOLOGY_ANNOTATIONS, String.valueOf(chkAnnotations.isSelected()));

    }//GEN-LAST:event_chkAnnotationsActionPerformed

    private void chkSameAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSameAsActionPerformed
        preference.put(QuestCoreSettings.SAME_AS, String.valueOf(chkSameAs.isSelected()));

    }//GEN-LAST:event_chkSameAsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup AboxMode;
    private javax.swing.JCheckBox chkAnnotations;
    private javax.swing.JCheckBox chkObtainFromMappings;
    private javax.swing.JCheckBox chkObtainFromOntology;
    private javax.swing.JCheckBox chkRewrite;
    private javax.swing.JCheckBox chkSameAs;
    private javax.swing.ButtonGroup datalocationGroup;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel labelNote;
    private javax.swing.JLabel lblDataLocation;
    private javax.swing.JLabel lblDataSource;
    private javax.swing.JLabel lblDataStrategy;
    private javax.swing.ButtonGroup mapper;
    private javax.swing.ButtonGroup mappingMode;
    private javax.swing.JPanel pnlABoxConfiguration;
    private javax.swing.JPanel pnlReformulationMethods;
    private javax.swing.JRadioButton radClassicObda;
    private javax.swing.JRadioButton radDirect;
    private javax.swing.JRadioButton radInMemoryDatabase;
    private javax.swing.JRadioButton radRemoteDatabase;
    private javax.swing.JRadioButton radSemanticIndex;
    private javax.swing.JRadioButton radUniversal;
    private javax.swing.JRadioButton radVirtualObda;
    // End of variables declaration//GEN-END:variables
}
