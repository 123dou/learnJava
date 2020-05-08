package dahuaDesignPattern.abstractFactory.factory;

import dahuaDesignPattern.abstractFactory.dao.IDepartmentDao;
import dahuaDesignPattern.abstractFactory.dao.IUserDao;
import dahuaDesignPattern.abstractFactory.dao.OracleDeptDao;
import dahuaDesignPattern.abstractFactory.dao.OracleUserDao;

/**
 * Oracle数据库对dao层的实现也就是具体的工厂
 * @author dou
 *
 */
public class OracleDaoFactory implements IFactory {

	@Override
	public IUserDao createUserDao() {
		return new OracleUserDao();
	}

	@Override
	public IDepartmentDao createDepartmentDao() {
		return new OracleDeptDao();
	}

}
