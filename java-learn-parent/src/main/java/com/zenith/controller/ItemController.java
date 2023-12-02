package com.zenith.controller;

import com.zenith.common.R;
import com.zenith.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;


    @PostMapping("/generate/{num}")
    public R generateItemData(@PathVariable Integer num){
        return itemService.generateItemData(num);
    }

    @PostMapping("/generateLogicalTTL")
    public R generateLogicalTTLItemData(){
        return itemService.generateLogicalTTLItemData();
    }


}
