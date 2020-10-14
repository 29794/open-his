package xyz.ly11.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author by 29794
 * @date 2020/10/9 1:27
 */
public class IdGeneratorSnowflake {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private static Snowflake snowflake;

    static {
        long workId = 0;
        try {
            workId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的工作ID为:" + workId);
            long dataCenterId = 1;
            snowflake=IdUtil.createSnowflake(workId, dataCenterId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("当前机器的workId获取失败", e);
            workId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    /**
     * 生成ID
     */
    public static synchronized Long snowflakeId() {
        return snowflake.nextId();
    }

    /**
     * 根据前缀前成ID
     */
    public static String generatorIdWithPrefix(String prefix) {
        return prefix + snowflakeId();
    }

}
