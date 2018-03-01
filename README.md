# AES-Encryption-Tool
Using AES Encryption to encrypt and decrypt phrases with GUI. Written in Java used in Eclipse
package aes_encryption;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptionGUI {

	private JFrame frame;
	private static final String ALGO = "AES";
	private byte[] keyValue;
	private JTextArea outputText;
	private JTextArea inputText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EncryptionGUI window = new EncryptionGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EncryptionGUI() {
		initialize();
		// keyValue = key.getBytes();
	}

	public EncryptionGUI(String key) {
		keyValue = key.getBytes();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 526, 323);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		inputText = new JTextArea();
		inputText.setWrapStyleWord(true);
		inputText.setLineWrap(true);
		inputText.setFont(new Font("Cambria", Font.PLAIN, 13));

		JLabel label = new JLabel("Enter Text");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));

		JButton button = new JButton("Encypt");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = inputText.getText().trim();
				try {
					String encryptedString = encrypt(input);
					// System.out.println(encryptedString);
					outputText.setText(encryptedString);
				} catch (Exception e) {
					System.out.println("Error: " + e.getStackTrace());
					Logger.getLogger(AES_Encryption.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel label_1 = new JLabel("Encypted/Decrypted Text");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));

		outputText = new JTextArea();
		outputText.setWrapStyleWord(true);
		outputText.setLineWrap(true);
		outputText.setFont(new Font("Cambria", Font.PLAIN, 13));

		JButton button_1 = new JButton("Decrypt");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String output = outputText.getText();

				try {
					outputText.setText(decrypt(output));
				} catch (Exception e) {
					System.out.println("Error: " + e.getStackTrace());
					Logger.getLogger(AES_Encryption.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
								.addComponent(inputText, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 115,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
								.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(18, 58, Short.MAX_VALUE)
												.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 189,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup().addGap(9).addComponent(outputText,
												GroupLayout.PREFERRED_SIZE, 229, Short.MAX_VALUE)))
								.addContainerGap())
								.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_1,
												GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
										.addGap(18)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup().addGap(5)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(inputText, GroupLayout.PREFERRED_SIZE, 167,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(outputText, GroupLayout.PREFERRED_SIZE, 165,
												GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(
						groupLayout.createParallelGroup(Alignment.LEADING).addComponent(button).addComponent(button_1))
				.addGap(34)));
		frame.getContentPane().setLayout(groupLayout);
	}

	private Key generateKey() throws Exception {
		String myKey = "my test is good.";
		keyValue = myKey.getBytes();
		Key key = new SecretKeySpec(keyValue, ALGO);
		String k = key.toString();
		return key;
	}

	private String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encVal);
		System.out.print(encryptedValue);
		return encryptedValue;
	}

	private String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = c.doFinal(decodedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

}
