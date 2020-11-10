package com.hpy.convertfoodemo.convert.entity.VO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 一般为页面的名+VO
 * 当前端需求一个请求获取很多对象时比较慢时，可以启用
 * @author A51398
 */
@Data
@Accessors(chain = true)
public class PersonVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Date birthday;
    private String sex;
}
