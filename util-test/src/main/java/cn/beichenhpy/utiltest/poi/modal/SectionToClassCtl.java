package cn.beichenhpy.utiltest.poi.modal;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 顶层 工段对班组 一对多
 * @since 2021/2/23 14:32
 */
@Data
public class SectionToClassCtl {
    @Excel(name = "工段",width = 15,needMerge = true)
    private String section;
    @ExcelCollection(name = "班组")
    private List<ClassCtlToDriver> classCtlToDriverList;
}
