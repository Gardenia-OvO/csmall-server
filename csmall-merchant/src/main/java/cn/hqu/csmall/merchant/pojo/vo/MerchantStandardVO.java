package cn.hqu.csmall.merchant.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MerchantStandardVO implements Serializable {
    private Long id;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;
    private String logo;
    private String description;
    private Integer status;
    private Integer sort;
    private Integer sales;
}
