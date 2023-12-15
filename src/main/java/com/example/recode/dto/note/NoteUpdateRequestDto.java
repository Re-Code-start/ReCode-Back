package com.example.recode.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteUpdateRequestDto {

    private String newCode;         // 개선 코드

    private String improvement;     // 개선점

}
