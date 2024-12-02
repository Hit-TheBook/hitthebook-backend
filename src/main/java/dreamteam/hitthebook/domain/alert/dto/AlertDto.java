package dreamteam.hitthebook.domain.alert.dto;


import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class AlertDto {
    @Data
    @NoArgsConstructor
    public static class AlertResponseDto{
        private List<AlertContent> alertNoticeList = new ArrayList<>();
        private List<AlertContent> alertLevelEmblemList = new ArrayList<>();
        private List<AlertContent> alertStudyList = new ArrayList<>();
        public AlertResponseDto(List<Alert> noticeList, List<Alert> levelList, List<Alert> emblemList, List<Alert> timerList, List<Alert> plannerList) {
            for(Alert alert : noticeList){
                alertNoticeList.add(new AlertContent(alert.getAlertType(), alert.getAlertTitle(), alert.getAlertContent()));
            }
            for(Alert alert : levelList){
                alertLevelEmblemList.add(new AlertContent(alert.getAlertType(), alert.getAlertTitle(), alert.getAlertContent()));
            }
            for(Alert alert : emblemList){
                alertLevelEmblemList.add(new AlertContent(alert.getAlertType(), alert.getAlertTitle(), alert.getAlertContent()));
            }
            for(Alert alert : timerList){
                alertStudyList.add(new AlertContent(alert.getAlertType(), alert.getAlertTitle(), alert.getAlertContent()));
            }
            for(Alert alert : plannerList){
                alertStudyList.add(new AlertContent(alert.getAlertType(), alert.getAlertTitle(), alert.getAlertContent()));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class AlertContent{
        private String title;
        private String text;
        private AlertTypeEnum alertType;
        public AlertContent(AlertTypeEnum alertType, String title, String text){
            this.alertType = alertType;
            this.title = title;
            this.text = text;
        }
    }
}
