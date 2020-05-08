package dahuaDesignPattern.abstractFactory.dao;

import dahuaDesignPattern.abstractFactory.entity.User;

/**
 * 有可能有不止一种的实现,所以可以考虑有抽像工厂的设计模式
 */

public interface IUserDao {
    void insert(User user);
    void update(User user);
}
