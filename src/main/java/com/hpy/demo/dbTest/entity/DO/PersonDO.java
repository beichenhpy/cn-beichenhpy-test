package com.hpy.demo.dbTest.entity.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author A51398
 * 个人实体类
 */
@Data
@TableName(value = "person")
@Accessors(chain = true)
public class PersonDO {

    private Integer id;
    private String name;
    private Integer sex;
}
