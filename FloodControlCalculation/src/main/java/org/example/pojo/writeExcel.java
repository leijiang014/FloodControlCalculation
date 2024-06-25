package org.example.pojo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class writeExcel {
    public static void writeExcel(List<Reservoir> reservoirs) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        for (Reservoir reservoir : reservoirs) {
            XSSFSheet sheet = workbook.createSheet(reservoir.name);

            // 创建表头
            String[] headers = {"Inflow", "Volume", "WaterLevel", "Outflow"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 填充数据
            int dataSize = reservoir.inflow.length; // 假设所有数组长度相同
            for (int i = 0; i < dataSize; i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(reservoir.inflow[i]);
                row.createCell(1).setCellValue(reservoir.reservoirVolume[i]);
                row.createCell(2).setCellValue(reservoir.reservoirWaterLevel[i]);
                row.createCell(3).setCellValue(reservoir.outflow[i]);
            }
        }

        // 写入到文件
        try (FileOutputStream fileOut = new FileOutputStream("FloodControlResult.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭工作簿
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
