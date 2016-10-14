package com.social.credits.resource.test.db.cg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.mockito.BDDMockito.given;

/**
 * Created by tiger on 16-9-7.
 */
public class BuyProductProcessTest extends ScTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private VisitorRecordService visitorRecordService;


    @Before
    public void setup() {
        String companyName = "someCompany";

        given(this.visitorRecordService.
                findByVisitorRecordId("123456")
        ).willReturn(
                companyName);
    }

    @Test
    public void test() {
        Assert.assertEquals("someCompany", visitorRecordService.findByVisitorRecordId("123456"));
    }

}
