package com.amazontest;

import com.amazontest.domain.GameEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class KboCrawlingService {
    private static final String CHROME_DRIVER_PATH = "src/main/resources/static/driver/chromedriver.exe";
    private static final String URL = "https://www.koreabaseball.com/Schedule/Schedule.aspx";
    private static final String TABLE_ID = "tblScheduleList";

    public void saveGames() {
        WebDriver driver = null;
        try {
            driver = initializeWebDriver();
            navigateToPage(driver);
            selectYearAndMonth(driver, "2024", "06");
            Document doc = Jsoup.parse(driver.getPageSource());
            processTable(doc);
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static WebDriver initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "--lang=ko_KR");
        return new ChromeDriver(options);
    }

    private static void navigateToPage(WebDriver driver) {
        driver.get(URL);
    }

    private static void selectYearAndMonth(WebDriver driver, String year, String month) {
        selectOption(driver, "ddlYear", year);
        selectOption(driver, "ddlMonth", month);
    }

    private static void selectOption(WebDriver driver, String selectId, String value) {
        Select select = new Select(driver.findElement(By.id(selectId)));
        select.selectByValue(value);
    }

    private static void processTable(Document doc) {
        Element table = doc.select("table#" + TABLE_ID).first();
        if (table == null) {
            System.out.println("테이블을 찾을 수 없습니다.");
            return;
        }

        printTableHeaders(table);
        printTableRows(table);
    }

    private static void printTableHeaders(Element table) {
        List<String> headers = table.select("thead th").stream()
                .map(Element::text)
                .collect(Collectors.toList());
        System.out.println(String.join("\t", headers));
    }

    private static void printTableRows(Element table) {
        Elements rows = table.select("tbody tr");
        Element Day = null;
            for (Element row : rows) {
            Element day = row.selectFirst("td.day");
            Element time = row.selectFirst("td.time");
            Element team1 = row.selectFirst("td.play > span");
            Element vs = row.selectFirst("td.play > em"); // 이미 진행된 경기일경우 vs가 아니라 점수까지 표시됨(8vs5 em> span1 span2)
            Element team2 = row.selectFirst("td.play > span:nth-child(3)");
            Element location = row.selectFirst("td:nth-child(8)");
            if (day == null) {
                day = Day;
            } else {
                Day = day;
            }
            if (time == null) {
                break;
            }
            LocalDateTime gameTime = createLocalDateTime(day,time);

            assert location != null;
            assert team2 != null;
            assert team1 != null;
            GameEntity gameEntity = GameEntity.fromCrawling(gameTime, team1, team2, location);

        }
    }
    public static LocalDateTime createLocalDateTime(Element day, Element time) {
        String dayStr = day.text(); // "06.01(토)"
        String timeStr = time.text(); // "17:00"

        // 날짜 문자열에서 월과 일 추출
        String[] dateParts = dayStr.split("\\.");
        int month = Integer.parseInt(dateParts[0]);
        int dayOfMonth = Integer.parseInt(dateParts[1].split("\\(")[0]);

        // 시간 문자열에서 시와 분 추출
        String[] timeParts = timeStr.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // 현재 년도 가져오기 (또는 필요한 경우 특정 년도 설정)
        int year = LocalDate.now().getYear();

        // LocalDateTime 객체 생성 및 반환
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

}