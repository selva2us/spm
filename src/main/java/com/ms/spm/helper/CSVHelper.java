package com.ms.spm.helper;

import com.ms.spm.entity.Goods;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id", "GoodsName", "Price", "Description","TotalStock" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Goods> csvToGoods(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Goods> goods = new ArrayList<Goods>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Goods tutorial = new Goods(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("GoodsName"),
                        Long.parseLong(csvRecord.get("Price")),
                        csvRecord.get("Description"),
                        Long.parseLong(csvRecord.get("TotalStock"))
                );

                goods.add(tutorial);
            }

            return goods;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
