package eCommerce;

public class StateTaxforCalifornia extends StateTaxDecorator {

	public StateTaxforCalifornia(StateTax newTax) {
		super(newTax);
		// TODO Auto-generated constructor stub
	}
	@Override
	public double getTax()
	{
		return tax.getTax() + 0.03;
	}
}
