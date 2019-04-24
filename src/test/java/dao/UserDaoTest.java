//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
//import com.epam.kirillcheldishkin.dao.UserCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.Client;
//import com.epam.kirillcheldishkin.entity.User;
//import com.epam.kirillcheldishkin.entity.user.UserRole;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class UserDaoTest {
//    @BeforeClass
//    public void initializePool() throws SQLException{
//        JdbcConnectionPool.getInstance().initialize();
//    }
//
//    @AfterClass
//    public void destroyPool() throws SQLException, ConnectionNotClosedException{
//        JdbcConnectionPool.getInstance().destroy();
//    }
//    @Test
//    public void findAll() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        System.out.println(dao.findById(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        System.out.println(dao.save(new User("1", "1", "1", "1", UserRole.CLIENT)));
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        User client = new Client("2", "3", "3", "$", 36);
//        client.setId(19L);
//        dao.update(client);
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        dao.deleteById(19L);
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void finByLogin() throws DaoException, DaoNotFoundException, ConnectionNotClosedException{
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        System.out.println(dao.findByLogin("KiruxaCheldik"));
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void identifyUser() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createDao(User.class);
//        System.out.println(dao.identifyUser("cheldik100", "1ga2gagjhlnh3ga4ga"));
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void transactionalFindAll() throws DaoException, DaoNotFoundException, IOException, SQLException, ConnectionNotClosedException {
//        Properties properties = new Properties();
//        properties.load(new FileInputStream("/Users/kirillceldyskin/Downloads/FinalTask/src/main/resources/db.properties"));
//        UserCrudDao dao = (UserCrudDao) GenericDaoFactory.getInstance().createTransactionalDao(User.class);
//        ((AbstractCrudDao)dao).setConnection(DriverManager.getConnection(properties.getProperty("url"), properties));
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }
//}
