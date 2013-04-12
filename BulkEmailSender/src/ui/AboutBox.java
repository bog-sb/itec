package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.Font;

@SuppressWarnings("serial")
public class AboutBox extends JFrame {

	/**
	 * Create the frame.
	 */
	public AboutBox() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutBox.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		setResizable(false);
		setTitle("About us");
		setBounds(100, 100, 255, 181);
		getContentPane().setLayout(null);
		
		JLabel lblMadeWithPassion = new JLabel("The no-nonsense mailer made with passion by:");
		lblMadeWithPassion.setBounds(10, 11, 255, 23);
		getContentPane().add(lblMadeWithPassion);
		
		JLabel lblDanHaiduc = new JLabel("Dan Haiduc");
		lblDanHaiduc.setVerticalAlignment(SwingConstants.TOP);
		lblDanHaiduc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanHaiduc.setBounds(20, 127, 116, 14);
		getContentPane().add(lblDanHaiduc);
		
		JLabel lblPauliei = new JLabel("Paul \u021Ai\u021Bei");
		lblPauliei.setVerticalAlignment(SwingConstants.TOP);
		lblPauliei.setHorizontalAlignment(SwingConstants.CENTER);
		lblPauliei.setBounds(20, 75, 116, 23);
		getContentPane().add(lblPauliei);
		
		JLabel lblBogdanSbiera = new JLabel("Bogdan Sbiera");
		lblBogdanSbiera.setVerticalAlignment(SwingConstants.TOP);
		lblBogdanSbiera.setHorizontalAlignment(SwingConstants.CENTER);
		lblBogdanSbiera.setBounds(20, 100, 116, 23);
		getContentPane().add(lblBogdanSbiera);
		
		JButton btnOk = new JButton("OK");
		final AboutBox me = this;
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.setVisible(false);
			}
		});
	
		btnOk.setBounds(146, 123, 94, 23);
		getContentPane().add(btnOk);
		
		JLabel lblCodejunkies = new JLabel("CodeJunkies");
		lblCodejunkies.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblCodejunkies.setBounds(31, 39, 94, 23);
		getContentPane().add(lblCodejunkies);

	}
}
