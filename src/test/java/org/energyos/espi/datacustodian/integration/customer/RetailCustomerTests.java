/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.datacustodian.integration.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.service.RetailCustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class RetailCustomerTests {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    private RetailCustomer customer;

    @Autowired
    private RetailCustomerService retailCustomerService;
    
    protected TestingAuthenticationToken authentication;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        customer = retailCustomerService.findById(1L);
        authentication = new TestingAuthenticationToken(customer, null);
    }

    @Test
    public void displaysCustomerHomeView() throws Exception {
        mockMvc.perform(get("/RetailCustomer/" + customer.getId() + "/home").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(view().name("/customer/home"));
    }
}
