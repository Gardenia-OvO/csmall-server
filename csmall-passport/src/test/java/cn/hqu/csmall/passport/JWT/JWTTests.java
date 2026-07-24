package cn.hqu.csmall.passport.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTests {
    String secretKey = "sadsadvojsnanjdsoikppsalcmoaskmqfwqsdkzmalvkspncdksakbfaisbadscwcxzaldwvdcsnas";
    //生成JWT
    @Test
    public void testGenerate(){
        //准备playload数据
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",9527);
        claims.put("username","zhangsan");
        String jwt = Jwts.builder()
                //设置Header头
                .setHeaderParam("alg","HS256")
                .setHeaderParam("typ","JWT")
                //设置Payload数据
                .addClaims(claims)
                //设置有效期
                .setExpiration((new Date(System.currentTimeMillis()+1000*60*3)))
                //Signature Verification签名
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        System.out.println(jwt);
    }
    //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTUyNywidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.bCQuAAQo0GoVyxHiLcg3tCk2UYl1l0_DtBM-4GX4300

    //产生JWT
    @Test
    public void testParse(){
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTUyNywidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.bCQuAAQo0GoVyxHiLcg3tCk2UYl1l0_DtBM-4GX4300";
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
        Object id = claims.get("id");
        Object username = claims.get("username");
        System.out.println("id:"+id);
        System.out.println("username:"+username);
    }
}
