package dahuaDesignPattern.factoryMethod.factory;


import dahuaDesignPattern.factoryMethod.operation.Operation;
import dahuaDesignPattern.factoryMethod.operation.OperationAdd;

public class FactoryAdd implements IFactory{
	public Operation createOperation() {
		return new OperationAdd();
	}
}
