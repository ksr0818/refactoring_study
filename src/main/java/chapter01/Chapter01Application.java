package chapter01;

import chapter01.domain.Statement;
import chapter01.dto.Invoice;
import chapter01.dto.Play;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;

public class Chapter01Application {
    public static <T> T readJson(String filepath, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File(filepath), valueType);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }


//    public static String statementHTML(Invoice invoice, Play[] plays) throws Exception {
//        int totalAmount = 0;
//
//        String result = String.format("청구 내역 (고객명: %s)\n", invoice.getCustomer());
//
//        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en-US"));
//        format.setCurrency(Currency.getInstance("USD"));
//        format.setMinimumFractionDigits(2);
//
//        int volumeCredits = 0;
//        for (Invoice.Performance perf : invoice.getPerformances()) {
//            Play play =
//                    Arrays.stream(plays)
//                            .filter(p -> p.getPlayId().equals(perf.getPlayId()))
//                            .findFirst()
//                            .get();
//
//            int thisAmount = amountFor(perf, play);
//            // 포인트 적립
//            volumeCredits += Math.max(perf.getAudience() - 30, 0);
//
//            // 희극 관객 5명마다 추가 포인트 제공
//            if (play.getType().equals("comedy")) {
//                volumeCredits += Math.floor(perf.getAudience() / 5);
//            }
//
//            // 청구 내역 출력
//            result +=
//                    String.format(
//                            "%15s:%12s%4s석\n",
//                            play.getName(), format.format(thisAmount / 100), perf.getAudience());
//            totalAmount += thisAmount;
//        }
//
//        result += String.format("총액: %s\n", format.format(totalAmount / 100));
//        result += String.format("적립 포인트: %s점\n", volumeCredits);
//        return result;
//    }
//
//    private static int amountFor(Invoice.Performance perf, Play play) throws Exception {
//        int thisAmount = 0;
//
//        switch (play.getType()) {
//            case "tragedy":
//                thisAmount = 40000;
//                if (perf.getAudience() > 30) {
//                    thisAmount += 1000 * (perf.getAudience() - 30);
//                }
//                break;
//            case "comedy":
//                thisAmount = 30000;
//                if (perf.getAudience() > 20) {
//                    thisAmount += 10000 + 500 * (perf.getAudience() - 20);
//                }
//                thisAmount += 300 * perf.getAudience();
//                break;
//            default:
//                throw new Exception(String.format("알 수 없는 장르: %s", play.getType()));
//        }
//        return thisAmount;
//    }

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("file.separator"));
        Play[] plays = readJson("resource/plays.json", Play[].class);
        Invoice[] invoices = readJson("resource/invoices.json", Invoice[].class);

        Statement statement = new Statement(invoices[0], plays);
        String plainText = statement.readPlainText();

        System.out.println(plainText);
    }
}
