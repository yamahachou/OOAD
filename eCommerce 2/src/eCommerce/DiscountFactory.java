package eCommerce;

public class DiscountFactory {

	public Discount getDiscountClass(double total)
	{
		Discount disc = null;
		if(total >= 500 && total <1500)
		{
			return new MinnowDiscount();
		}
		else if(total >=1500 && total <3000)
		{
			return new MegaDiscount();
		}
		else if(total >3000)
		{
			return new HyperDiscount();
		}
		return new NoDiscount();
	}
}
