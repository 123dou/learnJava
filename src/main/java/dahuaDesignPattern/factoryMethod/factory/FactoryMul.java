package dahuaDesignPattern.factoryMethod.factory;

import dahuaDesignPattern.factoryMethod.operation.Operation;
import dahuaDesignPattern.factoryMethod.operation.OperationMul;

public class FactoryMul implements IFactory{
	public Operation createOperation() {
		return new OperationMul();
	}
}
