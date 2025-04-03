package action.service;

import java.sql.SQLException;

import action.model.dao.ActionDao;
import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import state.service.StateService;

public class ActionService {
    private static volatile ActionService instance = new ActionService();
    private StateService stateService;
    private ActionDao actionDao;

    private ActionService(){
        this.actionDao = ActionDao.getInstance();
    }

    public static ActionService getInstance() {
        return instance;
    }

    public void performAction(String actionType, int mentalDelta, int physicalDelta, int intelligenceDelta) throws SQLException {
        // 각 상태별로 데이터베이스에서 현재 값을 가져옵니다.
        int currentMental = actionDao.getStat("mental");
        int currentPhysical = actionDao.getStat("physical");
        int currentIntelligence = actionDao.getStat("intelligence");

        // 상태 값을 업데이트합니다.
        actionDao.updateStat("mental", currentMental + mentalDelta);
        actionDao.updateStat("physical", currentPhysical + physicalDelta);
        actionDao.updateStat("intelligence", currentIntelligence + intelligenceDelta);
    }

    public ActionResponseDto doAction(int playerId, ActionType actionType) {
        // actionDao에서 해당 action(문자열 from action where action_name = actionType)
        // 인 액션을 찾고 action_stat_change와 stat_change 테이블을 조인하여 해당 액션의 스텟변경 수치를 찾는다
        // 변경수치를 StateUpdateReqestDto에 담는다. State서비스의 updateState(playerId, updateDto) 함수를 호출한다(구현예정)
        // 서비스를 호출해서 변경에 성공하면 ActionResponseDto에 변경된 수치 정보를 StateUpdateInfoDto에 담고 결과 정보를 담아서 리턴한다.
        actionDao.
        return null;
    }

    private void StudyAction() {
        try {
             actionService.performAction("STUDY", -3, -2, +1);
             System.out.println("공부중입니다.. 정신력 -3, 체력 -2, 지능 +1");
         } catch (SQLException e) {
             e.printStackTrace();
         }
        
     }
 
     private void ExerciseAction() {
         try {
             actionService.performAction("EXERCISE", -1, -1, 0);
             System.out.println("운동중입니다.. 정신력 -1, 체력 -1");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 
     private void RestAction() {
         try {
             actionService.performAction("REST", +1, +1, 0);
             System.out.println("휴식 중입니다.. 정신력 +5, 체력 +5");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     
     public void Quiz1Action() {
         try {
             actionService.performAction("QUIZ1", 0, 0, +1);
             System.out.println("퀴즈 중입니다.. 지능 +1");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 
     public void Quiz2Action() {
         try {
             actionService.performAction("QUIZ2", 0, 0, +1);
             System.out.println("퀴즈 중입니다.. 지능 +1");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 
     public void Quiz3Action() {
         try {
             actionService.performAction("QUIZ3", 0, 0, +1);
             System.out.println("퀴즈 중입니다.. 지능 +1");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 
     public void WorkAction() {
         try {
             actionService.performAction("PART_TIME_JOB", 0, -1, 0); 
             System.out.println("아르바이트 중입니다.. 체력 -1");
         } catch (SQLException e) {
             e.printStackTrace();
         }
     
     }    


}
