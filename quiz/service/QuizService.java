package quiz.service;

public class QuizService {
    private static volatile QuizService instance = new QuizService();

    private QuizService() {}

    public static QuizService getInstance() {
        return instance;
    }
}
