package com.graduation.cache;

import com.graduation.domain.UrlLibrary;
import com.graduation.repository.UrlLibraryRepository;
import com.graduation.utils.HtmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DingYS on 2016/12/29.
 */
@Component
public class CacheService {

    private Map<String,String> maps = new HashMap<String, String>();
    @Autowired
    private UrlLibraryRepository urlLibraryRepository;

    public String getMap(String key){
        if(maps.isEmpty()){
           List<UrlLibrary> collectLibrarieList = urlLibraryRepository.findAll();
            for(UrlLibrary urlLibrary : collectLibrarieList){
                maps.put(urlLibrary.getUrl(), urlLibrary.getLogoUrl());
            }
        }
        if(null == maps.get(key)){
            this.addMaps(key);
        }
        return maps.get(key);
    }


    public void addMaps(String key){
        String logoUrl = HtmlUtil.getImge(key);
        maps.put(key,logoUrl);
        UrlLibrary urlLibrary = new UrlLibrary();
        urlLibrary.setUrl(key);
        urlLibrary.setLogoUrl(logoUrl);
        urlLibraryRepository.save(urlLibrary);
    }

    public boolean refreshOne(String key,String newValue){
        if(StringUtils.isNotBlank(key)){
            String value = getMap(key);
            if(StringUtils.isNotBlank(newValue) && !newValue.equals(value)){
                maps.put(key,newValue);
                return true;
            }
        }
        return false;
    }
}
