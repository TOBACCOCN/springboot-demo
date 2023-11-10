package com.springboot.example.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZookeeperLockTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void create() throws Exception {
        String result = curatorFramework.create().forPath("/persistent", "foo".getBytes());
        log.debug(">>>>> RESULT: [{}]", result);
        // result = curatorFramework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/persistent_sequential", "bar".getBytes());
        // log.debug(">>>>> RESULT: [{}]", result);
        // result = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/ephemeral", "fooz".getBytes());
        // log.debug(">>>>> RESULT: [{}]", result);
        //  result = curatorFramework.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/ephemeral_sequential", "barz".getBytes());
        // log.debug(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void checkExists() throws Exception {
        // 节点不存在时 stat 为空
        Stat stat = curatorFramework.checkExists().forPath("/persistents");
        log.debug(">>>>> STAT: [{}]", stat);
    }

    @Test
    public void delete() throws Exception {
        curatorFramework.delete().forPath("/persistent");
        checkExists();
    }

    @Test
    public void deletingChildrenIfNeeded() throws Exception {
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/persistent");
        checkExists();
    }

    @Test
    public void getData() throws Exception {
        String result = new String(curatorFramework.getData().forPath("/persistent"));
        log.debug(">>>>> RESULT: [{}]", result);
        // result = Arrays.toString(curatorFramework.getData().forPath("/persistent_sequential"));
        // log.debug(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void getStat() throws Exception {
        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/persistent");
        log.debug(">>>>> STAT: [{}]", stat);
    }

    @Test
    public void setData() throws Exception {
        Stat stat = curatorFramework.setData().forPath("/persistent", "hello".getBytes());
        log.debug(">>>>> STAT: [{}]", stat);
    }

    // https://zhuanlan.zhihu.com/p/150127393
    private void reentrantAcquire(InterProcessMutex interProcessMutex, int count) throws Exception {
        if (count-- > 0) {
            boolean acquire = false;
            try {
                acquire = interProcessMutex.acquire(1, TimeUnit.SECONDS);
                log.debug(">>>>> zookeeper reentrant lock acquire: [{}]", acquire);
                reentrantAcquire(interProcessMutex, count);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (acquire) {
                    interProcessMutex.release();
                    log.debug(">>>>> zookeeper reentrant lock release");
                }
            }
        }
    }

    @Test
    public void reentrantAcquire() throws Exception {
        int count = 2;
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/zookeeper_lock");
        reentrantAcquire(interProcessMutex, count);
    }

    private void semaphoreAcquire(InterProcessSemaphoreMutex interProcessSemaphoreMutex, int count) throws Exception {
        if (count-- > 0) {
            boolean acquire = false;
            try {
                acquire = interProcessSemaphoreMutex.acquire(1, TimeUnit.SECONDS);
                log.debug(">>>>> zookeeper Semaphore lock acquire: [{}]", acquire);
                semaphoreAcquire(interProcessSemaphoreMutex, count);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (acquire) {
                    interProcessSemaphoreMutex.release();
                    log.debug(">>>>> zookeeper Semaphore lock release");
                }
            }
        }
    }

    // 不可重入锁
    @Test
    public void semaphoreAcquire() throws Exception {
        int count = 2;
        InterProcessSemaphoreMutex interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorFramework, "/zookeeper_semaphore_lock");
        semaphoreAcquire(interProcessSemaphoreMutex, count);
    }

}
