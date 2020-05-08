package dahuaDesignPattern.abstractFactory.abstractFactory_reflect;


import dahuaDesignPattern.abstractFactory.dao.IDepartmentDao;
import dahuaDesignPattern.abstractFactory.dao.IUserDao;

public class DaoAccess {
    //利用反射来产生对应的工厂
    //获取UserDao
    public static IUserDao createUserDao(String db)
    		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	IUserDao userDao = (IUserDao) Class.forName(db).newInstance();
    	return userDao;
    }
    
    //获取DeptDao
    public static IDepartmentDao createDeptDao(String db)
    		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	IDepartmentDao deptDao = (IDepartmentDao) Class.forName(db).newInstance();
    	return deptDao;
    }
}
