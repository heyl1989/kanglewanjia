package cn.v1.kanglewanjia.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by qy on 2018/1/9.
 */

public class PayInfoData extends BaseData {


    /**
     * data : {"alipay":{"payTypeName":"支付宝","qrCode ":"https://qr.alipay.com/bax095470djn1hzzwkfw409d","tradeNo":"1"},"orderInfo":{"doctorName":"盖伟伟","doctorPic":"https://tva4.sinaimg.cn/crop.0.0.180.180.180/7b9aed1ajw1e8qgp5bmzyj2050050aa8.jpg","doctorPosition":1,"orderId":10005,"price":100,"ybPrice":0},"weixin":{"codeUrl":"二维码链接","payTypeName":"微信","tradeNo":"交易号"}}
     */

    @SerializedName("data")
    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * alipay : {"payTypeName":"支付宝","qrCode ":"https://qr.alipay.com/bax095470djn1hzzwkfw409d","tradeNo":"1"}
         * orderInfo : {"doctorName":"盖伟伟","doctorPic":"https://tva4.sinaimg.cn/crop.0.0.180.180.180/7b9aed1ajw1e8qgp5bmzyj2050050aa8.jpg","doctorPosition":1,"orderId":10005,"price":100,"ybPrice":0}
         * weixin : {"codeUrl":"二维码链接","payTypeName":"微信","tradeNo":"交易号"}
         */

        @SerializedName("alipay")
        private AlipayData alipay;
        @SerializedName("orderInfo")
        private OrderInfoData orderInfo;
        @SerializedName("weixin")
        private WeixinData weixin;

        public AlipayData getAlipay() {
            return alipay;
        }

        public void setAlipay(AlipayData alipay) {
            this.alipay = alipay;
        }

        public OrderInfoData getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoData orderInfo) {
            this.orderInfo = orderInfo;
        }

        public WeixinData getWeixin() {
            return weixin;
        }

        public void setWeixin(WeixinData weixin) {
            this.weixin = weixin;
        }

        public static class AlipayData {
            /**
             * payTypeName : 支付宝
             * qrCode  : https://qr.alipay.com/bax095470djn1hzzwkfw409d
             * tradeNo : 1
             */

            @SerializedName("payTypeName")
            private String payTypeName;
            @SerializedName("qrCode ")
            private String qrCode;
            @SerializedName("tradeNo")
            private String tradeNo;

            public String getPayTypeName() {
                return payTypeName;
            }

            public void setPayTypeName(String payTypeName) {
                this.payTypeName = payTypeName;
            }

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public String getTradeNo() {
                return tradeNo;
            }

            public void setTradeNo(String tradeNo) {
                this.tradeNo = tradeNo;
            }
        }

        public static class OrderInfoData {
            /**
             * doctorName : 盖伟伟
             * doctorPic : https://tva4.sinaimg.cn/crop.0.0.180.180.180/7b9aed1ajw1e8qgp5bmzyj2050050aa8.jpg
             * doctorPosition : 1
             * orderId : 10005
             * price : 100.0
             * ybPrice : 0.0
             */

            @SerializedName("doctorName")
            private String doctorName;
            @SerializedName("doctorPic")
            private String doctorPic;
            @SerializedName("doctorPosition")
            private String doctorPosition;
            @SerializedName("orderId")
            private int orderId;
            @SerializedName("price")
            private double price;
            @SerializedName("realPrice")
            private double realPrice;
            @SerializedName("ybPrice")
            private double ybPrice;

            public double getRealPrice() {
                return realPrice;
            }

            public void setRealPrice(double realPrice) {
                this.realPrice = realPrice;
            }

            public String getDoctorName() {
                return doctorName;
            }

            public void setDoctorName(String doctorName) {
                this.doctorName = doctorName;
            }

            public String getDoctorPic() {
                return doctorPic;
            }

            public void setDoctorPic(String doctorPic) {
                this.doctorPic = doctorPic;
            }

            public String getDoctorPosition() {
                return doctorPosition;
            }

            public void setDoctorPosition(String doctorPosition) {
                this.doctorPosition = doctorPosition;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getYbPrice() {
                return ybPrice;
            }

            public void setYbPrice(double ybPrice) {
                this.ybPrice = ybPrice;
            }
        }

        public static class WeixinData {
            /**
             * codeUrl : 二维码链接
             * payTypeName : 微信
             * tradeNo : 交易号
             */

            @SerializedName("codeUrl")
            private String codeUrl;
            @SerializedName("payTypeName")
            private String payTypeName;
            @SerializedName("tradeNo")
            private String tradeNo;

            public String getCodeUrl() {
                return codeUrl;
            }

            public void setCodeUrl(String codeUrl) {
                this.codeUrl = codeUrl;
            }

            public String getPayTypeName() {
                return payTypeName;
            }

            public void setPayTypeName(String payTypeName) {
                this.payTypeName = payTypeName;
            }

            public String getTradeNo() {
                return tradeNo;
            }

            public void setTradeNo(String tradeNo) {
                this.tradeNo = tradeNo;
            }
        }
    }
}
