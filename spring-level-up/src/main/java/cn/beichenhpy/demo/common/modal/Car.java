package cn.beichenhpy.demo.common.modal;

import java.io.Serializable;
/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Car description：
 * @since 2021/5/7 10:26 上午
 */
public class Car implements Serializable {
    private String type;
    private Integer price;
    public Car() {
    }

    public Car(String type,Integer price) {
        this.type = type;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
