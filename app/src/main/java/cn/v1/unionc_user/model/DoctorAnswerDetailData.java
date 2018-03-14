package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/8.
 */

public class DoctorAnswerDetailData extends BaseData {


    /**
     * data : {"questionInfo":{"Answer":"回答","PageView":"17","Question":"提问","QuestionId":"1"}}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * questionInfo : {"Answer":"回答","PageView":"17","Question":"提问","QuestionId":"1"}
         */

        private QuestionInfoData questionInfo;

        public QuestionInfoData getQuestionInfo() {
            return questionInfo;
        }

        public void setQuestionInfo(QuestionInfoData questionInfo) {
            this.questionInfo = questionInfo;
        }

        public static class QuestionInfoData {
            /**
             * Answer : 回答
             * PageView : 17
             * Question : 提问
             * QuestionId : 1
             */

            private String Answer;
            private String PageView;
            private String Question;
            private String QuestionId;

            public String getAnswer() {
                return Answer;
            }

            public void setAnswer(String Answer) {
                this.Answer = Answer;
            }

            public String getPageView() {
                return PageView;
            }

            public void setPageView(String PageView) {
                this.PageView = PageView;
            }

            public String getQuestion() {
                return Question;
            }

            public void setQuestion(String Question) {
                this.Question = Question;
            }

            public String getQuestionId() {
                return QuestionId;
            }

            public void setQuestionId(String QuestionId) {
                this.QuestionId = QuestionId;
            }
        }
    }
}
