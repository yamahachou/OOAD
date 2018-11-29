package eCommerce;

public abstract class StateTaxDecorator implements StateTax{
	protected StateTax tax;
	public StateTaxDecorator(StateTax newTax)
	{
		tax = newTax;
	}

	@Override
	public double getTax()
	{
		return tax.getTax();
	}
}
