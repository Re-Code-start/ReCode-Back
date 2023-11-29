package com.example.recode.dto.note;

import com.example.recode.domain.FeedbackType;
import com.example.recode.domain.Folder;
import com.example.recode.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteAddRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String link;

    @NotNull
    private FeedbackType feedbackType;

    @NotBlank
    private String oldCode;

    private String newCode;

    private String improvement;

    private String comment;

    @NotNull
    private Long folderId;

    public Note toEntity(Folder folder) {
        return Note.builder()
                .title(title)
                .link(link)
                .type(feedbackType)
                .oldCode(oldCode)
                .newCode(newCode)
                .improvement(improvement)
                .comment(comment)
                .folder(folder)
                .build();

    }

}
