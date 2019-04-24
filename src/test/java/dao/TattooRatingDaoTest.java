//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.TattooRatingCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.TattooRating;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class TattooRatingDaoTest {
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
//        TattooRatingCrudDao dao = (TattooRatingCrudDao) GenericDaoFactory.getInstance().createDao(TattooRating.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        TattooRatingCrudDao dao = (TattooRatingCrudDao) GenericDaoFactory.getInstance().createDao(TattooRating.class);
//        System.out.println(dao.findById(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findRatingsByTattooId() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooRatingCrudDao dao = (TattooRatingCrudDao) GenericDaoFactory.getInstance().createDao(TattooRating.class);
//        System.out.println(dao.findRatingsByTattooId(2L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooRatingCrudDao dao = (TattooRatingCrudDao) GenericDaoFactory.getInstance().createDao(TattooRating.class);
//        TattooRating rating = new TattooRating(5);
//        rating.setUserId(1L);
//        rating.setTattooId(2L);
//        System.out.println(dao.save(rating));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooRatingCrudDao dao = (TattooRatingCrudDao) GenericDaoFactory.getInstance().createDao(TattooRating.class);
//        TattooRating rating = new TattooRating(3);
//        rating.setId(1L);
//        rating.setUserId(1L);
//        rating.setTattooId(2L);
//        dao.update(rating);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooRatingCrudDao dao = (TattooRatingCrudDao) GenericDaoFactory.getInstance().createDao(TattooRating.class);
//        dao.deleteById(1L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
