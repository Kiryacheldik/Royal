//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.ImageCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.Image;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class ImageDaoTest {
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
//        ImageCrudDao dao = (ImageCrudDao) GenericDaoFactory.getInstance().createDao(Image.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        ImageCrudDao dao = (ImageCrudDao) GenericDaoFactory.getInstance().createDao(Image.class);
//        System.out.println(dao.findById(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        ImageCrudDao dao = (ImageCrudDao) GenericDaoFactory.getInstance().createDao(Image.class);
//        Image image = new Image("/image/hello.jpg");
//        System.out.println(dao.save(image));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        ImageCrudDao dao = (ImageCrudDao) GenericDaoFactory.getInstance().createDao(Image.class);
//        Image image = new Image("/images/hello2.jpg");
//        image.setId(1L);
//        dao.update(image);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        ImageCrudDao dao = (ImageCrudDao) GenericDaoFactory.getInstance().createDao(Image.class);
//        dao.deleteById(1L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
