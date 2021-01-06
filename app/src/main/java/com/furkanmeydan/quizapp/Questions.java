package com.furkanmeydan.quizapp;

public class Questions {

    public static final String CATEGORY_COMPUTERS = "Computers";
    public static final String CATEGORY_HISTORY = "History";
    public static final String CATEGORY_MATHS = "Maths";
    public static final String CATEGORY_ENGLISH = "English";
    public static final String CATEGORY_GRAPHICS = "Graphics";


    public static final int LEVEL1 = 1;
    public static final int LEVEL2 =2;
    public static final int LEVEL3 = 3;

    private String question ,option1,option2,option3,option4, category;
    private int levels;

    private int answerNr;

    //daha sonra bu classtan soru nesneleri üretmek için yazıldı.

    public Questions(){}

    public Questions(String question, String option1, String option2, String option3, String option4, int answerNr,String category, int levels) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
        this.category=category;
        this.levels=levels;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }
}
