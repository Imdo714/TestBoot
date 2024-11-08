package com.hello.Lim.Item.service;

import com.hello.Lim.Item.dto.FileStore;
import com.hello.Lim.Item.dto.ItemDTO;
import com.hello.Lim.Item.dto.UploadFile;
import com.hello.Lim.Item.entity.ItemEntity;
import com.hello.Lim.Item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final FileStore fileStore;
    private final ItemRepository itemRepository;

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public int insertItem(ItemDTO itemDTO) {

        ItemEntity item = new ItemEntity();
        item.setItemName(itemDTO.getItemName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());

        // 파일 저장
        List<UploadFile> storedFiles = new ArrayList<>();

        // 첨부파일 처리
        if (itemDTO.getAttachFile() != null && !itemDTO.getAttachFile().isEmpty()) {
            try {
                // FileStore를 통해 첨부파일 저장
                UploadFile attachFile = fileStore.storeFile(itemDTO.getAttachFile());
                storedFiles.add(attachFile);
                item.setAttachFile(attachFile); // ItemEntity에 첨부파일 설정
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리 (로깅 또는 사용자에게 알림)
            }
        }

        // 이미지 파일 처리
        if (itemDTO.getImageFiles() != null) {
            try {
                // FileStore를 통해 이미지 파일들 저장
                List<UploadFile> imageFiles = fileStore.storeFiles(itemDTO.getImageFiles());
                storedFiles.addAll(imageFiles); // 모든 이미지 저장
                item.setImageFiles(imageFiles); // ItemEntity에 이미지 목록 설정
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리 (로깅 또는 사용자에게 알림)
            }
        }
        
        // item이제 JPA로 저장하면 끝
        log.info("item2222 = {}", item);
        ItemEntity savedItem = itemRepository.save(item);

        return savedItem.getItemId();
    }

    @Override
    public ItemEntity findByItemId(int itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Override
    public List<ItemEntity> findItemAll() {
        return itemRepository.findAll();
    }

    @Override
    @Transactional
    public void deletImg(String storeFileName, int itemId) {
//        itemRepository.deleteByStoreFileNameAndItemId(storeFileName, itemId);
        // itemId로 ItemEntity를 조회
        Optional<ItemEntity> itemEntityOptional = itemRepository.findById(itemId);

        if (itemEntityOptional.isPresent()) {
            ItemEntity itemEntity = itemEntityOptional.get();

            // imageFiles 리스트에서 storeFileName이 일치하는 이미지 제거
            itemEntity.getImageFiles().removeIf(image -> image.getStoreFileName().equals(storeFileName));

            // 변경된 ItemEntity를 저장하여 컬렉션의 변경사항을 반영
            itemRepository.save(itemEntity);

            String path = fileDir + storeFileName;
            File file = new File(path);

            if (file.delete()) {
                log.info("파일 삭제 성공: {}", storeFileName);
            } else {
                log.error("파일 삭제 실패: {}", storeFileName);
            }
        }
    }


}
