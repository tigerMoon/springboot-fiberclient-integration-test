

### run with integration test ,the bug will display

mvn -Dmaven.test.skip=false verify


### about the bug

the bug detail in [stackoverflow](http://stackoverflow.com/questions/39970668/spring-boot-1-4-hibernate-5-integration-test-java-io-ioexception-too-many-o?noredirect=1#comment67262056_39970668) 

the bug caused by the class `com.social.credits.web.client.ScHttpClientConfig`

when use fiberHttpClient for httpClient.

you can comment this config use default httpClient.
