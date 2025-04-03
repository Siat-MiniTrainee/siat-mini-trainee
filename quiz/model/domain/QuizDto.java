package quiz.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class QuizDto {
    private int quizId;
    private String content;
    private QuizType quizType;
    private int level;
}
