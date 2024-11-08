package com.hello.Lim.Item.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename(); // 오리지날 파일 이름
        String storeFileName = createStoreFileName(originalFilename);  // 확장명 때는 부분
        multipartFile.transferTo(new File(getFullPath(storeFileName))); // 서버에 저장하는 부분

        return new UploadFile(originalFilename, storeFileName);
    }
    private String createStoreFileName(String originalFilename) { // 서버에 올릴 파일 이름 바꿔주는 메서드
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) { // 파일 확장명 분리해서 서버파일이름과 합쳐주는 메서드
        int pos = originalFilename.lastIndexOf(".");

        return originalFilename.substring(pos + 1);
    }
}
