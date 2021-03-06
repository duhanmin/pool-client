/*
 * Copyright 2015-2016 Dark Phoenixs (Open-Source Organization).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.le.pool.socket;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class SocketConnectionFactoryTest {


    @Before
    public void before() throws Exception {

        Thread th = new Thread(new Runnable() {

            private ServerSocket serverSocket;

            @Override
            public void run() {

                try {
                    serverSocket = new ServerSocket(1234);

                    serverSocket.accept();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        th.setDaemon(true);
        th.start();
    }

    @Test
    public void test_0() throws Exception {

        SocketConnectionFactory factory = new SocketConnectionFactory(
                SocketConfig.DEFAULT_HOST, SocketConfig.DEFAULT_PORT,
                SocketConfig.DEFAULT_BUFFERSIZE,
                SocketConfig.DEFAULT_BUFFERSIZE, SocketConfig.DEFAULT_TIMEOUT,
                SocketConfig.DEFAULT_TIMEOUT, 1, true, true, new String[]{
                "0", "1", "2"});

        PooledObject<Socket> pooledObject = null;
        try {
            pooledObject = factory.makeObject();
        } catch (Exception e) {
        }

        factory.activateObject(new DefaultPooledObject<Socket>(new Socket()));
        try {
            factory.activateObject(new DefaultPooledObject<Socket>(null));
        } catch (Exception e) {
        }

        factory.validateObject(new DefaultPooledObject<Socket>(new Socket()));
        try {
            factory.validateObject(new DefaultPooledObject<Socket>(null));
        } catch (Exception e) {
        }

        try {
            factory.validateObject(pooledObject);
        } catch (Exception e) {
        }

        try {
            pooledObject.getObject().close();
            factory.validateObject(pooledObject);
        } catch (Exception e) {
        }

        factory.passivateObject(new DefaultPooledObject<Socket>(new Socket()));
        try {
            factory.passivateObject(new DefaultPooledObject<Socket>(null));
        } catch (Exception e) {
        }

        factory.destroyObject(new DefaultPooledObject<Socket>(new Socket()));
        try {
            factory.destroyObject(new DefaultPooledObject<Socket>(null));
        } catch (Exception e) {
        }
    }

    @Test
    public void test_1() throws Exception {

        Properties pro = new Properties();

        try {
            new SocketConnectionFactory(pro);
        } catch (Exception e) {
        }

        pro.setProperty(SocketConfig.ADDRESS_PROPERTY,
                SocketConfig.DEFAULT_HOST + ":" + SocketConfig.DEFAULT_PORT);
        pro.setProperty(SocketConfig.RECE_BUFFERSIZE_PROPERTY,
                SocketConfig.DEFAULT_BUFFERSIZE + "");
        pro.setProperty(SocketConfig.SEND_BUFFERSIZE_PROPERTY,
                SocketConfig.DEFAULT_BUFFERSIZE + "");
        pro.setProperty(SocketConfig.CONN_TIMEOUT_PROPERTY,
                SocketConfig.DEFAULT_TIMEOUT + "");
        pro.setProperty(SocketConfig.SO_TIMEOUT_PROPERTY,
                SocketConfig.DEFAULT_TIMEOUT + "");
        pro.setProperty(SocketConfig.LINGER_PROPERTY,
                SocketConfig.DEFAULT_LINGER + "");
        pro.setProperty(SocketConfig.KEEPALIVE_PROPERTY,
                SocketConfig.DEFAULT_KEEPALIVE + "");
        pro.setProperty(SocketConfig.TCPNODELAY_PROPERTY,
                SocketConfig.TCPNODELAY_PROPERTY + "");

        new SocketConnectionFactory(pro);

        Properties pro2 = new Properties();
        pro2.setProperty(SocketConfig.ADDRESS_PROPERTY,
                SocketConfig.DEFAULT_HOST + ":" + SocketConfig.DEFAULT_PORT);

        try {
            new SocketConnectionFactory(pro2).createConnection();
        } catch (Exception e) {
        }

        Properties pro3 = new Properties();
        pro3.setProperty(SocketConfig.ADDRESS_PROPERTY,
                SocketConfig.DEFAULT_HOST + ":" + 1233);

        try {
            new SocketConnectionFactory(pro3).createConnection();
        } catch (Exception e) {
        }

        Properties pro4 = new Properties();
        pro4.setProperty(SocketConfig.ADDRESS_PROPERTY,
                SocketConfig.DEFAULT_HOST + ":" + 1234);
        pro4.setProperty(SocketConfig.PERFORMANCE_PROPERTY,
                "0,1,2");
        try {
            new SocketConnectionFactory(pro4).createConnection();
        } catch (Exception e) {
        }
    }
}
