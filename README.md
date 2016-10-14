

### run with integration test

mvn -Dmaven.test.skip=false verify


### bug

the bug caused by the class `com.social.credits.web.client.ScHttpClientConfig`

when use fiberHttpClient for httpClient.

you can comment this config use default httpClient.
