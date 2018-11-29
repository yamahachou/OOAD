package eCommerce;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
// this class handles the checkout operation
// this class contains the tax and totals calculations
// based on state and necessity items
// and the related GUI components
public class checkout extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public ArrayList<ArrayList<String>> fin = new ArrayList<ArrayList<String>>();
	public double total = 0;
	public double netPayable = 0;
	public double discount = 0;
	public double stTax = 0;
	private JTextField textField_3;
	public checkout(ArrayList<ArrayList<String>>cart, String state) {

		TaxVisitor calc = new TaxVisitor();
		int i = 1;
		for(ArrayList<String> line : cart)
		{
			boolean found = false;
			for(ArrayList<String> t : fin)
			{
				if (t.get(0).equals(line.get(0)))
				{
					double qty = Double.parseDouble(t.get(5));
					qty++;
					t.set(5, Double.toString(qty));
					t.set(6, Double.toString(qty*Double.parseDouble(t.get(3))));
					found = true;
				}
			}
			if(!found)
			{
				Double tax = 0.0;
				if(line.get(3).equals("1"))
				{
					Necessity obj = new Necessity(Double.parseDouble(line.get(2)));
					tax = obj.accept(calc);

				}
				if(line.get(3).equals("0"))
				{
					nonNecessity obj = new nonNecessity(Double.parseDouble(line.get(2)));
					tax = obj.accept(calc);
				}
				ArrayList<String> lineItem = new ArrayList<String>();

				double necessityTax = 0;
				double applicablePrice = 0;

				necessityTax = tax-Double.parseDouble(line.get(2));
				String nT = String.format("%.2f", necessityTax);

				applicablePrice = necessityTax + Double.parseDouble(line.get(2));
				String aP = String.format("%.2f", applicablePrice);
				lineItem.add(line.get(0));
				lineItem.add(line.get(1));
				lineItem.add(line.get(2));
				lineItem.add(nT);
				lineItem.add(aP);
				lineItem.add("1");
				double total = applicablePrice;
				lineItem.add(Double.toString(total));
				fin.add(lineItem);
			}
		}

		for(ArrayList l : fin)
		{
			double qty = Double.parseDouble((String) l.get(5));
			double price = Double.parseDouble((String) l.get(4));
			String aP = String.format("%.2f", qty*price);
			l.set(6, aP);
		}
		System.out.print(fin);

		setTitle("Your Receipt");
		getContentPane().setLayout(null);

		JLabel lblReceipt = new JLabel("RECEIPT");
		lblReceipt.setBounds(428, 13, 109, 34);
		lblReceipt.setForeground(new Color(128, 0, 0));
		lblReceipt.setFont(new Font("Segoe UI", Font.BOLD, 16));
		getContentPane().add(lblReceipt);

		String col[] = {"Item Code","Item Name","Price","After Necessity Tax", "Applicable Price", "Quantity", "Total"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);



		for(int j=0; j<fin.size(); j++)
		{
			Object[] objs = fin.get(j).toArray();
			total += Double.parseDouble(fin.get(j).get(6));
			tableModel.addRow(objs);
		}

		StateTax tax;
		if(state.equals("CA - California"))
		{
			tax = new StateTaxforCalifornia(new BaseTax());
		}

		else if(state.equals("NJ - New Jersey"))
		{
			tax = new StateTaxforNewJersey(new BaseTax());
		}

		else if(state.equals("TX - Texas"))
		{
			tax = new StateTaxforTexas(new BaseTax());
		}

		else
		{
			tax = new BaseTax();
		}

		stTax = tax.getTax();
		DiscountFactory factory = new DiscountFactory();
		Discount obj = factory.getDiscountClass(total);
		discount = obj.getDiscount();
		netPayable = total + (total * stTax) - (total * discount);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(680, 403, 86, 34);
		lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
		getContentPane().add(lblTotal);

		JLabel lblStateTax = new JLabel("State Tax");
		lblStateTax.setBounds(680, 438, 86, 34);
		lblStateTax.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		getContentPane().add(lblStateTax);

		JLabel lblNetPayable = new JLabel("Net Payable");
		lblNetPayable.setBounds(680, 509, 86, 34);
		lblNetPayable.setFont(new Font("Segoe UI", Font.BOLD, 14));
		getContentPane().add(lblNetPayable);


		textField = new JTextField();
		textField.setBounds(788, 410, 116, 22);
		textField.setEditable(false);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText(Double.toString(Math.round(total)));

		textField_1 = new JTextField();
		textField_1.setBounds(788, 445, 116, 22);
		textField_1.setEditable(false);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(Double.toString(stTax));

		textField_2 = new JTextField();
		textField_2.setBounds(788, 516, 116, 22);
		textField_2.setEditable(false);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(Double.toString(Math.round(netPayable)));

		JTable tbl = new JTable(tableModel);
		tbl.setBounds(12, 67, 892, 337);
		tbl.setBackground(Color.cyan);
		tbl.setRowHeight(23);
		tbl.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(tbl);
		scrollPane.setBounds(12, 67, 892, 337);
		getContentPane().add(scrollPane);

		JLabel label = new JLabel("Discount");
		label.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(680, 474, 86, 34);
		getContentPane().add(label);

		textField_3 = new JTextField();
		textField_3.setText("0.0");
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(788, 481, 116, 22);
		getContentPane().add(textField_3);
		textField_3.setText(Double.toString(discount));

	}
}
