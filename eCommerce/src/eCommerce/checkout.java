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

public class checkout extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public ArrayList<ArrayList<String>> fin = new ArrayList<ArrayList<String>>();
	public double total = 0;
	public double netPayable = 0;
	public double stTax = 0;
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
				lineItem.add(line.get(0));
				lineItem.add(line.get(1));
				lineItem.add(line.get(2));
				lineItem.add(Double.toString(tax));
				lineItem.add(Double.toString((Double.parseDouble(line.get(2))+tax)));
				lineItem.add("1");
				double total = tax+Double.parseDouble(line.get(2));
				lineItem.add(Double.toString(total));
				fin.add(lineItem);
			}
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

		netPayable = total + (total * stTax);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(680, 417, 86, 34);
		lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
		getContentPane().add(lblTotal);

		JLabel lblStateTax = new JLabel("State Tax");
		lblStateTax.setBounds(680, 462, 86, 34);
		lblStateTax.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		getContentPane().add(lblStateTax);

		JLabel lblNetPayable = new JLabel("Net Payable");
		lblNetPayable.setBounds(680, 509, 86, 34);
		lblNetPayable.setFont(new Font("Segoe UI", Font.BOLD, 14));
		getContentPane().add(lblNetPayable);


		textField = new JTextField();
		textField.setBounds(788, 424, 116, 22);
		textField.setEditable(false);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText(Double.toString(Math.round(total)));

		textField_1 = new JTextField();
		textField_1.setBounds(788, 469, 116, 22);
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






	}
}
