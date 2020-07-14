package com.testutils.testreporter;

import org.testng.*;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ToDo : Screenshots of pages tested, and further fine tuning the report
 * class to implement Customized reporting
 */
public class TestNgCustomReporter implements IReporter {
    private int row;
    private Integer testIndex;
    private int methodIndex;
    private String title = "TestNG Customized Report";
    private String filename = "test-customized-report.html";
    private PrintWriter printWriter;

    public void generateReport(List<XmlSuite> list, List<ISuite> suiteList, String s) {
        try {
            new File(s).mkdirs();
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(s, filename))));
        } catch (IOException e) {
            System.err.println("Unable to create report file");
            e.printStackTrace();
            return;
        }
        startHtml(printWriter);
        writeReportTitle(title);
        generateSuiteSummaryReport(suiteList);
        generateMethodSummaryReport(suiteList);
        generateMethodDetailReport(suiteList);
        endHTML(printWriter);
        printWriter.flush();
        printWriter.close();
    }

    private void startHtml(PrintWriter writer) {
        writer.println("<!DOCTYPE html PUBLIC \" -//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        writer.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        writer.println("<head>");
        writer.println("<title>TestNG Report</title>");
        writer.println("<style type=\"text/css\">");
        writer.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        writer.println("td,th {border:1px solid #009;padding:.25em .5em}");
        writer.println(".result th {vertical-align:bottom}");
        writer.println(".param th {padding-left:1em;padding-right:1em}");
        writer.println(".param td {padding-left:.5em;padding-right:2em}");
        writer.println(".stripe td,.stripe th {background-color: #E6EBF9}");
        writer.println(".numi,.numi_attn {text-align:right}");
        writer.println(".total td {font-weight:bold}");
        writer.println(".passedodd td {background-color: #0A0}");
        writer.println(".passedeven td {background-color: #3F3}");
        writer.println(".skippedodd td {background-color: #CCC}");
        writer.println(".skippedodd td {background-color: #DDD}");
        writer.println(".failedodd td,.numi_attn {background-color: #F33}");
        writer.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
        writer.println(".stacktrace {white-space:pre;font-family:monospace}");
        writer.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        writer.println("</style>");
        writer.println("</head>");
        writer.println("<body>");
    }

    private void writeReportTitle(String ttl ){
        printWriter.print("<center><h1>" + title + " - " + getDateAsString() + "</h1></center>");
    }

    public void generateSuiteSummaryReport(List<ISuite> suites){
        tableStart("testOverview", null);
        printWriter.print("<tr>");
        tableColumnStart("Test");
        tableColumnStart("Methods<br/>Passed");
        tableColumnStart("# skipped");
        tableColumnStart("# failed");
        tableColumnStart("Browser");
        tableColumnStart("Start<br/>Time");
        tableColumnStart("End<br/>Time");
        tableColumnStart("Total<br/>Time(hh:mm:ss)");
        tableColumnStart("Included<br/>Groups");
        tableColumnStart("Excluded<br/>Groups");

        printWriter.println("</tr>");
        NumberFormat formatter = new DecimalFormat("#,##0.0");
        int qty_tests = 0;
        int qty_pass_m = 0;
        int qty_pass_s = 0;
        int qty_skip = 0;
        long time_start = Long.MAX_VALUE;
        int qty_fail = 0;
        long time_end = Long.MIN_VALUE;
        testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() >= 1) {
                titleRow(suite.getName(), 10);
            }
            Map<String, ISuiteResult> tests = suite.getResults();
            for (ISuiteResult r : tests.values()) {
                qty_tests += 1;
                ITestContext overview = r.getTestContext();

                startSummaryRow(overview.getName());
                int q = getMethodSet(overview.getPassedTests(), suite).size();
                qty_pass_m += q;
                summaryCell(q, Integer.MAX_VALUE);
                q = getMethodSet(overview.getSkippedTests(), suite).size();
                qty_skip += q;
                summaryCell(q, 0);
                q = getMethodSet(overview.getFailedTests(), suite).size();
                qty_fail += q;
                summaryCell(q, 0);

                // Write OS and Browser
                summaryCell(suite.getParameter("browserType"), true);
                printWriter.println("</td>");

                SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss");
                summaryCell(summaryFormat.format(overview.getStartDate()),true);
                printWriter.println("</td>");

                summaryCell(summaryFormat.format(overview.getEndDate()),true);
                printWriter.println("</td>");

                time_start = Math.min(overview.getStartDate().getTime(), time_start);
                time_end = Math.max(overview.getEndDate().getTime(), time_end);
                summaryCell(timeConversion((overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000), true);

                summaryCell(overview.getIncludedGroups());
                summaryCell(overview.getExcludedGroups());
                printWriter.println("</tr>");
                testIndex++;
            }
        }
        if (qty_tests > 1) {
            printWriter.println("<tr class=\"total\"><td>Total</td>");
            summaryCell(qty_pass_m, Integer.MAX_VALUE);
            summaryCell(qty_skip, 0);
            summaryCell(qty_fail, 0);
            summaryCell(" ", true);
            summaryCell(" ", true);
            summaryCell(" ", true);
            summaryCell(timeConversion(((time_end - time_start) / 1000)), true);
            printWriter.println("<td colspan=\"3\">&nbsp;</td></tr>");
        }
        printWriter.println("</table>");

    }

    private Collection<ITestNGMethod> getMethodSet(IResultMap passedTests, ISuite suite) {
        List<IInvokedMethod> r = Lists.newArrayList();
        List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
        for (IInvokedMethod im : invokedMethods) {
            if (passedTests.getAllMethods().contains(im.getTestMethod())) {
                r.add(im);
            }
        }

        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
        Collections.sort(r,new TestSorter());
        List<ITestNGMethod> result = Lists.newArrayList();

        // Add all the invoked methods
        for (IInvokedMethod m : r) {
            for (ITestNGMethod temp : result) {
                if (!temp.equals(m.getTestMethod()))
                    result.add(m.getTestMethod());
            }
        }

        // Add all the methods that weren't invoked (e.g. skipped) that we
        // haven't added yet
        Collection<ITestNGMethod> allMethodsCollection=passedTests.getAllMethods();
        List<ITestNGMethod> allMethods=new ArrayList<ITestNGMethod>(allMethodsCollection);
        Collections.sort(allMethods, new TestMethodSorter());

        for (ITestNGMethod m : allMethods) {
            if (!result.contains(m)) {
                result.add(m);
            }
        }
        return result;
    }

    protected void endHTML (PrintWriter writer){
        writer.println("<center> TestNG Report </center>");
        writer.println("</body></html>");
    }

    private void tableColumnStart(String label) {
        printWriter.print("<th>" + label + "</th>");
    }

    private void tableStart(String cssclass, String id) {
        printWriter.println("<table cellspacing=\"0\" cellpadding=\"0\""+ (cssclass != null ?  "class=\"" +cssclass+ "\""
                : "style=\"padding-bottom:2em\"")
                + (id != null ? "id=\"" + id + "\""  : "") +">");
        row = 0;
    }

    private void titleRow(String label, int cq) {
        titleRow(label, cq, null);
    }

    private void titleRow(String label, int cq, String id) {
        printWriter.print("<tr");
        if (id != null) {
            printWriter.print(" id=\"" + id + "\"");
        }
        printWriter.println("><th colspan=\"" + cq + "\">" + label + "</th></tr>");
        row = 0;
    }

    private void summaryCell(String[] val) {
        StringBuffer b = new StringBuffer();
        for (String v : val) {
            b.append(v + " ");
        }
        summaryCell(b.toString(), true);
    }

    private void summaryCell(String v, boolean isgood) {
        printWriter.print("<td class=\"numi" + (isgood ? "" : "_attn") + "\">" + v
                + "</td>");
    }

    private void summaryCell(int v, int maxexpected) {
        summaryCell(String.valueOf(v), v <= maxexpected);
    }

    private void startSummaryRow(String label) {
        row += 1;
        printWriter.print("<tr"
                + (row % 2 == 0 ? " class=\"stripe\"" : "")
        + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
                + testIndex + "\"><b>" + label + "</b></a>" + "</td>");

    }

    private String getDateAsString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String timeConversion(long seconds) {

        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
        seconds -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int)seconds);
    }

    private String prefixZeroToDigit(int num){
        int number=num;
        if(number<=9){
            String sNumber="0"+number;
            return sNumber;
        }
        else
            return ""+number;

    }

    protected void generateMethodSummaryReport(List<ISuite> suites) {
        methodIndex = 0;
        startResultSummaryTable("methodOverview");
        int testIndex = 1;
        for (ISuite suite : suites) {
            if (suites.size() >= 1) {
                titleRow(suite.getName(), 5);
            }

            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                String testName = testContext.getName();
                testIndex = testIndex;
                resultSummary(suite, testContext.getFailedConfigurations(), testName, "failed", " (configuration methods)");
                resultSummary(suite, testContext.getFailedTests(), testName, "failed", "");
                resultSummary(suite, testContext.getSkippedConfigurations(), testName, "skipped", " (configuration methods)");
                resultSummary(suite, testContext.getSkippedTests(), testName, "skipped", "");
                resultSummary(suite, testContext.getPassedTests(), testName, "passed", "");
                testIndex++;
            }
        }
        printWriter.println("</table>");
    }

    private void startResultSummaryTable(String methodOverview) {
        tableStart(methodOverview, "summary");
        printWriter.println("<tr><th>Class</th>"
                + "<th>Method</th><th>Exception Info</th><th>Start Time </th><th>Execution Time<br/>(hh:mm:ss)</th></tr>");
        row = 0;
    }

    private void resultSummary(ISuite suite, IResultMap tests, String testname,
                               String style, String details) {

        if (tests.getAllResults().size() > 0) {
            StringBuffer buff = new StringBuffer();
            String lastClassName = "";
            int mq = 0;
            int cq = 0;
            for (ITestNGMethod method : getMethodSet(tests, suite)) {
                row += 1;
                methodIndex += 1;
                ITestClass testClass = method.getTestClass();
                String className = testClass.getName();
                if (mq == 0) {
                    String id = (testIndex == null ? null : "t"
                            + Integer.toString(testIndex));
                    titleRow(testname + " &#8212; " + style + details, 5, id);
                    testIndex = null;
                }
                if (!className.equalsIgnoreCase(lastClassName)) {
                    if (mq > 0) {
                        cq += 1;
                        printWriter.print("<tr class=\"" + style
                                + (cq % 2 == 0 ? "even" : "odd") + "\">"
                                + "<td");
                        if (mq > 1) {
                            printWriter.print(" rowspan=\"" + mq + "\"");
                        }
                        printWriter.println(">" + lastClassName + "</td>" + buff);
                    }
                    mq = 0;
                    buff.setLength(0);
                    lastClassName = className;
                }
                Set<ITestResult> resultSet = tests.getResults(method);
                long end = Long.MIN_VALUE;
                long start = Long.MAX_VALUE;
                long startMS=0;
                String firstLine="";

                for (ITestResult testResult : tests.getResults(method)) {
                    if (testResult.getEndMillis() > end) {
                        end = testResult.getEndMillis()/1000;
                    }
                    if (testResult.getStartMillis() < start) {
                        startMS = testResult.getStartMillis();
                        start =startMS/1000;
                    }

                    Throwable exception=testResult.getThrowable();
                    boolean hasThrowable = exception != null;
                    if(hasThrowable){
                        String str = Utils.stackTrace(exception, true)[0];
                        Scanner scanner = new Scanner(str);
                        firstLine = scanner.nextLine();
                    }
                }
                DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(startMS);

                mq += 1;
                if (mq > 1) {
                    buff.append("<tr class=\"" + style
                            + (cq % 2 == 0 ? "odd" : "even") + "\">");
                }
                String description = method.getDescription();
                String testInstanceName = resultSet
                        .toArray(new ITestResult[] {})[0].getTestName();
                buff.append("<td><a href=\"#m"
                        + methodIndex
                        + "\">"
                        + qualifiedName(method)
                        + " "
                        + (description != null && description.length() > 0 ? "(\""
                        + description + "\")"
								: "")
                + "</a>"
                        + (null == testInstanceName ? "" : "<br>("
                        + testInstanceName + ")") + "</td>"
                        + "<td class=\"numi\" style=\"text-align:left;padding-right:2em\">" + firstLine+"<br/></td>"
                        + "<td style=\"text-align:right\">" + formatter.format(calendar.getTime()) + "</td>" + "<td class=\"numi\">"
                        + timeConversion(end - start) + "</td>" + "</tr>");

            }
            if (mq > 0) {
                cq += 1;
                printWriter.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
                if (mq > 1) {
                    printWriter.print(" rowspan=\"" + mq + "\"");
                }
                printWriter.println(">" + lastClassName + "</td>" + buff);
            }
        }
    }

    protected void generateMethodDetailReport(List<ISuite> suites) {
        methodIndex = 0;
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> r = suite.getResults();
            for (ISuiteResult r2 : r.values()) {
                ITestContext testContext = r2.getTestContext();
                if (r.values().size() > 0) {
                    printWriter.println("<h1>" + testContext.getName() + "</h1>");
                }
                resultDetail(testContext.getFailedConfigurations());
                resultDetail(testContext.getFailedTests());
                resultDetail(testContext.getSkippedConfigurations());
                resultDetail(testContext.getSkippedTests());
                resultDetail(testContext.getPassedTests());
            }
        }
    }

    private void resultDetail(IResultMap tests) {
        Set<ITestResult> testResults=tests.getAllResults();
        List<ITestResult> testResultsList = new ArrayList<ITestResult>(testResults);
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        System.setProperty("java.util.Collections.useLegacyMergeSort", "true");
        Collections.sort(testResultsList, new TestResultsSorter());
        for (ITestResult result : testResultsList) {
            ITestNGMethod method = result.getMethod();
            methodIndex++;
            String cname = method.getTestClass().getName();
            printWriter.println("<h2 id=\"m" + methodIndex + "\">" + cname + ":"
                    + method.getMethodName() + "</h2>");
            Set<ITestResult> resultSet = tests.getResults(method);
            generateResult(result, method, resultSet.size());
            printWriter.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");

        }
    }

    private void generateResult(ITestResult ans, ITestNGMethod method,
                                int resultSetSize) {
        Object[] parameters = ans.getParameters();
        boolean hasParameters = parameters != null && parameters.length > 0;
        if (hasParameters) {
            tableStart("result", null);
            printWriter.print("<tr class=\"param\">");
            for (int x = 1; x <= parameters.length; x++) {
                printWriter.print("<th>Param." + x + "</th>");
            }
            printWriter.println("</tr>");
            printWriter.print("<tr class=\"param stripe\">");
            for (Object p : parameters) {
                printWriter.println("<td>" + Utils.escapeHtml(Utils.toString(p, p.getClass()))
                        + "</td>");
            }
            printWriter.println("</tr>");
        }
        List<String> msgs = Reporter.getOutput(ans);
        boolean hasReporterOutput = msgs.size() > 0;
        Throwable exception = ans.getThrowable();
        boolean hasThrowable = exception != null;
        if (hasReporterOutput || hasThrowable) {
            if (hasParameters) {
                printWriter.print("<tr><td");
                if (parameters.length > 1) {
                    printWriter.print(" colspan=\"" + parameters.length + "\"");
                }
                printWriter.println(">");
            } else {
                printWriter.println("<div>");
            }
            if (hasReporterOutput) {
                if (hasThrowable) {
                    printWriter.println("<h3>Test Messages</h3>");
                }
                for (String line : msgs) {
                    printWriter.println(line + "<br/>");
                }
            }
            if (hasThrowable) {
                boolean wantsMinimalOutput = ans.getStatus() == ITestResult.SUCCESS;
                if (hasReporterOutput) {
                    printWriter.println("<h3>"
                            + (wantsMinimalOutput ? "Expected Exception"
                            : "Failure") + "</h3>");
                }
//                generateExceptionReport(exception, method);
            }
            if (hasParameters) {
                printWriter.println("</td></tr>");
            } else {
                printWriter.println("</div>");
            }
        }
        if (hasParameters) {
            printWriter.println("</table>");
        }
    }

    private String qualifiedName(ITestNGMethod method) {
        StringBuilder addon = new StringBuilder();
        String[] groups = method.getGroups();
        int length = groups.length;
        if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
            addon.append("(");
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    addon.append(", ");
                }
                addon.append(groups[i]);
            }
            addon.append(")");
        }

        return "<b>" + method.getMethodName() + "</b> " + addon;
    }

    private class TestSorter implements Comparator<IInvokedMethod>{


        public int compare(IInvokedMethod o1, IInvokedMethod o2) {
            return o1.getTestMethod().getTestClass().getName().compareTo(o2.getTestMethod().getTestClass().getName());
        }
    }

    private class TestMethodSorter implements Comparator<ITestNGMethod> {
        public int compare(ITestNGMethod obj1, ITestNGMethod obj2) {
            int r = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
            if (r == 0) {
                r = obj1.getMethodName().compareTo(obj2.getMethodName());
            }
            return r;
        }
    }

    private class TestResultsSorter implements Comparator<ITestResult> {
        public int compare(ITestResult obj1, ITestResult obj2) {
            int result = obj1.getTestClass().getName().compareTo(obj2.getTestClass().getName());
            if (result == 0) {
                result = obj1.getMethod().getMethodName().compareTo(obj2.getMethod().getMethodName());
            }
            return result;
        }
    }

}
