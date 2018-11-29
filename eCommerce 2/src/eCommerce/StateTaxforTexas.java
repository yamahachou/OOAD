package eCommerce;

public class StateTaxforTexas extends StateTaxDecorator {

	public StateTaxforTexas(StateTax newTax) {
		super(newTax);
		// TODO Auto-generated constructor stub
	}
	@Override
	public double getTax()
	{
		return tax.getTax() + 0.05;
	}
}
