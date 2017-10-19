package Homework_lesson_17_GoodCode.Source;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {

    private Connection connection;

    public SalaryHtmlReportNotifier ( Connection databaseConnection ) {
        this.connection = databaseConnection;
    }

    public void SendHtmlSalaryReport ( String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients ) {
        try {
            ResultSet dataFromBd = getDataFromBd (departmentId, dateFrom, dateTo);
            String htmlReport = generateHtmlReport (dataFromBd);
//            JavaMailSenderImpl mailSender = new JavaMailSenderImpl ();
//            mailSender.setHost ("mail.google.com");
//            MimeMessage mimeMessage = mailSender.createMimeMessage ();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper (mimeMessage, true);
//            mimeMessageHelper.setTo (recipients);
//            mimeMessageHelper.setText (htmlReport, true);
//            mimeMessageHelper.setSubject ("Monthly department salary report");
//            mailSender.send (mimeMessage);
        } catch (SQLException e) {
            e.printStackTrace ();
        } /*catch (MessagingException e) {
            e.printStackTrace ();
        }*/
    }

    /**
     *
     * @param dataFromBd data, from the department of the period of time "dateFrom" to "dateTo".
     * @return HTML-page that contains report created via tables.
     * @throws SQLException
     */
    private String generateHtmlReport ( ResultSet dataFromBd ) throws SQLException {
        StringBuilder resultingHtml = new StringBuilder ();
        resultingHtml.append ("<html>" +
                                "<body>" +
                                    "<table><tr>" +
                                        "<td>Employee</td>" +
                                        "<td>Salary</td>" +
                                    "</tr>");
        double totalSalary = 0;
        while (dataFromBd.next ()) {
            resultingHtml.append ("<tr>");
            resultingHtml.append ("<td>")
                         .append (dataFromBd.getString ("emp_name"))
                         .append ("</td>");
            resultingHtml.append ("<td>")
                         .append (dataFromBd.getDouble ("salary"))
                         .append ("</td>");
            resultingHtml.append ("</tr>");
            totalSalary += dataFromBd.getDouble ("salary");
        }
        resultingHtml.append ("<tr>" +
                                "<td>Total</td>" +
                                "<td>")
                     .append (totalSalary)
                     .append ("</td>" +
                             "</tr>");
        resultingHtml.append ("</table></body></html>");
        return resultingHtml.toString ();
    }

    /**
     *
     * @param departmentId - id of department that produces date.
     * @param dateFrom
     * @param dateTo
     * @return set of data from departments BD.
     * @throws SQLException
     */
    private ResultSet getDataFromBd ( String departmentId, LocalDate dateFrom, LocalDate dateTo ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement (
                "select emp.id as emp_id, emp.name as amp_name," +
                " sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ?" +
                "and sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
        preparedStatement.setString (0, departmentId);
        preparedStatement.setDate (1, new java.sql.Date (dateFrom.toEpochDay ()));
        preparedStatement.setDate (2, new java.sql.Date (dateTo.toEpochDay ()));
        return preparedStatement.executeQuery ();
    }
}
