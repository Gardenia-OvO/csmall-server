package cn.hqu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌与类别的关联实体
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@TableName("pms_brand_category")
public class BrandCategory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long brandId;
    private Long categoryId;
}
