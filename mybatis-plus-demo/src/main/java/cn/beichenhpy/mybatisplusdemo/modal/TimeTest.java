package cn.beichenhpy.mybatisplusdemo.modal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@TableName(value = "test_time")
public class TimeTest {
    @TableId(value = "id")
    private String id;

    /**
     * JsonFormat可以格式化
     * DateTimeFormat不能格式化
     */
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm")
    private LocalTime startTime;
}
