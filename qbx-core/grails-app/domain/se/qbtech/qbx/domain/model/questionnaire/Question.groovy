package se.qbtech.qbx.domain.model.questionnaire

import se.qbtech.qbx.domain.model.common.BasePersistentObject

class Question extends BasePersistentObject {

    String questionDescription
    int ageGroup

    static hasMany = [questionAnswers: QuestionAnswer]

    static constraints = {
        questionDescription(null: false)
        ageGroup(null: false)
    }
}
