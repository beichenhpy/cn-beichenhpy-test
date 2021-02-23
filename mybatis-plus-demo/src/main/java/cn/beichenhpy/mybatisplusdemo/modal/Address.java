package cn.beichenhpy.mybatisplusdemo.modal;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 实体类定义为数据库结构模型
 * @author A51398
 */
@Data
@TableName(value = "address")
public class Address {
    private Integer id;
    private String address;
    private String name;
}
