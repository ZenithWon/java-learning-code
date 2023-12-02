package com.zenith.utis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BloomFilter {
    private final int[] bitmap=new int[100000];

    private int hash1(String key){
        return Math.abs(key.hashCode()%20359);
    }

    private int hash2(String key){
        return Math.abs(key.hashCode()%66463);
    }

    private int hash3(String key){
        return Math.abs(key.hashCode()%94351);
    }

    public boolean select(String key) {
        List<Integer> hashValue= getHashValue(key);
        for(Integer value:hashValue){
            if(bitmap[value]==0){
                return false;
            }
        }
        return true;
    }

    public void insert(String key) {
        List<Integer> hashValue = getHashValue(key);
        for(Integer value:hashValue){
            bitmap[value]=1;
        }
    }

    private List<Integer> getHashValue(String key) {
        List<Integer> hashRes=new ArrayList<>();
        hashRes.add(hash1(key));
        hashRes.add(hash2(key));
        hashRes.add(hash3(key));

        return hashRes;
    }
}
