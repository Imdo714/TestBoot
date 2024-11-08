package com.hello.Lim.Item.controller;

import com.google.gson.Gson;
import com.hello.Lim.Item.dto.FileStore;
import com.hello.Lim.Item.dto.ItemDTO;
import com.hello.Lim.Item.entity.ItemEntity;
import com.hello.Lim.Item.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/itemadd")
    public String itemadd(@ModelAttribute("itemDTO")ItemDTO itemDTO) {

        return "item/upload-form";
    }

    @PostMapping("/itemadd")
    public String add(@Valid @ModelAttribute("itemDTO") ItemDTO itemDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "item/upload-form";
        }

        int itemId  = itemService.insertItem(itemDTO); // 아이템 등록

        redirectAttributes.addAttribute("itemId", itemId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    // 상품 상세 보기
    @GetMapping("/items/{itemId}")
    public String itemView(@PathVariable int itemId, Model model){
        log.info("itemId = {}", itemId);
        ItemEntity item = itemService.findByItemId(itemId);
        log.info("item = {}", item);

        model.addAttribute("item", item);

        return "item/item";
    }

    @GetMapping("/items/attach/{itemId}") // 첨부파일 다운로드 메서드
    public ResponseEntity<Resource> downloadAttach(@PathVariable int itemId) throws MalformedURLException {
        ItemEntity item = itemService.findByItemId(itemId); // 아이디로 찾음

        String storeFileName = item.getAttachFile().getStoreFileName();   // 서버 업로드 명
        String uploadFileName = item.getAttachFile().getUploadFileName();   // 다운로드명
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName)); // 서버 업로드명으로 파일 찾음
        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // 다운로드할 파일을 인코딩
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\""; // 인코딩한 파일 경로

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

    // 수정 뷰 보여주는 메서드
    @GetMapping("/items/{itemId}/edit")
    public String editForm(@PathVariable int itemId, Model model) {
        ItemEntity item = itemService.findByItemId(itemId);
        log.info("item = {}", item);

        model.addAttribute("item", item);

        return "item/editForm";
    }

    @ResponseBody
    @RequestMapping(value="/items/edit", produces="application/json; charset=UTF-8")
    public String editImgAjax(String storeFileName, int itemId) {
        log.info("storeFileName = {}", storeFileName);

        itemService.deletImg(storeFileName, itemId);

        return new Gson().toJson(itemId);
    }

}
