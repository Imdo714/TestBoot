package com.hello.Lim.Item.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemDTO {

    private Integer itemId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Range(min = 1000, max = 1000000) // 범위 안의 값이어야 함
    private Integer price;

    @NotNull(message = "갯수는 필수 입력 값입니다.")
    @Max(9999) // 최대 갯수
    private Integer quantity;

    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
