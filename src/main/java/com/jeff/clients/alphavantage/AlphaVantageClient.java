package com.jeff.clients.alphavantage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.clients.Client;
import com.jeff.clients.reddit.ChildData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class AlphaVantageClient implements Client {

    private HashMap<String, ChildData> newPosts;
    private RestTemplate restTemplate;
    private HttpEntity<String> entity;
    private ObjectMapper mapper;
    private List<String> oldPosts;

    public AlphaVantageClient() {
        newPosts = new HashMap<>();
        try {
            restTemplate = getRestTemplate();
        } catch (Exception e) {
            System.out.println("Problem when generating rest template");
            e.printStackTrace();
        }
        entity = createEntity();
        mapper = new ObjectMapper();
    }

    public void checkFiveDaySma() {
        String query = AlphaVantageRequestBuilder.aRequest().build();
        System.out.println(query);

        ResponseEntity result = restTemplate.exchange(query, HttpMethod.GET, entity, String.class);

        try {
            //JSON from String to Object
            //  redditResult = mapper.readValue(result.getBody().toString(), RedditResult.class);
            System.out.println(result.getBody().toString());
        } catch (Exception e) {
            System.out.println("IOException thrown while mapping");
            e.printStackTrace();
        }

    }


    private HttpEntity<String> createEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new HttpEntity<>("", httpHeaders);
    }

    public static void main(String[] args) {
        AlphaVantageClient client = new AlphaVantageClient();

        client.checkFiveDaySma();
    }

    private RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        };
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
