package main.utils;

public class MessageConstants {
    public static final String DUPLICATE_LOGIN = "Уже присутствует в системе пользователь с логином %s!";
    public static final String MISSING_REQUIRED_FIELDS = "Не все обязательные поля заполнены!";
    public static final String WRONG_LOGIN_PASSWORD = "Неправильный логин и/или пароль!";

    public static final String CANNOT_DELETE_MANAGER = "Невозможно удалить данного исполнителя: у него есть привязанные услуги.";
    public static final String CANNOT_DELETE_PRODUCT = "Невозможно удалить данную услугу: по ней есть записи.";
}
