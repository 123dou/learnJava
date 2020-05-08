package dahuaDesignPattern.factoryMethod;


import dahuaDesignPattern.abstractFactory.factory.IFactory;
import dahuaDesignPattern.factoryMethod.factory.FactoryAdd;
import dahuaDesignPattern.factoryMethod.operation.Operation;

public class Test {
	public static void main(String[] args) {
		try {
			IFactory factory = (IFactory) new FactoryAdd();
			Operation operation = ((FactoryAdd) factory).createOperation();
			operation.setNumber1(23);
			operation.setNumber2(34);
			System.out.println(operation.getResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
