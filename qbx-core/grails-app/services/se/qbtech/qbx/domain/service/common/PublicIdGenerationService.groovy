package se.qbtech.qbx.domain.service.common

import org.apache.commons.lang.RandomStringUtils

/**
 * @author Michael Astreiko
 */
class PublicIdGenerationService {
    static transactional = false

    /**
     * Generate PublicId for domain and check if there is no more any other domain with same public Id
     *
     * @param clazz - domain class
     * @return unique public Id
     */
    String generatePublicId(Class clazz) {
        String newId = ""
        boolean isGenerated = false
        clazz.withNewSession {
            while (!isGenerated) {
                newId = generateId()

                if (clazz.countByPublicId(newId, [cache: true]) == 0) {
                    isGenerated = true
                }
            }
        }
        return newId
    }

    private String generateId() {
        return RandomStringUtils.randomAlphanumeric(10)
    }
}
