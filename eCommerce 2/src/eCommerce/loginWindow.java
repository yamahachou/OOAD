package eCommerce;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// This is the first dialog box that you see
// This contains the login authentication for
// a user using the Facade pattern and the
// respective GUI components
public class loginWindow extends JFrame{
	private JTextField textField;
	private JPasswordField passwordField;
	public String username;
	public String password;
	public loginWindow() {
		setTitle("Login!");
		getContentPane().setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(12, 43, 98, 16);
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 82, 98, 16);
		getContentPane().add(lblPassword);

		textField = new JTextField();
		textField.setBounds(136, 40, 274, 22);
		getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(136, 79, 274, 22);
		getContentPane().add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				username = textField.getText().toString();
				password = new String(passwordField.getPassword());
				loginFacade object = new loginFacade(username,password);
				String check = object.confirmLogin();
				if(check.equals("-1"))
				{
					System.exit(1);
				}
				DBInterface db = new DBInterface();
				UserInterface view = new UserInterface(db,username);
				view.setBounds(200, 10, 1060, 880);
				view.setVisible(true);
				setVisible(false);
			}
		});
		btnLogin.setBounds(136, 124, 274, 25);
		getContentPane().add(btnLogin);
	}

}
