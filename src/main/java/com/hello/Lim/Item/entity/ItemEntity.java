package com.hello.Lim.Item.entity;

import com.hello.Lim.Item.dto.UploadFile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@Entity(name = "item2")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @NotBlank(message = "이름은 필수 입력 값입니다.") // 빈값 공백 허용하지 않음
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Range(min = 1000, max = 1000000, message = "가격은 1000 ~ 1000000 까지 허용합니다.") // 범위 안의 값이어야 함
    private Integer price;

    @NotNull(message = "갯수는 필수 입력 값입니다.")
    @Max(9999) // 최대 갯수
    private Integer quantity;

    @Embedded
    private UploadFile attachFile;       // 첨부파일
    @ElementCollection
    private List<UploadFile> imageFiles; // 여러 이미지

//    private String attachFile;       // 첨부파일
//    private String imageFiles; // 여러 이미지
}
