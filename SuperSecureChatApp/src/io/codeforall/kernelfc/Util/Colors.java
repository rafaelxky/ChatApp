package io.codeforall.kernelfc.Util;

public enum Colors {
    RESET("\u001B[0m"),
    YELLOW("\u001B[33m"),
    RED("\u001B[31m"),
    CYAN("\u001B[36m"),
    GREEN("\u001B[32m")
    ;

    public String code;
    Colors(String colorCode) {
        this.code = colorCode;
    }
}
