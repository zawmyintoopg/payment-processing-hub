package com.mybank.paymenthub.excel;

import org.apache.poi.ss.usermodel.*;

public final class ExcelCommonStyle {

    private ExcelCommonStyle() {
    }

    // =========================================================
    // FONT
    // =========================================================

    private static Font createFont(
            Workbook workbook,
            short size,
            boolean bold
    ) {

        Font font = workbook.createFont();

        // Myanmar Unicode Font
        font.setFontName("Pyidaungsu");

        font.setFontHeightInPoints(size);
        font.setBold(bold);

        return font;
    }

    // =========================================================
    // TITLE
    // =========================================================

    public static CellStyle title(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setFont(
                createFont(
                        workbook,
                        (short) 18,
                        true
                )
        );

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        thinBorder(style);

        return style;
    }

    // =========================================================
    // LABEL
    // =========================================================

    public static CellStyle label(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setFont(
                createFont(
                        workbook,
                        (short) 11,
                        true
                )
        );

        style.setAlignment(
                HorizontalAlignment.LEFT
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        thinBorder(style);

        return style;
    }

    // =========================================================
    // HEADER
    // =========================================================

    public static CellStyle header(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setFont(
                createFont(
                        workbook,
                        (short) 10,
                        true
                )
        );

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        style.setWrapText(true);

        thinBorder(style);

        return style;
    }

    // =========================================================
    // TEXT
    // =========================================================

    public static CellStyle text(Workbook workbook) {

        return style(
                workbook,
                HorizontalAlignment.LEFT,
                null
        );
    }

    // =========================================================
    // CENTER
    // =========================================================

    public static CellStyle center(Workbook workbook) {

        return style(
                workbook,
                HorizontalAlignment.CENTER,
                null
        );
    }

    // =========================================================
    // NUMBER
    // =========================================================

    public static CellStyle number(Workbook workbook) {

        return style(
                workbook,
                HorizontalAlignment.RIGHT,
                "#,##0"
        );
    }

    // =========================================================
    // AMOUNT
    // =========================================================

    public static CellStyle amount(Workbook workbook) {

        return style(
                workbook,
                HorizontalAlignment.RIGHT,
                "#,##0.00"
        );
    }

    // =========================================================
    // BLANK CELL
    // =========================================================

    public static CellStyle blankCell(Workbook workbook) {

        CellStyle style = workbook.createCellStyle();

        style.setFont(
                createFont(
                        workbook,
                        (short) 10,
                        false
                )
        );

        style.setAlignment(
                HorizontalAlignment.LEFT
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        thinBorder(style);

        return style;
    }

    // =========================================================
    // TOTAL
    // =========================================================

    public static CellStyle total(
            Workbook workbook,
            HorizontalAlignment alignment,
            String format
    ) {

        CellStyle style = workbook.createCellStyle();

        style.setFont(
                createFont(
                        workbook,
                        (short) 10,
                        true
                )
        );

        style.setAlignment(alignment);

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        if (format != null) {

            style.setDataFormat(
                    workbook
                            .createDataFormat()
                            .getFormat(format)
            );
        }

        totalBorder(style);

        return style;
    }

    // =========================================================
    // COMMON STYLE
    // =========================================================

    private static CellStyle style(
            Workbook workbook,
            HorizontalAlignment alignment,
            String format
    ) {

        CellStyle style = workbook.createCellStyle();

        style.setFont(
                createFont(
                        workbook,
                        (short) 10,
                        false
                )
        );

        style.setAlignment(alignment);

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        if (format != null) {

            style.setDataFormat(
                    workbook
                            .createDataFormat()
                            .getFormat(format)
            );
        }

        thinBorder(style);

        return style;
    }

    // =========================================================
    // BORDER
    // =========================================================

    public static void thinBorder(CellStyle style) {

        style.setBorderTop(
                BorderStyle.THIN
        );

        style.setBorderBottom(
                BorderStyle.THIN
        );

        style.setBorderLeft(
                BorderStyle.THIN
        );

        style.setBorderRight(
                BorderStyle.THIN
        );
    }

    // =========================================================
    // TOTAL BORDER
    // =========================================================

    public static void totalBorder(CellStyle style) {

        style.setBorderTop(
                BorderStyle.MEDIUM
        );

        style.setBorderBottom(
                BorderStyle.THIN
        );

        style.setBorderLeft(
                BorderStyle.THIN
        );

        style.setBorderRight(
                BorderStyle.THIN
        );
    }
}