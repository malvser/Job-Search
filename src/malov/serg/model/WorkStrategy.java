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
public class WorkStrategy implements Strategy {

    private String city;
    private static final String URL_FORMAT = "https://www.work.ua/jobs-%s-java/?page=%d";

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String userAgent="Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
        String referrer="https://www.work.ua/jobs-kyiv-java/?page=1";
        switch (searchString) {
            case "Kiev":
                city = "kyiv";
                break;
            case "Odessa":
                city = "Odesa";
                break;
            case "Lviv":
                city = "lviv";
                break;
            case "Harkov":
                city = "kharkiv";
                break;
            case "Sumy":
                city = "sumy";
                break;

        }


        String url = String.format(URL_FORMAT, city, page);
        return Jsoup.connect(url).userAgent(userAgent).referrer(referrer).get();

    }



    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> vacancies = new ArrayList<>();


        int i = 1;
        try
        {
            while (true)
            {

                Document html = getDocument(searchString, i++);
                Elements s = html.getElementsByAttributeValueContaining("class", "card card-hover card-visited job-link");

                if (s.size() == 0)
                {
                    break;
                }
                for (Element element : s)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl("https://www.work.ua" + element.getElementsByTag("a").attr("href"));
                    vacancy.setCompanyName(element.getElementsByTag("span").text());
                    vacancy.setCity(element.getElementsByAttribute("span").text());
                    vacancy.setSiteName(html.title());
                    vacancy.setSalary(element.getElementsByAttributeValue("class", "nowrap").text());
                    vacancy.setTitle(element.getElementsByTag("a").text());
                    vacancy.setDate(element.getElementsByAttributeValue("class", "dt").text());
                    vacancies.add(vacancy);
                }
            }
        }catch (IOException e)
        {
            e.getStackTrace();
        }
        return vacancies;
    }
}
