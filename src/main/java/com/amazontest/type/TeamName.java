package com.amazontest.type;

public enum TeamName {
    LOTTE("롯데", "Lotte Giants"),
    DOOSAN("두산", "Doosan Bears"),
    KIA("KIA", "KIA Tigers"),
    SAMSUNG("삼성", "Samsung Lions"),
    SSG("SSG", "SSG Landers"),
    NC("NC", "NC Dinos"),
    LG("LG", "LG Twins"),
    KIWOOM("키움", "Kiwoom Heroes"),
    KT("KT", "KT Wiz"),
    HANWHA("한화", "Hanwha Eagles");
    private final String koreanName;
    private final String englishName;

    TeamName(String koreanName, String englishName) {
        this.koreanName = koreanName;
        this.englishName = englishName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public String getEnglishName() {
        return englishName;
    }
    public static TeamName fromKoreanName(String koreanName) {
        for (TeamName team : TeamName.values()) {
            if (team.getKoreanName().equals(koreanName)) {
                return team;
            }
        }
        throw new IllegalArgumentException("Unknown team name: " + koreanName);
    }
}
