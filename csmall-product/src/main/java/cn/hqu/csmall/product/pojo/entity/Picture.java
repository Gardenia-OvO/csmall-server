package cn.hqu.csmall.product.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("pms_picture")
@Data
public class Picture implements Serializable {
    //配置主键生成的方式
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long albumId;
    private String url;
    private String description;
    private Integer width;
    private Integer height;
    private Integer isCover;
    private Integer sort;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
