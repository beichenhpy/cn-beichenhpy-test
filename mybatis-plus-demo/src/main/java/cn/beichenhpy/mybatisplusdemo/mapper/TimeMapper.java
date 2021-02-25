package cn.beichenhpy.mybatisplusdemo.mapper;

import cn.beichenhpy.mybatisplusdemo.modal.Address;
import cn.beichenhpy.mybatisplusdemo.modal.TimeTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author A51398
 */
@Mapper
public interface TimeMapper extends BaseMapper<TimeTest> {

}
