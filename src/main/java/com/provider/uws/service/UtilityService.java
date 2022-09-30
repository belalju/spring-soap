package com.provider.uws.service;

import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class UtilityService {
    public XMLGregorianCalendar getCurrentTime(){
        XMLGregorianCalendar xmlDate = null;

        // Gregorian Calendar object creation
        GregorianCalendar gc = new GregorianCalendar();

        // giving current date and time to gc
        gc.setTime(new Date());

        try {
            xmlDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return xmlDate;
    }
}
