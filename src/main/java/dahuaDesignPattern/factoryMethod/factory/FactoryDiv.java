package dahuaDesignPattern.factoryMethod.factory;

import dahuaDesignPattern.factoryMethod.operation.Operation;
import dahuaDesignPattern.factoryMethod.operation.OperationDiv;

public class FactoryDiv implements IFactory{
	public Operation createOperation() {
		return new OperationDiv();
	}
}
