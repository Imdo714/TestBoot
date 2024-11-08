package com.hello.Lim.Item.service;

import com.hello.Lim.Item.dto.ItemDTO;
import com.hello.Lim.Item.entity.ItemEntity;

import java.util.List;

public interface ItemService {

    int insertItem(ItemDTO itemDTO);

    ItemEntity findByItemId(int itemId);

    List<ItemEntity> findItemAll();

    void deletImg(String storeFileName, int itemId);
}
