package com.intranet.springboot.util;


import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CsvUtil {

    private static final String COMMA = ",";
    private static final String DEFAULT_SEPARATOR = COMMA;
    private static final String DOUBLE_QUOTES = "\"";
    private static final String EMBEDDED_DOUBLE_QUOTES = "\"\"";
    private static final String NEW_LINE_UNIX = "\n";
    private static final String NEW_LINE_WINDOWS = "\r\n";

    public String convertList(List<String[]> lines) {
        StringBuffer buffer = new StringBuffer();
        lines.forEach(line->{
            buffer.append(convertToCsvFormat(line)).append(System.lineSeparator());
        });
        return buffer.toString();
    }

    public String convertToCsvFormat(final String[] line) {
        return convertToCsvFormat(line, DEFAULT_SEPARATOR);
    }

    public String convertToCsvFormat(final String[] line, final String separator) {
        return convertToCsvFormat(line, separator, true);
    }

    private String convertToCsvFormat(
            final String[] line,
            final String separator,
            final boolean quote) {

        return Stream.of(line)
                .map(l -> formatCsvField(l, quote))
                .collect(Collectors.joining(separator));

    }

    private String formatCsvField(final String field, final boolean quote) {
        String result = field==null?"":field;
        if (result.contains(COMMA)
                || result.contains(DOUBLE_QUOTES)
                || result.contains(NEW_LINE_UNIX)
                || result.contains(NEW_LINE_WINDOWS)) {

            result = result.replace(DOUBLE_QUOTES, EMBEDDED_DOUBLE_QUOTES);
            result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;
        } else {
            if (quote) {
                result = DOUBLE_QUOTES + result + DOUBLE_QUOTES;
            }
        }
        return result;
    }

    public void fileResponse(HttpServletResponse response, String applications, String fileName, String ContentType) throws IOException {
        OutputStream os = response.getOutputStream();
        try {
            response.setHeader("Content-disposition", "attachment; filename="+fileName+".csv");
            response.setHeader("Content-Type", ContentType);
            IOUtils.write(applications, os, StandardCharsets.UTF_8);
            os.flush();
        } catch (Throwable e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value() ,e.getMessage());
        } finally {
            IOUtils.closeQuietly(os);
        }
    }
}

