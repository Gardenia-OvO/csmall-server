package cn.hqu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 类别与属性模板的关联实体
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Data
@TableName("pms_category_attribute_template")
public class CategoryAttributeTemplate implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private Long attributeTemplateId;
}
