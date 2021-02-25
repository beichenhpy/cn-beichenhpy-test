package cn.beichenhpy.mybatisplusdemo.service.impl;

import cn.beichenhpy.mybatisplusdemo.mapper.AddressMapper;
import cn.beichenhpy.mybatisplusdemo.mapper.PersonMapper;
import cn.beichenhpy.mybatisplusdemo.mapper.TimeMapper;
import cn.beichenhpy.mybatisplusdemo.modal.Person;
import cn.beichenhpy.mybatisplusdemo.modal.TimeTest;
import cn.beichenhpy.mybatisplusdemo.service.PersonService;
import cn.beichenhpy.mybatisplusdemo.service.TimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author A51398
 */
@Slf4j
@Service
public class TimeServiceImpl extends ServiceImpl<TimeMapper, TimeTest> implements TimeService{

}
