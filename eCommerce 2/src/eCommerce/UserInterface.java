package eCommerce;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

// This is the main User Interface class, the main method is this class
// The main method calls the login window class for the login dialog
// Then if that passes, this frame is created where the user is shown the
// products along with all the applicable actions

public class UserInterface extends JFrame {

	static String [] states = { "AK - Alaska",
              "AL - Alabama",
              "AR - Arkansas",
              "AS - American Samoa",
              "AZ - Arizona",
              "CA - California",
              "CO - Colorado",
              "CT - Connecticut",
              "DC - District of Columbia",
              "DE - Delaware",
              "FL - Florida",
              "GA - Georgia",
              "GU - Guam",
              "HI - Hawaii",
              "IA - Iowa",
              "ID - Idaho",
              "IL - Illinois",
              "IN - Indiana",
              "KS - Kansas",
              "KY - Kentucky",
              "LA - Louisiana",
              "MA - Massachusetts",
              "MD - Maryland",
              "ME - Maine",
              "MI - Michigan",
              "MN - Minnesota",
              "MO - Missouri",
              "MS - Mississippi",
              "MT - Montana",
              "NC - North Carolina",
              "ND - North Dakota",
              "NE - Nebraska",
              "NH - New Hampshire",
              "NJ - New Jersey",
              "NM - New Mexico",
              "NV - Nevada",
              "NY - New York",
              "OH - Ohio",
              "OK - Oklahoma",
              "OR - Oregon",
              "PA - Pennsylvania",
              "PR - Puerto Rico",
              "RI - Rhode Island",
              "SC - South Carolina",
              "SD - South Dakota",
              "TN - Tennessee",
              "TX - Texas",
              "UT - Utah",
              "VA - Virginia",
              "VI - Virgin Islands",
              "VT - Vermont",
              "WA - Washington",
              "WI - Wisconsin",
              "WV - West Virginia",
              "WY - Wyoming"};

	ArrayList<ArrayList<String>> products = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> cart = new ArrayList<ArrayList<String>>();
	static String userFn;
	static String userLn;

    public static String ask() {

        String result = null;

        if (EventQueue.isDispatchThread()) {

            JPanel panel = new JPanel();
            panel.add(new JLabel("Please make a selection:"));
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (String value : states) {
                model.addElement(value);
            }
            JComboBox comboBox = new JComboBox(model);
            panel.add(comboBox);

            int iResult = JOptionPane.showConfirmDialog(null, panel, "State ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (iResult) {
                case JOptionPane.OK_OPTION:
                    result = (String) comboBox.getSelectedItem();
                    break;
            }

        } else {

            Response response = new Response(states);
            try {
                SwingUtilities.invokeAndWait(response);
                result = response.getResponse();
            } catch (InterruptedException | InvocationTargetException ex) {
                ex.printStackTrace();
            }

        }

        return result;

    }

    public static class Response implements Runnable {

        private String[] values;
        private String response;

        public Response(String... values) {
            this.values = values;
        }

        @Override
        public void run() {
            response = ask();
        }

        public String getResponse() {
            return response;
        }
    }

	public UserInterface(DBInterface db,String username) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();		users = db.getData().get(0);
		products = db.getData().get(1);

		for(ArrayList<String> a: users)
		{
			if(a.get(0).equals(username))
			{
				userFn = a.get(1);
				userLn = a.get(2);
			}
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Shopping Store");
		getContentPane().setLayout(null);

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		statusPanel.setBounds(12, 13, 1014, 31);
		getContentPane().add(statusPanel);
		statusPanel.setLayout(null);

		JLabel lblUser = new JLabel("User: "+userFn+" "+userLn);
		lblUser.setBounds(12, 13, 288, 19);
		statusPanel.add(lblUser);

		JLabel lblLoggedInAt = new JLabel("Logged In at: "+dateFormat.format(date));
		lblLoggedInAt.setBounds(708, 13, 294, 19);
		statusPanel.add(lblLoggedInAt);

		JPanel Productspanel = new JPanel();
		Productspanel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		Productspanel.setBounds(12, 67, 1014, 674);
		getContentPane().add(Productspanel);
		Productspanel.setLayout(new GridLayout(3, 3, 2, 2));

		JLabel lblProducts = new JLabel("Products");
		lblProducts.setBounds(12, 47, 81, 20);
		getContentPane().add(lblProducts);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		panel.setBounds(12, 766, 1014, 52);
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String response = ask();
				System.out.println(response);
				checkout frame = new checkout(cart, response);
				frame.setVisible(true);
				frame.setBounds(250, 150, 950, 600);
			}
		});
		btnCheckout.setBounds(12, 13, 249, 25);
		panel.add(btnCheckout);

		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		btnExit.setBounds(753, 13, 249, 25);
		panel.add(btnExit);

		JLabel lblActions = new JLabel("Actions");
		lblActions.setBounds(12, 743, 81, 20);
		getContentPane().add(lblActions);

		ImageIcon emptyCircle = new ImageIcon("product.jpg");
		JPanel[][] gridComponent = new JPanel[3][3];
		JButton[][] buttons = new JButton[3][3];
		int i=0;
		for (int a = 0; a < 3; a++) {
			if (a == 2)
				break;
			for (int b = 0; b < 3; b++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gridComponent[a][b] = new JPanel();
				gridComponent[a][b].setLayout(new GridBagLayout());
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gridComponent[a][b].add(new JLabel(new ImageIcon("images/"+Integer.toString(i+1)+".jpg")),gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gridComponent[a][b].add(new JLabel(products.get(i).get(1)+" --- $"+products.get(i).get(2),SwingConstants.CENTER),gbc);
				gbc.gridx = 0;
				gbc.gridy = 2;
				buttons[a][b] = new JButton("Add to Cart");
				buttons[a][b].addActionListener(new ActionListener() {
		            @Override
					public void actionPerformed(ActionEvent ae) {
		            	for(int i=0;i<3;i++)
		            	{
		            		for(int j=0;j<3;j++)
		            		{
		            			if(ae.getSource() == buttons[i][j])
		            			{
		            				JOptionPane.showMessageDialog(buttons[i][j],"Added");
		            				cart.add(products.get((i*2)+i+j));
		            			}
		            		}
		            	}
		            }
		        });
				gridComponent[a][b].add(buttons[a][b],gbc);
				Productspanel.add(gridComponent[a][b]);
				Productspanel.repaint();
				Productspanel.revalidate();
				i++;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loginWindow login = new loginWindow();
		login.setVisible(true);
		login.setBounds(700,400,500,250);
	}
}
