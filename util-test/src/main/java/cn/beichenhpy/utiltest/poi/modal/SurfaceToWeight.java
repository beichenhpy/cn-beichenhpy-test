package cn.beichenhpy.utiltest.poi.modal;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 倒数第二层 工作面与重量信息 1对多
 * @since 2021/2/23 14:29
 */
@Data
public class SurfaceToWeight {

    private Integer id;
    @Excel(name = "工作面",width = 15,needMerge = true)
    private String surface;
    @ExcelCollection(name = "信息")
    private List<WeightInfo> weightInfoList;
}
