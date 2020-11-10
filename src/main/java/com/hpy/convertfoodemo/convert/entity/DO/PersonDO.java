package com.hpy.convertfoodemo.convert.entity.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author A51398
 * 个人实体类
 */
@Data
@TableName(value = "person")
@Accessors(chain = true)
public class PersonDO {
    private String name;
    private Date birthday;
    private Integer sex;
    private String password;
}
