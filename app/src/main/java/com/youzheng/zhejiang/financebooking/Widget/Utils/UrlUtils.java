package com.youzheng.zhejiang.financebooking.Widget.Utils;

public class UrlUtils {

//    public static String BASE_URL = "http://192.168.2.217:8088/financer/";

    public static String BASE_URL = "http://webapi.quzhoumr.com/financer/";
    public static String PHOTO_ADD = "http://image.quzhoumr.com/";
    public static String BASEL_PHOTO = "http://webapi.quzhoumr.com/";
    public static String SEND_CODE = BASE_URL+"login/sendsmscode";
    public static String REGISTER_USER= BASE_URL+"login/register";
    public static String LOGIN_USER = BASE_URL+"login/select";
    public static String SEND_CODE_PASS = BASE_URL+"login/checkphone";
    public static String CHANGE_PASS = BASE_URL+"login/revisepassword";
    public static String HOME_INTRO = BASE_URL+"api/productManager/homePageProductRecom";
    public static String HOME_DETAILS = BASE_URL+"api/productManager/selHomePageProductInfo";

    public static String USER_ONFO = BASE_URL+"member/getInfoById";
    public static String AUTH_INFO = BASE_URL+"member/getMemberAuth";
    public static String GET_CARD_LIST = BASE_URL+"member/queryMBankCards";
    public static String DELETE_BANK_CARD = BASE_URL+"member/deleteBank";
    public static String BANK_CARD_DETAILS = BASE_URL+"member/getBankCard";
    public static String CHO_INVEST =BASE_URL+"api/productManager/wisChoWisInvest";
    public static String MEMBER_RECORD = BASE_URL+"member/queryRechargeRecords";
    public static String APPLAY_MONEY = BASE_URL+"member/queryApplyCaseRecords";
    public static String FINANCE_MANAGE = BASE_URL+"order/orderRecord";
    public static String UPLOAD_PHOTO = BASE_URL+"api/financingPersonal/uploadPerImage";

    public static String GET_MY_FONANCE = BASE_URL+"member/getFinancings";
    public static String ALL_INVERST = BASE_URL+"member/getAssetRecords";
    public static String SEND_CODE_ALL = BASE_URL+"login/sendSMS";
    public static String APPLAY_MONEY_SELF = BASE_URL+"api/financingPersonal/personalApply";
    public static String APPLAY_CO_SELF = BASE_URL+"api/financingCompany/companyApply";
    public static String APPLAY_CO_PHOTO = BASE_URL+"api/financingCompany/uploadComImage";
    public static String APPLAY_GET_MONEY = BASE_URL+"member/applyCash";
    public static String ADD_BANK_CARD = BASE_URL+"member/addBank";
    public static String TOUZI_RECODE = BASE_URL+"api/productManager/bidderRecord";

    public static String AVAIL_MONEY = BASE_URL+"order/getProductAvailAccount";
    public static String TOUZI_JIEKOU = BASE_URL+"order/investCommit";
    public static String COMMINT_JIEKOU = BASE_URL+"order/placeAnOrder";

    public static String QUET_HAND_EXIST = BASE_URL+"api/gesture/details";
    public static String GESTURE_IS_OPEN = BASE_URL+"api/gesture/switch";
    public static String GESTURE_LOGIN = BASE_URL+"api/gesture/login";
    public static String ADD_GESTURE = BASE_URL+"api/gesture/addGesture";

    public static String REVISE_PASSWORD = BASE_URL+"login/revisepassword";
    public static String EXCHANGE_PASSWORD = BASE_URL+"member/editTradePass";

    public static String INVEST_MANAGE = BASE_URL+"order/getInvestmentInfo";
    public static String LOGIN_OUT = BASE_URL+"login/logout";

    public static String UPDAATE_VERSION = BASE_URL+"order/versionTerminal";
    public static String UPLIAD_MEN_PHOTO = BASE_URL+"member/uploadFiles";
    public static String CONFIRM_USER = BASE_URL+"member/invokeBankCard";

    public static String ADVERSTION_INFO = BASE_URL+"api/advertising/carousels";

    public static String GET_INFO =BASE_URL+"data/app/start";

    public static String NEW_INVEST = BASE_URL+"/api/productManager/v1.2/wisChoWisInvest";

    public static String ACCOUNT_OR_CONSULTING =BASE_URL+"api/advertising/v1.2/information";
}
