package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

public class FileUtils {
    public static synchronized void writeExcelReportFile(String reportName, List<String> columnName, List<String[]> values, Date date, String reporter) {
        reportName.toUpperCase();
        columnName.add(0, "STT");
        String colspanGlobal = columnName.size() * 2 + "";
        File file = new File("report" + System.currentTimeMillis() + ".xls");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.getName().contains(".xlsx")) throw new IllegalArgumentException("xlsx is not supported");
            else if (file.getName().contains(".xls")) {
                String result = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" " +
                    "xmlns:x=\"urn:schemas-microsoft-com:office:excel\"  " +
                    "xmlns=\"http://www.w3.org/TR/REC-html40\"><head><!--[if gte mso 9]>" +
                    "<xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>" +
                    "rpt_foreign_prepaid_control_new.xls</x:Name><x:WorksheetOptions>" +
                    "<x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>" +
                    "</x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->" +
                    "<meta http-equiv=\"content-type\" content=\"text/plain;" +
                    " charset=UTF-8\"/><style>table tr th {mso-number-format:\\@;}</style></head><body><table>";
                result += "<tr><th align='center' colspan='" + colspanGlobal + "'>" + "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM" + "</th></tr>";
                result += "<tr><th align='center' colspan='" + colspanGlobal + "'>" + "Độc lập - Tự do - Hạnh phúc" + "</th></tr>";
                result += "<tr><th align='center' colspan='" + colspanGlobal + "'>" + "___***___" + "</th></tr>";
                result += "<tr><th align='center' colspan='" + colspanGlobal + "'>" + reportName + "</th></tr>";
                result += "<tr><th align='center' colspan='" + colspanGlobal + "'>" + "Tháng " + getMonthAndYear(date) + "</th></tr><tr><th colspan='" + colspanGlobal + "></th></tr>";
                result += "<tr><th colspan='" + colspanGlobal + "'></th></tr>";
                result += "<thead>";
                result += "<tr>";
                for (String s : columnName) result += "<th colspan='2' style=\"border: 0.5pt solid;\">" + s + "</th>";
                result += "</tr></thead>";
                result += "<tbody>";
                int i = 1;
                for (String[] s : values) {
                    result += "<tr>";
                    result += "<td colspan='2' align=\"center\" style=\"border: 0.5pt solid;\">" + i + "</td>";
                    for (String y : s) {
                        result += "<td colspan='2' align=\"center\" style=\"border: 0.5pt solid;\">" + y.toString() + "</td>";
                    }
                    result += "</tr>";
                    i++;
                }
                result += "<tr></tr>";
                result += "</tbody></table></body></html>";
                write(file, result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getMonthAndYear(Date date) {
        return (date.getMonth() + 1) + "/" + date.getYear();
    }

    private static void write(File file, String result) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(result.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
