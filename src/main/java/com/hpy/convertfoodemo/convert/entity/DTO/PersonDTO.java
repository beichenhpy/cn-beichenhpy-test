package com.hpy.convertfoodemo.convert.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据传输用的实体。可以传输Controller与service之间需要的变量
 * 或者明确知道前端需要什么则使用DTO进行传输
 * @author A51398
 */
@Data
@Accessors(chain = true)
public class PersonDTO {
    private String name;
    private Integer sex;
}
