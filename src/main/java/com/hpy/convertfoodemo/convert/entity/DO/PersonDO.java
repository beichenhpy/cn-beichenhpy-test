package com.hpy.convertfoodemo.convert.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
