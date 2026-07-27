package cn.hqu.csmall.marketing.pojo.vo;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ActivityListItemVO implements Serializable {
    private Long id;
    private String title;
    private Integer type;
    private String discountRule;
    private Integer status;
    private Integer sort;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
