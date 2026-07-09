package com.igordev.sistema_financeiro.exception.message;

public class ExceptionMessages {

    private ExceptionMessages() {}

    public static final String ID_REQUIRED = "O ID deve ser informado";

    public static final String CATEGORY_NAME_REQUIRED = "O nome da categoria deve ser informado";
    public static final String CATEGORY_TYPE_REQUIRED = "O tipo da categoria deve ser informado";
    public static final String CATEGORY_NOT_FOUND = "Categoria não encontrada";

    public static final String TRANSACTION_AMOUNT_REQUIRED = "O valor da transação deve ser informado";
    public static final String TRANSACTION_DATE_REQUIRED = "A data da transação deve ser informada";
    public static final String TRANSACTION_CATEGORY_REQUIRED = "A categoria da transação deve ser informada";
    public static final String TRANSACTION_TYPE_REQUIRED = "O tipo da transação deve ser informado";
    public static final String TRANSACTION_NATURE_REQUIRED = "A natureza da transação deve ser informada";
    public static final String TRANSACTION_NOT_FOUND = "Transação não encontrada";
    public static final String CATEGORY_TYPE_INVALID = "Tipo de categoria inválido. Os valores aceitos são: INCOME, EXPENSE";

    public static final String TRANSACTION_AMOUNT_INVALID = "O valor da transação deve ser maior que zero";
    public static final String TRANSACTION_DATE_INVALID = "A data da transação não pode ser no futuro";
    public static final String TRANSACTION_MONTH_INVALID = "O mês deve ser entre 1 e 12";
    public static final String TRANSACTION_YEAR_INVALID = "O ano informado é inválido";

    public static final String TRANSACTION_AMOUNT_RANGE_INVALID = "O valor mínimo não pode ser maior que o valor máximo";
    public static final String TRANSACTION_DATE_RANGE_INVALID = "A data inicial não pode ser posterior à data final";

    public static final String TRANSACTION_TYPE_CATEGORY_MISMATCH = "O tipo da transação deve ser compatível com o tipo da categoria";

    public static final String MONTHS_BACK_INVALID = "O número de meses deve ser maior que zero";
}
