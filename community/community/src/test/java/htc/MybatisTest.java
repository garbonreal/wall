package htc;

import com.lyh.community.dao.ILikeCommentDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    @Test
    public void testLikeComment() throws IOException {
        String resource="mybatis-config.xml";
        InputStream inputStream= Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        //执行
        ILikeCommentDao userMapper=sqlSession.getMapper(ILikeCommentDao.class);
        // int uid=1;
        int cid=5;
        int num=userMapper.selectCountByCid(cid);
        // userMapper.insertByUidCid(1,2);
        System.out.println(num);
        
        sqlSession.close();
    }
    
    @Test
    public void testCommentAddLike() throws IOException {
        String resource="mybatis-config.xml";
        InputStream inputStream= Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession
        SqlSession sqlSession=sqlSessionFactory.openSession(true);
        
    }
}
