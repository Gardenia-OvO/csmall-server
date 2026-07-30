package cn.hqu.csmall.passport.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ams_permission")
public class Permission implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String value;
    private String description;
    private Integer sort;
}
