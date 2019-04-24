//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.OrderCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.Order;
//import com.epam.kirillcheldishkin.entity.state.SubmittedState;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class OrderDaoTest {
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
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        System.out.println(dao.findById(3L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findUserListByTattooId() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        System.out.println(dao.findUserListByTattooId(2L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findTattooListByUserId() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        System.out.println(dao.findTattooListByUserId(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        Order order = new Order(1L, 2L);
//        System.out.println(dao.save(order));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        Order order = new Order(1L, 2L);
//        order.setId(1L);
//        order.setState(new SubmittedState());
//        order.setState(order.getState().nextState());
//        dao.update(order);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        OrderCrudDao dao = (OrderCrudDao) GenericDaoFactory.getInstance().createDao(Order.class);
//        dao.deleteById(3L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
