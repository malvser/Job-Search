package malov.serg;

import malov.serg.model.Model;

/**
 * Created by Serg on 10.10.2016.
 */
public class Controller {

    private Model model;

    public Controller(Model model)
    {
        if(model == null) throw new IllegalArgumentException();
        this.model = model;
    }

    public void onCitySelect(String cityName){

        model.selectCity(cityName);
    }
}
