package cn.hqu.csmall.product.mapper;


import cn.hqu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AlbumMapperTest {
    @Autowired
    private AlbumMapper albumMapper;

    @Test
    void testInsertAlbum(){
        Album album = new Album();
        album.setId(1L);
        album.setName("测试相册01");
        album.setDescription("测试相册01的描述");
        album.setSort(1);
        album.setGmtCreate(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());
        System.out.println("插入之前：ID="+album.getId());
        int rows = albumMapper.insert(album);
        System.out.println("插入之后：ID="+album.getId());
        System.out.println("受影响的行数rows = " + rows);
    }
}
