//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.DiscountCardCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.DiscountCard;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class DiscountCardDaoTest {
//
//    @BeforeClass
//    public void initializePool() throws SQLException{
//        JdbcConnectionPool.getInstance().initialize();
//    }
//
//    @AfterClass
//    public void destroyPool() throws SQLException, ConnectionNotClosedException{
//        JdbcConnectionPool.getInstance().destroy();
//    }
//
//    @Test
//    public void findAll() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        DiscountCardCrudDao dao = (DiscountCardCrudDao) GenericDaoFactory.getInstance().createDao(DiscountCard.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws ConnectionNotClosedException, DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException {
//        DiscountCardCrudDao dao = (DiscountCardCrudDao) GenericDaoFactory.getInstance().createDao(DiscountCard.class);
//        System.out.println(dao.findById(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        DiscountCardCrudDao dao = (DiscountCardCrudDao) GenericDaoFactory.getInstance().createDao(DiscountCard.class);
//        DiscountCard card = new DiscountCard(30382859, 0);
//        card.setDiscountId(3L);
//        card.setUserId(3L);
//        System.out.println(dao.save(card));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        DiscountCardCrudDao dao = (DiscountCardCrudDao) GenericDaoFactory.getInstance().createDao(DiscountCard.class);
//        DiscountCard card = new DiscountCard(30439284, 1);
//        card.setId(1L);
//        card.setDiscountId(4L);
//        dao.update(card);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        DiscountCardCrudDao dao = (DiscountCardCrudDao) GenericDaoFactory.getInstance().createDao(DiscountCard.class);
//        dao.deleteById(1L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
