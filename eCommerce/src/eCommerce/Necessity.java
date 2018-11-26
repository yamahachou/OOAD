package eCommerce;

public class Necessity implements Visitable{

	private double price;
	Necessity(double item)
	{
		price = item;
	}
	public double getPrice()
	{
		return price;
	}
	@Override
	public double accept(Visitor visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

}
