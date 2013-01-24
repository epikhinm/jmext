/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package jm.ext.mail.sampler.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import jm.ext.mail.sampler.ExtSMTPSampler;

import org.apache.jmeter.protocol.smtp.sampler.gui.SecuritySettingsPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Class to build superstructure-gui for SMTP-panel, sets/gets value for a JMeter's testElement-object (i.e. also for save/load-purposes).
 * This class extends AbstractSamplerGui, therefor most implemented methods are defined by JMeter's structure.
 */
public class ExtSMTPSamplerGui extends AbstractSamplerGui {

    /**
     *
     */
    private static final Logger log = LoggingManager.getLoggerForClass();
    private static final long serialVersionUID = 123043L;
    private ExtSMTPPanel smtpPanel;

    /**
     * Creates new SmtpSamplerGui, standard constructer. Calls init();
     */
    public ExtSMTPSamplerGui() {
        super();
        init();
        log.info("ExtSMTPGUI constructor");
    }

    /**
     * Method to be implemented by interface, overwritten by getStaticLabel(). Method has to be implemented by interface
     * @return Null-String
     * @see org.apache.jmeter.gui.JMeterGUIComponent#getLabelResource()
     */
    public String getStaticLabel() {
        return "jm@ext Ext SMTP Sampler";
    }

    /**
     * Copy the data from the test element to the GUI, method has to be implemented by interface
     * @param element Test-element to be used as data-input
     * @see org.apache.jmeter.gui.AbstractJMeterGuiComponent#configure(org.apache.jmeter.testelement.TestElement)
     */
    @Override
    public void configure(TestElement element) {
        if (smtpPanel == null){
            smtpPanel = new ExtSMTPPanel();
        }
        
        //Extensions
        smtpPanel.setConnectionTimeout(element.getPropertyAsString(ExtSMTPSampler.CONNECTION_TIMEOUT));
        smtpPanel.setSocketTimeout(element.getPropertyAsString(ExtSMTPSampler.SOCKET_TIMEOUT));
        smtpPanel.setQuitWait(element.getPropertyAsBoolean(ExtSMTPSampler.QUIT_WAIT));
        smtpPanel.setReuseTransport(element.getPropertyAsBoolean(ExtSMTPSampler.REUSE_TRANSPORT));
        smtpPanel.setReconnect(element.getPropertyAsBoolean(ExtSMTPSampler.RECONNECT));
        
        smtpPanel.setServer(element.getPropertyAsString(ExtSMTPSampler.SERVER));
        smtpPanel.setPort(element.getPropertyAsString(ExtSMTPSampler.SERVER_PORT));
        smtpPanel.setMailFrom(element.getPropertyAsString(ExtSMTPSampler.MAIL_FROM));
        smtpPanel.setMailReplyTo(element.getPropertyAsString(ExtSMTPSampler.MAIL_REPLYTO));
        smtpPanel.setReceiverTo(element.getPropertyAsString(ExtSMTPSampler.RECEIVER_TO));
        smtpPanel.setReceiverCC(element.getPropertyAsString(ExtSMTPSampler.RECEIVER_CC));
        smtpPanel.setReceiverBCC(element.getPropertyAsString(ExtSMTPSampler.RECEIVER_BCC));

        smtpPanel.setBody(element.getPropertyAsString(ExtSMTPSampler.MESSAGE));
        smtpPanel.setPlainBody(element.getPropertyAsBoolean(ExtSMTPSampler.PLAIN_BODY));
        smtpPanel.setSubject(element.getPropertyAsString(ExtSMTPSampler.SUBJECT));
        smtpPanel.setSuppressSubject(element.getPropertyAsBoolean(ExtSMTPSampler.SUPPRESS_SUBJECT));
        smtpPanel.setIncludeTimestamp(element.getPropertyAsBoolean(ExtSMTPSampler.INCLUDE_TIMESTAMP));
        JMeterProperty headers = element.getProperty(ExtSMTPSampler.HEADER_FIELDS);
        if (headers instanceof CollectionProperty) { // Might be NullProperty
            smtpPanel.setHeaderFields((CollectionProperty)headers);            
        } else {
            smtpPanel.setHeaderFields(new CollectionProperty());
        }
        smtpPanel.setAttachments(element.getPropertyAsString(ExtSMTPSampler.ATTACH_FILE));

        smtpPanel.setUseEmlMessage(element.getPropertyAsBoolean(ExtSMTPSampler.USE_EML));
        smtpPanel.setEmlMessage(element.getPropertyAsString(ExtSMTPSampler.EML_MESSAGE_TO_SEND));

        SecuritySettingsPanel secPanel = smtpPanel.getSecuritySettingsPanel();
        secPanel.configure(element);

        smtpPanel.setUseAuth(element.getPropertyAsBoolean(ExtSMTPSampler.USE_AUTH));
        smtpPanel.setUsername(element.getPropertyAsString(ExtSMTPSampler.USERNAME));
        smtpPanel.setPassword(element.getPropertyAsString(ExtSMTPSampler.PASSWORD));

        smtpPanel.setMessageSizeStatistic(element.getPropertyAsBoolean(ExtSMTPSampler.MESSAGE_SIZE_STATS));
        smtpPanel.setEnableDebug(element.getPropertyAsBoolean(ExtSMTPSampler.ENABLE_DEBUG));

        super.configure(element);
        
        log.info("ExtSMTPGUI configure");
    }

    /**
     * Creates a new TestElement and set up its data
     * @return Test-element for JMeter
     * @see org.apache.jmeter.gui.JMeterGUIComponent#createTestElement()
     */
    @Override
    public TestElement createTestElement() {
        ExtSMTPSampler sampler = new ExtSMTPSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    /**
     * Modifies a given TestElement to mirror the data in the gui components
     * @param te TestElement for JMeter
     * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(org.apache.jmeter.testelement.TestElement)
     */
    @Override
    public void modifyTestElement(TestElement te) {
        te.clear();
        super.configureTestElement(te);
        
        
        te.setProperty(ExtSMTPSampler.CONNECTION_TIMEOUT, smtpPanel.getConnectionTimeout());
        te.setProperty(ExtSMTPSampler.SOCKET_TIMEOUT, smtpPanel.getSocketTimeout());
        te.setProperty(ExtSMTPSampler.QUIT_WAIT, smtpPanel.getQuitWait());
        te.setProperty(ExtSMTPSampler.REUSE_TRANSPORT, smtpPanel.getReuseTransport());
        te.setProperty(ExtSMTPSampler.RECONNECT, smtpPanel.getReconnect());
        
        te.setProperty(ExtSMTPSampler.SERVER, smtpPanel.getServer());
        te.setProperty(ExtSMTPSampler.SERVER_PORT, smtpPanel.getPort());
        te.setProperty(ExtSMTPSampler.MAIL_FROM, smtpPanel.getMailFrom());
        te.setProperty(ExtSMTPSampler.MAIL_REPLYTO, smtpPanel.getMailReplyTo());
        te.setProperty(ExtSMTPSampler.RECEIVER_TO, smtpPanel.getReceiverTo());
        te.setProperty(ExtSMTPSampler.RECEIVER_CC, smtpPanel.getReceiverCC());
        te.setProperty(ExtSMTPSampler.RECEIVER_BCC, smtpPanel.getReceiverBCC());
        te.setProperty(ExtSMTPSampler.SUBJECT, smtpPanel.getSubject());
        te.setProperty(ExtSMTPSampler.SUPPRESS_SUBJECT, Boolean.toString(smtpPanel.isSuppressSubject()));
        te.setProperty(ExtSMTPSampler.INCLUDE_TIMESTAMP, Boolean.toString(smtpPanel.isIncludeTimestamp()));
        te.setProperty(ExtSMTPSampler.MESSAGE, smtpPanel.getBody());
        te.setProperty(ExtSMTPSampler.PLAIN_BODY, Boolean.toString(smtpPanel.isPlainBody()));
        te.setProperty(ExtSMTPSampler.ATTACH_FILE, smtpPanel.getAttachments());
        
        SecuritySettingsPanel secPanel = smtpPanel.getSecuritySettingsPanel();
        secPanel.modifyTestElement(te);

        te.setProperty(ExtSMTPSampler.USE_EML, smtpPanel.isUseEmlMessage());
        te.setProperty(ExtSMTPSampler.EML_MESSAGE_TO_SEND, smtpPanel.getEmlMessage());

        te.setProperty(ExtSMTPSampler.USE_AUTH, Boolean.toString(smtpPanel.isUseAuth()));
        te.setProperty(ExtSMTPSampler.PASSWORD, smtpPanel.getPassword());
        te.setProperty(ExtSMTPSampler.USERNAME, smtpPanel.getUsername());

        te.setProperty(ExtSMTPSampler.MESSAGE_SIZE_STATS, Boolean.toString(smtpPanel.isMessageSizeStatistics()));
        te.setProperty(ExtSMTPSampler.ENABLE_DEBUG, Boolean.toString(smtpPanel.isEnableDebug()));

        te.setProperty(smtpPanel.getHeaderFields());
        
        log.info("ExtSMTPGUI modify");
    }

    /**
     * Helper method to set up the GUI screen
     */
    private void init() {
        // Standard setup
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title
        add(makeDataPanel(), BorderLayout.CENTER);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGui() {
        super.clearGui();
        if (smtpPanel != null) {
            smtpPanel.clear();
        }
    }
    /**
     * Creates a sampler-gui-object, singleton-method
     * @return Panel for entering the data
     */
    private Component makeDataPanel() {
        if (smtpPanel == null)
            smtpPanel = new ExtSMTPPanel();
        return smtpPanel;
    }

    @Override
    public String getLabelResource() {
        return this.getClass().getSimpleName();
    }
}