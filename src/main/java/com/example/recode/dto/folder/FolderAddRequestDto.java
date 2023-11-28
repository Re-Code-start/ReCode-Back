package com.example.recode.dto.folder;

import com.example.recode.domain.Folder;
import com.example.recode.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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

    public Folder toEntity() {
        return Folder.builder()
                .name(name)
                .build();
    }

}
