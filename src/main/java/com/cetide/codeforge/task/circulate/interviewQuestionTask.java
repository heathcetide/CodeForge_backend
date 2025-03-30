package com.cetide.codeforge.task.circulate;

import com.cetide.codeforge.model.entity.InterviewQuestion;
import com.cetide.codeforge.service.InterviewQuestionsService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
public class interviewQuestionTask {

    private final JavaMailSender mailSender;


    private final SpringTemplateEngine templateEngine;


    private final InterviewQuestionsService interviewQuestionService;  // 获取面试题的服务

    public interviewQuestionTask(JavaMailSender mailSender, SpringTemplateEngine templateEngine, InterviewQuestionsService interviewQuestionService) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.interviewQuestionService = interviewQuestionService;
    }


    // 每天早上8点发送面试题
    @Scheduled(cron = "0 0 6 * * ?")
    public void sendInterviewQuestionsAt8am() {
        sendInterviewQuestions();
    }

    // 每天中午12点发送面试题
    @Scheduled(cron = "0 0 12 * * ?")
    public void sendInterviewQuestionsAt12pm() {
        sendInterviewQuestions();
    }

    // 每天晚上9点发送面试题
    @Scheduled(cron = "0 0 21 * * ?")
    public void sendInterviewQuestionsAt9pm() {
        sendInterviewQuestions();
    }

    // 发送面试题给用户的方法
    private void sendInterviewQuestions() {
        List<InterviewQuestion> questions = interviewQuestionService.getRandomInterviewQuestions();
        String emailContent = generateEmailContent(questions);  // 生成邮件内容
        try {
            // 发送邮件
            sendMessage("19511899044@163.com", "每日面试题", emailContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // 生成邮件内容
    private String generateEmailContent(List<InterviewQuestion> questions) {
        Context context = new Context();
        context.setVariable("questions", questions);  // 将面试题列表传入模板

        // 使用 Thymeleaf 渲染模板生成邮件内容
        return templateEngine.process("daily-interview-questions.html", context);
    }

    // 发送邮件的方法
    private void sendMessage(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        message.setFrom("heath-cetide@zohomail.com");
        String officialFrom = "CodeForge <heath-cetide@zohomail.com>";
        helper.setFrom(new InternetAddress(officialFrom));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(message);
    }
}
