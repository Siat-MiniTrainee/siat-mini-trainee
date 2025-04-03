package quiz.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.model.domain.StateUpdateInfoDto;

@Getter
@Setter
@Builder
@ToString
public class QuizResponseDto {
    private String result;
    private StateUpdateInfoDto stateChange;
}