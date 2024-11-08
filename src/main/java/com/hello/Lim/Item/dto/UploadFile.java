package com.hello.Lim.Item.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UploadFile { // 업로드 파일 정보 보관

    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName;  // 서버 내부에서 관리하는 파일명

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile() {} // 기본 생성자 필수

}

