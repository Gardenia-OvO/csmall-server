package cn.hqu.csmall.product.pojo.entity;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Album implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Integer sort;
}
