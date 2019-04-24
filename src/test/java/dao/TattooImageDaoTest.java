//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.TattooImageCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.TattooImage;
//import ImageType;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class TattooImageDaoTest {
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
//        TattooImageCrudDao dao = (TattooImageCrudDao) GenericDaoFactory.getInstance().createDao(TattooImage.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        TattooImageCrudDao dao = (TattooImageCrudDao) GenericDaoFactory.getInstance().createDao(TattooImage.class);
//        System.out.println(dao.findById(1L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooImageCrudDao dao = (TattooImageCrudDao) GenericDaoFactory.getInstance().createDao(TattooImage.class);
//        TattooImage image = new TattooImage();
//        image.setTattooId(2L);
//        image.setType(ImageType.ORDINARY);
//        image.setId(2L);
//        System.out.println(dao.save(image));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooImageCrudDao dao = (TattooImageCrudDao) GenericDaoFactory.getInstance().createDao(TattooImage.class);
//        TattooImage image = new TattooImage();
//        image.setTattooId(2L);
//        image.setType(ImageType.TITLE);
//        image.setId(2L);
//        image.setTattooImageId(1L);
//        dao.update(image);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        TattooImageCrudDao dao = (TattooImageCrudDao) GenericDaoFactory.getInstance().createDao(TattooImage.class);
//        dao.deleteById(1L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
