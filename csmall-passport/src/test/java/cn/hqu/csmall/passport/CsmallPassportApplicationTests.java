package cn.hqu.csmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class CsmallPassportApplicationTests {

    @Autowired
    private DataSource source;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(source.getConnection());
    }

}
