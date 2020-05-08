package dahuaDesignPattern.factoryMethod.factory;

import dahuaDesignPattern.factoryMethod.operation.Operation;
import dahuaDesignPattern.factoryMethod.operation.OperationSub;

public class FactorySub implements IFactory{
	public Operation createOperation() {
		return new OperationSub();
	}
}
