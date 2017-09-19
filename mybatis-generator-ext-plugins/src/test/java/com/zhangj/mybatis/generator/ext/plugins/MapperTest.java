package com.zhangj.mybatis.generator.ext.plugins;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhangj.mybatis.generator.ext.plugins.model.TableTestSliceMonthEntity;
import com.zhangj.mybatis.generator.ext.plugins.model.TableTestSliceMonthFilter;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * Created by chengyang
 */
public class MapperTest {
    public static void main(String[] args) {
        String resource = "/mybatis_config.xml";

        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = MapperTest.class.getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);

        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
//
//                TableTestSliceMonthMapper mapper = session.getMapper(TableTestSliceMonthMapper.class);
//
//                TableTestSliceMonthEntity entity = new TableTestSliceMonthEntity();
//                entity.setJacksonId1(1L);
//                entity.setJacksonId2("2");
//                entity.setCouldSumCol(4);
//                entity.setJacksonTime(new Date());
//                entity.setVersion(1L);
//                entity.setSliceMonthId(new Date());
//
//                TableTestSliceMonthEntity entity1 = new TableTestSliceMonthEntity();
//                entity1.setJacksonId1(2L);
//                entity1.setJacksonId2("22");
//                entity1.setCouldSumCol(24);
//                entity1.setJacksonTime(new Date());
//                entity1.setVersion(2L);
//                entity1.setSliceMonthId(new Date());
//
//                TableTestSliceMonthEntity entity2 = new TableTestSliceMonthEntity();
//                entity2.setJacksonId1(21L);
//                entity2.setJacksonId2("21");
//                entity2.setCouldSumCol(21);
//                entity2.setJacksonTime(new Date());
//                entity2.setVersion(21L);
//                entity2.setSliceMonthId(new Date());
//
//                List<TableTestSliceMonthEntity> list = new ArrayList<>();
//                list.add(entity);
//                list.add(entity1);
//                list.add(entity2);
//                mapper.batchInsert(list);
//                session.commit();
//
//                TableTestSliceMonthFilter filter = new TableTestSliceMonthFilter();
//                filter.page(1,2).createCriteria().andIdGreaterThan(0l);
//                System.out.println(mapper.selectByFilter(filter));

                //System.out.println(mapper.minJacksonId1ByFilter(filter));

//        System.out.println(mapper.countA());
    }
}
