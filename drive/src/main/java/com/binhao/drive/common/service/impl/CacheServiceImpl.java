package com.binhao.drive.common.service.impl;


import com.binhao.drive.common.bo.CacheBean;
import com.binhao.drive.common.bo.SessionUser;
import com.binhao.drive.common.service.CacheService;
import com.binhao.drive.common.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    private Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);
    private static Map<String, CacheBean> mapCache = new HashMap(4);
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    protected HttpServletRequest request;
    @Value("${spring.redis.start:false}")
    private boolean startRedis = false;
    public static final String AUTH_TOKEN = "authToken";
    private static Long TIMEOUT_TEN_MINUTES = 6000L;
    @Value("${spring.redis.timeout:86400}")
    private long timeout = 86400L;

    public CacheServiceImpl() {
    }

    public String getAuthToken() {
        String authToken = null;
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            authToken = this.request.getHeader("authToken");
        }

        return authToken;
    }

    public void setSessionCache(String authCode, String key, Object value) {
        if (authCode != null && !"".equals(authCode)) {
            this.setObj(authCode + "_" + key, value, this.timeout);
        } else {
            this.request.getSession().setAttribute(key, value);
        }

    }

    public void cleanSessionCache(String authToken, String key) {
        this.deleteKey(authToken + "_" + key);
    }

    public Object getSessionCache(String authCode, String key) {
        return authCode != null && !"".equals(authCode) ? this.getObj(authCode + "_" + key) : this.request.getSession().getAttribute(key);
    }

    private boolean isStartRedis() {
        if (this.startRedis) {
            try {
                this.redisTemplate.getConnectionFactory().getConnection();
            } catch (Exception var2) {
                this.startRedis = false;
                this.log.error("获得redis连接失败,请确认是否已打开redis", var2);
            }
        }

        return this.startRedis;
    }

    public void deleteKey(String key) {
        if (this.isStartRedis()) {
            this.redisTemplate.delete(key);
        } else {
            mapCache.remove(key);
        }

    }

    public void setObj(String key, Object obj, Long seconds) {
        if (this.isStartRedis()) {
            // 1.序列化 2.转json格式 再利用String方式储存
            byte[] serialize = SerializeUtil.serialize(obj);
            byte[] bytes = key.getBytes();
            if (seconds != null) {
                this.redisTemplate.opsForValue().set(bytes, serialize, seconds, TimeUnit.SECONDS);
            } else {
                this.redisTemplate.opsForValue().set(bytes, serialize);
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            CacheBean cacheBean = new CacheBean();
            cacheBean.setCreate(calendar.getTimeInMillis());
            cacheBean.setFlag(true);
            cacheBean.setData(obj);
            if (seconds != null) {
                cacheBean.setAutoUpdate(true);
                cacheBean.setValidTimeSecond(seconds);
                calendar.add(13, new Integer(seconds + ""));
                cacheBean.setTimeout(calendar.getTimeInMillis());
            } else {
                cacheBean.setAutoUpdate(false);
            }

            mapCache.put(key, cacheBean);
        }

    }

    public void setObj(String key, Object obj) {
        this.setObj(key, obj, (Long)null);
    }

    public void setObjTenMinutes(String key, Object obj) {
        this.setObj(key, obj, TIMEOUT_TEN_MINUTES);
    }

    public Object getObj(String key) {
        if (key != null && !"".equals(key)) {
            if (this.isStartRedis()) {
                //反序列化SessionUser对象
                Object obj = SerializeUtil.unSerialize((byte[]) this.redisTemplate.opsForValue().get(key.getBytes()));
                return obj;
            } else {
                CacheBean cacheBean = (CacheBean)mapCache.get(key);
                if (cacheBean != null) {
                    Calendar calendar = Calendar.getInstance();
                    if (cacheBean.getTimeout() != null && cacheBean.getTimeout() < calendar.getTimeInMillis()) {
                        mapCache.remove(key);
                        return null;
                    } else {
                        if (cacheBean.isAutoUpdate() && cacheBean.getValidTimeSecond() != null) {
                            calendar.add(13, new Integer(cacheBean.getValidTimeSecond() + ""));
                            cacheBean.setTimeout(calendar.getTimeInMillis());
                            //是否自动更新失效时间   后重新设置失效时间
                            mapCache.put(key, cacheBean);
                        }

                        return cacheBean.getData();
                    }
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public void cleanTimeout() {
        List<String> timeoutKey = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        Iterator var3 = mapCache.entrySet().iterator();

        while(true) {
            while(var3.hasNext()) {
                Entry<String, CacheBean> entry = (Entry)var3.next();
                if (((CacheBean)entry.getValue()).getTimeout() != null && ((CacheBean)entry.getValue()).getTimeout() < calendar.getTimeInMillis()) {
                    timeoutKey.add(entry.getKey());
                } else if (!((CacheBean)entry.getValue()).isFlag()) {
                    timeoutKey.add(entry.getKey());
                }
            }

            var3 = timeoutKey.iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                mapCache.remove(key);
            }

            return;
        }
    }


    /**
     * @Author zengbh
     * @Description //TODO 单点登录  清理之前登录的缓存  本地缓存版
     * @Date 8:52
     * @Param [userAccount]
     * @return void
     **/
    public void singleSign(String userid){
        //多余的SessionUser
        List<String> unnecessary = new ArrayList();
        if(this.isStartRedis()){
            /* 本系统规模小不使用redis 但思路一定要有
             * @Author zengbh
             * @Description //TODO 思路：通过redis使用String（authToke+唯一性账号）,String（Json形式）的存储方法通过将以 唯一性账号为模糊查询到已登录的对象进行清除即可
             * @Date 12:13
             * @Param [userid]
             * @return void
             **/
//            Set<Object> keys = redisTemplate.keys("*account");

            //TODO 思路2：由于账号是唯一性标识 可直接固定存储  直接顶替原来的k-v键值对 形成单点登录
//            redisTemplate.opsForValue().set("账号+标识","Java转Json对象",30,TimeUnit.MINUTES);

        }else {
            /**
             * @Author zengbh
             * @Description //TODO 思路：服务器本地缓存hashMap转set集合  直接筛选判断userid若存在  即可把筛选列表循环弄掉
             * @Date 16:29 
             * @Param [userid]
             * @return void
             **/
            //创建迭代器 map集合转化为Map.Entry对象集合
            Iterator<Entry<String, CacheBean>> iterator = mapCache.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, CacheBean> next = iterator.next();
                SessionUser sessionUser = (SessionUser) next.getValue().getData();
                //把已登录的筛选 放到集合中
                if (sessionUser.getUserId().equals(userid)) {
                    unnecessary.add(next.getKey());
                }
            }
            //进行多余迭代SessionUser清除
            Iterator<String> iterator1 = unnecessary.iterator();
            while (iterator1.hasNext()) {
                mapCache.remove(iterator1.next());
            }
        }
        return;
    }

    public void setTimeOut(String key) {
        this.deleteKey(key);
    }
}
