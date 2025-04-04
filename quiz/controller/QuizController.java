package quiz.controller;

import java.util.List;

import action.model.domain.ActionType;
import quiz.model.domain.QuizConfirmResponseDto;
import quiz.model.domain.QuizDto;
import quiz.model.domain.QuizResponseDto;
import quiz.model.domain.QuizType;
import quiz.service.QuizService;
import state.model.domain.StateUpdateInfoDto;

public class QuizController {
    private QuizService quizService;

    public QuizController() {
        this.quizService = new QuizService();
    }

    public List<QuizDto> getQuizListByDifficulty(int level, QuizType quizType) {
        return quizService.getQuizListByDifficulty(level,quizType);
    }

    public QuizConfirmResponseDto confirmQuiz(int quizId, String answer) {

        return quizService.confirmQuiz(quizId, answer);
    }

    public QuizResponseDto submitQuizResult(int playerId, int quizLevel, String result) {
            ActionType quizLevelType = null;
            switch (quizLevel) {
                case 1:
                quizLevelType= ActionType.QUIZ1;
                    break;
                case 2:
                quizLevelType= ActionType.QUIZ2;    
                    break;
                case 3:
                quizLevelType= ActionType.QUIZ3;    
                    break;
            
                  default:
                return QuizResponseDto.builder()
                    .result("잘못된요청")
                    .stateChange(StateUpdateInfoDto.builder().build())
                    .build();
            }
        return quizService.submitQuizResult(playerId, quizLevelType, result);
    }
}
