package cn.hqu.csmall.product.mapper;

import cn.hqu.csmall.product.pojo.vo.AlbumListItemVO;
import cn.hqu.csmall.product.pojo.vo.PageData;
import cn.hqu.csmall.product.util.PageInfoToPageDataConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PageHelperTest {
    @Autowired
    private AlbumMapper albumMapper;
    @Test
    void list(){
        //页码，从1开始计数
        int pageNum = 1;
        //每页显示的条数,每页查多少条数据
        int pageSize = 5;
        //执行分页查询，传入页码和条数
        //这里注意，以下两句代码中间不要插入别的有效代码，特别是if分支语句，否则会出现线程安全问题
        PageHelper.startPage(pageNum,pageSize);
        List<AlbumListItemVO> list = albumMapper.list();
        //将查询结果封装到PageInfo对象中，此对象中包括大量分页查询时需的参数
        PageInfo<AlbumListItemVO> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        System.out.println("-------------------");
        PageData<AlbumListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        System.out.println(pageData);
    }
}
