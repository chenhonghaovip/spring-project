package com.honghao.cloud.userapi.dto.easypoi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenhonghao
 * @date 2019-11-25 13:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaybillBcListEasyPoi {
    @Excel(name = "运单主键")
    private String wId;

    @Excel(name = "批次号")
    private String batchId;

    @Excel(name = "商户流水号")
    private String serialNumber;

    @Excel(name = "用户主键")
    private String userId;

    @Excel(name = "运单号")
    private String shipId;

    @Excel(name = "寄件姓名")
    private String sendName;

    @Excel(name = "寄件手机")
    private String sendPhone;

    @Excel(name = "寄件省")
    private String sendProvince;

    @Excel(name = "寄件市")
    private String sendCity;

    @Excel(name = "寄件区")
    private String sendCounty;

    @Excel(name = "寄件adcode")
    private String sendAdcode;

    @Excel(name = "寄件地址")
    private String sendAddress;

    @Excel(name = "发货人经度")
    private BigDecimal sendLongitude;

    @Excel(name = "发货人纬度")
    private BigDecimal sendLatitude;

    @Excel(name = "收件姓名")
    private String receiveName;

    @Excel(name = "收件手机")
    private String receivePhone;

    @Excel(name = "收件省")
    private String receiveProvince;

    @Excel(name = "收件区")
    private String receiveCounty;

    @Excel(name = "收件市")
    private String receiveCity;

    @Excel(name = "收件adcode")
    private String receiveAdcode;

    @Excel(name = "收件地址")
    private String receiveAddress;

    @Excel(name = "收货人经度")
    private BigDecimal receiveLongitude;

    @Excel(name = "收货人纬度")
    private BigDecimal receiveLatitude;

    @Excel(name = "是否预约")
    private Integer isTimely;

    @Excel(name = "预约开始时间")
    private Date etStartTime;

    @Excel(name = "预约结束时间")
    private Date etArriveTime;

    @Excel(name = "物品重量")
    private BigDecimal cargoWeight;

    @Excel(name = "物品价格")
    private BigDecimal cargoAmount;

    @Excel(name = "商品类型")
    private String cargoType;

    @Excel(name = "订单状态")
    private Integer orderStatus;

    @Excel(name = "支付方式")
    private Integer orderPayment;

    @Excel(name = "订单支付")
    private Integer orderPayStatus;

    @Excel(name = "配送费")
    private BigDecimal deliveryFee;

    @Excel(name = "订单小计")
    private BigDecimal totalPrices;

    @Excel(name = "骑士预计收入")
    private BigDecimal expectPrices;

    @Excel(name = "备注")
    private String orderRemark;

    @Excel(name = "订单来源")
    private Integer orderSource;

    @Excel(name = "订单类型")
    private Integer orderType;

    @Excel(name = "分单方式")
    private Integer singleWay;

    @Excel(name = "配送模式")
    private Integer deliveryMode;

    @Excel(name = "下单时间")
    private Date orderTime;

    @Excel(name = "是否超距")
    private Integer isOverdistance;

    @Excel(name = "是否超时")
    private Integer isOvertime;


    public static boolean isBatchId(WaybillBcListEasyPoi waybillBcListEasyPoi){
        return "123".equals(waybillBcListEasyPoi.getBatchId());
    }
}
