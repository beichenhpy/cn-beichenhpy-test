package com.hpy.demo.dbTest.entity.DTO;

import lombok.Data;

/**
 * @author A51398
 * 接收左连接查询的信息
 */
@Data
public class PersonInfoDTO {
    private String name;
    private Integer id;
    private Integer sex;
    private String address;
}
