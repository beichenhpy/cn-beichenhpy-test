package cn.beichenhpy.webfluxdemo.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Ticket description：车票实体类
 * @since 2021/6/19 14:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    private String fromAddress;
    private String toAddress;
    private BigDecimal price;
}
