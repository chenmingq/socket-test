package com.github.chenmingq.server.system.udp.proto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author : chengmingqin
 * date : 2020/7/4
 * description :
 */

@Getter
@Setter
public class ReqGetMoney extends BaseProto{

    /**
     * 代码
     */
    private String code;

    /**
     * 实时价格
     */
    private BigDecimal money;

    /**
     * 当前时间
     */
    private long time;

    @Override
    public String toString() {
        return "ReqGetMoney{" +
                "code='" + code + '\'' +
                ", money=" + money +
                ", time=" + time +
                '}';
    }

    @Override
    public <T> T deCoder(Class<? extends T> clazz, byte[] proto) {
        return super.deCoder(clazz, proto);
    }

    @Override
    public byte[] enCoder(BaseProto proto) {
        return super.enCoder(proto);
    }
}
