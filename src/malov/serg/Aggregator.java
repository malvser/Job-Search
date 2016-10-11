package malov.serg;

import malov.serg.model.*;
import malov.serg.view.HtmlView;

/**
 * Created by Serg on 10.10.2016.
 */
public class Aggregator {

    public static void main(String[] args)
    {

        Provider[] arraysproviders = { new Provider(new HHStrategy()), new Provider(new WorkStrategy()), new Provider(new RabotaStrategy())};
        HtmlView view = new HtmlView();
        view.setController(new Controller(new Model(view, arraysproviders)));
        view.userCitySelectEmulationMethod();



    }
}
