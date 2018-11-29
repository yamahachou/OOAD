package eCommerce;

public interface Visitor {
	public double visit(Necessity item);
	public double visit(nonNecessity item);
}
