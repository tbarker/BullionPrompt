package com.thomasbarker.bullionprompt.model.enums;

import jakarta.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum TradeType {
    ORDER_BOARD_TRADE,
    ORDER_AT_FIX,
    CLIENT_ORDER
}
