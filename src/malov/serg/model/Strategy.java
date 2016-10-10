package malov.serg.model;

import malov.serg.vo.Vacancy;

import java.util.List;

/**
 * Created by Serg on 10.10.2016.
 */
public interface Strategy {

    public List<Vacancy> getVacancies(String searchString);

}
