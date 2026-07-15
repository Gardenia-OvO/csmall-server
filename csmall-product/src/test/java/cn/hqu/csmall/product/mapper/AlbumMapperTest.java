package cn.hqu.csmall.product.mapper;


import cn.hqu.csmall.product.pojo.entity.Album;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AlbumMapperTest {
    @Autowired
    private AlbumMapper albumMapper;

    @Test
    void testInsertAlbum() {
        Album album = new Album();
        album.setId(1L);
        album.setName("测试相册01");
        album.setDescription("测试相册01的描述");
        album.setSort(1);
        album.setGmtCreated(LocalDateTime.now());
        album.setGmtModified(LocalDateTime.now());
        System.out.println("插入之前：ID=" + album.getId());
        int rows = albumMapper.insert(album);
        System.out.println("插入之后：ID=" + album.getId());
        System.out.println("受影响的行数rows = " + rows);
    }

    @Test
    void testDeleteById() {
        int rows = albumMapper.deleteById(1L);
        System.out.println("受影响的行数rows = " + rows);
    }

    @Test
    void testUpdateAlbum() {
        Album album = new Album();
        album.setId(2L);
        album.setName("测试相册02");
        album.setDescription("测试相册02的描述");
        album.setSort(1);
        album.setGmtModified(LocalDateTime.now());
        int rows = albumMapper.updateById(album);
        System.out.println("受影响的行数：" + rows);
    }

    @Test
    void testSelectById() {
        Album album = albumMapper.selectById(3L);
        System.out.println(album);
    }

    @Test
    void testDeleteBatchIds() {
        int rows = albumMapper.deleteBatchIds(Arrays.asList(2L, 3L, 4L));
        System.out.println("受影响的行数：" + rows);
    }

    @Test
    void testSelectByIds() {
        List<Album> list = albumMapper.selectBatchIds(Arrays.asList(5L, 6L, 7L));
        System.out.println(list);
    }
}
