//package dao;
//
//import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
//import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
//import com.epam.kirillcheldishkin.dao.UserProposalCrudDao;
//import com.epam.kirillcheldishkin.dao.exception.*;
//import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
//import com.epam.kirillcheldishkin.entity.UserProposal;
//import com.epam.kirillcheldishkin.entity.state.AcceptedState;
//import com.epam.kirillcheldishkin.entity.state.SubmittedState;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.sql.SQLException;
//
//public class ProposalDaoTest {
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
//        UserProposalCrudDao dao = (UserProposalCrudDao) GenericDaoFactory.getInstance().createDao(UserProposal.class);
//        System.out.println(dao.findAll());
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void findById() throws DaoNotFoundException, SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, ConnectionNotClosedException {
//        UserProposalCrudDao dao = (UserProposalCrudDao) GenericDaoFactory.getInstance().createDao(UserProposal.class);
//        System.out.println(dao.findById(2L));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void save() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserProposalCrudDao dao = (UserProposalCrudDao) GenericDaoFactory.getInstance().createDao(UserProposal.class);
//        UserProposal proposal = new UserProposal(new SubmittedState());
//        proposal.setState(new SubmittedState());
//        proposal.getUser().setId(1L);
//        proposal.getImage().setId(2L);
//        System.out.println(dao.save(proposal));
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void update() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserProposalCrudDao dao = (UserProposalCrudDao) GenericDaoFactory.getInstance().createDao(UserProposal.class);
//        UserProposal proposal = new UserProposal(new AcceptedState());
//        proposal.setId(2L);
//        proposal.getUser().setId(1L);
//        proposal.getImage().setId(1L);
//        dao.update(proposal);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//
//    @Test
//    public void deleteById() throws DaoException, DaoNotFoundException, ConnectionNotClosedException {
//        UserProposalCrudDao dao = (UserProposalCrudDao) GenericDaoFactory.getInstance().createDao(UserProposal.class);
//        dao.deleteById(2L);
//        JdbcConnectionPool.getInstance().destroy();
//    }//accepted
//}
