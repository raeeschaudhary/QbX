package se.qbtech.qbx.domain.model.questionnaire

import se.qbtech.qbx.domain.model.common.BasePersistentObject

class AnswerOption extends BasePersistentObject {

    String answer
    int scaleValue

    static hasMany = [questionAnswers: QuestionAnswer]

    static constraints = {
        answer(null: false)
        scaleValue(null: false)
    }
}
