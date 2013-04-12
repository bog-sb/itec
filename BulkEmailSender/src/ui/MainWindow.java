package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.xml.sax.SAXParseException;

import controller.MainController;
import domain.Session;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements Observer {

	private JPanel contentPane;
	private JTextField txtSrcaddr;
	private JTextField txtDestaddr;
	private JTextField txtSubject;
	private JTextField txtServer;
	private JTextField txtUser;
	private JLabel lblTitle = new JLabel("<unsaved>");

	private AboutBox popup = new AboutBox();

	// For the anonymous inner classes:
	private final MainWindow me = this;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JFileChooser chooseYourDestiny = new JFileChooser(".");
	private JFileChooser attachFile = new JFileChooser(".");
	private JPasswordField txtPasswd;
	private MainController ctrl;
	private JList<String> listAttachments;
	private JSpinner txtNumber;
	private JSpinner txtDelay;
	private JCheckBox chckbxCounter = new JCheckBox("Add counter to subject");
	private JCheckBox chckbxRandom = new JCheckBox("Add random subject");
	private JSpinner txtPort = new JSpinner();
	private JTextArea txtrMessage = new JTextArea();
	private Boolean isSaved = false;
	private JMenuItem mntmSaveAs = new JMenuItem("Save as...");

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainController ctrl = new MainController();
					MainWindow frame = new MainWindow(ctrl);

					ctrl.newSession();
					frame.updateFields();

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param ctrl
	 */
	public MainWindow(final MainController ctl) {

		this.ctrl = ctl;

		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						MainWindow.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/HardDrive.gif")));
		setResizable(false);
		setTitle("Bulk Email Sender");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 697);
		popup.setVisible(false);

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML files", "xml");
		chooseYourDestiny.setFileFilter(filter);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.newSession();
				chooseYourDestiny = new JFileChooser(".");
				lblTitle.setText("<unsaved>");
				updateFields();

			}
		});
		mntmNew.setIcon(new ImageIcon(MainWindow.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")));
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooseYourDestiny.showOpenDialog(me);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						ctrl.openSession(chooseYourDestiny.getSelectedFile()
								.getPath());
						lblTitle.setText(chooseYourDestiny.getSelectedFile()
								.getName());
						isSaved = true;
					} catch (SAXParseException err) {
						JOptionPane.showMessageDialog(null,
								"Invalid XML structure.", "Error",
								JOptionPane.WARNING_MESSAGE);

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Error", JOptionPane.WARNING_MESSAGE);
					}
				}
				me.updateFields();
			}
		});
		mntmOpen.setIcon(new ImageIcon(
				MainWindow.class
						.getResource("/com/sun/java/swing/plaf/windows/icons/TreeOpen.gif")));
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");

		final ActionListener saveAsAct = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = chooseYourDestiny.showSaveDialog(me);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						updateSession();
						ctrl.saveSession(chooseYourDestiny.getSelectedFile()
								.getPath());
						lblTitle.setText(chooseYourDestiny.getSelectedFile()
								.getName());
						isSaved = true;
					} catch (Exception err) {
						err.printStackTrace();
					}
				}
			}
		};

		ActionListener saveAct = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (chooseYourDestiny.getSelectedFile() != null)
					{
					updateSession();
					ctrl.saveSession(chooseYourDestiny.getSelectedFile()
							.getPath());
					lblTitle.setText(chooseYourDestiny.getSelectedFile()
							.getName());
					isSaved = true;
					}
					else
					{
						saveAsAct.actionPerformed(e);
					}
				}
				catch (TransformerException e1) {
					JOptionPane.showMessageDialog(null, "XML exception",
							"Error", JOptionPane.WARNING_MESSAGE);
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "XML exception",
							"Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		};
		mntmSave.addActionListener(saveAct);

		mntmSave.setIcon(new ImageIcon(
				MainWindow.class
						.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mnFile.add(mntmSave);

		mntmSaveAs.addActionListener(saveAsAct);
		mnFile.add(mntmSaveAs);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// me.
			}
		});
		mntmQuit.setIcon(new ImageIcon(MainWindow.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnFile.add(mntmQuit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout
				.setIcon(new ImageIcon(
						MainWindow.class
								.getResource("/javax/swing/plaf/metal/icons/ocean/collapsed.gif")));
		final AboutBox pop = this.popup;
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Show the About popup
				pop.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 11, 531, 328);
		contentPane.add(panel);
		panel.setLayout(null);
		lblTitle.setBounds(10, 0, 380, 14);
		panel.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 25, 249, 116);
		panel.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(0, 2, 46, 14);
		panel_4.add(lblFrom);

		txtSrcaddr = new JTextField();
		txtSrcaddr.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		txtSrcaddr.setBounds(0, 18, 249, 20);
		panel_4.add(txtSrcaddr);
		txtSrcaddr.setColumns(10);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(0, 40, 46, 14);
		panel_4.add(lblTo);

		txtDestaddr = new JTextField();
		txtDestaddr.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
			}
		});
		txtDestaddr.setBounds(0, 56, 249, 20);
		panel_4.add(txtDestaddr);
		txtDestaddr.setColumns(10);

		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(0, 78, 46, 14);
		panel_4.add(lblSubject);

		txtSubject = new JTextField();
		txtSubject.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		txtSubject.setBounds(0, 94, 249, 20);
		panel_4.add(txtSubject);
		txtSubject.setColumns(10);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(269, 22, 97, 20);
		panel.add(lblMessage);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 165, 249, 114);
		panel.add(scrollPane_1);

		listAttachments = new JList<String>();
		scrollPane_1.setViewportView(listAttachments);
		listAttachments.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));

		JButton btnAttach = new JButton("Attach");
		btnAttach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = attachFile.showOpenDialog(me);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						me.updateFields();
						Vector<String>vec = ctrl.getSession().getAttachments();
						vec.add(attachFile.getSelectedFile().getPath());
						ctrl.getSession().setAttachments(vec);
						listAttachments.setListData(ctrl.getSession().getAttachments());
						
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Error", JOptionPane.WARNING_MESSAGE);
						ex.printStackTrace();
					}
				}
				
			}
		});
		btnAttach.setBounds(10, 292, 82, 23);
		panel.add(btnAttach);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dead = listAttachments.getSelectedIndex();
				Vector<String>vec = ctrl.getSession().getAttachments();
				vec.remove(dead);
				ctrl.getSession().setAttachments(vec);
				listAttachments.setListData(ctrl.getSession().getAttachments());
			}
		});
		btnRemove.setBounds(104, 292, 82, 23);
		panel.add(btnRemove);

		JLabel lblAttachments = new JLabel("Attachments:");
		lblAttachments.setBounds(10, 146, 249, 14);
		panel.add(lblAttachments);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(271, 44, 248, 235);
		panel.add(scrollPane);

		scrollPane.setViewportView(txtrMessage);

		Panel panel_1 = new Panel();
		panel_1.setBounds(0, 345, 531, 257);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblOptions = new JLabel("Options");
		lblOptions.setBounds(10, 6, 62, 14);
		panel_1.add(lblOptions);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(10, 28, 254, 220);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JRadioButton rdbtnSmtp = new JRadioButton("SMTP");
		rdbtnSmtp.setSelected(true);
		buttonGroup.add(rdbtnSmtp);
		rdbtnSmtp.setBounds(6, 29, 109, 23);
		panel_2.add(rdbtnSmtp);

		JRadioButton rdbtnExchangeServerMail = new JRadioButton(
				"Exchange Server Mail Protocol");
		buttonGroup.add(rdbtnExchangeServerMail);
		rdbtnExchangeServerMail.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// TODO: ESMP
				} else {
					// TODO: SMTP
				}
			}
		});

		rdbtnExchangeServerMail.setBounds(6, 55, 220, 23);
		panel_2.add(rdbtnExchangeServerMail);

		JPanel panelESMP = new JPanel();
		panelESMP.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		panelESMP.setBounds(6, 85, 238, 124);
		panel_2.add(panelESMP);
		panelESMP.setLayout(null);

		txtServer = new JTextField();
		txtServer.setBounds(10, 24, 160, 20);
		panelESMP.add(txtServer);
		txtServer.setColumns(10);

		JLabel lblServer_1 = new JLabel("Server");
		lblServer_1.setBounds(10, 11, 46, 14);
		panelESMP.add(lblServer_1);

		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(180, 11, 46, 14);
		panelESMP.add(lblPort);

		txtUser = new JTextField();
		txtUser.setBounds(78, 55, 148, 20);
		panelESMP.add(txtUser);
		txtUser.setColumns(10);

		JLabel lblUser = new JLabel("User");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(10, 55, 58, 14);
		panelESMP.add(lblUser);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 80, 58, 14);
		panelESMP.add(lblPassword);

		txtPasswd = new JPasswordField();
		txtPasswd.setBounds(78, 78, 148, 20);
		panelESMP.add(txtPasswd);
		txtPort.setModel(new SpinnerNumberModel(0, 0, 65535, 1));

		txtPort.setBounds(182, 25, 44, 20);
		panelESMP.add(txtPort);

		JLabel lblServer = new JLabel("Protocol");
		lblServer.setBounds(6, 8, 194, 14);
		panel_2.add(lblServer);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(267, 28, 254, 220);
		panel_1.add(panel_3);

		JLabel lblMultiplicity = new JLabel("Multiplicity");
		lblMultiplicity.setBounds(10, 11, 234, 14);
		panel_3.add(lblMultiplicity);

		JLabel lblNumberOfEmails = new JLabel("Number of emails to send");
		lblNumberOfEmails.setBounds(10, 39, 157, 14);
		panel_3.add(lblNumberOfEmails);

		txtNumber = new JSpinner();
		txtNumber.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
			}
		});
		txtNumber.setModel(new SpinnerNumberModel(new Integer(1),
				new Integer(0), null, new Integer(1)));
		txtNumber.setBounds(177, 36, 67, 20);
		panel_3.add(txtNumber);

		JLabel lblDelayBetweenSending = new JLabel("Delay between sending (ms)");
		lblDelayBetweenSending.setBounds(10, 64, 157, 18);
		panel_3.add(lblDelayBetweenSending);

		txtDelay = new JSpinner();
		txtDelay.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				ctrl.getSession().setDelay((Integer)txtDelay.getValue());
			}
		});
		txtDelay.setModel(new SpinnerNumberModel(new Integer(1000),
				new Integer(0), null, new Integer(1)));
		txtDelay.setBounds(177, 62, 67, 20);
		panel_3.add(txtDelay);


		chckbxCounter.setSelected(true);
		chckbxCounter.setBounds(10, 160, 234, 25);
		panel_3.add(chckbxCounter);


		chckbxRandom.setSelected(true);
		chckbxRandom.setBounds(10, 188, 234, 25);
		panel_3.add(chckbxRandom);

		JLabel lblSubjectCustomization = new JLabel("Subject customization");
		lblSubjectCustomization.setBounds(10, 139, 234, 14);
		panel_3.add(lblSubjectCustomization);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(12, 608, 254, 27);
		contentPane.add(progressBar);

		final JButton btnStart = new JButton("Start");
		final JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.stopSession();
				btnStop.setEnabled(false);
				btnStart.setEnabled(true);
			}
		});

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				me.updateSession();
				ctrl.runSession();
				btnStop.setEnabled(true);
				btnStart.setEnabled(false);
			}
		});
		btnStart.setBounds(276, 608, 119, 27);
		contentPane.add(btnStart);

		btnStop.setEnabled(false);
		btnStop.setBounds(409, 608, 116, 27);
		contentPane.add(btnStop);
	}

	public void updateFields() {
		Session s = ctrl.getSession();
		txtSrcaddr.setText(s.getFrom());

		// Merge the destinations into a string
		StringBuilder builder = new StringBuilder();
		Iterator<String> iter = s.getTo().iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (!iter.hasNext()) {
				break;
			}
			builder.append(", ");
		}
		txtDestaddr.setText(builder.toString());
		txtSubject.setText(s.getSubject());
		listAttachments.setListData(s.getAttachments());
		txtrMessage.setText(s.getMessage());
		txtServer.setText(s.getServer());
		txtPort.setValue(s.getServerPort());
		txtUser.setText(s.getUser());
		txtPasswd.setText(s.getPassword());
		txtNumber.setValue(s.getQuantity());
		txtDelay.setValue(s.getDelay());
		chckbxCounter.setSelected(s.getCounter());
		chckbxRandom.setSelected(s.getRandomSubject());
	}
	
	public void updateSession()
	{
		Session s = ctrl.getSession();
		s.setFrom(txtSrcaddr.getText());
		Vector<String> tos = new Vector<String>();
		for (String to : txtDestaddr.getText().split(",")) {
			tos.add(to.trim());
		}
		s.setTo(tos);
		s.setFrom(txtSrcaddr.getText());
		s.setSubject(txtSubject.getText());
		s.setQuantity((Integer) txtNumber.getValue());
		s.setCounter(chckbxCounter.isSelected());
		s.setRandomSubject(chckbxRandom.isSelected());
		s.setMessage(txtrMessage.getText());
		s.setServer(txtServer.getText());
		s.setServerPort((Integer)txtPort.getValue());
		s.setUser(txtUser.getText());
		s.setPassword(String.valueOf(txtPasswd.getPassword()));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}


