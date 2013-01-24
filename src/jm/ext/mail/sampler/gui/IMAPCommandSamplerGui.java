/**
 * 
 */
package jm.ext.mail.sampler.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jm.ext.mail.sampler.IMAPCommandSampler;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

/**
 * @author schizophrenia
 *
 */
public class IMAPCommandSamplerGui extends AbstractSamplerGui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5291619409498223675L;
	private JTextField	tfName;
	private JTextField	tfSessionName;
//	private JTextField	tfCharset;
	private JTextArea	taCommand;
	
	public IMAPCommandSamplerGui() {
		super();
		init();
		initFields();
	}
	
	public String getStaticLabel() {
		return "jm@ext IMAP Command Sampler";
	}
	
	@Override
	public TestElement createTestElement() {
		IMAPCommandSampler sampler = new IMAPCommandSampler();
		modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public String getLabelResource() {
		return this.getClass().getSimpleName();
	}

	public void configure(TestElement te) {
		super.configure(te);
		if(te instanceof IMAPCommandSampler) {
			IMAPCommandSampler cs =(IMAPCommandSampler)te;
			this.tfName.setText(cs.getName());
			this.tfSessionName.setText(cs.getSessionName());
	//		this.tfCharset.setText(cs.getCharset());
			this.taCommand.setText(cs.getCommand());
		}
	}

	@Override
	public void modifyTestElement(TestElement sampler) {
		if(sampler == null)	System.err.println("sampler is null");
		super.configureTestElement(sampler);
		if (sampler instanceof IMAPCommandSampler) {
			IMAPCommandSampler imapSampler = (IMAPCommandSampler) sampler;
			imapSampler.setLabel(tfName.getText());
			imapSampler.setName(tfName.getText());
			imapSampler.setSessionName(tfSessionName.getText());
		//	imapSampler.setCharset(tfCharset.getText());
			imapSampler.setCommand(taCommand.getText());

		}
	}
	private void initFields() {
		this.tfName.setText("jm@ext IMAP Command");
		this.tfSessionName.setText("default");
		//this.tfCharset.setText("UTF-8");
		this.taCommand.setText("NOOP");
	}
	private void init() {
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());

		JPanel mainPanel = new JPanel(new GridBagLayout());

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;

		GridBagConstraints editConstraints = new GridBagConstraints();
		editConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		editConstraints.weightx = 1.0;
		editConstraints.fill = GridBagConstraints.HORIZONTAL;

		editConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
		labelConstraints.insets = new java.awt.Insets(2, 0, 0, 0);

		addToPanel(mainPanel, labelConstraints, 0, 0, new JLabel("Name: ", JLabel.RIGHT));
		addToPanel(mainPanel, editConstraints, 1, 0, tfName = new JTextField(20));
		addToPanel(mainPanel, labelConstraints, 0, 1, new JLabel("Session: ", JLabel.RIGHT));
		addToPanel(mainPanel, editConstraints, 1, 1, tfSessionName = new JTextField(32));
		addToPanel(mainPanel, labelConstraints, 2, 1, new JLabel("Charset: ", JLabel.RIGHT));
		//addToPanel(mainPanel, editConstraints, 1, 2, tfCharset = new JTextField(32));
		addToPanel(mainPanel, labelConstraints, 0, 3, new JLabel("Command: ", JLabel.RIGHT));
		addToPanel(mainPanel, editConstraints, 1, 3, taCommand = new JTextArea());
		taCommand.setRows(4);
		taCommand.setLineWrap(true);
		taCommand.setWrapStyleWord(true);

		
		JPanel container = new JPanel(new BorderLayout());
		container.add(mainPanel, BorderLayout.NORTH);
		add(container, BorderLayout.CENTER);
	}
	private void addToPanel(JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
		constraints.gridx = col;
		constraints.gridy = row;
		panel.add(component, constraints);
	}


}
