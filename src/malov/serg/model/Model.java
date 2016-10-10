package malov.serg.model;

import malov.serg.view.View;
import malov.serg.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serg on 10.10.2016.
 */
public class Model {

    private Provider[] providers;
    private View view;

    public Model(View view, Provider[] providers)
    {
        if (providers == null || providers.length == 0 || view == null)
            throw new IllegalArgumentException();
        this.providers = providers;
        this.view = view;
    }

    public void selectCity(String city)
    {
        List<Vacancy> allvacancyList = new ArrayList<>();
        for (Provider provid : providers)
        {
            for (Vacancy a : provid.getJavaVacancies(city))
                allvacancyList.add(a);

        }
        view.update(allvacancyList);
    }
}
