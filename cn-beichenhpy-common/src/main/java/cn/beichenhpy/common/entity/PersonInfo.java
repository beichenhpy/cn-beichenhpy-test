package cn.beichenhpy.common.entity;

import cn.beichenhpy.common.anno.Section;
import lombok.Data;

/**
 * @author A51398
 * 实体类定义为业务模型
 */
@Data
public class PersonInfo {
    private String name;
    private Integer id;
    private Integer sex;
    private String address;
    /**
     * 测试section注入值
     */
    @Section
    private String section;
}
