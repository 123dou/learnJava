package dahuaDesignPattern.abstractFactory.abstractFactory_reflect;


import dahuaDesignPattern.abstractFactory.dao.IDepartmentDao;
import dahuaDesignPattern.abstractFactory.dao.IUserDao;
import dahuaDesignPattern.abstractFactory.entity.Department;
import dahuaDesignPattern.abstractFactory.entity.User;
import dahuaDesignPattern.abstractFactory.factory.IFactory;
import dahuaDesignPattern.abstractFactory.factory.OracleDaoFactory;

public class Test {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		User user = new User();
		IUserDao userDao = DaoAccess.createUserDao("abstractFactory.dao.SqlUserDao");
		userDao.insert(user);
		//需要换不同的数据库的时候只需要改这一处
		IFactory factory2 = new OracleDaoFactory();
		Department department = new Department();
		IDepartmentDao deptDao = factory2.createDepartmentDao();
		deptDao.update(department);
		
	}
}
