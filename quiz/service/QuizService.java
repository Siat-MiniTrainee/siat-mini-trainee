package quiz.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import action.model.dao.ActionDao;
import action.model.domain.ActionType;
import quiz.model.dao.QuizDao;
import quiz.model.domain.QuizConfirmResponseDto;
import quiz.model.domain.QuizDto;
import quiz.model.domain.QuizResponseDto;
import quiz.model.domain.QuizType;
import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateInfoDto;
import state.model.domain.StateUpdateRequestDto;
import state.service.StateService;

public class QuizService {
    private static volatile QuizService instance = new QuizService();
    private StateService stateService;
    private ActionDao actionDao;
    private QuizDao quizDao;

    public QuizService() {
        this.quizDao = QuizDao.getInstance();
        this.actionDao = ActionDao.getInstance();
        this.stateService = StateService.getInstance();
    }

    public static QuizService getInstance() {
        return instance;
    }

    public List<QuizDto> getQuizListByDifficulty(int difficulty, QuizType quizType) {
        List<QuizDto> level1Quizzes = new ArrayList<>();
        List<QuizDto> level2Quizzes = new ArrayList<>();
        List<QuizDto> level3Quizzes = new ArrayList<>();
        List<QuizDto> finalQuizList = new ArrayList<>();
        if (quizType == QuizType.ALL) {
            level1Quizzes = quizDao.selectQuizzesByLevel(1);
            level2Quizzes = quizDao.selectQuizzesByLevel(2);
            level3Quizzes = quizDao.selectQuizzesByLevel(3);
            
        } else {
            level1Quizzes = quizDao.selectQuizzesByTypeAndLevel(quizType, 1);
            level2Quizzes = quizDao.selectQuizzesByTypeAndLevel(quizType, 2);
            level3Quizzes = quizDao.selectQuizzesByTypeAndLevel(quizType, 3);
        }
        if (difficulty == 2) {
            finalQuizList.addAll(getRandomSubset(level1Quizzes, Math.max((int) (5 * 0.3),1))); // 30%
            finalQuizList.addAll(getRandomSubset(level2Quizzes, Math.max((int) (5 * 0.4),1))); // 40%
            finalQuizList.addAll(getRandomSubset(level3Quizzes, Math.max((int) (5 * 0.3),1))); // 30%
        } else if (difficulty == 3) {
            finalQuizList.addAll(getRandomSubset(level1Quizzes, Math.max((int) (8 * 0.1),1))); // 10%
            finalQuizList.addAll(getRandomSubset(level2Quizzes, Math.max((int) (8 * 0.4),1))); // 40%
            finalQuizList.addAll(getRandomSubset(level3Quizzes, Math.max((int) (8 * 0.5),1))); // 50%
        }

        Collections.shuffle(finalQuizList);
        return finalQuizList;
    }

    private List<QuizDto> getRandomSubset(List<QuizDto> sourceList, int size) {
        if (sourceList == null || sourceList.isEmpty()) {
            return List.of();
        }
        Collections.shuffle(sourceList); // 랜덤화
        return sourceList.subList(0, Math.min(size, sourceList.size())); // 부분 리스트 반환
    }

    public QuizConfirmResponseDto confirmQuiz(int quizId, String answer) 
    {
        Optional<QuizConfirmResponseDto> result = quizDao.selectQuizAnswer(quizId, answer);
        if (result.isPresent()) {
            return result.get();
        } else {
            
            return quizDao.selectQuiz(quizId).orElse(QuizConfirmResponseDto.builder()
                    .result("잘못된 퀴즈 번호")
            .build());
        }
      
    }

    public QuizResponseDto submitQuizResult(int playerId, ActionType quizType, String result) {
        StateUpdateInfoDto updateInfo = actionDao.selectActionStatChanges(quizType);
        if (result.equals("성공")) {
            Optional<StateResponseDto> playerStateResult = stateService.getState(playerId);
            if (playerStateResult.isPresent()) {
                Optional<StateUpdateInfoDto> updateResult = stateService.updateState(StateUpdateRequestDto.builder()
                .playerId(playerId)
                .hp(updateInfo.getHp())
                .mp(updateInfo.getMp())
                .intelligence(updateInfo.getIntelligence())
                .strength(updateInfo.getStrength())
                .money(updateInfo.getMoney()
                + updateInfo.getMoney() * playerStateResult.get().getIntelligence() * 0.1)
                        .build());
                if (updateResult.isPresent()) {
                    return QuizResponseDto.builder()
                        .result("성공")
                        .stateChange(updateResult.get())
                        .build();
                }
            }
        } else {
            Optional<StateUpdateInfoDto> updateResult = stateService.updateState(StateUpdateRequestDto.builder()
                    .hp(updateInfo.getHp())
                    .mp(updateInfo.getMp())
                    .build());
            if (updateResult.isPresent()) {
                return QuizResponseDto.builder()
                        .result("성공")
                        .stateChange(StateUpdateInfoDto.builder().build())
                        .build();
            }
        }
        return QuizResponseDto.builder()
        .result("실패")
        .build();
    }
}
