package dahuaDesignPattern.simpleFactory;

public class OperationMul extends Operation{

	@Override
	public double getResult() {
		return number1 * number2;
	}

}
