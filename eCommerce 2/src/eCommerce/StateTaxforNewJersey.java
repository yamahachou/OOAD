package eCommerce;

public class StateTaxforNewJersey extends StateTaxDecorator{


		public StateTaxforNewJersey(StateTax newTax) {
			super(newTax);
			// TODO Auto-generated constructor stub
		}
		@Override
		public double getTax()
		{
			return tax.getTax() + 0.07;
		}
	}