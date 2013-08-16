/*
 * Copyright 2013 EnergyOS ESPI
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

package org.energyos.espi.datacustodian.web.customer;

import org.energyos.espi.datacustodian.models.RetailCustomer;
import org.energyos.espi.datacustodian.models.UsagePoint;
import org.energyos.espi.datacustodian.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/customer/usagepoints/feed")
public class UsagePointFeedController {

    @Autowired
    private UsagePointService service;

    public void setService(UsagePointService service) {
        this.service = service;
    }

    @ModelAttribute
    public List<UsagePoint> usagePointsList() {
        RetailCustomer customer = new RetailCustomer();
        customer.setId(1L);

        return  service.findAllByRetailCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public String index() {
        return "/customer/usagepoints/feed";
    }
}