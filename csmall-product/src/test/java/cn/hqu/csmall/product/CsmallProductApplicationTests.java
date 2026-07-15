package cn.hqu.csmall.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
class CsmallProductApplicationTests {

    @Test
    void contextLoads() {
        int x = 1;
        int y = 2;
        System.out.println("x="+x+"y="+y);
        log.trace("这是一条【trace】级别的日志");
        log.debug("这是一条【debug】级别的日志");
        log.info("这是一条【info】级别的日志");
        log.warn("这是一条【warn】级别的日志");
        log.error("这是一条【error】级别的日志");
    }

    @Autowired
    DataSource dataSource;
    @Test
    void testDataSource() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
