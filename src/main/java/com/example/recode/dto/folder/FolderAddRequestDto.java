package com.example.recode.dto.folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderAddRequestDto {

    @NotBlank
    private String userId;  // 폴더 생성자 ID

    @NotBlank
    private String name;    // 폴더명

    @CreationTimestamp
    private LocalDateTime createDt; // 생성일시

}
