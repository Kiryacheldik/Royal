//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.TattooCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.Tattoo;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class TattooDaoTest {
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
//        TattooCrudDao dao = (TattooCrudDao) GenericDaoFactory.getInstance().createDao(Tattoo.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        TattooCrudDao dao = (TattooCrudDao) GenericDaoFactory.getInstance().createDao(Tattoo.class);
//        System.out.println(dao.findById(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findByNameLike() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooCrudDao dao = (TattooCrudDao) GenericDaoFactory.getInstance().createDao(Tattoo.class);
//        System.out.println(dao.findByNameLike("Tat"));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooCrudDao dao = (TattooCrudDao) GenericDaoFactory.getInstance().createDao(Tattoo.class);
//        Tattoo tattoo = new Tattoo("Tattoo");
//        dao.save(tattoo);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooCrudDao dao = (TattooCrudDao) GenericDaoFactory.getInstance().createDao(Tattoo.class);
//        Tattoo tattoo = new Tattoo("Tattoo");
//        tattoo.setName("SuperTattoo");
//        tattoo.setId(1L);
//        dao.update(tattoo);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooCrudDao dao = (TattooCrudDao) GenericDaoFactory.getInstance().createDao(Tattoo.class);
//        dao.deleteById(1L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
