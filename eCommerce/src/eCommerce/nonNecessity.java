package eCommerce;

public class nonNecessity implements Visitable{

	private double price;
	nonNecessity(double item)
	{
		price = item;
	}
	public double getPrice()
	{
		return price;
	}
	@Override
	public double accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
