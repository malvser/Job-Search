package malov.serg.model;

import malov.serg.vo.Vacancy;

import java.util.List;

/**
 * Created by Serg on 10.10.2016.
 */
public class Provider {

    private Strategy strategy;

    public Provider(Strategy strategy)
    {
        this.strategy = strategy;
    }


    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }


    public List<Vacancy> getJavaVacancies(String searchString)
    {

        return strategy.getVacancies(searchString);
    }
}
