package com.mybank.paymenthub.excel;

import com.mybank.paymenthub.dto.response.MerchantSummaryResponse;
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
public class MerchantSummaryExcelExporter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public byte[] export(
            List<MerchantSummaryResponse> merchants,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream()
        ) {

            // =====================================================
            // Sheet
            // =====================================================

            Sheet sheet =
                    workbook.createSheet("Merchant Summary");

            sheet.setDisplayGridlines(false);


            // =====================================================
            // Fonts
            // =====================================================

            Font titleFont =
                    workbook.createFont();

            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 18);


            Font headerFont =
                    workbook.createFont();

            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);


            Font totalFont =
                    workbook.createFont();

            totalFont.setBold(true);
            totalFont.setFontHeightInPoints((short) 10);


            // =====================================================
            // Title Style
            // =====================================================

            CellStyle titleStyle =
                    workbook.createCellStyle();

            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(
                    HorizontalAlignment.CENTER
            );
            titleStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );


            // =====================================================
            // Label Style
            // =====================================================

            CellStyle labelStyle =
                    workbook.createCellStyle();

            labelStyle.setFont(headerFont);
            labelStyle.setAlignment(
                    HorizontalAlignment.LEFT
            );
            labelStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );


            // =====================================================
            // Date Value Style
            // =====================================================

            CellStyle dateValueStyle =
                    workbook.createCellStyle();

            dateValueStyle.setAlignment(
                    HorizontalAlignment.LEFT
            );
            dateValueStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );


            // =====================================================
            // Header Style
            // =====================================================

            CellStyle headerStyle =
                    workbook.createCellStyle();

            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(
                    HorizontalAlignment.CENTER
            );
            headerStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );
            headerStyle.setWrapText(true);



            // =====================================================
            // Text Style
            // =====================================================

            CellStyle textStyle =
                    workbook.createCellStyle();

            textStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );



            // =====================================================
            // Number Style
            // =====================================================

            CellStyle numberStyle =
                    workbook.createCellStyle();

            numberStyle.setAlignment(
                    HorizontalAlignment.RIGHT
            );
            numberStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );
            numberStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("#,##0")
            );



            // =====================================================
            // Amount Style
            // =====================================================

            CellStyle amountStyle =
                    workbook.createCellStyle();

            amountStyle.setAlignment(
                    HorizontalAlignment.RIGHT
            );
            amountStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );
            amountStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("#,##0.00")
            );



            // =====================================================
            // Total Label Style
            // =====================================================

            CellStyle totalLabelStyle =
                    workbook.createCellStyle();

            totalLabelStyle.setFont(totalFont);
            totalLabelStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );



            // =====================================================
            // Total Number Style
            // =====================================================

            CellStyle totalNumberStyle =
                    workbook.createCellStyle();

            totalNumberStyle.setFont(totalFont);
            totalNumberStyle.setAlignment(
                    HorizontalAlignment.RIGHT
            );
            totalNumberStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );
            totalNumberStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("#,##0")
            );



            // =====================================================
            // Total Amount Style
            // =====================================================

            CellStyle totalAmountStyle =
                    workbook.createCellStyle();

            totalAmountStyle.setFont(totalFont);
            totalAmountStyle.setAlignment(
                    HorizontalAlignment.RIGHT
            );
            totalAmountStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );
            totalAmountStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("#,##0.00")
            );



            // =====================================================
            // Title
            // =====================================================

            Row titleRow =
                    sheet.createRow(0);

            titleRow.setHeightInPoints(30);

            Cell titleCell =
                    titleRow.createCell(0);

            titleCell.setCellValue(
                    "MERCHANT SUMMARY REPORT"
            );

            titleCell.setCellStyle(titleStyle);

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            0,
                            0,
                            0,
                            8
                    )
            );


            // =====================================================
            // Report Period
            // =====================================================

            Row periodRow =
                    sheet.createRow(2);

            periodRow.setHeightInPoints(20);

            Cell fromLabel =
                    periodRow.createCell(0);

            fromLabel.setCellValue("Report From");
            fromLabel.setCellStyle(labelStyle);

            Cell fromValue =
                    periodRow.createCell(1);

            fromValue.setCellValue(
                    fromDate != null
                            ? fromDate.format(DATE_FORMAT)
                            : ""
            );

            fromValue.setCellStyle(dateValueStyle);


            Cell toLabel =
                    periodRow.createCell(3);

            toLabel.setCellValue("Report To");
            toLabel.setCellStyle(labelStyle);

            Cell toValue =
                    periodRow.createCell(4);

            toValue.setCellValue(
                    toDate != null
                            ? toDate.format(DATE_FORMAT)
                            : ""
            );

            toValue.setCellStyle(dateValueStyle);


            // =====================================================
            // Header
            // =====================================================

            Row headerRow =
                    sheet.createRow(4);

            headerRow.setHeightInPoints(40);

            String[] headers = {

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

            for (int i = 0; i < headers.length; i++) {

                Cell cell =
                        headerRow.createCell(i);

                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }


            // =====================================================
            // Data
            // =====================================================

            int rowIndex = 5;

            long totalTransactions = 0;
            long totalSuccessfulTransactions = 0;
            long totalFailedTransactions = 0;
            long totalReversedTransactions = 0;

            BigDecimal totalTransactionAmount =
                    BigDecimal.ZERO;

            BigDecimal totalMdrAmount =
                    BigDecimal.ZERO;

            BigDecimal totalSettlementAmount =
                    BigDecimal.ZERO;


            if (merchants != null) {

                for (
                        MerchantSummaryResponse merchant :
                        merchants
                ) {

                    Row row =
                            sheet.createRow(rowIndex++);

                    row.setHeightInPoints(20);


                    // Merchant Number

                    Cell merchantNumber =
                            row.createCell(0);

                    merchantNumber.setCellValue(
                            safeString(
                                    merchant.getMerchantNumber()
                            )
                    );

                    merchantNumber.setCellStyle(textStyle);


                    // Merchant Name

                    Cell merchantName =
                            row.createCell(1);

                    merchantName.setCellValue(
                            safeString(
                                    merchant.getMerchantName()
                            )
                    );

                    merchantName.setCellStyle(textStyle);


                    // Total Transactions

                    long transactions =
                            safeLong(
                                    merchant.getTotalTransactions()
                            );

                    Cell transactionCell =
                            row.createCell(2);

                    transactionCell.setCellValue(
                            transactions
                    );

                    transactionCell.setCellStyle(numberStyle);


                    // Successful Transactions

                    long successful =
                            safeLong(
                                    merchant.getSuccessfulTransactions()
                            );

                    Cell successfulCell =
                            row.createCell(3);

                    successfulCell.setCellValue(
                            successful
                    );

                    successfulCell.setCellStyle(numberStyle);


                    // Failed Transactions

                    long failed =
                            safeLong(
                                    merchant.getFailedTransactions()
                            );

                    Cell failedCell =
                            row.createCell(4);

                    failedCell.setCellValue(failed);
                    failedCell.setCellStyle(numberStyle);


                    // Reversed Transactions

                    long reversed =
                            safeLong(
                                    merchant.getReversedTransactions()
                            );

                    Cell reversedCell =
                            row.createCell(5);

                    reversedCell.setCellValue(reversed);
                    reversedCell.setCellStyle(numberStyle);


                    // Transaction Amount

                    BigDecimal transactionAmount =
                            safeAmount(
                                    merchant.getTotalTransactionAmount()
                            );

                    Cell transactionAmountCell =
                            row.createCell(6);

                    transactionAmountCell.setCellValue(
                            transactionAmount.doubleValue()
                    );

                    transactionAmountCell.setCellStyle(
                            amountStyle
                    );


                    // MDR Amount

                    BigDecimal mdrAmount =
                            safeAmount(
                                    merchant.getTotalMdrAmount()
                            );

                    Cell mdrCell =
                            row.createCell(7);

                    mdrCell.setCellValue(
                            mdrAmount.doubleValue()
                    );

                    mdrCell.setCellStyle(amountStyle);


                    // Settlement Amount

                    BigDecimal settlementAmount =
                            safeAmount(
                                    merchant.getTotalSettlementAmount()
                            );

                    Cell settlementCell =
                            row.createCell(8);

                    settlementCell.setCellValue(
                            settlementAmount.doubleValue()
                    );

                    settlementCell.setCellStyle(amountStyle);


                    // Totals

                    totalTransactions += transactions;
                    totalSuccessfulTransactions += successful;
                    totalFailedTransactions += failed;
                    totalReversedTransactions += reversed;

                    totalTransactionAmount =
                            totalTransactionAmount.add(
                                    transactionAmount
                            );

                    totalMdrAmount =
                            totalMdrAmount.add(
                                    mdrAmount
                            );

                    totalSettlementAmount =
                            totalSettlementAmount.add(
                                    settlementAmount
                            );
                }
            }


            // =====================================================
            // Total Row
            // =====================================================

            Row totalRow =
                    sheet.createRow(rowIndex);

            totalRow.setHeightInPoints(22);


            // TOTAL merged 0-1

            Cell totalLabel =
                    totalRow.createCell(0);

            totalLabel.setCellValue("TOTAL");
            totalLabel.setCellStyle(totalLabelStyle);

            Cell totalMergedCell =
                    totalRow.createCell(1);

            totalMergedCell.setCellStyle(totalLabelStyle);

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            rowIndex,
                            rowIndex,
                            0,
                            1
                    )
            );


            // Total Transactions

            Cell totalTransactionsCell =
                    totalRow.createCell(2);

            totalTransactionsCell.setCellValue(
                    totalTransactions
            );

            totalTransactionsCell.setCellStyle(
                    totalNumberStyle
            );


            // Total Successful

            Cell totalSuccessfulCell =
                    totalRow.createCell(3);

            totalSuccessfulCell.setCellValue(
                    totalSuccessfulTransactions
            );

            totalSuccessfulCell.setCellStyle(
                    totalNumberStyle
            );


            // Total Failed

            Cell totalFailedCell =
                    totalRow.createCell(4);

            totalFailedCell.setCellValue(
                    totalFailedTransactions
            );

            totalFailedCell.setCellStyle(
                    totalNumberStyle
            );


            // Total Reversed

            Cell totalReversedCell =
                    totalRow.createCell(5);

            totalReversedCell.setCellValue(
                    totalReversedTransactions
            );

            totalReversedCell.setCellStyle(
                    totalNumberStyle
            );


            // Total Transaction Amount

            Cell totalTransactionAmountCell =
                    totalRow.createCell(6);

            totalTransactionAmountCell.setCellValue(
                    totalTransactionAmount.doubleValue()
            );

            totalTransactionAmountCell.setCellStyle(
                    totalAmountStyle
            );


            // Total MDR

            Cell totalMdrCell =
                    totalRow.createCell(7);

            totalMdrCell.setCellValue(
                    totalMdrAmount.doubleValue()
            );

            totalMdrCell.setCellStyle(
                    totalAmountStyle
            );


            // Total Settlement

            Cell totalSettlementCell =
                    totalRow.createCell(8);

            totalSettlementCell.setCellValue(
                    totalSettlementAmount.doubleValue()
            );

            totalSettlementCell.setCellStyle(
                    totalAmountStyle
            );


            // =====================================================
            // Auto Filter
            // =====================================================

            sheet.setAutoFilter(
                    new CellRangeAddress(
                            4,
                            Math.max(4, rowIndex - 1),
                            0,
                            8
                    )
            );


            // =====================================================
            // Freeze Header
            // =====================================================

            sheet.createFreezePane(0, 5);


            // =====================================================
            // Column Width
            // =====================================================

            int[] widths = {
                    20, 35, 20, 24, 20,
                    22, 25, 20, 25
            };

            for (int i = 0; i < widths.length; i++) {
                sheet.setColumnWidth(
                        i,
                        widths[i] * 256
                );
            }


            // =====================================================
            // Write
            // =====================================================

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to generate Merchant Summary Excel",
                    e
            );
        }
    }


    private String safeString(String value) {

        return value != null ? value : "";
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