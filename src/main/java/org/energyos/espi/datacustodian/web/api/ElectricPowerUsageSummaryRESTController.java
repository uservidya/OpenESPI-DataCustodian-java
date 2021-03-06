package org.energyos.espi.datacustodian.web.api;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.Routes;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.service.ElectricPowerUsageSummaryService;
import org.energyos.espi.common.service.ExportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sun.syndication.io.FeedException;

@Controller
public class ElectricPowerUsageSummaryRESTController {

    @Autowired
    private ElectricPowerUsageSummaryService electricPowerUsageSummaryService;
    @Autowired
    private UsagePointService usagePointService;

    @Autowired
    private ExportService exportService;
    
    @Autowired
    private ResourceService resourceService;


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleGenericException() {}

    // ROOT RESTful forms
    //
    @RequestMapping(value = Routes.ROOT_ELECTRIC_POWER_USAGE_SUMMARY_COLLECTION, method = RequestMethod.GET)
    public void index(HttpServletResponse response,
    		@RequestParam Map<String, String> params) throws IOException, FeedException {
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
        exportService.exportElectricPowerUsageSummarys(response.getOutputStream(), new ExportFilter(params));
    }

    // 
    //
    @RequestMapping(value = Routes.ROOT_ELECTRIC_POWER_USAGE_SUMMARY_MEMBER, method = RequestMethod.GET)
    public void show(HttpServletResponse response, 
    		@PathVariable long electricPowerUsageSummaryId,
    		@RequestParam Map<String, String> params) throws IOException, FeedException {
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);

        try {
              exportService.exportElectricPowerUsageSummary(electricPowerUsageSummaryId, response.getOutputStream(), new ExportFilter(params));
                    } catch (Exception e) {
              response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          }     
        }

    // 
    //
    @RequestMapping(value = Routes.ROOT_ELECTRIC_POWER_USAGE_SUMMARY_COLLECTION, method = RequestMethod.POST)
    public void create(HttpServletResponse response, 
    		@RequestParam Map<String, String> params,
    		InputStream stream) throws IOException {
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
        try {
            ElectricPowerUsageSummary electricPowerUsageSummary = this.electricPowerUsageSummaryService.importResource(stream);
            exportService.exportElectricPowerUsageSummary(electricPowerUsageSummary.getId(), response.getOutputStream(), new ExportFilter(params));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    //

    @RequestMapping(value = Routes.ROOT_ELECTRIC_POWER_USAGE_SUMMARY_MEMBER, method = RequestMethod.PUT)
    public void update(HttpServletResponse response, 
    		@PathVariable long electricPowerUsageSummaryId,
    		@RequestParam Map<String, String> params,
    		InputStream stream) throws IOException, FeedException {
    	ElectricPowerUsageSummary electricPowerUsageSummary = electricPowerUsageSummaryService.findById(electricPowerUsageSummaryId);
        if (electricPowerUsageSummary != null) {
            try {
                ElectricPowerUsageSummary newElectricPowerUsageSummary = electricPowerUsageSummaryService.importResource(stream);
                electricPowerUsageSummary.merge(newElectricPowerUsageSummary);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = Routes.ROOT_ELECTRIC_POWER_USAGE_SUMMARY_MEMBER, method = RequestMethod.DELETE)
    public void delete(HttpServletResponse response, 
    		@PathVariable long electricPowerUsageSummaryId,
    		@RequestParam Map<String, String> params,
    		InputStream stream) throws IOException, FeedException {
    	ElectricPowerUsageSummary electricPowerUsageSummary = electricPowerUsageSummaryService.findById(electricPowerUsageSummaryId);

        if (electricPowerUsageSummary != null) {
        	electricPowerUsageSummaryService.delete(electricPowerUsageSummary);
        }
    }    		

    // XPath RESTful forms
    //
    @RequestMapping(value = Routes.ELECTRIC_POWER_USAGE_SUMMARY_COLLECTION, method = RequestMethod.GET)
    public void index(HttpServletResponse response,
    		@PathVariable long retailCustomerId,
    		@PathVariable long usagePointId,
    		@RequestParam Map<String, String> params) throws IOException, FeedException {
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
        exportService.exportElectricPowerUsageSummarys(retailCustomerId, usagePointId, response.getOutputStream(), new ExportFilter(params));
    }

    // 
    //
    @RequestMapping(value = Routes.ELECTRIC_POWER_USAGE_SUMMARY_MEMBER, method = RequestMethod.GET)
    public void show(HttpServletResponse response, 
    		@PathVariable long retailCustomerId,
    		@PathVariable long usagePointId,
    		@PathVariable long electricPowerUsageSummaryId,
    		@RequestParam Map<String, String> params) throws IOException, FeedException {
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
        try {
            exportService.exportElectricPowerUsageSummary(retailCustomerId, usagePointId, electricPowerUsageSummaryId, response.getOutputStream(), new ExportFilter(params));
                    } catch (Exception e) {
              response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          } 
        }

    // 
    //
    @RequestMapping(value = Routes.ELECTRIC_POWER_USAGE_SUMMARY_COLLECTION, method = RequestMethod.POST)
    public void create(HttpServletResponse response, 
    		@PathVariable long retailCustomerId,
    		@PathVariable long usagePointId,
    		@RequestParam Map<String, String> params,
    		InputStream stream) throws IOException {
        response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);

        try {
        	if (null != resourceService.findIdByXPath(retailCustomerId,usagePointId, ElectricPowerUsageSummary.class )) {
            	UsagePoint usagePoint = usagePointService.findById(usagePointId);
                ElectricPowerUsageSummary electricPowerUsageSummary = this.electricPowerUsageSummaryService.importResource(stream);
                electricPowerUsageSummaryService.associateByUUID(usagePoint, electricPowerUsageSummary.getUUID());
                exportService.exportElectricPowerUsageSummary(retailCustomerId, usagePointId, electricPowerUsageSummary.getId(), response.getOutputStream(), new ExportFilter(params));
        	}
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    //

    @RequestMapping(value = Routes.ELECTRIC_POWER_USAGE_SUMMARY_MEMBER, method = RequestMethod.PUT)
    public void update(HttpServletResponse response, 
    		@PathVariable long retailCustomerId,
    		@PathVariable long usagePointId,
    		@PathVariable long electricPowerUsageSummaryId,
    		@RequestParam Map<String, String> params,
    		InputStream stream) throws IOException, FeedException {

           try {
               	if (null != resourceService.findIdByXPath(retailCustomerId,usagePointId, electricPowerUsageSummaryId, ElectricPowerUsageSummary.class )) {
               		ElectricPowerUsageSummary electricPowerUsageSummary = resourceService.findById(electricPowerUsageSummaryId, ElectricPowerUsageSummary.class);
                    ElectricPowerUsageSummary newElectricPowerUsageSummary = electricPowerUsageSummaryService.importResource(stream);
                    electricPowerUsageSummary.merge(newElectricPowerUsageSummary);
            	}
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
    
    }

    @RequestMapping(value = Routes.ELECTRIC_POWER_USAGE_SUMMARY_MEMBER, method = RequestMethod.DELETE)
    public void delete(HttpServletResponse response, 
       		@PathVariable long retailCustomerId,
    		@PathVariable long usagePointId,
    		@PathVariable long electricPowerUsageSummaryId,
    		@RequestParam Map<String, String> params,
    		InputStream stream) throws IOException, FeedException {
    	
        try {
           	if (null != resourceService.findIdByXPath(retailCustomerId,usagePointId, electricPowerUsageSummaryId, ElectricPowerUsageSummary.class )) {
                resourceService.deleteById(electricPowerUsageSummaryId, ElectricPowerUsageSummary.class );
           	}
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
   }    		
   
    public void setElectricPowerUsageSummaryService(ElectricPowerUsageSummaryService electricPowerUsageSummaryService) {
        this.electricPowerUsageSummaryService = electricPowerUsageSummaryService;
    }


    public void setUsagePointService(UsagePointService usagePointService) {
        this.usagePointService = usagePointService;
    }

    public void setExportService(ExportService exportService) {
        this.exportService = exportService;
    }
}
