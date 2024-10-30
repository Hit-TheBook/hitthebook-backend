package dreamteam.hitthebook.domain.alert.dto;


import dreamteam.hitthebook.domain.alert.entity.Alert;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class AlertDto {

    @Data
    @NoArgsConstructor
    public static class AlertContents{
        private Long alertId;
        private String alertContent;
        private boolean isChecked;

        public AlertContents(Alert alert){
            this.alertId = alert.getAlertId();
            this.alertContent = alert.getAlertContent();
            this.isChecked = alert.isChecked();
        }
    }

    @Data
    @NoArgsConstructor
    public static class AlertListDto{
        private List<AlertContents> alertContentsList = new ArrayList<>();
        public AlertListDto(List<Alert> alertList){
            for(Alert alert : alertList){
                this.alertContentsList.add(new AlertContents(alert));
            }
        }
    }
}
