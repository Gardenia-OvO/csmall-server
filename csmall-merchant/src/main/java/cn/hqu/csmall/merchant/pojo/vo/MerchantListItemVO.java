package cn.hqu.csmall.merchant.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantListItemVO implements Serializable {
    private Long id;
    private String name;
    private String contactPerson;
    private String phone;
    private Integer status;
    private Integer sort;
    private Integer sales;
}
