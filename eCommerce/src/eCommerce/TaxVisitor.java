package eCommerce;

public class TaxVisitor implements Visitor {

	@Override
	public double visit(Necessity item) {
		// TODO Auto-generated method stub
		return (item.getPrice()*.05)+item.getPrice();
	}

	@Override
	public double visit(nonNecessity item) {
		// TODO Auto-generated method stub
		return (item.getPrice()*.18)+item.getPrice();
	}

}
