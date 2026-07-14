package cn.hqu.csmall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class CsmallProductApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    DataSource dataSource;
    @Test
    void testDataSource() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
