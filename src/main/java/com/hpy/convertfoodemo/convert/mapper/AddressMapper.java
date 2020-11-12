package com.hpy.convertfoodemo.convert.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hpy.convertfoodemo.convert.entity.DO.AddressDO;
import com.hpy.convertfoodemo.convert.entity.DO.PersonDO;
import com.hpy.convertfoodemo.convert.entity.DTO.PersonInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author A51398
 */
@Repository
public interface AddressMapper extends BaseMapper<AddressDO> {

}
