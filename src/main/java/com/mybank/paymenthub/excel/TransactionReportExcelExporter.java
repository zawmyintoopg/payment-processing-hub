package com.mybank.paymenthub.excel;

import com.mybank.paymenthub.dto.response.TransactionReportResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.xddf.usermodel.chart.*;

import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TransactionReportExcelExporter {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private static final DateTimeFormatter DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");


    // =========================================================
    // EXPORT
    // =========================================================

    public byte[] export(
            List<TransactionReportResponse> transactions,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {

            List<TransactionReportResponse> safeTransactions =
                    transactions != null
                            ? transactions
                            : List.of();


            // =================================================
            // 1. TRANSACTION DETAIL
            // =================================================

            createDetailSheet(
                    workbook,
                    safeTransactions,
                    fromDate,
                    toDate
            );


            // =================================================
            // 2. TRANSACTION SUMMARY
            // =================================================

            createSummarySheet(
                    workbook,
                    safeTransactions,
                    fromDate,
                    toDate
            );


            // =================================================
            // 3. MERCHANT CHART
            // =================================================

            createMerchantChartSheet(
                    workbook,
                    safeTransactions,
                    fromDate,
                    toDate
            );


            workbook.write(output);

            return output.toByteArray();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to generate Transaction Report Excel",
                    e
            );
        }
    }


    // =========================================================
    // DETAIL SHEET
    // =========================================================

    private void createDetailSheet(
            Workbook workbook,
            List<TransactionReportResponse> transactions,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        Sheet sheet =
                workbook.createSheet("Transaction Detail");

        sheet.setDisplayGridlines(false);


        // -----------------------------------------------------
        // Styles
        // -----------------------------------------------------

        CellStyle titleStyle =
                ExcelCommonStyle.title(workbook);

        CellStyle headerStyle =
                ExcelCommonStyle.header(workbook);

        CellStyle textStyle =
                ExcelCommonStyle.text(workbook);

        CellStyle centerStyle =
                ExcelCommonStyle.center(workbook);

        CellStyle amountStyle =
                ExcelCommonStyle.amount(workbook);

        CellStyle labelStyle =
                ExcelCommonStyle.label(workbook);


        // -----------------------------------------------------
        // Title
        // -----------------------------------------------------

        Row titleRow =
                sheet.createRow(0);

        titleRow.setHeightInPoints(30);

        Cell title =
                titleRow.createCell(0);

        title.setCellValue(
                "TRANSACTION DETAIL REPORT"
        );

        title.setCellStyle(titleStyle);


        sheet.addMergedRegion(
                new CellRangeAddress(
                        0,
                        0,
                        0,
                        17
                )
        );


        // -----------------------------------------------------
        // Period
        // -----------------------------------------------------

        Row periodRow =
                sheet.createRow(2);


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


        // -----------------------------------------------------
        // Headers
        // -----------------------------------------------------

        String[] headers = {

                "Transaction Number",
                "Transaction Date",
                "Reference Number",

                "Merchant Number",
                "Merchant Name",

                "Outlet Number",
                "Outlet Name",

                "Terminal Number",

                "Payment Channel",
                "Payment Method",

                "Currency",

                "Transaction Type",

                "Transaction Amount",
                "MDR Rate",
                "MDR Amount",
                "Settlement Amount",

                "Transaction Status",
                "Settlement Status"
        };


        Row headerRow =
                sheet.createRow(4);

        headerRow.setHeightInPoints(40);


        for (int i = 0; i < headers.length; i++) {

            Cell cell =
                    headerRow.createCell(i);

            cell.setCellValue(headers[i]);

            cell.setCellStyle(headerStyle);
        }


        // -----------------------------------------------------
        // Data
        // -----------------------------------------------------

        int rowIndex = 5;


        for (TransactionReportResponse tx : transactions) {

            Row row =
                    sheet.createRow(rowIndex++);


            setText(
                    row,
                    0,
                    tx.transactionNumber(),
                    textStyle
            );


            setText(
                    row,
                    1,
                    tx.transactionTimestamp() != null
                            ? tx.transactionTimestamp()
                            .format(DATETIME_FORMAT)
                            : "",
                    textStyle
            );


            setText(
                    row,
                    2,
                    tx.referenceNumber(),
                    textStyle
            );


            setText(
                    row,
                    3,
                    tx.merchantNumber(),
                    textStyle
            );


            setText(
                    row,
                    4,
                    tx.merchantName(),
                    textStyle
            );


            setText(
                    row,
                    5,
                    tx.outletNumber(),
                    textStyle
            );


            setText(
                    row,
                    6,
                    tx.outletName(),
                    textStyle
            );


            setText(
                    row,
                    7,
                    tx.terminalNumber(),
                    textStyle
            );


            setText(
                    row,
                    8,
                    tx.paymentChannel(),
                    textStyle
            );


            setText(
                    row,
                    9,
                    tx.paymentMethod(),
                    textStyle
            );


            setText(
                    row,
                    10,
                    tx.currency(),
                    centerStyle
            );


            setText(
                    row,
                    11,
                    tx.transactionType() != null
                            ? tx.transactionType().name()
                            : "",
                    centerStyle
            );


            setAmount(
                    row,
                    12,
                    tx.transactionAmount(),
                    amountStyle
            );


            setAmount(
                    row,
                    13,
                    tx.mdrRate(),
                    amountStyle
            );


            setAmount(
                    row,
                    14,
                    tx.mdrAmount(),
                    amountStyle
            );


            setAmount(
                    row,
                    15,
                    tx.settlementAmount(),
                    amountStyle
            );


            setText(
                    row,
                    16,
                    tx.transactionStatus() != null
                            ? tx.transactionStatus().name()
                            : "",
                    centerStyle
            );


            setText(
                    row,
                    17,
                    tx.settlementStatus() != null
                            ? tx.settlementStatus().name()
                            : "",
                    centerStyle
            );
        }


        // -----------------------------------------------------
        // Auto Filter
        // -----------------------------------------------------

        sheet.setAutoFilter(
                new CellRangeAddress(
                        4,
                        Math.max(4, rowIndex - 1),
                        0,
                        17
                )
        );


        // -----------------------------------------------------
        // Freeze
        // -----------------------------------------------------

        sheet.createFreezePane(0, 5);


        // -----------------------------------------------------
        // Column Width
        // -----------------------------------------------------

        int[] widths = {

                22,
                22,
                20,

                18,
                30,

                18,
                28,

                18,

                20,
                20,

                12,

                18,

                20,
                15,
                18,
                20,

                20,
                20
        };


        for (int i = 0; i < widths.length; i++) {

            sheet.setColumnWidth(
                    i,
                    widths[i] * 256
            );
        }
    }


    // =========================================================
    // SUMMARY SHEET
    // =========================================================

    private void createSummarySheet(
            Workbook workbook,
            List<TransactionReportResponse> transactions,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        XSSFSheet sheet =
                (XSSFSheet) workbook.createSheet(
                        "Transaction Summary"
                );

        sheet.setDisplayGridlines(false);


        // -----------------------------------------------------
        // Styles
        // -----------------------------------------------------

        CellStyle titleStyle =
                ExcelCommonStyle.title(workbook);

        CellStyle headerStyle =
                ExcelCommonStyle.header(workbook);

        CellStyle textStyle =
                ExcelCommonStyle.text(workbook);

        CellStyle numberStyle =
                ExcelCommonStyle.number(workbook);

        CellStyle amountStyle =
                ExcelCommonStyle.amount(workbook);

        CellStyle labelStyle =
                ExcelCommonStyle.label(workbook);


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


        // -----------------------------------------------------
        // Title
        // -----------------------------------------------------

        Row titleRow =
                sheet.createRow(0);

        titleRow.setHeightInPoints(30);

        Cell title =
                titleRow.createCell(0);

        title.setCellValue(
                "TRANSACTION SUMMARY REPORT"
        );

        title.setCellStyle(titleStyle);


        sheet.addMergedRegion(
                new CellRangeAddress(
                        0,
                        0,
                        0,
                        8
                )
        );


        // -----------------------------------------------------
        // Period
        // -----------------------------------------------------

        Row periodRow =
                sheet.createRow(2);


        Cell fromLabel =
                periodRow.createCell(0);

        fromLabel.setCellValue("Report From");
        fromLabel.setCellStyle(labelStyle);


        periodRow.createCell(1)
                .setCellValue(
                        fromDate != null
                                ? fromDate.format(DATE_FORMAT)
                                : ""
                );


        Cell toLabel =
                periodRow.createCell(3);

        toLabel.setCellValue("Report To");
        toLabel.setCellStyle(labelStyle);


        periodRow.createCell(4)
                .setCellValue(
                        toDate != null
                                ? toDate.format(DATE_FORMAT)
                                : ""
                );


        // -----------------------------------------------------
        // Headers
        // -----------------------------------------------------

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


        Row headerRow =
                sheet.createRow(4);

        headerRow.setHeightInPoints(40);


        for (int i = 0; i < headers.length; i++) {

            Cell cell =
                    headerRow.createCell(i);

            cell.setCellValue(headers[i]);

            cell.setCellStyle(headerStyle);
        }


        // -----------------------------------------------------
        // Group by Merchant
        // -----------------------------------------------------

        Map<String, List<TransactionReportResponse>> groups =

                transactions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        tx ->
                                                tx.merchantNumber() != null
                                                        ? tx.merchantNumber()
                                                        : ""
                                )
                        );


        int rowIndex = 5;


        long totalTransactions = 0;
        long totalSuccessful = 0;
        long totalFailed = 0;
        long totalReversed = 0;


        BigDecimal totalAmount =
                BigDecimal.ZERO;

        BigDecimal totalMdr =
                BigDecimal.ZERO;

        BigDecimal totalSettlement =
                BigDecimal.ZERO;


        // -----------------------------------------------------
        // Merchant Rows
        // -----------------------------------------------------

        for (
                Map.Entry<String, List<TransactionReportResponse>> entry
                : groups.entrySet()
        ) {

            List<TransactionReportResponse> merchantTx =
                    entry.getValue();


            if (merchantTx.isEmpty()) {
                continue;
            }


            TransactionReportResponse first =
                    merchantTx.get(0);


            long total =
                    merchantTx.size();


            long successful =
                    merchantTx.stream()
                            .filter(
                                    t ->
                                            t.transactionStatus() != null
                                                    &&
                                                    t.transactionStatus()
                                                            .name()
                                                            .equals("SUCCESS")
                            )
                            .count();


            long failed =
                    merchantTx.stream()
                            .filter(
                                    t ->
                                            t.transactionStatus() != null
                                                    &&
                                                    t.transactionStatus()
                                                            .name()
                                                            .equals("FAILED")
                            )
                            .count();


            long reversed =
                    merchantTx.stream()
                            .filter(
                                    t ->
                                            t.transactionStatus() != null
                                                    &&
                                                    t.transactionStatus()
                                                            .name()
                                                            .equals("REVERSED")
                            )
                            .count();


            BigDecimal amount =
                    sum(
                            merchantTx,
                            TransactionReportResponse::transactionAmount
                    );


            BigDecimal mdr =
                    sum(
                            merchantTx,
                            TransactionReportResponse::mdrAmount
                    );


            BigDecimal settlement =
                    sum(
                            merchantTx,
                            TransactionReportResponse::settlementAmount
                    );


            Row row =
                    sheet.createRow(rowIndex++);


            setText(
                    row,
                    0,
                    first.merchantNumber(),
                    textStyle
            );


            setText(
                    row,
                    1,
                    first.merchantName(),
                    textStyle
            );


            setNumber(
                    row,
                    2,
                    total,
                    numberStyle
            );


            setNumber(
                    row,
                    3,
                    successful,
                    numberStyle
            );


            setNumber(
                    row,
                    4,
                    failed,
                    numberStyle
            );


            setNumber(
                    row,
                    5,
                    reversed,
                    numberStyle
            );


            setAmount(
                    row,
                    6,
                    amount,
                    amountStyle
            );


            setAmount(
                    row,
                    7,
                    mdr,
                    amountStyle
            );


            setAmount(
                    row,
                    8,
                    settlement,
                    amountStyle
            );


            // -------------------------------------------------
            // Total calculation
            // -------------------------------------------------

            totalTransactions += total;
            totalSuccessful += successful;
            totalFailed += failed;
            totalReversed += reversed;


            totalAmount =
                    totalAmount.add(amount);

            totalMdr =
                    totalMdr.add(mdr);

            totalSettlement =
                    totalSettlement.add(settlement);
        }


        // -----------------------------------------------------
        // TOTAL ROW
        // -----------------------------------------------------

        Row totalRow =
                sheet.createRow(rowIndex);


        Cell totalLabel =
                totalRow.createCell(0);

        totalLabel.setCellValue("TOTAL");

        totalLabel.setCellStyle(totalLabelStyle);


        sheet.addMergedRegion(
                new CellRangeAddress(
                        rowIndex,
                        rowIndex,
                        0,
                        1
                )
        );


        setNumber(
                totalRow,
                2,
                totalTransactions,
                totalNumberStyle
        );


        setNumber(
                totalRow,
                3,
                totalSuccessful,
                totalNumberStyle
        );


        setNumber(
                totalRow,
                4,
                totalFailed,
                totalNumberStyle
        );


        setNumber(
                totalRow,
                5,
                totalReversed,
                totalNumberStyle
        );


        setAmount(
                totalRow,
                6,
                totalAmount,
                totalAmountStyle
        );


        setAmount(
                totalRow,
                7,
                totalMdr,
                totalAmountStyle
        );


        setAmount(
                totalRow,
                8,
                totalSettlement,
                totalAmountStyle
        );


        // -----------------------------------------------------
        // Filter
        // -----------------------------------------------------

        sheet.setAutoFilter(
                new CellRangeAddress(
                        4,
                        Math.max(4, rowIndex - 1),
                        0,
                        8
                )
        );


        // -----------------------------------------------------
        // Freeze
        // -----------------------------------------------------

        sheet.createFreezePane(0, 5);


        // -----------------------------------------------------
        // Summary Width
        // -----------------------------------------------------

        int[] widths = {

                20,
                32,

                20,
                24,
                20,
                22,

                25,
                20,
                25
        };


        for (int i = 0; i < widths.length; i++) {

            sheet.setColumnWidth(
                    i,
                    widths[i] * 256
            );
        }


        // =====================================================
        // STATUS CHART DATA
        // =====================================================

        createStatusChartData(
                sheet,
                transactions
        );


        // =====================================================
        // STATUS PIE CHART
        // =====================================================

        createStatusChart(
                sheet
        );
    }


    // =========================================================
    // STATUS CHART DATA
    // =========================================================

    private void createStatusChartData(
            XSSFSheet sheet,
            List<TransactionReportResponse> transactions
    ) {

        // K:L
        sheet.setColumnHidden(10, true);
        sheet.setColumnHidden(11, true);


        // -----------------------------------------------------
        // Header
        // -----------------------------------------------------

        Row header =
                getOrCreateRow(
                        sheet,
                        0
                );


        header.createCell(10)
                .setCellValue("Status");


        header.createCell(11)
                .setCellValue("Count");


        // -----------------------------------------------------
        // Success
        // -----------------------------------------------------

        long successCount =
                transactions.stream()
                        .filter(
                                t ->
                                        t.transactionStatus() != null
                                                &&
                                                t.transactionStatus()
                                                        .name()
                                                        .equals("SUCCESS")
                        )
                        .count();


        // -----------------------------------------------------
        // Failed
        // -----------------------------------------------------

        long failedCount =
                transactions.stream()
                        .filter(
                                t ->
                                        t.transactionStatus() != null
                                                &&
                                                t.transactionStatus()
                                                        .name()
                                                        .equals("FAILED")
                        )
                        .count();


        // -----------------------------------------------------
        // Reversed
        // -----------------------------------------------------

        long reversedCount =
                transactions.stream()
                        .filter(
                                t ->
                                        t.transactionStatus() != null
                                                &&
                                                t.transactionStatus()
                                                        .name()
                                                        .equals("REVERSED")
                        )
                        .count();


        createChartDataRow(
                sheet,
                1,
                10,
                11,
                "SUCCESS",
                successCount
        );


        createChartDataRow(
                sheet,
                2,
                10,
                11,
                "FAILED",
                failedCount
        );


        createChartDataRow(
                sheet,
                3,
                10,
                11,
                "REVERSED",
                reversedCount
        );
    }


    // =========================================================
    // STATUS PIE CHART
    // =========================================================

    private void createStatusChart(
            XSSFSheet sheet
    ) {

        XSSFDrawing drawing =
                sheet.createDrawingPatriarch();


        // -----------------------------------------------------
        // Chart Position
        // -----------------------------------------------------

        XSSFClientAnchor anchor =
                drawing.createAnchor(
                        0,
                        0,
                        0,
                        0,
                        10,
                        4,
                        17,
                        18
                );


        XSSFChart chart =
                drawing.createChart(anchor);


        // -----------------------------------------------------
        // Title
        // -----------------------------------------------------

        chart.setTitleText(
                "Transaction Status"
        );

        chart.setTitleOverlay(false);


        // -----------------------------------------------------
        // Legend
        // -----------------------------------------------------

        XDDFChartLegend legend =
                chart.getOrAddLegend();

        legend.setPosition(
                LegendPosition.RIGHT
        );


        // -----------------------------------------------------
        // Categories
        //
        // K2:K4
        // -----------------------------------------------------

        XDDFDataSource<String> statusData =
                XDDFDataSourcesFactory.fromStringCellRange(
                        sheet,
                        new CellRangeAddress(
                                1,
                                3,
                                10,
                                10
                        )
                );


        // -----------------------------------------------------
        // Values
        //
        // L2:L4
        // -----------------------------------------------------

        XDDFNumericalDataSource<Double> countData =
                XDDFDataSourcesFactory.fromNumericCellRange(
                        sheet,
                        new CellRangeAddress(
                                1,
                                3,
                                11,
                                11
                        )
                );


        // -----------------------------------------------------
        // Create Pie
        // -----------------------------------------------------

        XDDFChartData chartData =
                chart.createData(
                        ChartTypes.PIE,
                        null,
                        null
                );


        chartData.setVaryColors(true);


        XDDFChartData.Series series =
                chartData.addSeries(
                        statusData,
                        countData
                );


        series.setTitle(
                "Transactions",
                null
        );


        chart.plot(chartData);
    }


    // =========================================================
    // MERCHANT CHART SHEET
    // =========================================================

    private void createMerchantChartSheet(
            Workbook workbook,
            List<TransactionReportResponse> transactions,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        XSSFSheet sheet =
                (XSSFSheet) workbook.createSheet(
                        "Merchant Chart"
                );

        sheet.setDisplayGridlines(false);


        // -----------------------------------------------------
        // Styles
        // -----------------------------------------------------

        CellStyle titleStyle =
                ExcelCommonStyle.title(workbook);

        CellStyle headerStyle =
                ExcelCommonStyle.header(workbook);

        CellStyle amountStyle =
                ExcelCommonStyle.amount(workbook);

        CellStyle labelStyle =
                ExcelCommonStyle.label(workbook);


        // -----------------------------------------------------
        // Title
        // -----------------------------------------------------

        Row titleRow =
                sheet.createRow(0);

        titleRow.setHeightInPoints(30);


        Cell title =
                titleRow.createCell(0);

        title.setCellValue(
                "TRANSACTION AMOUNT BY MERCHANT"
        );

        title.setCellStyle(titleStyle);


        sheet.addMergedRegion(
                new CellRangeAddress(
                        0,
                        0,
                        0,
                        6
                )
        );


        // -----------------------------------------------------
        // Period
        // -----------------------------------------------------

        Row periodRow =
                sheet.createRow(2);


        Cell fromLabel =
                periodRow.createCell(0);

        fromLabel.setCellValue(
                "Report From"
        );

        fromLabel.setCellStyle(
                labelStyle
        );


        periodRow.createCell(1)
                .setCellValue(
                        fromDate != null
                                ? fromDate.format(DATE_FORMAT)
                                : ""
                );


        Cell toLabel =
                periodRow.createCell(3);

        toLabel.setCellValue(
                "Report To"
        );

        toLabel.setCellStyle(
                labelStyle
        );


        periodRow.createCell(4)
                .setCellValue(
                        toDate != null
                                ? toDate.format(DATE_FORMAT)
                                : ""
                );


        // =====================================================
        // GROUP BY MERCHANT
        // =====================================================

        Map<String, List<TransactionReportResponse>> groups =

                transactions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        tx ->
                                                tx.merchantNumber() != null
                                                        ? tx.merchantNumber()
                                                        : ""
                                )
                        );


        // =====================================================
        // CHART DATA
        // =====================================================

        /*
         * We keep chart data in columns J and K.
         *
         * J = Merchant
         * K = Transaction Amount
         */

        sheet.setColumnHidden(9, true);
        sheet.setColumnHidden(10, true);


        Row chartHeader =
                sheet.createRow(4);


        chartHeader.createCell(9)
                .setCellValue(
                        "Merchant"
                );


        chartHeader.createCell(10)
                .setCellValue(
                        "Transaction Amount"
                );


        int merchantDataRow = 5;


        for (
                Map.Entry<String, List<TransactionReportResponse>> entry
                : groups.entrySet()
        ) {

            String merchantNumber =
                    entry.getKey();


            BigDecimal amount =
                    sum(
                            entry.getValue(),
                            TransactionReportResponse::transactionAmount
                    );


            Row row =
                    sheet.createRow(
                            merchantDataRow
                    );


            row.createCell(9)
                    .setCellValue(
                            merchantNumber
                    );


            Cell amountCell =
                    row.createCell(10);


            amountCell.setCellValue(
                    amount.doubleValue()
            );


            amountCell.setCellStyle(
                    amountStyle
            );


            merchantDataRow++;
        }


        // =====================================================
        // CREATE BAR CHART
        // =====================================================

        if (!groups.isEmpty()) {

            createMerchantAmountChart(
                    sheet,
                    groups.size()
            );
        }


        // =====================================================
        // COLUMN WIDTH
        // =====================================================

        sheet.setColumnWidth(
                0,
                25 * 256
        );


        sheet.setColumnWidth(
                1,
                25 * 256
        );


        sheet.setColumnWidth(
                3,
                25 * 256
        );


        sheet.setColumnWidth(
                4,
                25 * 256
        );
    }


    // =========================================================
    // MERCHANT AMOUNT BAR CHART
    // =========================================================

    private void createMerchantAmountChart(
            XSSFSheet sheet,
            int merchantCount
    ) {

        XSSFDrawing drawing =
                sheet.createDrawingPatriarch();


        // -----------------------------------------------------
        // Chart Position
        // -----------------------------------------------------

        XSSFClientAnchor anchor =
                drawing.createAnchor(
                        0,
                        0,
                        0,
                        0,
                        0,
                        5,
                        16,
                        30
                );


        XSSFChart chart =
                drawing.createChart(anchor);


        // -----------------------------------------------------
        // Title
        // -----------------------------------------------------

        chart.setTitleText(
                "Transaction Amount by Merchant"
        );

        chart.setTitleOverlay(false);


        // -----------------------------------------------------
        // Legend
        // -----------------------------------------------------

        XDDFChartLegend legend =
                chart.getOrAddLegend();

        legend.setPosition(
                LegendPosition.BOTTOM
        );


        // -----------------------------------------------------
        // Merchant Names
        //
        // J6:J...
        //
        // Java row 5 = Excel row 6
        // -----------------------------------------------------

        XDDFDataSource<String> merchantNames =
                XDDFDataSourcesFactory.fromStringCellRange(
                        sheet,
                        new CellRangeAddress(
                                5,
                                merchantCount + 4,
                                9,
                                9
                        )
                );


        // -----------------------------------------------------
        // Transaction Amount
        //
        // K6:K...
        // -----------------------------------------------------

        XDDFNumericalDataSource<Double> merchantAmounts =
                XDDFDataSourcesFactory.fromNumericCellRange(
                        sheet,
                        new CellRangeAddress(
                                5,
                                merchantCount + 4,
                                10,
                                10
                        )
                );


        // -----------------------------------------------------
        // Category Axis
        // -----------------------------------------------------

        XDDFCategoryAxis categoryAxis =
                chart.createCategoryAxis(
                        AxisPosition.BOTTOM
                );


        categoryAxis.setTitle(
                "Merchant"
        );


        // -----------------------------------------------------
        // Value Axis
        // -----------------------------------------------------

        XDDFValueAxis valueAxis =
                chart.createValueAxis(
                        AxisPosition.LEFT
                );


        valueAxis.setTitle(
                "Transaction Amount"
        );


        // -----------------------------------------------------
        // Create Bar Chart
        // -----------------------------------------------------

        XDDFChartData chartData =
                chart.createData(
                        ChartTypes.BAR,
                        categoryAxis,
                        valueAxis
                );


        XDDFBarChartData barData =
                (XDDFBarChartData) chartData;


        /*
         * IMPORTANT:
         *
         * Do NOT use:
         *
         * barData.setBarGrouping(
         *     BarGrouping.CLUSTERED
         * );
         *
         * Your Apache POI version does not provide
         * this method.
         */


        // -----------------------------------------------------
        // Vertical Columns
        // -----------------------------------------------------

        barData.setBarDirection(
                BarDirection.COL
        );


        // -----------------------------------------------------
        // Different color for each merchant
        // -----------------------------------------------------

        barData.setVaryColors(true);


        // -----------------------------------------------------
        // Add Series
        // -----------------------------------------------------

        XDDFChartData.Series series =
                barData.addSeries(
                        merchantNames,
                        merchantAmounts
                );


        series.setTitle(
                "Transaction Amount",
                null
        );


        // -----------------------------------------------------
        // Plot
        // -----------------------------------------------------

        chart.plot(
                barData
        );
    }


    // =========================================================
    // CREATE CHART DATA ROW
    // =========================================================

    private void createChartDataRow(
            Sheet sheet,
            int rowNumber,
            int labelColumn,
            int valueColumn,
            String label,
            long value
    ) {

        Row row =
                getOrCreateRow(
                        sheet,
                        rowNumber
                );


        row.createCell(labelColumn)
                .setCellValue(label);


        row.createCell(valueColumn)
                .setCellValue(value);
    }


    // =========================================================
    // GET / CREATE ROW
    // =========================================================

    private Row getOrCreateRow(
            Sheet sheet,
            int rowNumber
    ) {

        Row row =
                sheet.getRow(rowNumber);


        if (row == null) {

            row =
                    sheet.createRow(rowNumber);
        }


        return row;
    }


    // =========================================================
    // TEXT
    // =========================================================

    private void setText(
            Row row,
            int column,
            String value,
            CellStyle style
    ) {

        Cell cell =
                row.createCell(column);


        cell.setCellValue(
                value != null
                        ? value
                        : ""
        );


        cell.setCellStyle(style);
    }


    // =========================================================
    // NUMBER
    // =========================================================

    private void setNumber(
            Row row,
            int column,
            long value,
            CellStyle style
    ) {

        Cell cell =
                row.createCell(column);


        cell.setCellValue(value);


        cell.setCellStyle(style);
    }


    // =========================================================
    // AMOUNT
    // =========================================================

    private void setAmount(
            Row row,
            int column,
            BigDecimal value,
            CellStyle style
    ) {

        Cell cell =
                row.createCell(column);


        cell.setCellValue(
                value != null
                        ? value.doubleValue()
                        : 0
        );


        cell.setCellStyle(style);
    }


    // =========================================================
    // SUM
    // =========================================================

    private BigDecimal sum(
            List<TransactionReportResponse> transactions,
            java.util.function.Function<
                    TransactionReportResponse,
                    BigDecimal
                    > getter
    ) {

        return transactions.stream()

                .map(getter)

                .filter(
                        value ->
                                value != null
                )

                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
}