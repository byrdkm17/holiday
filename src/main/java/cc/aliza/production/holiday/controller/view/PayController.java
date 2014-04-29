package cc.aliza.production.holiday.controller.view;

import cc.aliza.production.holiday.dao.CartDao;
import cc.aliza.production.holiday.dao.GoodsDao;
import cc.aliza.production.holiday.dao.OrderDao;
import cc.aliza.production.holiday.entity.Cart;
import cc.aliza.production.holiday.entity.Order;
import cc.aliza.production.holiday.interceptor.view.DataInterceptor;
import cc.aliza.production.holiday.interceptor.view.LoginInterceptor;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.bugull.mongo.BuguMapper;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 14-2-8.
 */
@Before({LoginInterceptor.class, DataInterceptor.class})
public class PayController extends Controller {

    public void index() {

        String id = getPara(0);

        Order order = OrderDao.dao.findOne(id);

        if (order.getMember() == null) {
            OrderDao.dao.set(id, "member", getAttr("member"));
        } else {
            if (!order.getMember().equals(order.getMember())) {
                redirect("/");
                return;
            }
        }

        setAttr("order", order);
        render("/view/pay.html");
    }

    public void cart() {

        String id = getPara(0);

        Cart cart = CartDao.dao.findOne(id);
        BuguMapper.fetchCascade(cart, "orders");

        if (!cart.getMember().equals(cart.getMember())) {
            redirect("/");
            return;
        }

        setAttr("cart", cart);
        render("/view/payCart.html");
    }

    public void doPay() {
        Order order = OrderDao.dao.findOne(getPara(0));

        String payment_type = "1";
        String notify_url = "http://www.jiarifengguang.com/holiday/pay/pay_notify";
        String return_url = "http://www.jiarifengguang.com/holiday/pay/done/";
        String seller_email = "jiarifengguang@126.com";
        String out_trade_no = order.getId();
        String subject = order.getGoodsObject().getName();
        String price = String.valueOf(order.getPayPrice() / 100);
        String quantity = "1";
        String logistics_fee = "0.00";
        String logistics_type = "EXPRESS";
        String logistics_payment = "SELLER_PAY";
        String body = order.getGoodsObject().getCaption();
        String show_url = "http://www.jiarifengguang.com/holiday/detail/" + order.getGoods().getId();
        String receive_name = "";
        String receive_address = "";
        String receive_zip = "";
        String receive_phone = "";
        String receive_mobile = "";

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("price", price);
        sParaTemp.put("quantity", quantity);
        sParaTemp.put("logistics_fee", logistics_fee);
        sParaTemp.put("logistics_type", logistics_type);
        sParaTemp.put("logistics_payment", logistics_payment);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("receive_name", receive_name);
        sParaTemp.put("receive_address", receive_address);
        sParaTemp.put("receive_zip", receive_zip);
        sParaTemp.put("receive_phone", receive_phone);
        sParaTemp.put("receive_mobile", receive_mobile);

        setAttr("sHtmlText", AlipaySubmit.buildRequest(sParaTemp, "get", "确认"));
        render("/view/doPay.html");
    }

    public void doPayCart() {
        Cart order = CartDao.dao.findOne(getPara(0));

        String payment_type = "1";
        String notify_url = "http://www.jiarifengguang.com/holiday/pay/payCart_notify";
        String return_url = "http://www.jiarifengguang.com/holiday/pay/cartDone/";
        String seller_email = "jiarifengguang@126.com";
        String out_trade_no = order.getId();
        String subject = "【假日风格】旅游产品";
        String price = String.valueOf(order.getPayPrice() / 100);
        String quantity = "1";
        String logistics_fee = "0.00";
        String logistics_type = "EXPRESS";
        String logistics_payment = "SELLER_PAY";
        String receive_name = "";
        String receive_address = "";
        String receive_zip = "";
        String receive_phone = "";
        String receive_mobile = "";

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("price", price);
        sParaTemp.put("quantity", quantity);
        sParaTemp.put("logistics_fee", logistics_fee);
        sParaTemp.put("logistics_type", logistics_type);
        sParaTemp.put("logistics_payment", logistics_payment);
        sParaTemp.put("body", "");
        sParaTemp.put("show_url", "");
        sParaTemp.put("receive_name", receive_name);
        sParaTemp.put("receive_address", receive_address);
        sParaTemp.put("receive_zip", receive_zip);
        sParaTemp.put("receive_phone", receive_phone);
        sParaTemp.put("receive_mobile", receive_mobile);

        setAttr("sHtmlText", AlipaySubmit.buildRequest(sParaTemp, "get", "确认"));
        render("/view/doPay.html");
    }

    public void done() {
        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = getParaMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(getPara("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号

            String trade_no = new String(getPara("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(getPara("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            if (AlipayNotify.verify(params)) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                Order order = OrderDao.dao.findOne(out_trade_no);

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    if (order.getStatus() == 1) {
                        OrderDao.dao.set(order, "status", 2);
                        GoodsDao.dao.inc(order.getGoods(), "sales", 1);

                        Map<String, String> trade = new HashMap<String, String>();
                        trade.put("trade_no", trade_no);
                        trade.put("trade_status", trade_status);
                        trade.put("time", String.valueOf(new Date().getTime()));
                        OrderDao.dao.push(order, "trades", trade);
                    }
                }

                //该页面可做页面美工编辑
                setAttr("order", order);
                render("/view/payDone.html");

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                //该页面可做页面美工编辑
                redirect("/user/order");
            }
        } catch (Exception e) {
            redirect("/user/order");
        }
    }

    public void cartDone() {
        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = getParaMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(getPara("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号

            String trade_no = new String(getPara("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(getPara("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            if (AlipayNotify.verify(params)) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                Cart order = CartDao.dao.findOne(out_trade_no);

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    if (order.getStatus() == 1) {
                        CartDao.dao.set(order, "status", 2);

                        for (Order o : order.getOrders()) {
                            OrderDao.dao.set(o, "status", 2);
                        }

                        Map<String, String> trade = new HashMap<String, String>();
                        trade.put("trade_no", trade_no);
                        trade.put("trade_status", trade_status);
                        trade.put("time", String.valueOf(new Date().getTime()));
                        CartDao.dao.push(order, "trades", trade);
                    }
                }

                //该页面可做页面美工编辑
                setAttr("order", order);
                render("/view/payDone.html");

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                //该页面可做页面美工编辑
                redirect("/user/order");
            }
        } catch (Exception e) {
            redirect("/user/order");
        }
    }

    @ClearInterceptor(ClearLayer.ALL)
    @Before(POST.class)
    public void pay_notify() {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();

            Map requestParams = getParaMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }


            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

            //商户订单号
            String out_trade_no = new String(getPara("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(getPara("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(getPara("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            if (AlipayNotify.verify(params)) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                Order order = OrderDao.dao.findOne(out_trade_no);

                Map<String, String> trade = new HashMap<String, String>();
                trade.put("trade_no", trade_no);
                trade.put("trade_status", trade_status);
                trade.put("time", String.valueOf(new Date().getTime()));

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (trade_status.equals("WAIT_BUYER_PAY")) {
                    //该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
                    trade.put("trade_status_message", "买家已在支付宝交易管理中产生了交易记录，但没有付款");

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
                    //该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
                    trade.put("trade_status_message", "买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货");
                    if (order.getStatus() == 1) {
                        OrderDao.dao.set(order, "status", 2);
                        GoodsDao.dao.inc(order.getGoods(), "sales", 1);
                    }

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")) {
                    //该判断表示卖家已经发了货，但买家还没有做确认收货的操作
                    trade.put("trade_status_message", "卖家已经发了货，但买家还没有做确认收货的操作");

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else if (trade_status.equals("TRADE_FINISHED")) {
                    //该判断表示买家已经确认收货，这笔交易完成
                    trade.put("trade_status_message", "买家已经确认收货，这笔交易完成");

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else {
                    renderText("success");    //请不要修改或删除
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                OrderDao.dao.push(order, "trades", trade);
                //////////////////////////////////////////////////////////////////////////////////////////
            } else {//验证失败
                renderText("fail");
            }
        } catch (Exception e) {
            renderText("fail");
        }
    }

    @ClearInterceptor(ClearLayer.ALL)
    @Before(POST.class)
    public void payCart_notify() {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();

            Map requestParams = getParaMap();
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }


            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

            //商户订单号
            String out_trade_no = new String(getPara("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(getPara("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(getPara("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            if (AlipayNotify.verify(params)) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                Cart order = CartDao.dao.findOne(out_trade_no);

                Map<String, String> trade = new HashMap<String, String>();
                trade.put("trade_no", trade_no);
                trade.put("trade_status", trade_status);
                trade.put("time", String.valueOf(new Date().getTime()));

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (trade_status.equals("WAIT_BUYER_PAY")) {
                    //该判断表示买家已在支付宝交易管理中产生了交易记录，但没有付款
                    trade.put("trade_status_message", "买家已在支付宝交易管理中产生了交易记录，但没有付款");

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
                    //该判断表示买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货
                    trade.put("trade_status_message", "买家已在支付宝交易管理中产生了交易记录且付款成功，但卖家没有发货");
                    if (order.getStatus() == 1) {
                        CartDao.dao.set(order, "status", 2);

                        for (Order o : order.getOrders()) {
                            OrderDao.dao.set(o, "status", 2);
                        }
                    }

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS")) {
                    //该判断表示卖家已经发了货，但买家还没有做确认收货的操作
                    trade.put("trade_status_message", "卖家已经发了货，但买家还没有做确认收货的操作");

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else if (trade_status.equals("TRADE_FINISHED")) {
                    //该判断表示买家已经确认收货，这笔交易完成
                    trade.put("trade_status_message", "买家已经确认收货，这笔交易完成");

                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    renderText("success");    //请不要修改或删除
                } else {
                    renderText("success");    //请不要修改或删除
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                CartDao.dao.push(order, "trades", trade);
                //////////////////////////////////////////////////////////////////////////////////////////
            } else {//验证失败
                renderText("fail");
            }
        } catch (Exception e) {
            renderText("fail");
        }
    }
}
