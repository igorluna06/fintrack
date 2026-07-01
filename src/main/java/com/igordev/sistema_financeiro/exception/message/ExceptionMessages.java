package com.igordev.sistema_financeiro.exception.message;

public class ExceptionMessages {

    private ExceptionMessages() {}

    public static final String ID_REQUIRED = "O ID deve ser informado";

    public static final String CATEGORY_NAME_REQUIRED = "O nome da categoria deve ser informado";
    public static final String CATEGORY_TYPE_REQUIRED = "O tipo da categoria deve ser informado";
    public static final String CATEGORY_NOT_FOUND = "Categoria não encontrada";

    public static final String TRANSACTION_VALUE_REQUIRED = "O valor da transação deve ser informado";
    public static final String TRANSACTION_DATE_REQUIRED = "A data da transação deve ser informada";
    public static final String TRANSACTION_CATEGORY_REQUIRED = "A categoria da transação deve ser informada";
    public static final String TRANSACTION_TYPE_REQUIRED = "O tipo da transação deve ser informado";
    public static final String TRANSACTION_NATURE_REQUIRED = "A natureza da transação deve ser informada";
    public static final String TRANSACTION_NOT_FOUND = "Transação não encontrada";
    public static final String CATEGORY_TYPE_INVALID = "Tipo de categoria inválido. Os valores aceitos são: INCOME, EXPENSE";
}
