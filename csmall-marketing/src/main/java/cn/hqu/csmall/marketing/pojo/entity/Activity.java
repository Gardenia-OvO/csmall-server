package cn.hqu.csmall.marketing.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("promotion_activity")
public class Activity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Integer type;
    private String discountRule;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private Integer sort;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
