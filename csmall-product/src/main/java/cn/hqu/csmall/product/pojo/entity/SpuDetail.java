package cn.hqu.csmall.product.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("pms_spu_detail")
public class SpuDetail implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long spuId;
    private String detail;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
