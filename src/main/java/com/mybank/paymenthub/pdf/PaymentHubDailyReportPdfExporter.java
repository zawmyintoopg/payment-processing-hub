package com.mybank.paymenthub.pdf;

import com.mybank.paymenthub.dto.response.DashboardResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class PaymentHubDailyReportPdfExporter {

    private static final float MARGIN = 50;
    private static final float LINE_HEIGHT = 25;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public byte[] export(
            DashboardResponse dashboard,
            LocalDate reportDate
    ) {

        try (
                PDDocument document = new PDDocument();
                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream()
        ) {

            PDPage page =
                    new PDPage(PDRectangle.A4);

            document.addPage(page);

            try (
                    PDPageContentStream contentStream =
                            new PDPageContentStream(
                                    document,
                                    page
                            )
            ) {

                float y =
                        page.getMediaBox().getHeight()
                                - MARGIN;

                // =====================================================
                // Title
                // =====================================================

                contentStream.beginText();

                contentStream.setFont(
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA_BOLD
                        ),
                        18
                );

                contentStream.newLineAtOffset(
                        MARGIN,
                        y
                );

                contentStream.showText(
                        "PAYMENT HUB DAILY REPORT"
                );

                contentStream.endText();

                y -= 35;

                // =====================================================
                // Report Date
                // =====================================================

                contentStream.beginText();

                contentStream.setFont(
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA
                        ),
                        11
                );

                contentStream.newLineAtOffset(
                        MARGIN,
                        y
                );

                contentStream.showText(
                        "Report Date : "
                                + reportDate.format(
                                DATE_FORMATTER
                        )
                );

                contentStream.endText();

                y -= 40;

                // =====================================================
                // Summary
                // =====================================================

                y = writeRow(
                        contentStream,
                        "Total Transactions",
                        String.valueOf(
                                dashboard.totalTransactions()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "Successful",
                        String.valueOf(
                                dashboard.successfulTransactions()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "Failed",
                        String.valueOf(
                                dashboard.failedTransactions()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "Pending",
                        String.valueOf(
                                dashboard.pendingTransactions()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "Reversed",
                        String.valueOf(
                                dashboard.reversedTransactions()
                        ),
                        y
                );

                y -= 10;

                // =====================================================
                // Amount
                // =====================================================

                y = writeRow(
                        contentStream,
                        "Total Transaction Amount",
                        formatAmount(
                                dashboard.totalTransactionAmount()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "MDR",
                        formatAmount(
                                dashboard.totalMdrAmount()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "Settlement",
                        formatAmount(
                                dashboard.totalSettlementAmount()
                        ),
                        y
                );

                y -= 10;

                // =====================================================
                // Rates
                // =====================================================

                y = writeRow(
                        contentStream,
                        "Success Rate",
                        formatPercentage(
                                dashboard.successRate()
                        ),
                        y
                );

                y = writeRow(
                        contentStream,
                        "Failure Rate",
                        formatPercentage(
                                dashboard.failureRate()
                        ),
                        y
                );

                // =====================================================
                // Footer
                // =====================================================

                y -= 35;

                contentStream.beginText();

                contentStream.setFont(
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA
                        ),
                        9
                );

                contentStream.newLineAtOffset(
                        MARGIN,
                        y
                );

                contentStream.showText(
                        "Generated by Payment Hub"
                );

                contentStream.endText();
            }

            document.save(outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to generate Payment Hub Daily Report PDF",
                    e
            );
        }
    }


    // =============================================================
    // Write Row
    // =============================================================

    private float writeRow(
            PDPageContentStream contentStream,
            String label,
            String value,
            float y
    ) throws IOException {

        contentStream.beginText();

        contentStream.setFont(
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA
                ),
                11
        );

        contentStream.newLineAtOffset(
                MARGIN,
                y
        );

        contentStream.showText(label);

        contentStream.endText();


        contentStream.beginText();

        contentStream.setFont(
                new PDType1Font(
                        Standard14Fonts.FontName.HELVETICA_BOLD
                ),
                11
        );

        contentStream.newLineAtOffset(
                350,
                y
        );

        contentStream.showText(value);

        contentStream.endText();

        return y - LINE_HEIGHT;
    }


    // =============================================================
    // Format Amount
    // =============================================================

    private String formatAmount(
            BigDecimal amount
    ) {

        if (amount == null) {
            return "0.00";
        }

        return String.format(
                "%,.2f",
                amount
        );
    }


    // =============================================================
    // Format Percentage
    // =============================================================

    private String formatPercentage(
            BigDecimal percentage
    ) {

        if (percentage == null) {
            return "0.00%";
        }

        return percentage
                .setScale(2)
                .toPlainString()
                + "%";
    }
}