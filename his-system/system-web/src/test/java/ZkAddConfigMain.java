import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author 29794
 * @date 11/30/2021 20:59
 * 在Zookeeper中添加关于元数据的配置
 */
public class ZkAddConfigMain {

    public static void main(String[] args) {
        try {
            CuratorFramework zkClient = CuratorFrameworkFactory.builder().
                    connectString("113.31.118.68:2181").
                    retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
            zkClient.start();

            if (zkClient.checkExists().forPath("/dubbo/config/dubbo/dubbo.properties") == null) {
                zkClient.create().creatingParentsIfNeeded().forPath("/dubbo/config/dubbo/dubbo.properties");
            }
            zkClient.setData().forPath("/dubbo/config/dubbo/dubbo.properties", ("dubbo.registry.address=zookeeper://113.31.118.68:2181\n" +
                    "dubbo.metadata-report.address=zookeeper://113.31.118.68:2181").getBytes());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}