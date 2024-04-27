import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideWiki {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "2560 x 1440";
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadStrategy = "eager";

    }

    @Test
    void openWiki() {
        //открыть страницу
        open("/selenide/selenide");
        //перейти в раздел вики
        $("#wiki-tab").click();
        //проскроллить страницу (наверное можно как-то доскроллить до какого-то элемента, скролл до show more pages не помог )
        actions().sendKeys(Keys.chord(Keys.SPACE)).perform();
        //раскрыть все страницы
        $("#wiki-pages-box").$(withText("more pages")).click();
        //проверить, что есть страница SoftAssertions среди страниц вики
        $("#wiki-pages-box").shouldHave(text("SoftAssertions"));
        //открыть страницу SoftAssertions
        $("#wiki-pages-box").$(withText("SoftAssertions")).click();
        //скролл до раздела с JUnit5
        actions().moveToElement($((byText("3. Using JUnit5 extend test class:")))).click().perform();
        //проверка наличия кода
        $("div.markdown-body").$(byText("3. Using JUnit5 extend test class:"))
                .closest("div").sibling(0).shouldHave(text("""
                @ExtendWith({SoftAssertsExtension.class})
                class Tests {
                    @Test
                    void test() {
                        Configuration.assertionMode = SOFT;
                        open("page.html");
    
                        $("#first").should(visible).click();
                        $("#second").should(visible).click();
                    }
                }"""));

    }
}
