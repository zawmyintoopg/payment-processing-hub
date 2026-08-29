package com.mybank.paymenthub.excel;

import com.mybank.paymenthub.dto.response.TerminalSummaryResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TerminalSummaryExcelExporter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public byte[] export(
            List<TerminalSummaryResponse> terminals,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Terminal Summary");
            sheet.setDisplayGridlines(false);

            // Styles
            CellStyle titleStyle = ExcelCommonStyle.title(workbook);
            CellStyle labelStyle = ExcelCommonStyle.label(workbook);
            CellStyle headerStyle = ExcelCommonStyle.header(workbook);
            CellStyle textStyle = ExcelCommonStyle.text(workbook);
            CellStyle numberStyle = ExcelCommonStyle.number(workbook);
            CellStyle amountStyle = ExcelCommonStyle.amount(workbook);

            CellStyle totalLabelStyle =
                    ExcelCommonStyle.total(
                            workbook,
                            HorizontalAlignment.LEFT,
                            null
                    );

            CellStyle totalNumberStyle =
                    ExcelCommonStyle.total(
                            workbook,
                            HorizontalAlignment.RIGHT,
                            "#,##0"
                    );

            CellStyle totalAmountStyle =
                    ExcelCommonStyle.total(
                            workbook,
                            HorizontalAlignment.RIGHT,
                            "#,##0.00"
                    );

            // =====================================================
            // Title
            // =====================================================

            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(30);

            setText(
                    titleRow,
                    0,
                    "TERMINAL SUMMARY REPORT",
                    titleStyle
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(0, 0, 0, 10)
            );

            // =====================================================
            // Report Period
            // =====================================================

            Row periodRow = sheet.createRow(2);

            setText(periodRow, 0, "Report From", labelStyle);

            setText(
                    periodRow,
                    1,
                    fromDate != null
                            ? fromDate.format(DATE_FORMAT)
                            : "",
                    textStyle
            );

            setText(periodRow, 3, "Report To", labelStyle);

            setText(
                    periodRow,
                    4,
                    toDate != null
                            ? toDate.format(DATE_FORMAT)
                            : "",
                    textStyle
            );

            // =====================================================
            // Header
            // =====================================================

            String[] headers = {
                    "Terminal Number",
                    "Terminal Name",
                    "Merchant Number",
                    "Merchant Name",
                    "Total Transactions",
                    "Successful Transactions",
                    "Failed Transactions",
                    "Reversed Transactions",
                    "Total Transaction Amount",
                    "Total MDR Amount",
                    "Total Settlement Amount"
            };

            Row headerRow = sheet.createRow(4);
            headerRow.setHeightInPoints(40);

            for (int i = 0; i < headers.length; i++) {
                setText(headerRow, i, headers[i], headerStyle);
            }

            // =====================================================
            // Data
            // =====================================================

            int rowIndex = 5;

            long totalTransactions = 0;
            long totalSuccessful = 0;
            long totalFailed = 0;
            long totalReversed = 0;

            BigDecimal totalTransactionAmount = BigDecimal.ZERO;
            BigDecimal totalMdrAmount = BigDecimal.ZERO;
            BigDecimal totalSettlementAmount = BigDecimal.ZERO;

            if (terminals != null) {

                for (TerminalSummaryResponse terminal : terminals) {

                    Row row = sheet.createRow(rowIndex++);

                    setText(
                            row,
                            0,
                            terminal.getTerminalNumber(),
                            textStyle
                    );

                    setText(
                            row,
                            1,
                            terminal.getTerminalName(),
                            textStyle
                    );

                    setText(
                            row,
                            2,
                            terminal.getMerchantNumber(),
                            textStyle
                    );

                    setText(
                            row,
                            3,
                            terminal.getMerchantName(),
                            textStyle
                    );

                    long transactions =
                            safeLong(terminal.getTotalTransactions());

                    long successful =
                            safeLong(terminal.getSuccessfulTransactions());

                    long failed =
                            safeLong(terminal.getFailedTransactions());

                    long reversed =
                            safeLong(terminal.getReversedTransactions());

                    setNumber(row, 4, transactions, numberStyle);
                    setNumber(row, 5, successful, numberStyle);
                    setNumber(row, 6, failed, numberStyle);
                    setNumber(row, 7, reversed, numberStyle);

                    BigDecimal transactionAmount =
                            safeAmount(
                                    terminal.getTotalTransactionAmount()
                            );

                    BigDecimal mdrAmount =
                            safeAmount(
                                    terminal.getTotalMdrAmount()
                            );

                    BigDecimal settlementAmount =
                            safeAmount(
                                    terminal.getTotalSettlementAmount()
                            );

                    setAmount(
                            row,
                            8,
                            transactionAmount,
                            amountStyle
                    );

                    setAmount(
                            row,
                            9,
                            mdrAmount,
                            amountStyle
                    );

                    setAmount(
                            row,
                            10,
                            settlementAmount,
                            amountStyle
                    );

                    totalTransactions += transactions;
                    totalSuccessful += successful;
                    totalFailed += failed;
                    totalReversed += reversed;

                    totalTransactionAmount =
                            totalTransactionAmount.add(transactionAmount);

                    totalMdrAmount =
                            totalMdrAmount.add(mdrAmount);

                    totalSettlementAmount =
                            totalSettlementAmount.add(settlementAmount);
                }
            }

            // =====================================================
            // Total
            // =====================================================

            Row totalRow = sheet.createRow(rowIndex);

            setText(
                    totalRow,
                    0,
                    "TOTAL",
                    totalLabelStyle
            );

            // Only TOTAL label is merged
            sheet.addMergedRegion(
                    new CellRangeAddress(
                            rowIndex,
                            rowIndex,
                            0,
                            3
                    )
            );

            setNumber(
                    totalRow,
                    4,
                    totalTransactions,
                    totalNumberStyle
            );

            setNumber(
                    totalRow,
                    5,
                    totalSuccessful,
                    totalNumberStyle
            );

            setNumber(
                    totalRow,
                    6,
                    totalFailed,
                    totalNumberStyle
            );

            setNumber(
                    totalRow,
                    7,
                    totalReversed,
                    totalNumberStyle
            );

            setAmount(
                    totalRow,
                    8,
                    totalTransactionAmount,
                    totalAmountStyle
            );

            setAmount(
                    totalRow,
                    9,
                    totalMdrAmount,
                    totalAmountStyle
            );

            setAmount(
                    totalRow,
                    10,
                    totalSettlementAmount,
                    totalAmountStyle
            );

            // =====================================================
            // Filter / Freeze
            // =====================================================

            sheet.setAutoFilter(
                    new CellRangeAddress(
                            4,
                            Math.max(4, rowIndex - 1),
                            0,
                            10
                    )
            );

            sheet.createFreezePane(0, 5);

            // =====================================================
            // Column Width
            // =====================================================

            int[] widths = {
                    20, 30, 20, 32, 20, 24,
                    20, 22, 25, 20, 25
            };

            for (int i = 0; i < widths.length; i++) {
                sheet.setColumnWidth(i, widths[i] * 256);
            }

            workbook.write(output);

            return output.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to generate Terminal Summary Excel",
                    e
            );
        }
    }

    // =============================================================
    // Helpers
    // =============================================================

    private void setText(
            Row row,
            int column,
            String value,
            CellStyle style
    ) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "");
        cell.setCellStyle(style);
    }

    private void setNumber(
            Row row,
            int column,
            long value,
            CellStyle style
    ) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void setAmount(
            Row row,
            int column,
            BigDecimal value,
            CellStyle style
    ) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value.doubleValue());
        cell.setCellStyle(style);
    }

    private long safeLong(Long value) {
        return value != null ? value : 0L;
    }

    private BigDecimal safeAmount(BigDecimal value) {
        return value != null
                ? value
                : BigDecimal.ZERO;
    }
}