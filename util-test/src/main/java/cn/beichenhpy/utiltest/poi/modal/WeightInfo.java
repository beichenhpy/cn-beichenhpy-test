package cn.beichenhpy.utiltest.poi.modal;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 最底层实体
 * @since 2021/2/23 14:27
 */
@Data
public class WeightInfo {
    @Excel(name = "载重",width = 15)
    private Integer weight;
    @Excel(name = "产量",width = 15)
    private Integer production;
    @Excel(name = "趟次",width = 15)
    private Integer carTimes;
    @Excel(name = "单价",width = 15)
    private Double price;
    @Excel(name = "总价",width = 15)
    private Double totalPrice;
}
