package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private ElementsCollection cards = $$("li div[data-test-id]");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";
  private String cssLocatorForTopUpButton = "[data-test-id='action-deposit']";
  private SelenideElement pageRefresh = $("[data-test-id='action-reload']");


  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public MoneyTransfer topUpButtonClick(DataHelper.InfoCard card) {
    cards.findBy(attribute("data-test-id", card.getDataTestId())).$(cssLocatorForTopUpButton).click();
    return new MoneyTransfer();
  }

  public SelenideElement findCard(DataHelper.InfoCard card) {
    return cards.findBy(attribute("data-test-id", card.getDataTestId()));
  }

  public int getCardBalance(DataHelper.InfoCard card) {
    val text = findCard(card).text();
    return extractBalance(text);
  }

  private int extractBalance(String text) {
    int start = text.indexOf(balanceStart);
    int finish = text.indexOf(balanceFinish);
    String value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }
}
