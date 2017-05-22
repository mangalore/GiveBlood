package com.example.ananya.giveblood.service;

import com.example.ananya.giveblood.util.Utility;

import java.util.Iterator;
import java.util.Map;


public class URLHelper {

    private static final String BASE_URL = "https://baas.kinvey.com/appdata/" + Utility.APP_KEY;

    public static String getAPIEndpoint(String collectionName, String id, String[] resolveParameter) {
        String url = null;
        if (resolveParameter != null) {
            url = String.format("%s/%s", BASE_URL, collectionName, id);
            url = url + "?resolve=";
            for (int i = 0; i < resolveParameter.length; i++) {
                if (resolveParameter.length == 1 || i == resolveParameter.length - 1) {
                    url = url + resolveParameter[i];
                } else {
                    url = url + resolveParameter[i] + ",";
                }
            }
            url = url + "&retainReferences=false";

        } else if (id == null || id.isEmpty()) {
            url = String.format("%s/%s", BASE_URL, collectionName);
        } else {
            url = String.format("%s/%s/%s", BASE_URL, collectionName, id);
        }
        return url;
    }

    public static String getQeryEndpoint(String collectionName, Map<String, Object> map, String[] resolve) {
        Iterator<String> iterator = map.keySet().iterator();
        StringBuilder url = new StringBuilder(getAPIEndpoint(collectionName, null, null));
        url = url.append("?query={");
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object obj = map.get(key);
            String paramValue = null;
            if (obj instanceof String) {
                String strVal = (String) obj;
                strVal = strVal.replace(" ", "%20");
                paramValue = String.format("\"%s\":\"%s\"", key, strVal);
            } else if (obj instanceof Boolean) {
                boolean boolVal = (boolean) obj;
                paramValue = String.format("\"%s\":%b", key, boolVal);
            } else if (obj instanceof Integer) {
                int intVal = (int) obj;
                paramValue = String.format("\"%s\":%d", key, intVal);
            }
            url = url.append(paramValue);
            if (iterator.hasNext()) {
                url = url.append(",");
            } else {
                url = url.append("}");
            }
        }
        if (resolve != null) {
            url = url.append("&resolve=");
            for (int i = 0; i < resolve.length; i++) {
                if (resolve.length == 1 || i == resolve.length - 1) {
                    url = url.append(resolve[i]);
                } else {
                    url = url.append(resolve[i] + ",");
                }
            }
            url = url.append("&retainReferences=false");
        }
        return url.toString();
    }

}
