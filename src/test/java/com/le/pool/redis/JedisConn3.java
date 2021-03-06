package com.le.pool.redis;

import redis.clients.jedis.Jedis;

public class JedisConn3 extends Jedis {

    public JedisConn3() {
    }

    public JedisConn3(String arg0, int arg1) {
        super(arg0, arg1);
    }

    @Override
    public String select(int index) {

        return String.valueOf(index);
    }

    @Override
    public boolean isConnected() {

        return false;
    }

    @Override
    public String ping() {

        return "PONG1";
    }

    @Override
    public String quit() {

        return "quit";
    }

    @Override
    public void disconnect() {

    }
}
