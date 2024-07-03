package com.watchbaseballlive.service;

import com.watchbaseballlive.domain.GameEntity;
import com.watchbaseballlive.repository.GameRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KboCrawlingService {
//    private static final String CHROME_DRIVER_PATH = "src/main/resources/static/driver/chromedriver.exe";
    private static final String URL = "https://www.koreabaseball.com/Schedule/Schedule.aspx";
    private static final String TABLE_ID = "tblScheduleList";
    private static final String[] MONTHS = {"07", "08"};
    private static final String YEAR = "2024";
    private final GameRepository gameRepository;

    @Transactional(readOnly = false)
    public void saveGames() {
        WebDriver driver = null;
        try {
            driver = initializeWebDriver();
            navigateToPage(driver);
            for(String month : MONTHS){
                selectYearAndMonth(driver, YEAR, month);
                Document doc = Jsoup.parse(driver.getPageSource());
                List<GameEntity> gameEntities = saveGames(doc);
                gameRepository.saveAll(gameEntities);
            }

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private WebDriver initializeWebDriver() {
//        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        WebDriverManager.chromedriver().browserVersion("126.0.6478.126").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "--lang=ko_KR", "--no-sandbox", "--disable-dev-shm-usage");
//        options.addArguments("headless", "--lang=ko_KR");
        return new ChromeDriver(options);
    }

    private void navigateToPage(WebDriver driver) {
        driver.get(URL);
    }

    private void selectYearAndMonth(WebDriver driver, String year, String month) {
        selectOption(driver, "ddlYear", year);
        selectOption(driver, "ddlMonth", month);
    }

    private void selectOption(WebDriver driver, String selectId, String value) {
        Select select = new Select(driver.findElement(By.id(selectId)));
        select.selectByValue(value);
    }

    private List<GameEntity> saveGames(Document doc) throws UnhandledAlertException {
        Element table = doc.select("table#" + TABLE_ID).first();
        if (table == null) {
            System.out.println("테이블을 찾을 수 없습니다.");
        }
        return GamesFromParsing(table);
    }

    private List<GameEntity> GamesFromParsing(Element table) {
        Elements rows = table.select("tbody tr");
        List<GameEntity> games = new ArrayList<>();
        Element Day = null;
            for (Element row : rows) {
            Element day = row.selectFirst("td.day");
            Element time = row.selectFirst("td.time");
            Element awayTeam = row.selectFirst("td.play > span");
//            Element vs = row.selectFirst("td.play > em"); // 이미 진행된 경기일경우 vs가 아니라 점수까지 표시됨(8vs5 em> span1 span2)
            Element homeTeam = row.selectFirst("td.play > span:nth-child(3)");
            Element location = row.selectFirst("td:nth-child(8)");
                if ("-".equals(location.text())) {
                    location = row.selectFirst("td:nth-child(7)");
                }
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
            assert awayTeam != null;
            assert homeTeam != null;
            games.add(GameEntity.fromCrawling(gameTime, awayTeam, homeTeam, location));

        }
            return games;
    }
    private LocalDateTime createLocalDateTime(Element day, Element time) {
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