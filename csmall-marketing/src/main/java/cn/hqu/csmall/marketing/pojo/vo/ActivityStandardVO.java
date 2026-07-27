package cn.hqu.csmall.marketing.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data @Accessors(chain = true)
public class ActivityStandardVO implements Serializable {
    private Long id; private String title; private Integer type;
    private String discountRule; private Integer status; private Integer sort;
    private LocalDateTime startTime; private LocalDateTime endTime;
}
