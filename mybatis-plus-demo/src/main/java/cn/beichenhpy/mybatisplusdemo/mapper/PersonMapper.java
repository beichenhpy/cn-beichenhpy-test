package cn.beichenhpy.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.beichenhpy.mybatisplusdemo.modal.Person;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author A51398
 */
@Mapper
public interface PersonMapper extends BaseMapper<Person> {

}
