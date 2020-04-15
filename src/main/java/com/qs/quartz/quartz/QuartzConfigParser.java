package com.qs.quartz.quartz;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuartzConfigParser {

    private static final String SCHEDULE_FACTORY_BEAN = "org.springframework.scheduling.quartz.SchedulerFactoryBean";
    private static final String CRON_TRIGGER_FACTORY_BEAN = "org.springframework.scheduling.quartz.CronTriggerFactoryBean";
    private static final String JOB_DETAIL_FACTORY_BEAN = "org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean";

    public static void getQuartzDefByConfig() throws DocumentException {
        String path = QuartzConfigParser.class.getResource("/spring/spring-quartz.xml").getPath();
        File file = new File(path);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        Element rootElement = document.getRootElement();
        List elements = rootElement.elements();
        Map<String, List<String>> triggersListMap = null;
        for (Object elem : elements) {
            Element element = (Element) elem;
            Attribute attribute = element.attribute("class");
            if (attribute != null) {
                String classValue = attribute.getValue();
                if (SCHEDULE_FACTORY_BEAN.equals(classValue)) {
                    triggersListMap = parseScheduleFactoryBean(element);
                }

                if (CRON_TRIGGER_FACTORY_BEAN.equals(classValue)) {
                    parseTriggerFactiryBean(element);
                }

                if (JOB_DETAIL_FACTORY_BEAN.equals(classValue)) {
                    parseJobDetailFactoryBean(element);
                }
            }

        }
    }

    private static void parseJobDetailFactoryBean(Element element) {

    }

    private static void parseTriggerFactiryBean(Element element) {
       /* Map<String, QuartzTrigger> triggerIdAndTriggerMap = new HashMap<>();
        Attribute idAttr = element.attribute("id");
        String beanId = idAttr.getValue();
        List elements = element.elements();
        for(Object elem : elements){
            Element childElem = (Element) elem;
            Attribute nameAttr = childElem.attribute("name");
            Attribute refAttr = childElem.attribute("ref");
            Attribute valueAttr = childElem.attribute("value");

            if(nameAttr != null){
                if("jobDetail".equals(nameAttr.getValue())){

                }
            }
        }
*/
    }

    private static Map<String, List<String>> parseScheduleFactoryBean(Element element) {
        Map<String, List<String>> triggersListMap = new HashMap<>();
        List elements = element.elements();
        for (Object elem : elements) {
            Element propsElement = (Element) elem;
            Attribute attribute = propsElement.attribute("name");
            if ("triggers".equals(attribute.getValue())) {
                List<String> triggers = triggersListMap.get("triggers");
                if (triggers == null) {
                    triggers = new ArrayList<>();
                    triggersListMap.put("triggers", triggers);
                }

                Element listElement = propsElement.element("list");
                List refElements = listElement.elements();
                for (Object refElement1 : refElements) {
                    Element refElement = (Element) refElement1;
                    Attribute attr = refElement.attribute("bean");
                    String beanValue = attr.getValue();
                    triggers.add(beanValue);
                }
            }
        }

        return triggersListMap;
    }


    public static void main(String[] args) throws DocumentException {

    }

}
