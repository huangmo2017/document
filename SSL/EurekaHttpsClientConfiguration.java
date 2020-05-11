@Configuration
public class EurekaHttpsClientConfiguration {

    @Value("${eureka.client.ssl.key-store}")
    private String ketStoreFileName;

    @Value("${eureka.client.ssl.key-store-password}")
    private String ketStorePassword;

    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
        builder.withClientName("eureka-https-client");
        URL url = this.getClass().getClassLoader().getResource(ketStoreFileName);
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(url, ketStorePassword.toCharArray()).build();
        builder.withCustomSSL(sslContext);
        builder.withMaxTotalConnections(10);
        builder.withMaxConnectionsPerHost(10);

        DiscoveryClient.DiscoveryClientOptionalArgs optionalArgs = new DiscoveryClient.DiscoveryClientOptionalArgs();
        optionalArgs.setEurekaJerseyClient(builder.build());

        return optionalArgs;
    }

}