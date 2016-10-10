package malov.serg.model;

import malov.serg.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Serg on 10.10.2016.
 */
public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String userAgent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";
        String referrer="https://www.google.com.ua";
        return Jsoup.connect(String.format(URL_FORMAT, searchString, page)).userAgent(userAgent).referrer(referrer).get();

    }

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> vacancies = new ArrayList<>();


        int i = 0;
        try
        {
            while (true)
            {

                Document html = getDocument(searchString, i++);
                Elements s = html.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if (s.size() == 0)
                {
                    break;
                }
                for (Element element : s)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setSiteName(html.title());
                    vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text());
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setDate(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-date").text());
                    vacancies.add(vacancy);
                }
            }
        }catch (IOException e)
        {

        }
        return vacancies;
    }
}
