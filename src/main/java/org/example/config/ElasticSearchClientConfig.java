package org.example.config;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ElasticSearchClientConfig {

    @Value("${elasticsearch.cluster.servers}")
    private String servers;

    @Value("${elasticsearch.cluster.username}")
    private String userName;

    @Value("${elasticsearch.cluster.password}")
    private String passWord;

    @Value("${elasticsearch.cluster.ioThreadCount:10}")
    private Integer IoThreadCount;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        /*RestHighLevelClient client=new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.209.205",30240,"http")));*/
        List<HttpHost> list = new ArrayList<>();
        for (String server : servers.split(",")) {
            String[] address = server.split(":");
            String ip = address[0];
            int port = 9200;
            if (address.length > 1) {
                port = Integer.valueOf(address[1]);
            }
            list.add(new HttpHost(ip, port, "http"));
        }
        HttpHost[] hosts = new HttpHost[list.size()];
        list.toArray(hosts);
        RestClientBuilder builder = RestClient.builder(hosts);
        String username = userName;
        String password = passWord;
        builder.setHttpClientConfigCallback(config -> {
            config.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(IoThreadCount).build());
            if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                final CredentialsProvider credential = new BasicCredentialsProvider();
                credential.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                config.setDefaultCredentialsProvider(credential);
            }
            return config;
        });
        builder.setRequestConfigCallback(config -> {
            config.setConnectTimeout(5 * 1000);
            config.setSocketTimeout(60 * 1000);
            return config;
        });
        RestHighLevelClient hclient = new RestHighLevelClient(builder);
        return hclient;
    }
}


