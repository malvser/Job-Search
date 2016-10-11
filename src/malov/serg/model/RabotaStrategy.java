package malov.serg.model;

import malov.serg.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serg on 11.10.2016.
 */
public class RabotaStrategy implements Strategy {

    private Integer city;
    private static final String URL_FORMAT = "http://rabota.ua/jobsearch/vacancy_list?regionId=%d&keyWords=java&pg=%d";

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        String referrer = "http://rabota.ua/";


        switch (searchString)
        {
            case "Kiev":
                city = 1;
                break;
            case "Odessa":
                city = 3;
                break;
            case "Lviv":
                city = 2;
                break;
            case "Harkov":
                city = 21;
                break;
            case "Sumy":
                city = 19;
                break;

        }
        String url = String.format(URL_FORMAT, city, page);
        return Jsoup.connect(url).userAgent(userAgent).referrer(referrer).get();

    }


    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();


        int i = 1;
        try {
            while (true) {

                Document html = getDocument(searchString, i++);
                Elements s = html.getElementsByAttributeValue("class", "v");

                if (s.size() == 0) {
                    break;
                }
                for (Element element : s) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl("http://rabota.ua" + element.getElementsByAttributeValue("class", "t").attr("href"));
                    vacancy.setCompanyName(element.getElementsByAttributeValue("class", "rua-p-c-default").text());
                    vacancy.setCity(element.getElementsByAttributeValue("class", "s").text());
                    vacancy.setSiteName(html.title());
                    vacancy.setSalary(element.getElementsByTag("b").text());
                    vacancy.setTitle(element.getElementsByAttributeValue("class", "tags").text());
                    vacancy.setDate(element.getElementsByAttributeValue("class", "dt").text());
                    vacancies.add(vacancy);
                }

            }

        } catch (IOException e) {
            e.getStackTrace();
        }
        return vacancies;
    }
}
