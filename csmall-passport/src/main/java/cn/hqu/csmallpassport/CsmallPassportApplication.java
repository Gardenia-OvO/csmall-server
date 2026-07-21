package cn.hqu.csmallpassport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.testng.annotations.Test;


import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class CsmallPassportApplication {
    @Autowired
    private DataSource source;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(source.getConnection());
    }

    public static void main(String[] args) {
        SpringApplication.run(CsmallPassportApplication.class, args);
    }

}
