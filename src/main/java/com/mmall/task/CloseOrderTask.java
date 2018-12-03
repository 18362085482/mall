package com.mmall.task;

import com.mmall.common.Const;
import com.mmall.common.RedissonManager;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOderService;

    @Autowired
    private RedissonManager redissonManager;

    private static String LOCK_NAME = Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK;

//    @Scheduled(cron = "0 */1 * * * ?")//每1分钟
    public void closeOrderTaskV1(){
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        iOderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")//每1分钟
    public void closeOrderTaskV2(){
        log.info("关闭订单定时任务启动");
        long lockTimout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout","5000"));

        Long setnxResult = RedisShardedPoolUtil.setnx(LOCK_NAME,String.valueOf(System.currentTimeMillis()+lockTimout));
        if(setnxResult != null && setnxResult.intValue() == 1){
            //如果返回值是1，设置成功，获取锁
            closeOrder(LOCK_NAME);
        }else{
            log.info("没有获得分布式锁：{}",LOCK_NAME);
        }
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")//每1分钟
    public void closeOrderTaskV3(){
        log.info("关闭订单定时任务启动");
        long lockTimout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout","5000"));

        Long setnxResult = RedisShardedPoolUtil.setnx(LOCK_NAME,String.valueOf(System.currentTimeMillis()+lockTimout));
        if(setnxResult != null && setnxResult.intValue() == 1){
            //如果返回值是1，设置成功，获取锁
            closeOrder(LOCK_NAME);
        }else{
            //未获取到锁继续判断，判断时间戳，看是否可以充值并获取到锁
            String lockValueStr = RedisShardedPoolUtil.get(LOCK_NAME);
            if(lockValueStr !=null && System.currentTimeMillis()>Long.parseLong(lockValueStr)){//锁已过期
                String getSetResult = RedisShardedPoolUtil.getSet(LOCK_NAME,String.valueOf(System.currentTimeMillis()+lockTimout));
                //再次用当前时间戳getset。
                //旧值判断，是否可以获取所
                //当key没有旧值时，返回nil->获取锁
                //智力set了一个新的value值，获取旧的值
                if(getSetResult == null || (getSetResult!=null && StringUtils.equals(getSetResult,lockValueStr))){
                    closeOrder(LOCK_NAME);
                }else {
                    log.info("没有获得分布式锁：{}",LOCK_NAME);

                }
            }else {
                log.info("没有获得分布式锁：{}",LOCK_NAME);

            }
        }
        log.info("关闭订单定时任务结束");
    }

//    @Scheduled(cron = "0 */1 * * * ?")//每1分钟
    public void closeOrderTaskV4(){
        log.info("关闭订单定时任务启动");
        RLock lock = redissonManager.getRedisson().getLock(LOCK_NAME);
        boolean getLock = false;
        try {
            if(getLock = lock.tryLock(0,5,TimeUnit.SECONDS)){
                log.info("Redisson获取分布式锁：{},ThreadName{}",LOCK_NAME,Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
                iOderService.closeOrder(hour);
            }else {
                log.info("Redisson没有获取分布式锁：{},ThreadName{}",LOCK_NAME,Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson分布式锁获取失败",e);
        } finally {
            if(!getLock){
                return;
            }
            lock.unlock();
            log.info("Redisson分布式锁释放锁");
        }
    }

    private void closeOrder(String lockName){
        RedisShardedPoolUtil.expire(lockName,5);
        log.info("获取{},ThreadName:{}",LOCK_NAME,Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour","2"));
        iOderService.closeOrder(hour);
        RedisShardedPoolUtil.del(LOCK_NAME);
        log.info("释放{},ThreadName:{}",LOCK_NAME,Thread.currentThread().getName());
        log.info("===========================");
    }


}
