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
import java.util.List;

@Endpoint
public class ProviderEndPoint {
    private static final String NAMESPACE_URI = "http://uws.provider.com/";

    private final UtilityService utilityService;

    public ProviderEndPoint(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkTransaction")
    @ResponsePayload
    public JAXBElement<CheckTransactionResult> getTransaction(@RequestPayload JAXBElement<CheckTransactionArguments> request) {

        System.out.println(request);
        ObjectFactory factory = new ObjectFactory ( );
        CheckTransactionResult response = new CheckTransactionResult ( );
        CheckTransactionArguments req = request.getValue ( );
        response.setProviderTrnId(req.getTransactionId());
        return factory.createCheckTransactionResult ( response );
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetInformationArguments")
    @ResponsePayload
    public JAXBElement<GetInformationResult> getInformation(@RequestPayload JAXBElement<GetInformationArguments> request) {
        ObjectFactory factory = new ObjectFactory();

        GetInformationResult response = new GetInformationResult();
        GetInformationArguments requestValue = request.getValue();

        System.out.println("Service ID: " + requestValue.getServiceId());

        response.setStatus(0);
        response.setTimeStamp(utilityService.getCurrentTime());

        List<GenericParam> paramList = new ArrayList<GenericParam>();
        GenericParam genericParam = new GenericParam();
        genericParam.setParamKey("balance");
        genericParam.setParamValue(RandomStringUtils.randomNumeric(5, 5));
        paramList.add(genericParam);

        genericParam = new GenericParam();
        genericParam.setParamKey("name");
        genericParam.setParamValue(RandomStringUtils.randomAlphabetic(5));
        paramList.add(genericParam);

        response.parameters = paramList;


        return factory.createGetInformationResult ( response );
    }
}
