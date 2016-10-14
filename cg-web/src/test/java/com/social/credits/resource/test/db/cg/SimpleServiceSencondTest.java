package com.social.credits.resource.test.db.cg;

import com.social.credits.web.service.SimpleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tiger on 16-10-13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleServiceSencondTest {
    @Autowired
    private SimpleService simpleService;

    @Test
    public void findByIdSuccess() {
        String someData = simpleService.getSomeData();
        assertThat(someData).isEqualTo("ok");
    }

}
