//package com.example.kristp.controller.admin;
//        import lombok.AllArgsConstructor;
//        import lombok.Data;
//        import lombok.NoArgsConstructor;
//        import lombok.extern.slf4j.Slf4j;
//        import org.apache.pdfbox.pdmodel.PDDocument;
//        import org.apache.pdfbox.pdmodel.PDPage;
//        import org.apache.pdfbox.pdmodel.PDPageContentStream;
//        import org.apache.pdfbox.pdmodel.common.PDRectangle;
//        import org.apache.pdfbox.pdmodel.font.PDFont;
//        import org.apache.pdfbox.pdmodel.font.PDType1Font;
//        import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//        import org.example.demo.entity.order.core.Order;
//        import org.example.demo.entity.order.properties.OrderDetail;
//        import org.example.demo.entity.product.core.ProductDetail;
//        import org.example.demo.exception.CustomExceptions;
//        import org.example.demo.repository.order.OrderRepository;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.stereotype.Service;
//
//        import java.awt.*;
//        import java.io.ByteArrayOutputStream;
//        import java.io.IOException;
//        import java.text.Format;
//        import java.text.SimpleDateFormat;
//        import java.util.Date;
//        import java.util.List;
//
//@Slf4j
//@Service
//public class testpart2 {
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public ByteArrayOutputStream exportPdf(Integer idOrder) {
//        try {
//            Order order = orderRepository.findById(idOrder).orElseThrow(() -> new CustomExceptions.CustomBadRequest("Không tìm thấy hóa đơn này"));
//
//            PDDocument document = new PDDocument();
//            PDPage firstPage = new PDPage(PDRectangle.A4);
//            document.addPage(firstPage);
//
//
//            String name = "FPolytechnic";
//            String callNo = "+84833486999";
//
//            Format d_format = new SimpleDateFormat("dd/MM/yyyy");
//            Format t_format = new SimpleDateFormat("HH:mm");
//
//            int pageWidth = (int) firstPage.getTrimBox().getWidth();
//            int pageHeight = (int) firstPage.getTrimBox().getHeight();
//
//            PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);
//            MyTextClass myTextClass = new MyTextClass(document, contentStream);
//
//            PDFont font = PDType1Font.HELVETICA;
//            PDFont italicFont = PDType1Font.HELVETICA_OBLIQUE;
//
////            PDImageXObject headImage = PDImageXObject.createFromFile("src/main/resources/image/header.png", document);
////            contentStream.drawImage(headImage, 0, pageHeight - 235, pageWidth, 239);
//
//            String[] contactDetails = new String[]{"+84 833486946", "+84 833486946"};
//            myTextClass.addMultiLineText(
//                    contactDetails,
//                    18,
//                    (int) (pageWidth - font.getStringWidth(contactDetails[0]) / 1000 * 15 - 10),
//                    pageHeight - 25,
//                    font,
//                    15,
//                    Color.BLACK
//            );
//            myTextClass.addSingleLineText("INDIAN TADKA", 25, pageHeight - 150, font, 40, Color.BLACK);
//
//            myTextClass.addSingleLineText("Khách hàng" + "phah04", 25, pageHeight - 250, font, 16, Color.BLACK);
//            myTextClass.addSingleLineText("Mo. No" + "phah04", 25, pageHeight - 274, font, 16, Color.BLACK);
//
//            String invoiceNo = "HD# " + "981772";
//            float textWidth = myTextClass.getTextWidth(invoiceNo, font, 16);
//            myTextClass.addSingleLineText(invoiceNo, (int) (pageWidth - 25 - textWidth), pageHeight - 250, font, 16, Color.BLACK);
//
//            float dateTextWidth = myTextClass.getTextWidth(
//                    "Date: " + d_format.format(new Date()),
//                    font,
//                    16
//            );
//            myTextClass.addSingleLineText("Ngày: " + d_format.format(new Date()), (int) (pageWidth - 25 - dateTextWidth), pageHeight - 274, font, 16, Color.BLACK);
//
//            String time = t_format.format(new Date());
//            String timeText = "Lúc: " + time;
//            float timeTextWidth = myTextClass.getTextWidth(timeText, font, 16);
//            myTextClass.addSingleLineText(timeText, (int) (pageWidth - 25 - timeTextWidth), pageHeight - 298, font, 16, Color.BLACK);
//
//
//            MyTableClass myTable = new MyTableClass(document, contentStream);
//            int[] cellWidths = {70, 160, 120, 90, 100};
//            myTable.setTable(cellWidths, 30, 25, pageHeight - 350);
//            myTable.setTableFont(font, 16, Color.BLACK);
//
//            Color tableHeadColor = new Color(122, 122, 122);
//            Color tableBodyColor = new Color(219, 218, 198);
//
//            myTable.addCell("Si.No.", tableHeadColor);
//            myTable.addCell("Items.", tableHeadColor);
//            myTable.addCell("Price", tableHeadColor);
//            myTable.addCell("Qty.", tableHeadColor);
//            myTable.addCell("Total", tableHeadColor);
//
//
//            List<OrderDetail> orderDetailList = order.getOrderDetails();
//            for (int i = 0; i < orderDetailList.size(); i++) {
//                OrderDetail s = orderDetailList.get(i);
//                String productName = s.getProductDetail().getProduct().getName();
//                String quantity = s.getQuantity().toString();
//                String price = String.valueOf(Math.round(s.getProductDetail().getPrice()));
//                String subTotal = String.valueOf(Math.round(s.getProductDetail().getPrice() * s.getQuantity()));
//
//                myTable.addCell(String.valueOf(i), tableBodyColor);
//                myTable.addCell(productName, tableBodyColor);
//                myTable.addCell(quantity, tableBodyColor);
//                myTable.addCell(price, tableBodyColor);
//                myTable.addCell(subTotal, tableBodyColor);
//            }
//
//            String[] paymentMethod = {"Methods of payment we accept:", "Cash, PhoneGe, GPay, RuPay", "Visa, Mastercard and American Express"};
//            myTextClass.addMultiLineText(paymentMethod, 15, 25, 180, italicFont, 10, new Color(122, 122, 122));
//
//            contentStream.setStrokingColor(Color.BLACK);
//            contentStream.setLineWidth(2);
//            contentStream.moveTo(pageWidth - 250, 150);
//            contentStream.lineTo(pageWidth - 25, 150);
//            contentStream.stroke();
//
//            String authorSign = "Authorised Signatory";
//            float authorSignWidth = myTextClass.getTextWidth(authorSign, italicFont, 16);
//            int xpos = pageWidth - 250 + pageWidth - 25;
//            myTextClass.addSingleLineText(authorSign, (int) (xpos - authorSignWidth) / 2, 125, italicFont, 16, Color.BLACK);
//
//
//            String bottomLine = "Rain or shine, time to dinner";
//            float bottomLineWidth = myTextClass.getTextWidth(bottomLine, font, 20);
//            myTextClass.addSingleLineText(bottomLine, (int) (pageWidth - bottomLineWidth) / 2, 50, italicFont, 20, Color.DARK_GRAY);
//
//
//            Color bottomRectColor = new Color(122, 122, 122);
//            contentStream.setNonStrokingColor(bottomRectColor);
//            contentStream.addRect(0, 0, pageWidth, 30);
//            contentStream.fill();
//
//
//            contentStream.close();
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////            document.save("C:\\PDF\\myPDF.pdf");
//            document.save(outputStream);
//            document.close();
//            System.out.println("PDF CREATED");
//            return outputStream;
//
//
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return null;
//        }
//
//    }
//
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Data
//    public static class MyTextClass {
//        PDDocument document;
//        PDPageContentStream contentStream;
//
//        void addSingleLineText(
//                String text,
//                int xPosition,
//                int yPosition,
//                PDFont font,
//                float fontSize,
//                Color color
//        ) throws IOException {
//            contentStream.beginText();
//            contentStream.setFont(font, fontSize);
//            contentStream.setNonStrokingColor(color);
//            contentStream.newLineAtOffset(xPosition, yPosition);
//            contentStream.showText(text);
//            contentStream.endText();
//            contentStream.moveTo(0, 0);
//        }
//
//        void addMultiLineText(
//                String[] textArray,
//                float leading,
//                int xPosition,
//                int yPosition,
//                PDFont font,
//                float fontSize,
//                Color color
//        ) throws IOException {
//            contentStream.beginText();
//            contentStream.setFont(font, fontSize);
//            contentStream.setNonStrokingColor(color);
//            contentStream.setLeading(leading);
//            contentStream.newLineAtOffset(xPosition, yPosition);
//            for (String text : textArray) {
//                contentStream.showText(text);
//                contentStream.newLine();
//            }
//            contentStream.endText();
//            contentStream.moveTo(0, 0);
//        }
//
//        float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
//            return font.getStringWidth(text) / 1000 * fontSize;
//        }
//
//    }
//
//
//    public static class MyTableClass {
//        PDDocument document;
//        PDPageContentStream contentStream;
//        private int[] colWidths;
//        private int cellHeight;
//        private int yPosition;
//        private int xPosition;
//        private int colPosition = 0;
//        private int xInitialPosition;
//        private float fontSize;
//        private PDFont font;
//        private Color fontColor;
//
//        public MyTableClass(PDDocument document, PDPageContentStream contentStream) {
//            this.document = document;
//            this.contentStream = contentStream;
//        }
//
//        void setTable(int[] colWidths, int cellHeight, int xPosition, int yPosition) {
//            this.colWidths = colWidths;
//            this.cellHeight = cellHeight;
//            this.xPosition = xPosition;
//            this.yPosition = yPosition;
//            xInitialPosition = xPosition;
//        }
//
//        void setTableFont(PDFont font, float fontSize, Color fontColor) {
//            this.font = font;
//            this.fontSize = fontSize;
//            this.fontColor = fontColor;
//        }
//
//        void addCell(String text, Color fillColor) throws IOException {
//            contentStream.setStrokingColor(1f);
//            if (fillColor != null) {
//                contentStream.setNonStrokingColor(fillColor);
//            }
//            contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHeight);
//            if (fillColor == null) {
//                contentStream.stroke();
//            } else {
//                contentStream.fillAndStroke();
//            }
//            contentStream.beginText();
//            contentStream.setNonStrokingColor(fontColor);
//
//            if (colPosition == 4 || colPosition == 2) {
//                float fontWidth = font.getStringWidth(text) / 1000 * fontSize;
//                contentStream.newLineAtOffset(xPosition + colWidths[colPosition] - 20 - fontWidth, yPosition + 10);
//            } else {
//                contentStream.newLineAtOffset(xPosition + 20, yPosition + 10);
//            }
//            contentStream.showText(text);
//            contentStream.endText();
//
//            xPosition = xPosition + colWidths[colPosition];
//            colPosition++;
//
//            if (colPosition == colWidths.length) {
//                colPosition = 0;
//                xPosition = xInitialPosition;
//                yPosition -= cellHeight;
//            }
//        }
//    }
//}
//
