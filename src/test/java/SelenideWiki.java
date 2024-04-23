import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.DownloadActions.click;

public class SelenideWiki {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "2560 x 1440";
        Configuration.baseUrl = "https://github.com/";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
        Configuration.timeout = 5000; // default 4000
    }

    @Test
    void openWiki() {
        //открыть страницу
        open("/selenide/selenide");
        //перейти в раздел вики
        $("#wiki-tab").click();
    }
    @Test
    void pages() {
        //открыть страницу
        open("/selenide/selenide/wiki");
        //проскроллить страницу (наверное можно как-то доскроллить до какого-то элемента, скролл до show more pages не помог )
        actions().sendKeys(Keys.chord(Keys.SPACE)).perform();
        //раскрыть все страницы
        $("#wiki-pages-box").$(withText("more pages")).click();
        //проверить, что есть страница SoftAssertions среди страниц вики
        $("#wiki-pages-box").shouldHave(text("SoftAssertions"));
        //открыть страницу SoftAssertions
        $("#wiki-pages-box").$(withText("SoftAssertions")).click();

    }
    @Test
    void findCode() {
        //открыть страницу
        open("/selenide/selenide/wiki/SoftAssertions");
        //скролл до раздела с JUnit5
        actions().moveToElement($((byText("3. Using JUnit5 extend test class:")))).click().perform();
        //проверка наличия кода
        $("div.markdown-body").$(byText("3. Using JUnit5 extend test class:"))
                .closest("div").sibling(0).should(exist);

    }
}
