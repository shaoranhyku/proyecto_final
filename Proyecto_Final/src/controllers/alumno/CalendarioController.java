package controllers.alumno;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalendarioController {

    public GridPane grid_days;
    public List<AnchorPane> listDays;
    public Label lbl_monthName;
    public Button btn_previousMonth;
    public Button btn_nextMonth;
    public YearMonth currentDisplayYearMonth;
    public PrincipalController callBack;

    public void initialize() throws IOException {
        listDays = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPane node = FXMLLoader.load(getClass().getResource("/fxml/item/gridItem_dia.fxml"));
                grid_days.add(node, j, i);
                listDays.add(node);
            }
        }

        currentDisplayYearMonth = YearMonth.now();

        displayMonthInCalendar(currentDisplayYearMonth);

        btn_nextMonth.setOnMouseClicked(event -> {
            currentDisplayYearMonth = currentDisplayYearMonth.plusMonths(1);
            displayMonthInCalendar(currentDisplayYearMonth);
        });

        btn_previousMonth.setOnMouseClicked(event -> {
            currentDisplayYearMonth = currentDisplayYearMonth.minusMonths(1);
            displayMonthInCalendar(currentDisplayYearMonth);
        });
    }

    private void displayMonthInCalendar(YearMonth currentYearMonth) {
        LocalDate calendarDate = LocalDate.of(currentYearMonth.getYear(), currentYearMonth.getMonthValue(), 1);
        while (!calendarDate.getDayOfWeek().toString().equals("MONDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }

        for (AnchorPane node : listDays) {
            if (calendarDate.getMonth().equals(currentYearMonth.getMonth())){
                Label LabelDayNumber = (Label) node.lookup("#lbl_dayNumber");
                LabelDayNumber.setText(String.valueOf(calendarDate.getDayOfMonth()));
                LocalDate finalCalendarDate = calendarDate;
                node.setOnMouseClicked(event -> {
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        if (event.getClickCount() == 2) {
                            callBack.setCenterDia(finalCalendarDate);
                        }
                    }
                });
            }else{
                Label LabelDayNumber = (Label) node.lookup("#lbl_dayNumber");
                LabelDayNumber.setText("");
                node.setOnMouseClicked(event -> {
                });
            }
            calendarDate = calendarDate.plusDays(1);
        }

        lbl_monthName.setText(com.sun.xml.internal.ws.util.StringUtils.capitalize(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))));
    }
}
