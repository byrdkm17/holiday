package cc.aliza.production.holiday.job;

import cc.aliza.production.holiday.dao.SmsDao;
import cc.aliza.production.holiday.entity.Sms;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by byrdkm17@gmail.com on 2014-03-30.
 * <p/>
 * 那一夜，我听了一宿梵唱，不为参悟，只为寻你的一丝气息。
 * 那一月，我转过所有经轮，不为超度，只为触摸你的指纹。
 * 那一年，我磕长头拥抱尘埃，不为朝佛，只为贴着了你的温暖。
 * 那一世，我翻遍十万大山，不为修来世，只为佑你平安喜乐。
 */
public class SmsJob implements Job {

    Client client = new Client();

    private static Boolean running = Boolean.FALSE;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        synchronized (SmsJob.class) {
            if (running) {
                System.out.println("正在发送短信，略过。");
                return;
            }
            running = Boolean.TRUE;
        }
        List<Sms> smsList = SmsDao.dao.query().is("status", 0).results();

        for (Sms sms : smsList) {
            System.out.print("Send to " + sms.getMobile() + " : " + sms.getContent());
            String result_mt = client.mt(sms.getMobile(), sms.getContent(), StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
            if (result_mt.startsWith("-") || result_mt.equals("")) {
                SmsDao.dao.set(sms, "sendTag", result_mt);
                SmsDao.dao.set(sms, "status", 2);
                System.out.println("发送失败！返回值为 : " + result_mt);
            } else {
                SmsDao.dao.set(sms, "sendTag", result_mt);
                SmsDao.dao.set(sms, "status", 1);
                System.out.println("发送成功，返回值为 : " + result_mt);
            }
        }
        synchronized (SmsJob.class) {
            running = Boolean.FALSE;
        }
    }
}
