package malov.serg;

import malov.serg.model.HHStrategy;
import malov.serg.model.Model;
import malov.serg.model.Provider;
import malov.serg.view.HtmlView;

/**
 * Created by Serg on 10.10.2016.
 */
public class Aggregator {

    public static void main(String[] args)
    {

        Provider[] arraysproviders = { new Provider(new HHStrategy())};
        HtmlView view = new HtmlView();
        view.setController(new Controller(new Model(view, arraysproviders)));
        view.userCitySelectEmulationMethod();



    }
}
