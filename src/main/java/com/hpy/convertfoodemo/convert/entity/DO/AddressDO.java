package com.hpy.convertfoodemo.convert.entity.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author A51398
 */
@Data
@TableName(value = "address")
public class AddressDO {
    private Integer id;
    private String address;
    private String name;
}
