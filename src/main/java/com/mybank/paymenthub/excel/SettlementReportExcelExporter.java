package com.mybank.paymenthub.excel;

import com.mybank.paymenthub.dto.response.SettlementReportResponse;
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
public class SettlementReportExcelExporter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public byte[] export(
            List<SettlementReportResponse> settlements,
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
                    workbook.createSheet("Settlement Report");

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
            // Center Style
            // =====================================================

            CellStyle centerStyle =
                    workbook.createCellStyle();

            centerStyle.setAlignment(
                    HorizontalAlignment.CENTER
            );
            centerStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );



            // =====================================================
            // Date Style
            // =====================================================

            CellStyle dateStyle =
                    workbook.createCellStyle();

            dateStyle.setAlignment(
                    HorizontalAlignment.CENTER
            );
            dateStyle.setVerticalAlignment(
                    VerticalAlignment.CENTER
            );
            dateStyle.setDataFormat(
                    workbook.createDataFormat()
                            .getFormat("dd-MMM-yyyy")
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
                    "SETTLEMENT REPORT"
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

                    "Settlement Number",
                    "Merchant Number",
                    "Merchant Name",
                    "Settlement Date",
                    "Transaction Count",
                    "Total Transaction Amount",
                    "Total MDR Amount",
                    "Total Settlement Amount",
                    "Settlement Status"
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

            long totalTransactionCount = 0;

            BigDecimal totalTransactionAmount =
                    BigDecimal.ZERO;

            BigDecimal totalMdrAmount =
                    BigDecimal.ZERO;

            BigDecimal totalSettlementAmount =
                    BigDecimal.ZERO;


            if (settlements != null) {

                for (
                        SettlementReportResponse settlement :
                        settlements
                ) {

                    Row row =
                            sheet.createRow(rowIndex++);

                    row.setHeightInPoints(20);


                    // Settlement Number

                    Cell settlementNumber =
                            row.createCell(0);

                    settlementNumber.setCellValue(
                            safeString(
                                    settlement.getSettlementNumber()
                            )
                    );

                    settlementNumber.setCellStyle(textStyle);


                    // Merchant Number

                    Cell merchantNumber =
                            row.createCell(1);

                    merchantNumber.setCellValue(
                            safeString(
                                    settlement.getMerchantNumber()
                            )
                    );

                    merchantNumber.setCellStyle(textStyle);


                    // Merchant Name

                    Cell merchantName =
                            row.createCell(2);

                    merchantName.setCellValue(
                            safeString(
                                    settlement.getMerchantName()
                            )
                    );

                    merchantName.setCellStyle(textStyle);


                    // Settlement Date

                    Cell settlementDate =
                            row.createCell(3);

                    if (
                            settlement.getSettlementDate()
                                    != null
                    ) {

                        settlementDate.setCellValue(
                                settlement
                                        .getSettlementDate()
                                        .format(DATE_FORMAT)
                        );
                    }

                    settlementDate.setCellStyle(dateStyle);


                    // Transaction Count

                    long transactionCount =
                            settlement.getTransactionCount() != null
                                    ? settlement.getTransactionCount()
                                    : 0L;

                    Cell transactionCountCell =
                            row.createCell(4);

                    transactionCountCell.setCellValue(
                            transactionCount
                    );

                    transactionCountCell.setCellStyle(
                            numberStyle
                    );


                    // Transaction Amount

                    BigDecimal transactionAmount =
                            safeAmount(
                                    settlement
                                            .getTotalTransactionAmount()
                            );

                    Cell transactionAmountCell =
                            row.createCell(5);

                    transactionAmountCell.setCellValue(
                            transactionAmount.doubleValue()
                    );

                    transactionAmountCell.setCellStyle(
                            amountStyle
                    );


                    // MDR Amount

                    BigDecimal mdrAmount =
                            safeAmount(
                                    settlement
                                            .getTotalMdrAmount()
                            );

                    Cell mdrCell =
                            row.createCell(6);

                    mdrCell.setCellValue(
                            mdrAmount.doubleValue()
                    );

                    mdrCell.setCellStyle(amountStyle);


                    // Settlement Amount

                    BigDecimal settlementAmount =
                            safeAmount(
                                    settlement
                                            .getTotalSettlementAmount()
                            );

                    Cell settlementAmountCell =
                            row.createCell(7);

                    settlementAmountCell.setCellValue(
                            settlementAmount.doubleValue()
                    );

                    settlementAmountCell.setCellStyle(
                            amountStyle
                    );


                    // Settlement Status

                    Cell statusCell =
                            row.createCell(8);

                    statusCell.setCellValue(
                            settlement.getSettlementStatus() != null
                                    ? settlement
                                    .getSettlementStatus()
                                    .name()
                                    : ""
                    );

                    statusCell.setCellStyle(centerStyle);


                    // Totals

                    totalTransactionCount +=
                            transactionCount;

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


            // TOTAL merged 0-3

            Cell totalLabel =
                    totalRow.createCell(0);

            totalLabel.setCellValue("TOTAL");
            totalLabel.setCellStyle(totalLabelStyle);

            for (int i = 1; i <= 3; i++) {

                totalRow
                        .createCell(i)
                        .setCellStyle(totalLabelStyle);
            }

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            rowIndex,
                            rowIndex,
                            0,
                            3
                    )
            );


            // Total Transaction Count

            Cell totalCount =
                    totalRow.createCell(4);

            totalCount.setCellValue(
                    totalTransactionCount
            );

            totalCount.setCellStyle(totalNumberStyle);


            // Total Transaction Amount

            Cell totalTransaction =
                    totalRow.createCell(5);

            totalTransaction.setCellValue(
                    totalTransactionAmount.doubleValue()
            );

            totalTransaction.setCellStyle(
                    totalAmountStyle
            );


            // Total MDR

            Cell totalMdr =
                    totalRow.createCell(6);

            totalMdr.setCellValue(
                    totalMdrAmount.doubleValue()
            );

            totalMdr.setCellStyle(totalAmountStyle);


            // Total Settlement

            Cell totalSettlement =
                    totalRow.createCell(7);

            totalSettlement.setCellValue(
                    totalSettlementAmount.doubleValue()
            );

            totalSettlement.setCellStyle(
                    totalAmountStyle
            );


            // Total Status

            Cell totalStatus =
                    totalRow.createCell(8);

            totalStatus.setCellStyle(totalLabelStyle);


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
                    23, 18, 32, 18, 18,
                    25, 20, 25, 18
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
                    "Failed to generate Settlement Report Excel",
                    e
            );
        }
    }


    private String safeString(String value) {

        return value != null ? value : "";
    }


    private BigDecimal safeAmount(BigDecimal value) {

        return value != null
                ? value
                : BigDecimal.ZERO;
    }
}