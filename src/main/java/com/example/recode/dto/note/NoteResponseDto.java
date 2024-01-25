package com.example.recode.dto.note;

import com.example.recode.domain.NoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {

    private String title;

    private String link;

    private NoteType noteType;

    private String oldCode;

    private String newCode;

    private String improvement;

    private String comment;

    private List<String> algorithmList;

}
