package malov.serg.view;

import malov.serg.Controller;
import malov.serg.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

/**
 * Created by Serg on 10.10.2016.
 */
public class HtmlView implements View {


    private Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replace(".", "/") + "/vacancies.html";


    @Override
    public void update(List<Vacancy> vacancies)
    {
        System.out.println(filePath);
        System.out.println(vacancies.size());
        try
        {
            updateFile(getUpdatedFileContent(vacancies));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }

    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod()
    {
        controller.onCitySelect("Kiev");


    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) throws IOException
    {
        String updatedFileContent;
        Document document = getDocument();
        Element tempElement = document.select("[class*=\"template\"]").first();
        //getting pattern Element for new vacancies
        Element pattern = tempElement.clone();
        pattern.removeAttr("style");
        pattern.removeClass("template");
        //remove all tags <tr> with only one class="vacancy"
        document.select("tr[class=\"vacancy\"]").remove();
        //adding new Vacancies to result page updatedFileContent
        for (Vacancy vacancy : vacancies)
        {
            Element newVacancyElement = pattern.clone();

            newVacancyElement.select("[class=\"city\"]").
                    first().text(vacancy.getCity());
            newVacancyElement.select("[class=\"companyName\"]").
                    first().text(vacancy.getCompanyName());
            newVacancyElement.select("[class=\"salary\"]").
                    first().text(vacancy.getSalary());
            newVacancyElement.select("[class=\"date\"]").
                    first().text(vacancy.getDate());




            Element link = newVacancyElement.select("a").first();
            link.text(vacancy.getTitle());
            link.attr("href", vacancy.getUrl());
            tempElement.before(newVacancyElement.outerHtml());
        }
        updatedFileContent = document.html();


        return updatedFileContent;
    }


    private void updateFile(String file)
    {
        try
        {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
            pw.write(file);
            pw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    protected Document getDocument() throws IOException
    {


        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
