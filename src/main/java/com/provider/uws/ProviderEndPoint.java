package com.provider.uws;

import com.provider.uws.client.gen.*;
import com.provider.uws.service.UtilityService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Endpoint
public class ProviderEndPoint {
    private static final String NAMESPACE_URI = "http://uws.provider.com/";

    private final UtilityService utilityService;

    public ProviderEndPoint(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetInformation")
    @ResponsePayload
    public JAXBElement<GetInformationResult> getInformation(@RequestPayload JAXBElement<GetInformationArguments> request) {
        ObjectFactory factory = new ObjectFactory();

        GetInformationResult response = new GetInformationResult();
        GetInformationArguments requestValue = request.getValue();

        System.out.println("Service ID: " + requestValue.getServiceId());

        response.setErrorMsg("");
        response.setStatus(0);
        response.setTimeStamp(utilityService.getCurrentTime());

        List<GenericParam> paramList = new ArrayList<GenericParam>();
        paramList.add(getGenericParam("balance", RandomStringUtils.randomNumeric(5, 5)));
        paramList.add(getGenericParam("name", RandomStringUtils.randomAlphabetic(5)));

        response.parameters = paramList;


        return factory.createGetInformationResult ( response );
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PerformTransaction")
    @ResponsePayload
    public JAXBElement<PerformTransactionResult> postTransaction(@RequestPayload JAXBElement<PerformTransactionArguments> request) {
        ObjectFactory factory = new ObjectFactory();

        PerformTransactionResult response = new PerformTransactionResult();
        PerformTransactionArguments requestValue = request.getValue();

        response.setErrorMsg("Ok");
        response.setStatus(0);
        response.setTimeStamp(utilityService.getCurrentTime());
        response.setProviderTrnId(new Random().nextLong());

        List<GenericParam> paramList = new ArrayList<GenericParam>();
        paramList.add(getGenericParam("balance", String.valueOf(requestValue.getAmount())));
        paramList.add(getGenericParam("traffic", RandomStringUtils.randomAlphabetic(5)));
        paramList.add(getGenericParam("date", new Date().toString()));

        response.parameters = paramList;


        return factory.createPerformTransactionResult ( response );
    }

    private GenericParam getGenericParam(String key, String value){
        GenericParam genericParam = new GenericParam();
        genericParam.setParamKey(key);
        genericParam.setParamValue(value);
        return genericParam;
    }
}
