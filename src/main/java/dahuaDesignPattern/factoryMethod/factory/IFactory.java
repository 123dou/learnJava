package dahuaDesignPattern.factoryMethod.factory;

import dahuaDesignPattern.factoryMethod.operation.Operation;

public interface IFactory {
    Operation createOperation();
}
