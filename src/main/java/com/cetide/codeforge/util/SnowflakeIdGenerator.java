package com.cetide.codeforge.util;

/**
 * SnowflakeIdGenerator 使用 Twitter 的 Snowflake 算法生成全局唯一的 64 位 ID。
 * 生成的 ID 由以下几个部分组成：
 * 1. 时间戳部分（41 位）：当前时间与自定义纪元（custom epoch）的差值（毫秒）。
 * 2. 数据中心标识（5 位）：标识生成该 ID 的数据中心。
 * 3. 机器标识（5 位）：标识数据中心中的具体机器。
 * 4. 序列号（12 位）：同一毫秒内生成的多个 ID，用于防止冲突。
 * 这种组合保证了在分布式系统中生成唯一的 ID，且生成速度高、趋势递增。
 *
 * @author heathcetide
 */
public class SnowflakeIdGenerator {

    // 数据中心 ID，取值范围 0~31，占 5 位
    private final long datacenterId;

    // 机器 ID，取值范围 0~31，占 5 位
    private final long machineId;

    // 序列号，取值范围 0~4095，占 12 位；同一毫秒内用于区分不同 ID
    private long sequence = 0L;

    // 上一次生成 ID 的时间戳，初始值为 -1 表示尚未生成过 ID
    private long lastTimestamp = -1L;

    /**
     * 构造方法，初始化数据中心和机器标识。
     *
     * @param datacenterId 数据中心标识（0~31）
     * @param machineId    机器标识（0~31）
     */
    public SnowflakeIdGenerator(long datacenterId, long machineId) {
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 生成下一个唯一的 ID，该方法线程安全。
     *
     * 算法步骤：
     * 1. 获取当前时间戳（毫秒）。
     * 2. 如果当前时间小于上次生成 ID 的时间，说明系统时钟回拨，抛出异常。
     * 3. 如果当前时间与上次生成 ID 的时间相同，则将序列号递增；
     *    如果序列号达到最大值（4095）则等待下一个毫秒。
     * 4. 如果当前时间大于上次生成 ID 的时间，则重置序列号为 0。
     * 5. 将当前时间戳更新为最后一次生成 ID 的时间。
     * 6. 按位移方式拼接各部分数据生成最终的 64 位 ID。
     *
     * @return 生成的全局唯一 ID
     */
    public synchronized long nextId() {
        // 获取当前系统时间（毫秒）
        long timestamp = System.currentTimeMillis();

        // 如果当前时间小于上次记录的时间戳，说明系统时钟发生了回拨
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards");
        }

        // 如果当前时间与上一次生成 ID 的时间相同
        if (timestamp == lastTimestamp) {
            // 序列号递增，并使用位运算限制序列号范围在 0~4095（12 位）
            sequence = (sequence + 1) & 4095;
            // 如果序列号已经达到上限，则需要等待到下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 如果当前时间已进入新的毫秒，重置序列号为 0
            sequence = 0L;
        }

        // 更新最后一次生成 ID 的时间戳
        lastTimestamp = timestamp;

        // 生成最终 ID 的过程：
        // - (timestamp - 1288834974657L) 将当前时间戳减去自定义纪元（这里使用 Twitter 的起始时间），
        //   结果左移 22 位，用于存放 41 位的时间部分。
        // - datacenterId 左移 17 位，占 5 位。
        // - machineId 左移 12 位，占 5 位。
        // - sequence 占 12 位。
        return ((timestamp - 1288834974657L) << 22)
                | (datacenterId << 17)
                | (machineId << 12)
                | sequence;
    }

    /**
     * 当同一毫秒内序列号用完时，等待到下一毫秒。
     *
     * @param lastTimestamp 上一次生成 ID 的时间戳
     * @return 新的时间戳，必须大于 lastTimestamp
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        // 当时间戳没有变化时不断循环等待
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
