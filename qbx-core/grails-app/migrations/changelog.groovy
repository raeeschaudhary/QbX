databaseChangeLog = {

    changeSet(author: "ami (generated)", id: "1391116941367-1") {
        createTable(tableName: "person") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "personPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "email", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "first_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_login_date", type: "datetime")

            column(name: "last_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime")

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "class", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "language", type: "varchar(255)")

            column(name: "preferred_date_format", type: "varchar(10)")

            column(name: "site_id", type: "bigint")

            column(name: "telephone", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-2") {
        createTable(tableName: "person_role") {
            column(name: "role_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "person_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-3") {
        createTable(tableName: "qb_test_param") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "qb_test_paramPK")
            }

            column(name: "key", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "quality_test_result_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "test_result_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "value", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-4") {
        createTable(tableName: "requestmap") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "requestmapPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "config_attribute", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-5") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-6") {
        createTable(tableName: "site") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sitePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "address_address1", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_address2", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_city", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_country_code", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_state", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "address_zip_code", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "email", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime")

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "notes", type: "varchar(5000)") {
                constraints(nullable: "false")
            }

            column(name: "payment_method", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "phone", type: "varchar(255)")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-7") {
        createTable(tableName: "test_result") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "test_resultPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "age", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "country_code", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "deleted", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime")

            column(name: "number_of_buttons", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "number_of_outliers", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "product_number", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "qbx_path", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "submitting_user_id", type: "bigint")

            column(name: "test_subject_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-8") {
        createTable(tableName: "test_subject") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "test_subjectPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "date_of_birth", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "gender", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "qb_test_patient_id", type: "varchar(255)")

            column(name: "site_id", type: "bigint")

            column(name: "site_patient_id", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-9") {
        createTable(tableName: "transaction") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "transactionPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "test_result_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-10") {
        createTable(tableName: "voucher") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "voucherPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "code", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "site_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "status", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "test_subject_id", type: "bigint")

            column(name: "transaction_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-11") {
        addPrimaryKey(columnNames: "role_id, person_id", constraintName: "person_rolePK", tableName: "person_role")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-24") {
        createIndex(indexName: "FKC4E39B557969BE4", tableName: "person") {
            column(name: "site_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-25") {
        createIndex(indexName: "email_uniq_1391116941226", tableName: "person", unique: "true") {
            column(name: "email")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-26") {
        createIndex(indexName: "FKE6A16B2050E8DD33", tableName: "person_role") {
            column(name: "role_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-27") {
        createIndex(indexName: "FKE6A16B20D50B1293", tableName: "person_role") {
            column(name: "person_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-28") {
        createIndex(indexName: "FKF5EB0D8E1203F387", tableName: "qb_test_param") {
            column(name: "quality_test_result_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-29") {
        createIndex(indexName: "FKF5EB0D8E43B44447", tableName: "qb_test_param") {
            column(name: "test_result_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-30") {
        createIndex(indexName: "url_uniq_1391116941247", tableName: "requestmap", unique: "true") {
            column(name: "url")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-31") {
        createIndex(indexName: "authority_uniq_1391116941248", tableName: "role", unique: "true") {
            column(name: "authority")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-32") {
        createIndex(indexName: "FKEF1E986A81F3242D", tableName: "test_result") {
            column(name: "test_subject_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-33") {
        createIndex(indexName: "FKEF1E986A84F4CCF2", tableName: "test_result") {
            column(name: "submitting_user_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-34") {
        createIndex(indexName: "FK43F38E7F7969BE4", tableName: "test_subject") {
            column(name: "site_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-35") {
        createIndex(indexName: "FK7FA0D2DE43B44447", tableName: "transaction") {
            column(name: "test_result_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-36") {
        createIndex(indexName: "test_result_id_uniq_1391116941266", tableName: "transaction", unique: "true") {
            column(name: "test_result_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-37") {
        createIndex(indexName: "FK26288EAE3097A212", tableName: "voucher") {
            column(name: "transaction_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-38") {
        createIndex(indexName: "FK26288EAE7969BE4", tableName: "voucher") {
            column(name: "site_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-39") {
        createIndex(indexName: "FK26288EAE81F3242D", tableName: "voucher") {
            column(name: "test_subject_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-40") {
        createIndex(indexName: "transaction_id_uniq_1391116941271", tableName: "voucher", unique: "true") {
            column(name: "transaction_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391116941367-12") {
        addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "person", constraintName: "FKC4E39B557969BE4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-13") {
        addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_role", constraintName: "FKE6A16B20D50B1293", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-14") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "person_role", constraintName: "FKE6A16B2050E8DD33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-15") {
        addForeignKeyConstraint(baseColumnNames: "quality_test_result_id", baseTableName: "qb_test_param", constraintName: "FKF5EB0D8E1203F387", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_result", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-16") {
        addForeignKeyConstraint(baseColumnNames: "test_result_id", baseTableName: "qb_test_param", constraintName: "FKF5EB0D8E43B44447", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_result", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-17") {
        addForeignKeyConstraint(baseColumnNames: "submitting_user_id", baseTableName: "test_result", constraintName: "FKEF1E986A84F4CCF2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-18") {
        addForeignKeyConstraint(baseColumnNames: "test_subject_id", baseTableName: "test_result", constraintName: "FKEF1E986A81F3242D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_subject", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-19") {
        addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "test_subject", constraintName: "FK43F38E7F7969BE4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-20") {
        addForeignKeyConstraint(baseColumnNames: "test_result_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE43B44447", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_result", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-21") {
        addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "voucher", constraintName: "FK26288EAE7969BE4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-22") {
        addForeignKeyConstraint(baseColumnNames: "test_subject_id", baseTableName: "voucher", constraintName: "FK26288EAE81F3242D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_subject", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391116941367-23") {
        addForeignKeyConstraint(baseColumnNames: "transaction_id", baseTableName: "voucher", constraintName: "FK26288EAE3097A212", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "transaction", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391582626185-1") {
        createTable(tableName: "access_code") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "access_codePK")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "qb_test_patient_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "site_id", type: "bigint")
        }
    }

    changeSet(author: "ami (generated)", id: "1391582626185-3") {
        createIndex(indexName: "FKC26F6FE87969BE4", tableName: "access_code") {
            column(name: "site_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391582626185-4") {
        createIndex(indexName: "unique_public_id", tableName: "access_code", unique: "true") {
            column(name: "site_id")

            column(name: "public_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1391582626185-2") {
        addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "access_code", constraintName: "FKC26F6FE87969BE4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1391780408175-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address_address2", tableName: "site")
    }

    changeSet(author: "ami (generated)", id: "1391780408175-2") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address_state", tableName: "site")
    }

    changeSet(author: "ami (generated)", id: "1391780408175-3") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address_zip_code", tableName: "site")
    }

    changeSet(author: "ami (generated)", id: "1391780408175-4") {
        dropNotNullConstraint(columnDataType: "varchar(5000)", columnName: "notes", tableName: "site")
    }

    changeSet(author: "ami (generated)", id: "1392061406348-1") {
        addColumn(tableName: "voucher") {
            column(name: "date_used", type: "datetime")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392110887249-1") {
        addColumn(tableName: "site") {
            column(name: "is_enabled", type: "bit")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392112938244-1") {
        addColumn(tableName: "transaction") {
            column(name: "voucher_id", type: "bigint")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392112938244-2") {
        dropForeignKeyConstraint(baseTableName: "voucher", constraintName: "FK26288EAE3097A212")
    }

    changeSet(author: "baxxabit (generated)", id: "1392112938244-4") {
        createIndex(indexName: "FK7FA0D2DE7E169C12", tableName: "transaction") {
            column(name: "voucher_id")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392112938244-5") {
        dropColumn(columnName: "transaction_id", tableName: "voucher")
    }

    changeSet(author: "baxxabit (generated)", id: "1392112938244-3") {
        addForeignKeyConstraint(baseColumnNames: "voucher_id", baseTableName: "transaction", constraintName: "FK7FA0D2DE7E169C12", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher", referencesUniqueColumn: "false")
    }

    changeSet(author: "baxxabit", id: "1392112938244-6") {
        sql("UPDATE site SET site.is_enabled = true;")
    }

    changeSet(author: "baxxabit (generated)", id: "1392122198584-2") {
        addNotNullConstraint(columnDataType: "bit", columnName: "is_enabled", tableName: "site")
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-1") {
        addColumn(tableName: "site") {
            column(name: "billing_address_address1", type: "varchar(255)")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-2") {
        addColumn(tableName: "site") {
            column(name: "billing_address_address2", type: "varchar(255)")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-3") {
        addColumn(tableName: "site") {
            column(name: "billing_address_city", type: "varchar(255)")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-4") {
        addColumn(tableName: "site") {
            column(name: "billing_address_country_code", type: "varchar(255)")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-5") {
        addColumn(tableName: "site") {
            column(name: "billing_address_state", type: "varchar(255)")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-6") {
        addColumn(tableName: "site") {
            column(name: "billing_address_zip_code", type: "varchar(255)")
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1392127510428-7") {
        dropNotNullConstraint(columnDataType: "bigint", columnName: "site_id", tableName: "voucher")
    }

    changeSet(author: "baxxabit (generated)", id: "1392208529936-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address_address1", tableName: "site")
    }

    changeSet(author: "baxxabit (generated)", id: "1392208529936-2") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address_city", tableName: "site")
    }

    changeSet(author: "baxxabit (generated)", id: "1392208529936-3") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address_country_code", tableName: "site")
    }

    changeSet(author: "baxxabit", id: "1392375640061-0") {
        sql("UPDATE voucher SET site_id = 1;")
    }

    changeSet(author: "baxxabit (generated)", id: "1392375640061-1") {
        addNotNullConstraint(columnDataType: "bigint", columnName: "site_id", tableName: "voucher")
    }

    changeSet(author: "ami (generated)", id: "1393070357528-1") {
        addColumn(tableName: "transaction") {
            column(name: "braintree_transaction_id", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1393417779588-2") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "country_code", tableName: "test_result")
    }

    changeSet(author: "ami (generated)", id: "1393417779588-3") {
        modifyDataType(columnName: "number_of_buttons", newDataType: "integer", tableName: "test_result")
    }

    changeSet(author: "ami (generated)", id: "1393417779588-4") {
        dropNotNullConstraint(columnDataType: "integer", columnName: "number_of_buttons", tableName: "test_result")
    }

    changeSet(author: "ami (generated)", id: "1393417779588-5") {
        modifyDataType(columnName: "number_of_outliers", newDataType: "integer", tableName: "test_result")
    }

    changeSet(author: "ami (generated)", id: "1393417779588-6") {
        dropNotNullConstraint(columnDataType: "integer", columnName: "number_of_outliers", tableName: "test_result")
    }

    changeSet(author: "ami (generated)", id: "1393417779588-7") {
        modifyDataType(columnName: "product_number", newDataType: "integer", tableName: "test_result")
    }

    changeSet(author: "ami (generated)", id: "1393621399173-1") {
        dropNotNullConstraint(columnDataType: "bigint", columnName: "quality_test_result_id", tableName: "qb_test_param")
    }

    changeSet(author: "ami (generated)", id: "1393621399173-2") {
        dropNotNullConstraint(columnDataType: "bigint", columnName: "test_result_id", tableName: "qb_test_param")
    }

    changeSet(author: "baxxabit (generated)", id: "1393852588132-1") {
        addColumn(tableName: "qb_test_param") {
            column(name: "qb_key", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1393852588132-2") {
        addColumn(tableName: "qb_test_param") {
            column(name: "qb_value", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "baxxabit (generated)", id: "1393852588132-5") {
        dropColumn(columnName: "key", tableName: "qb_test_param")
    }

    changeSet(author: "baxxabit (generated)", id: "1393852588132-6") {
        dropColumn(columnName: "value", tableName: "qb_test_param")
    }

    changeSet(author: "ami (generated)", id: "1395380394777-1") {
        addColumn(tableName: "site") {
            column(name: "braintree_customer_id", type: "varchar(255)")
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-1") {
        createTable(tableName: "answer_option") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "answer_optionPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "answer", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "scale_value", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-3") {
        createTable(tableName: "question") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "questionPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "age_group", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "question_description", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-4") {
        createTable(tableName: "question_answer") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "question_answPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "answer_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "question_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "test_subject_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    /*changeSet(author: "muhammad (generated)", id: "1398893068443-6") {
        modifyDataType(columnName: "product_number", newDataType: "integer", tableName: "test_result")
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-7") {
        addNotNullConstraint(columnDataType: "integer", columnName: "product_number", tableName: "test_result")
    }*/

    changeSet(author: "muhammad (generated)", id: "1398893068443-12") {
        createIndex(indexName: "FK561DF23714879AD7", tableName: "question_answer") {
            column(name: "answer_id")
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-13") {
        createIndex(indexName: "FK561DF23781F3242D", tableName: "question_answer") {
            column(name: "test_subject_id")
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-14") {
        createIndex(indexName: "FK561DF237AD493FC2", tableName: "question_answer") {
            column(name: "question_id")
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-8") {
        addForeignKeyConstraint(baseColumnNames: "answer_id", baseTableName: "question_answer", constraintName: "FK561DF23714879AD7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "answer_option", referencesUniqueColumn: "false")
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-9") {
        addForeignKeyConstraint(baseColumnNames: "question_id", baseTableName: "question_answer", constraintName: "FK561DF237AD493FC2", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "question", referencesUniqueColumn: "false")
    }

    changeSet(author: "muhammad (generated)", id: "1398893068443-10") {
        addForeignKeyConstraint(baseColumnNames: "test_subject_id", baseTableName: "question_answer", constraintName: "FK561DF23781F3242D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_subject", referencesUniqueColumn: "false")
    }


    changeSet(author: "muhammad (generated)", id: "1398964826229-1") {
        addColumn(tableName: "test_subject") {
            column(name: "number_of_computer_checks", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398964826229-2") {
        addColumn(tableName: "test_subject") {
            column(name: "number_of_practice_tests", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "muhammad (generated)", id: "1398964826229-3") {
        addColumn(tableName: "test_subject") {
            column(name: "test_status", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    //2014/05/12

    changeSet(author: "muhammad (generated)", id: "1399900120856-1") {
        createTable(tableName: "quality_parameter") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "quality_paramPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime")

            column(name: "last_updated", type: "datetime")

            column(name: "number_of_computer_checks", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "number_of_practise_tests", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "test_status", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "test_subject_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-2") {
        modifyDataType(columnName: "product_number", newDataType: "integer", tableName: "test_result")
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-3") {
        addNotNullConstraint(columnDataType: "integer", columnName: "product_number", tableName: "test_result")
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-5") {
        createIndex(indexName: "FKD46E7C6981F3242D", tableName: "quality_parameter") {
            column(name: "test_subject_id")
        }
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-6") {
        dropColumn(columnName: "number_of_computer_checks", tableName: "test_subject")
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-7") {
        dropColumn(columnName: "number_of_practice_tests", tableName: "test_subject")
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-8") {
        dropColumn(columnName: "test_status", tableName: "test_subject")
    }

    changeSet(author: "muhammad (generated)", id: "1399900120856-4") {
        addForeignKeyConstraint(baseColumnNames: "test_subject_id", baseTableName: "quality_parameter", constraintName: "FKD46E7C6981F3242D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "test_subject", referencesUniqueColumn: "false")
    }
}
