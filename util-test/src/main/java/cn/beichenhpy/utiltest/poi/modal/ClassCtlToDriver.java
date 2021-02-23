package cn.beichenhpy.utiltest.poi.modal;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 倒数第四层 班组与司机 一对多
 * @since 2021/2/23 14:30
 */
@Data
public class ClassCtlToDriver {
    @Excel(name = "班组",width = 15,needMerge = true)
    private String classCtl;
    @ExcelCollection(name = "司机")
    private List<DriverToSurface> driverToSurfaceList;
}
