package quiz.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class QuizConfirmResponseDto {
    private String result;      
    private String explanation; 
    private String answer;      
    private QuizType quizType;  
    private int level;          
}