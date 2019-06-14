/*
 * Copyright 2001-2017 the original author or authors.
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
package com.springboot.springes.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/14 11:40
 */
@Configuration
public class ElasticsearchConfig {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    @Value("${elasticsearch.ip}")
    private String hostName;

    @Value("${elasticsearch.port}")
    private String port;

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.pool}")
    private String poolSize;

    @Bean
    public TransportClient transportClient(){
        logger.info("Elasticsearch init...");
        TransportClient transportClient = null;

        try {
            Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .put("thread_pool.search.size", Integer.parseInt(poolSize))
                .build();

            transportClient = new PreBuiltTransportClient(settings);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
            transportClient.addTransportAddress(transportAddress);
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
        }
        return transportClient;
    }
}