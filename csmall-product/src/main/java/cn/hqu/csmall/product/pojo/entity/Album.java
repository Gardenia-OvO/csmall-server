package cn.hqu.csmall.product.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("pms_album")
public class Album implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    @TableField("gmt_Create")
    private LocalDateTime gmtCreated;
    @TableField("gmt_Modified")
    private LocalDateTime gmtModified;
    private Integer sort;
}
