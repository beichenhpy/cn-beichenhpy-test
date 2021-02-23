package cn.beichenhpy.utiltest.poi.modal;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 倒数第三层 司机与工作面 一对多
 * @since 2021/2/23 14:30
 */
@Data
public class DriverToSurface {
    private Integer id;
    @Excel(name = "司机",width = 15,needMerge = true)
    private String driver;
    @ExcelCollection(name = "工作面")
    private List<SurfaceToWeight> surfaceToWeightList;
}
