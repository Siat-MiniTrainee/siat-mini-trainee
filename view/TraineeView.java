package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TraineeView {

    public TraineeView() {
    }


    public void exec () {
        inputbox("개발자 생성", "이름을 입력하세요.");
        while (true) {
            int userChoice = mainMenu();
            switch (userChoice) {
                case 1:
                    studyMenu();
                    break;
                case 2:
                    exerciseMenu();
                    break;
                case 3:
                    workMenu();
                    break;
                case 4:
                    restMenu();
                    break;
                case 7:
                    shopLoop();
                    break;
                case 8:
                    inventoryLoop();
                    break;
                case 0:
                    System.out.println("종료합니다.");
                    return;
            
                default:
                    break;
            }
        }
    }

    public void shopLoop() {

        while (true) {

            String title = "상점";

            clear();
            System.out.println(AsciiArts.artShop);
            margin();
            System.out.println("[ 아이템 목록 ]");
            margin();
            System.out.println("  0. 뒤로");

            int userChoice = inputInt();
            switch (userChoice) {
                case 0:
                    return;
            
                default:
                    break;
            }
        }
    }

    public void inventoryLoop() {
        while (true) {

            String title = "소지품";

            clear();
            printBanner(title);
            margin();
            System.out.println("[ 아이템 목록 ]");
            margin();
            System.out.println("  0. 뒤로");

            int userChoice = inputInt();
            switch (userChoice) {
                case 0:
                    return;
            
                default:
                    break;
            }
        }
    }


    public void studyMenu() {
        int userChoice = ynMessagebox("공부", 
        "공부하시겠습니까? (Y/N)\r",
        1);
        if (userChoice == 1) {
            messagebox("공부 완료 메시지", "대충 내용");
            quizMenu();
        }
    }

    public void quizMenu() {
        clear();
        divider();
        System.out.println(AsciiArts.artQuiz);
        String area = "파이썬";
        String difficulty = "3";
        String content = "대충 문제";

        System.out.println("범위: "+area+"난이도: "+difficulty);
        margin();
        System.out.println(content);
        margin();

        inputInt();
    }

    public void exerciseMenu() {
        int userChoice = ynMessagebox("운동", 
        "운동하시겠습니까? (Y/N)\r",
        2);
    }

    public void restMenu() {
        int userChoice = ynMessagebox("휴식", 
        "휴식하시겠습니까? (Y/N)\r",
        3);
    }

    public void workMenu() {
        int userChoice = ynMessagebox("알바", 
        "알바하시겠습니까? (Y/N)\r");
    }

    public int mainMenu() {

        String title = "개발자 스토리";

        clear();
        printBanner(title, 1);
        margin();
        printStats();
        margin();
        printActions();
        margin();
        return inputInt();
    }

    public void margin (int size) {
        for (int i=0; i < size; i++ ) {
            System.out.println();
        }
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void margin () {
        margin(1);
    }

    public int ynMessagebox(String title, String content, int ascii) {
        clear();
        divider();
        System.out.println("[ "+title+" ]");

        switch (ascii) {
            case 1:
                System.out.println(AsciiArts.artBook);
                break;
            case 2:
                System.out.println(AsciiArts.artRunning);
                break;
            case 3:
                System.out.println(AsciiArts.artSofa);
                break;
            default:
                margin();
                break;
        }

        System.out.println(content);
        String yn = inputString();
        if (yn.equals("y") || yn.equals("Y")) {
            return 1;
        }
        return 0;
    }

    public int ynMessagebox(String title, String content) {
        return ynMessagebox(title, content, 0);
    }

    public void messagebox(String title, String content) {
        clear();
        divider();
        margin();
        System.out.println("[ "+title+" ]");
        margin();
        System.out.println(content);
        margin();
        inputEnter();
    }

    public String inputbox(String title, String content) {
        clear();
        divider();
        margin();
        System.out.println("[ "+title+" ]");
        margin();
        System.out.println(content);
        margin();
        return inputString();
    }

    public void printBanner(String title) {
        printBanner(title, 0);
    }
    
    public void printBanner(String title, int style) {
        String bannerStyle = null;
        boolean underLine = true;
        switch (style) {
            case 1:
                bannerStyle = "=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-";
                break;
            case 2:
                bannerStyle = "============================================================";
                break;
            case 3:
                underLine = false;
                break;
            default:
                bannerStyle = "------------------------------------------------------------";
                break;
        }
        System.out.println(bannerStyle);
        System.out.println();
        System.out.println("          "+title);
        System.out.println();
        if (underLine) {
            System.out.println(bannerStyle);
        }
    }

    public void printStats() {

        int temp = 0;
        System.out.println("[ 상태 ]");
        margin();
        System.out.println("  체  력 : "+ temp);
        System.out.println("  정신력 : "+ temp);
        System.out.println("  지  능 : "+ temp);
        System.out.println("  힘     : "+ temp);
        System.out.println("  허  기 : "+ temp);
        System.out.println("  소지금 : "+ temp);

    }

    public void printActions() {
        System.out.println("[ 행동 선택 ]              [ 메뉴 ]");
        margin();
        System.out.println("  1. 공부                    7. 상점");
        System.out.println("  2. 운동                    8. 소지품");
        System.out.println("  3. 알바                    0. 종료");
        System.out.println("  4. 휴식");
        margin();
    }

    public void divider() {
        System.out.println("------------------------------------------------------------");
    }

    public void getState() {
        return;
    }
    public void inputEnter() {
        System.out.println("------------------------------------------------------------");
        System.out.print("엔터를 눌러 메인 메뉴로 이동.");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public String inputString() {
        System.out.println("------------------------------------------------------------");
        System.out.print(">>>");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public int inputInt() {
        int result = 0;
        System.out.println("------------------------------------------------------------");
        while (true) {
            try {
                System.out.print(">>>");
                Scanner s = new Scanner(System.in);
                result = s.nextInt();
            } catch(InputMismatchException e) {
                System.err.println("숫자를 입력하세요.");
                continue;
            }
            return result;
        }
    }
}
    
