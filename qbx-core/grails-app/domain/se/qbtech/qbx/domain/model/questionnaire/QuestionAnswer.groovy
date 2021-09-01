package se.qbtech.qbx.domain.model.questionnaire

import se.qbtech.qbx.domain.model.common.BasePersistentObject
import se.qbtech.qbx.domain.model.qtest.TestSubject

class QuestionAnswer extends BasePersistentObject {

    static belongsTo = [testSubject: TestSubject, question: Question, answer: AnswerOption]

    static constraints = {
        testSubject(null: false)
        question(null: false)
        answer(null: false)
    }

}
