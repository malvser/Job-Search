package malov.serg.view;

import malov.serg.Controller;
import malov.serg.vo.Vacancy;

import java.util.List;

/**
 * Created by Serg on 10.10.2016.
 */
public interface View {
    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
